package com.example.onlineshoping;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    EditText editTextSearch;
    Spinner spinnerCategory;
    RadioGroup radioGroupPrice;
    CheckBox checkboxAvailable;
    Switch switchSort;
    Button buttonSearch;
    ListView listViewProducts;

    SharedPrefManager sharedPrefManager;
    ArrayList<Product> allProducts;
    ArrayList<Product> filteredProducts;
    ProductAdapter adapter;

    String[] categories = {"All", "Electronics", "Clothing", "Books"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI
        editTextSearch = findViewById(R.id.editTextSearch);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        radioGroupPrice = findViewById(R.id.radioGroupPrice);
        checkboxAvailable = findViewById(R.id.checkboxAvailable);
        switchSort = findViewById(R.id.switchSort);
        buttonSearch = findViewById(R.id.buttonSearch);
        listViewProducts = findViewById(R.id.listViewProducts);

        sharedPrefManager = new SharedPrefManager(this);

        // Load or initialize product list
        allProducts = sharedPrefManager.getProductList();
        if (allProducts == null || allProducts.isEmpty()) {
            allProducts = getInitialProducts();
            sharedPrefManager.saveProductList(allProducts);
        }

        // Setup Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        // Initial list load
        filteredProducts = new ArrayList<>(allProducts);
        adapter = new ProductAdapter(this, filteredProducts);
        listViewProducts.setAdapter(adapter);

        // Button click
        buttonSearch.setOnClickListener(v -> applyFilters());

        Button buttonGoToCart = findViewById(R.id.buttonGoToCart);
        buttonGoToCart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
        });

    }

    private void applyFilters() {
        String searchText = editTextSearch.getText().toString().toLowerCase();
        String selectedCategory = spinnerCategory.getSelectedItem().toString();
        boolean onlyAvailable = checkboxAvailable.isChecked();
        boolean sortByPrice = switchSort.isChecked();
        int selectedPrice = radioGroupPrice.getCheckedRadioButtonId();

        filteredProducts.clear();

        for (Product p : allProducts) {
            boolean matchesSearch = p.getName().toLowerCase().contains(searchText);
            boolean matchesCategory = selectedCategory.equals("All") || p.getCategory().equalsIgnoreCase(selectedCategory);
            boolean matchesAvailability = !onlyAvailable || p.getQuantity() > 0;

            if (matchesSearch && matchesCategory && matchesAvailability) {
                filteredProducts.add(p);
            }
        }

        // Sort by price if switch is on
        if (sortByPrice) {
            Collections.sort(filteredProducts, (a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        }

        // Reverse if "High" radio selected
        if (selectedPrice == R.id.radioHigh) {
            Collections.reverse(filteredProducts);
        }

        adapter.notifyDataSetChanged();
    }

    private ArrayList<Product> getInitialProducts() {
        ArrayList<Product> list = new ArrayList<>();
        list.add(new Product("Laptop", "Electronics", 555.99, 5));
        list.add(new Product("T-Shirt", "Clothing", 55.99, 10));
        list.add(new Product("Book - Android Dev", "Books", 22.99, 3));
        list.add(new Product("phones", "Electronics", 99.99, 7));
        list.add(new Product("Jeans", "Clothing", 67.99, 4));
        return list;
    }
}

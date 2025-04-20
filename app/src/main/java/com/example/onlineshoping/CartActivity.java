package com.example.onlineshoping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    ListView listViewCart;
    Button buttonCheckout;

    SharedPrefManager sharedPrefManager;
    ArrayList<Product> cartList;
    ArrayList<Product> productList;
    ArrayAdapter<Product> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        listViewCart = findViewById(R.id.listViewCart);
        buttonCheckout = findViewById(R.id.buttonCheckout);

        sharedPrefManager = new SharedPrefManager(this);
        cartList = sharedPrefManager.getCart();
        productList = sharedPrefManager.getProductList();

        if (cartList == null) cartList = new ArrayList<>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cartList);
        listViewCart.setAdapter(adapter);

        buttonCheckout.setOnClickListener(v -> {
            for (Product cartItem : cartList) {
                for (Product product : productList) {
                    if (cartItem.getName().equals(product.getName())) {
                        product.setQuantity(product.getQuantity() - 1);
                        break;
                    }
                }
            }

            // Save updated product list and clear cart
            sharedPrefManager.saveProductList(productList);
            sharedPrefManager.saveCart(new ArrayList<>());

            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_LONG).show();
            finish(); // Close cart activity
        });
    }
}


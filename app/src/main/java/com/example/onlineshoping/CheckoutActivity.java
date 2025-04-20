package com.example.onlineshoping;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {

    ListView listViewCheckout;
    TextView totalPriceValue;
    Button buttonCheckoutOrder;

    SharedPrefManager sharedPrefManager;
    ArrayList<Product> cartList;
    CartAdapter cartAdapter;
    double totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Initialize UI components
        listViewCheckout = findViewById(R.id.listViewCheckout);
        totalPriceValue = findViewById(R.id.totalPriceValue);
        buttonCheckoutOrder = findViewById(R.id.buttonCheckoutOrder);

        // Initialize SharedPrefManager to get cart data
        sharedPrefManager = new SharedPrefManager(this);
        cartList = sharedPrefManager.getCart();

        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        // Calculate the total price
        calculateTotalPrice();

        // Set up the CartAdapter
        cartAdapter = new CartAdapter(this, cartList);
        listViewCheckout.setAdapter(cartAdapter);

        // Handle Checkout Order Button Click
        buttonCheckoutOrder.setOnClickListener(v -> confirmOrder());
    }

    // Calculate the total price of the items in the cart
    private void calculateTotalPrice() {
        totalPrice = 0;
        for (Product product : cartList) {
            totalPrice += product.getPrice() * product.getQuantity();
        }
        totalPriceValue.setText(String.format("%.2f", totalPrice));
    }

    // Confirm the order and clear the cart
    private void confirmOrder() {
        // Deduct quantities from the products and update stock
        ArrayList<Product> productList = sharedPrefManager.getProductList();
        for (Product cartItem : cartList) {
            for (Product product : productList) {
                if (cartItem.getName().equals(product.getName())) {
                    product.setQuantity(product.getQuantity() - cartItem.getQuantity());
                    break;
                }
            }
        }

        // Save updated product list
        sharedPrefManager.saveProductList(productList);

        // Clear the cart and save it back
        sharedPrefManager.saveCart(new ArrayList<>());

        Toast.makeText(this, "Order Confirmed! Thank you for shopping!", Toast.LENGTH_LONG).show();
        finish(); // Close the CheckoutActivity and return to the main activity
    }
}

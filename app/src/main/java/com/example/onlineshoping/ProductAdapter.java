package com.example.onlineshoping;

import android.content.Context;
import android.view.*;
import android.widget.*;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> list) {
        super(context, 0, list);
        this.context = context;
        this.products = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Product p = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
        }


        TextView name = convertView.findViewById(R.id.textName);
        TextView cat = convertView.findViewById(R.id.textCategory);
        TextView price = convertView.findViewById(R.id.textPrice);
        TextView qty = convertView.findViewById(R.id.textQuantity);
        Button add = convertView.findViewById(R.id.buttonAddToCart);

        name.setText("Name: " + p.getName());
        cat.setText("Category: " + p.getCategory());
        price.setText("Price: $" + p.getPrice());
        qty.setText("Available: " + p.getQuantity());

        add.setOnClickListener(v -> {
            if (p.getQuantity() > 0) {
                SharedPrefManager sp = new SharedPrefManager(context);
                ArrayList<Product> cart = sp.getCart();
                if (cart == null) cart = new ArrayList<>();
                cart.add(p);
                sp.saveCart(cart);
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Out of stock", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
package com.example.onlineshoping;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Product> cartList;
    private LayoutInflater inflater;

    public CartAdapter(Context context, ArrayList<Product> cartList) {
        this.context = context;
        this.cartList = cartList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Product product = cartList.get(position);

        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        text1.setText(product.getName() + " (x" + product.getQuantity() + ")");
        text2.setText("$" + product.getPrice() * product.getQuantity());

        return convertView;
    }
}


package com.example.mamaoliech;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    MyDatabase.Product [] products;
    public ProductAdapter(MyDatabase.Product[] products) {
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.product.setText(products[position].product_name);
        holder.quantity.setText(String.valueOf(products[position].stock_level));
        holder.price.setText("Sh. "+String.valueOf(products[position].sp));
    }

    @Override
    public int getItemCount() {
        return products.length;
    }


    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView product, quantity, price;
        private MaterialCardView cardView;
        private MyViewHolder(View v) {
            super(v);
            product = v.findViewById(R.id.tvProductSold);
            quantity = v.findViewById(R.id.tvQuantitySold);
            price = v.findViewById(R.id.tvPriceSold);
            cardView = v.findViewById(R.id.cardView);
        }
    }
}

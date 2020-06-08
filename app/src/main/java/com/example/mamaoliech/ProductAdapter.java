package com.example.mamaoliech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

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
                .inflate(R.layout.stock_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        System.out.println("product is "+products[position].product_name);
        System.out.println("Stock level is "+products[position].stock_level);
        System.out.println("Buying Price  is "+products[position].bp);
        System.out.println("Selling Price is "+products[position].sp);

        holder.product.setText(products[position].product_name);
        holder.stockLevel.setText(String.valueOf(products[position].stock_level));
        holder.bpPrice.setText("Sh. "+String.valueOf(products[position].bp));
        holder.spPrice.setText("Sh. "+String.valueOf(products[position].sp));
        holder.profit.setText("Sh. "+String.valueOf(products[position].sp-products[position].bp));
    }

    @Override
    public int getItemCount() {
        return products.length;
    }


    protected static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView product, stockLevel, bpPrice, spPrice, profit;
        private MaterialCardView cardView;
        private MyViewHolder(View v) {
            super(v);
            product = v.findViewById(R.id.tvProductStock);
            stockLevel = v.findViewById(R.id.tvQuantityStock);
            bpPrice = v.findViewById(R.id.tvBPStock);
            spPrice = v.findViewById(R.id.tvSPStock);
            profit = v.findViewById(R.id.tvProfitMargin);
            cardView = v.findViewById(R.id.cardView);
        }
    }
}

package com.example.mamaoliech;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.google.android.material.card.MaterialCardView;

import androidx.recyclerview.widget.RecyclerView;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder> {
    private DatabaseTable.ProductsSold[] productsSold;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
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

    // Provide a suitable constructor (depends on the kind of dataset)
    protected SalesAdapter(DatabaseTable.ProductsSold[] myDataset) {
        productsSold = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SalesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_card, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if (productsSold[position].sales.size() != 0){
            int price = productsSold[position].sales.get(0).quantity_sold * productsSold[position].product.sp;
            holder.product.setText(productsSold[position].product.product_name);
            holder.quantity.setText(String.valueOf(productsSold[position].sales.get(0).quantity_sold));
            holder.price.setText("Sh. "+String.valueOf(price));
            Log.e("LOOGING", "onCreate: "+ productsSold[position] );
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return productsSold.length;
    }
}

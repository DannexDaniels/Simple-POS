package com.example.mamaoliech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder>{

    MyDatabase.Expenses[] expenses;

    public ExpenseAdapter(MyDatabase.Expenses[] expenses) {
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_card, parent, false);

        ExpenseAdapter.MyViewHolder vh = new ExpenseAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.product.setText(expenses[position].expense_name);
        holder.category.setText(String.valueOf(expenses[position].expense_category));
        holder.quantity.setText(String.valueOf(expenses[position].expense_quantity));
        holder.cost.setText("Sh. "+String.valueOf(expenses[position].expense_cost));
        holder.date.setText(String.valueOf(expenses[position].expense_date));
    }

    @Override
    public int getItemCount() {
        return expenses.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView product, category, quantity, cost, date;
        private MaterialCardView cardView;
        public MyViewHolder(@NonNull View v) {
            super(v);
            product = v.findViewById(R.id.tvProductExpense);
            category = v.findViewById(R.id.tvCategoryExpenses);
            quantity = v.findViewById(R.id.tvQuantityExpense);
            cost = v.findViewById(R.id.tvCostExpense);
            date = v.findViewById(R.id.tvDateExpense);
            cardView = v.findViewById(R.id.cardView);
        }
    }
}

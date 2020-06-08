package com.example.mamaoliech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

public class DisplayExpenses extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MyDatabase.AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_expenses);

        recyclerView = findViewById(R.id.expense_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //create database
        db = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.AppDatabase.class, "database-name").allowMainThreadQueries().build();

        MyDatabase.Expenses [] expenses = db.productDao().getExpenses();


        Log.e("DATABASE", "onCreate: "+ (db.productDao().getExpenses().toString()));

        // specify an adapter (see also next example)
        mAdapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(mAdapter);
    }
}

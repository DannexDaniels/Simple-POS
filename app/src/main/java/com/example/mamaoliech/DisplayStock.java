package com.example.mamaoliech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class DisplayStock extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private MyDatabase.AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_stock);

        recyclerView = findViewById(R.id.product_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //create database
        db = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.AppDatabase.class, "database-name").allowMainThreadQueries().build();

        MyDatabase.Product [] products = db.productDao().getAll();


        Log.e("DATABASE", "onCreate: "+ (db.productDao().getAll().toString()));

        // specify an adapter (see also next example)
        mAdapter = new ProductAdapter(products);
        recyclerView.setAdapter(mAdapter);
    }
}

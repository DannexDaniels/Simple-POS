package com.example.mamaoliech;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LinearLayout fabMenu, addStock, addSale, addBreakage;

    private DatabaseTable.AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        fabMenu = findViewById(R.id.fabMenu);
        addSale = findViewById(R.id.llAddSale);
        addBreakage = findViewById(R.id.llAddBreakage);
        addStock = findViewById(R.id.llAddStock);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //create database
        db = Room.databaseBuilder(getApplicationContext(),
                DatabaseTable.AppDatabase.class, "database-name").allowMainThreadQueries().build();


        String[] categories = {"BEER","VODKA","BRANDY","GLEN","SOFT DRINKS", "FOOD"};

        // specify an adapter (see also next example)
        mAdapter = new SalesAdapter(db.productDao().getProductsSold());
        recyclerView.setAdapter(mAdapter);

        setSupportActionBar(toolbar);

        final int[] clicks = {0};



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                System.err.println("trial error: "+ db.productDao().getProductsSold().length);
                for ( DatabaseTable.ProductsSold product : db.productDao().getProductsSold()){

                    Log.e("DATABASE", "onCreate: "+ ( product.product.product_name));
                    Log.e("DATABASE", "onCreate: "+ ( product.product.sp));
                    Log.e("DATABASE", "onCreate: "+ ( product.product.category));
                    Log.e("DATABASE", "onCreate: "+ ( product.product.stock_level));
                    Log.e("DATABASE", "onCreate: "+ ( product.sales.get(0).quantity_sold));
                    Log.e("DATABASE", "onCreate: "+ ( product.sales.get(0).product_sold));
                    Log.e("DATABASE", "onCreate: "+ ( product.sales.get(0).date_sold));
                }
                clicks[0]++;
                if (clicks[0] % 2 == 0)
                    fabMenu.setVisibility(View.GONE);
                else
                    fabMenu.setVisibility(View.VISIBLE);


            }
        });

    }

    public void addStock(View view){
        startActivity(new Intent(getApplicationContext(), AddStock.class));
    }

    public void addSale(View view){
        startActivity(new Intent(getApplicationContext(), AddSale.class));
    }

    public void addBreakage(View view){
        startActivity(new Intent(getApplicationContext(), AddBreakage.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

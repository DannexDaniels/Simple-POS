package com.example.mamaoliech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class AddStock extends AppCompatActivity {

    private EditText product_name, quantity, buying_price, selling_price;
    private AutoCompleteTextView category;
    private MaterialButton button;

    private DatabaseTable.AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        product_name = findViewById(R.id.product_name);
        quantity = findViewById(R.id.product_quantity);
        buying_price = findViewById(R.id.product_buying_price);
        selling_price = findViewById(R.id.product_selling_price);
        category = findViewById(R.id.product_category);
        button = findViewById(R.id.save_product);

        //populate the drop down menu
        String[] categories = {"BEER","VODKA","BRANDY","GLEN","SOFT DRINKS", "FOOD"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_menu_category,
                        categories);

        category.setAdapter(adapter);

        //create database
        db = Room.databaseBuilder(getApplicationContext(),
                DatabaseTable.AppDatabase.class, "database-name").allowMainThreadQueries().build();



        for ( DatabaseTable.Product product : db.productDao().getAll()){

            Log.e("DATABASE", "onCreate: "+ ( product.product_name));
            Log.e("DATABASE", "onCreate: "+ ( product.category));
            Log.e("DATABASE", "onCreate: "+ ( product.stock_level));
            Log.e("DATABASE", "onCreate: "+ ( product.bp));
            Log.e("DATABASE", "onCreate: "+ ( product.sp));


        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct(product_name.getText().toString(), category.getText().toString(), Integer.parseInt(quantity.getText().toString()), Integer.parseInt(buying_price.getText().toString()),Integer.parseInt(selling_price.getText().toString()));
            }
        });

    }

    private void saveProduct(String name, String category, int quantty, int bp, int sp){
        DatabaseTable.Product product = new DatabaseTable.Product(name, category, quantty, bp, sp);

        db.productDao().insertProduct(product);
        Toast.makeText(getApplicationContext(),"Product added successfully",Toast.LENGTH_LONG).show();
        product_name.setText("");
        quantity.setText("");
        buying_price.setText("");
        selling_price.setText("");
    }
}

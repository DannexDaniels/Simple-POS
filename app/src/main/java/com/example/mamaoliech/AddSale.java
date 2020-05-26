package com.example.mamaoliech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddSale extends AppCompatActivity {

    private EditText product_name, quantity, selling_price;
    private MaterialButton button;

    private DatabaseTable.AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        product_name = findViewById(R.id.product_sold);
        quantity = findViewById(R.id.product_quantity_sold);
        selling_price = findViewById(R.id.product_price);
        button = findViewById(R.id.sell_product);

        //create database
        db = Room.databaseBuilder(getApplicationContext(),
                DatabaseTable.AppDatabase.class, "database-name").allowMainThreadQueries().build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellProduct(product_name.getText().toString(), Integer.parseInt(quantity.getText().toString()),Integer.parseInt(selling_price.getText().toString()));
            }
        });
    }

    private void sellProduct(String productSold, int quantitySold, int price) {
        //get current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        //format it to day-month-year
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        DatabaseTable.Sale sale = new DatabaseTable.Sale(productSold, quantitySold, formattedDate);

        db.productDao().insertSale(sale);

        Toast.makeText(getApplicationContext(),"Product Sold successfully",Toast.LENGTH_LONG).show();

        startActivity(new Intent(AddSale.this, MainActivity.class));
    }
}

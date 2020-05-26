package com.example.mamaoliech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddSale extends AppCompatActivity {

    private EditText product_name, quantity;
    private MaterialButton button;
    private ListView lvProductList;

    private MyDatabase.AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sale);

        product_name = findViewById(R.id.product_sold);
        quantity = findViewById(R.id.product_quantity_sold);
        //selling_price = findViewById(R.id.product_price);
        button = findViewById(R.id.sell_product);
        lvProductList = findViewById(R.id.lvProducts);

        //create database
        db = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.AppDatabase.class, "database-name").allowMainThreadQueries().build();

        lvProductList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product_name.setText(String.valueOf(parent.getItemAtPosition(position)));
                lvProductList.setVisibility(View.GONE);
            }
        });

        product_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                System.out.println("No text change has been detected "+s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                MyDatabase.Product[] products = db.productDao().findProduct(String.valueOf(s));

                ArrayList<String> productList = new ArrayList<String>();

                for(MyDatabase.Product prod:products){
                    productList.add(prod.product_name);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddSale.this, android.R.layout.simple_list_item_1, productList);
                lvProductList.setAdapter(adapter);

                System.out.println("result is " + adapter.getItem(0));
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("Text has finished changing "+s.toString());

                if (s.toString().isEmpty())
                    lvProductList.setVisibility(View.GONE);
                else
                    lvProductList.setVisibility(View.VISIBLE);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sellProduct(product_name.getText().toString(), Integer.parseInt(quantity.getText().toString()));
            }
        });
    }

    private void sellProduct(String productSold, int quantitySold) {
        //get current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        //format it to day-month-year
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        MyDatabase.Sale sale = new MyDatabase.Sale(productSold, quantitySold, formattedDate);

        db.productDao().insertSale(sale);

        MyDatabase.Product product = db.productDao().getProduct(productSold);

        db.productDao().updateUsers(new MyDatabase.Product(product.product_name,product.category, product.stock_level-quantitySold, product.bp, product.sp));

        Toast.makeText(getApplicationContext(),"Product Sold successfully",Toast.LENGTH_LONG).show();

        startActivity(new Intent(AddSale.this, MainActivity.class));
    }
}

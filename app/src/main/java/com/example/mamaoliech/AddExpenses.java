package com.example.mamaoliech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddExpenses extends AppCompatActivity {

    private EditText expense_name, quantity, cost;
    private AutoCompleteTextView category;
    private MaterialButton button;

    private MyDatabase.AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        expense_name = findViewById(R.id.expense_name);
        quantity = findViewById(R.id.expense_quantity);
        cost = findViewById(R.id.expense_cost);
        category = findViewById(R.id.expense_category);
        button = findViewById(R.id.save_expense);

        //populate the drop down menu
        String[] categories = {"WAGES","RENT","TRANSPORT"};

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        getApplicationContext(),
                        R.layout.dropdown_menu_category,
                        categories);

        category.setAdapter(adapter);

        //create database
        db = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.AppDatabase.class, "database-name").allowMainThreadQueries().build();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct(expense_name.getText().toString(), category.getText().toString(), Integer.parseInt(quantity.getText().toString()), Integer.parseInt(cost.getText().toString()));
            }
        });
    }

    private void saveProduct(String name, String category, int qty, int charges) {
        //get current date
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        //format it to day-month-year
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        MyDatabase.Expenses expenses = new MyDatabase.Expenses(name,category,qty,charges,formattedDate);

        db.productDao().insertExpense(expenses);
        Toast.makeText(getApplicationContext(),"Product added successfully",Toast.LENGTH_LONG).show();
        expense_name.setText("");
        quantity.setText("");
        cost.setText("");


    }
}

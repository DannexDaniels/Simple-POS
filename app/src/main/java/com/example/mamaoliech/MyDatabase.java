package com.example.mamaoliech;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.RoomDatabase;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

public class MyDatabase {
    //Defining tables/entities

    @Entity
    public static class Product{
        @PrimaryKey @NonNull
        public String product_name;

        @ColumnInfo(name = "category")
        public String category;

        @ColumnInfo(name = "stock_level")
        public int stock_level;

        @ColumnInfo(name = "b_p")
        public int bp;

        @ColumnInfo(name = "s_p")
        public int sp;

        //for adding a product
        public Product (String product_name, String category, int stock_level, int bp, int sp){
            this.product_name = product_name;
            this.category = category;
            this.stock_level = stock_level;
            this.bp = bp;
            this.sp = sp;
        }

        //for updating stock level
        /*@Ignore
        public Product(String product_name, int stock_level){
            this.product_name = product_name;
            this.stock_level = stock_level;
        }*/
    }

    @Entity
    public static class Sale{
        @PrimaryKey (autoGenerate = true)
        public int id;

        @ColumnInfo(name = "product_sold")
        public String product_sold;

        @ColumnInfo(name = "quantity_sold")
        public int quantity_sold;

        @ColumnInfo(name = "date_sold")
        public String date_sold;

        public Sale(String product_sold, int quantity_sold, String date_sold){
            this.product_sold = product_sold;
            this.quantity_sold = quantity_sold;
            this.date_sold = date_sold;
        }
    }

    @Entity
    public static class Breakage{
        @PrimaryKey (autoGenerate = true)
        public int id;

        @ColumnInfo(name = "product_broken")
        public String product_broken;

        @ColumnInfo(name = "category_broken")
        public String category_broken;

        @ColumnInfo(name = "quantity_broken")
        public int quantity_broken;

        @ColumnInfo(name = "amount_paid")
        public int amount_paid;

        @ColumnInfo(name = "date_broken")
        public String date_broken;
    }

    @Entity
    public static class Expenses{
        @PrimaryKey (autoGenerate = true)
        public int id;

        @ColumnInfo(name = "expense_name")
        public String expense_name;

        @ColumnInfo(name = "expense_category")
        public String expense_category;

        @ColumnInfo(name = "expense_quantity")
        public int expense_quantity;

        @ColumnInfo(name = "expense_cost")
        public int expense_cost;

        @ColumnInfo(name = "expense_date")
        public String expense_date;

        public Expenses(String expense_name, String expense_category, int expense_quantity, int expense_cost, String expense_date){
            this.expense_name = expense_name;
            this.expense_category = expense_category;
            this.expense_quantity = expense_quantity;
            this.expense_cost = expense_cost;
            this.expense_date = expense_date;
        }

    }

    //creating relationships

    public static class ProductsSold {
        @Embedded
        public Product product;
        @Relation(
                parentColumn = "product_name",
                entityColumn = "product_sold"
        )
        public List<Sale> sales;
    }

    public static class ProductsBroken {
        @Embedded
        public Product product;
        @Relation(
                parentColumn = "product_name",
                entityColumn = "product_broken"
        )
        public List<Breakage> brokens;
    }

    //queries
    @Dao
    public interface ProductDao{
        //get all products
        @Query("SELECT * FROM Product")
        Product[]  getAll();

        //get specific product
        @Query("SELECT * FROM Product WHERE product_name = :product")
        Product  getProduct(String product);

        //get sales
        @Query("SELECT * FROM Expenses")
        Expenses[] getExpenses();

        //search for a product
        @Query("SELECT * FROM Product WHERE product_name LIKE '%' || :search  || '%' LIMIT 4")
        Product[] findProduct(String search);

        /*@Query("SELECT * FROM Product WHERE category IN (category)")
        List<Product> loadAllByCategory(String category);

        @Query("SELECT * FROM Product WHERE product_name LIKE '%' || :search  || '%' OR category LIKE '%' || :search || '%' LIMIT 4")
        List<Product> searchProduct(String search);*/

        //retrieving from relationship...

        //get products sold
        @Transaction
        @Query("Select * FROM Sale CROSS JOIN Product WHERE Product.product_name=Sale.product_sold")
        ProductsSold[] getProductsSold();

        //get products broken
        @Transaction
        @Query("SELECT * FROM Product CROSS JOIN Breakage WHERE Product.product_name=Breakage.product_broken")
        ProductsBroken[] getProductsBroken();

        @Insert
        void insertProduct(Product... Products);

        @Insert
        void insertSale(Sale... Sales);

        @Insert
        void insertBreakage(Breakage... Breakages);

        @Insert
        void insertExpense(Expenses... Expenses);

        @Update
        void updateUsers(Product... products);


        @Delete
        void delete(Product Product);
    }

    @androidx.room.Database(entities = {Product.class, Sale.class, Breakage.class, Expenses.class}, version = 2)
    public static abstract class AppDatabase extends RoomDatabase{
        public abstract ProductDao productDao();
    }
}

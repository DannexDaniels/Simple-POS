package com.example.mamaoliech;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Relation;
import androidx.room.RoomDatabase;
import androidx.room.Transaction;

import java.util.List;

public class DatabaseTable {
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

        public Product (String product_name, String category, int stock_level, int bp, int sp){
            this.product_name = product_name;
            this.category = category;
            this.stock_level = stock_level;
            this.bp = bp;
            this.sp = sp;
        }
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

    @Dao
    public interface ProductDao{
        @Query("SELECT * FROM Product")
        Product[]  getAll();

        @Query("SELECT * FROM Sale")
        Sale[] getAllSales();

        /*@Query("SELECT * FROM Product WHERE category IN (category)")
        List<Product> loadAllByCategory(String category);

        @Query("SELECT * FROM Product WHERE product_name LIKE '%' || :search  || '%' OR category LIKE '%' || :search || '%' LIMIT 4")
        List<Product> searchProduct(String search);*/

        //retrieving from relationship...

        @Transaction
        @Query("Select * FROM Product")
        ProductsSold[] getProductsSold();

        @Transaction
        @Query("SELECT * FROM Product")
        ProductsBroken[] getProductsBroken();

        @Insert
        void insertProduct(Product... Products);

        @Insert
        void insertSale(Sale... Sales);

        @Insert
        void insertBreakage(Breakage... Breakages);


        @Delete
        void delete(Product Product);
    }

    @Database(entities = {Product.class, Sale.class, Breakage.class}, version = 1)
    public static abstract class AppDatabase extends RoomDatabase{
        public abstract ProductDao productDao();
    }
}

package com.example.nvl.Sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.nvl.Class.Product;
import com.example.nvl.Class.User;

import java.util.ArrayList;
import java.util.List;

public class SqlHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Product.db";
    private static final int DB_VERSION = 11;
    private static final String DV_ID = "id";
    private static final String DV_NAME = "name";
    private static final String DV_REVIEW = "review";
    private static final String DV_MONEY = "money";
    private static final String DV_KIND = "kind";
    private static final String DV_IMG = "img";
    private static final String DV_RATE = "rate";
    private static final String DV_SIZE = "size";
    private static final String DV_COLOR = "color";
    private static final String DV_NUMBER = "number";
    private static final String TB_SANPHAM = "sanpham";

    private static final String TB_USER = "khachhang";
    private static final String DV_IDUSER = "id";
    private static final String DV_SDT = "numberphone";
    private static final String DV_PASSWORD = "password";
    private static final String DV_USERNAME = "username";
    private static final String DV_AVATAR = "avatar";

    public SqlHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE sanpham("
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "name TEXT,"
                + "review TEXT,"
                + "money INTEGER,"
                + "kind TEXT,"
                + "img TEXT,"
                + "rate DOUBLE,"
                + "size TEXT,"
                + "color TEXT,"
                + "number INTEGER"
                + ")";
        String sqlQuery2 = "CREATE TABLE khachhang("
                + "id INTEGER NOT NULL PRIMARY KEY,"
                + "numberphone TEXT,"
                + "password TEXT,"
                + "username TEXT,"
                + "avatar TEXT"
                + ")";
        db.execSQL(sqlQuery);
        db.execSQL(sqlQuery2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            String strQuery = "DROP TABLE IF EXISTS " + TB_SANPHAM;
            String strQuery2= "DROP TABLE IF EXISTS " + TB_USER;
            db.execSQL(strQuery);
            db.execSQL(strQuery2);
            onCreate(db);
        }
    }
    public void onAddProduct(int id, String name, String review, int money, String kind, String img, Double rate, String size, String color, int number) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DV_ID, id);
        contentValues.put(DV_NAME, name);
        contentValues.put(DV_REVIEW, review);
        contentValues.put(DV_KIND, kind);
        contentValues.put(DV_MONEY, money);
        contentValues.put(DV_IMG, img);
        contentValues.put(DV_RATE, rate);
        contentValues.put(DV_SIZE, size);
        contentValues.put(DV_COLOR, color);
        contentValues.put(DV_NUMBER, number);
        sqLiteDatabase.insert(TB_SANPHAM, null, contentValues);
        sqLiteDatabase.close();

        contentValues.clear();
    }


    public List<Product> onGetProduct() {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(false, TB_SANPHAM,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DV_ID));
            String name = cursor.getString(cursor.getColumnIndex(DV_NAME));
            String review = cursor.getString(cursor.getColumnIndex(DV_REVIEW));
            int money = cursor.getInt(cursor.getColumnIndex(DV_MONEY));
            String kind = cursor.getString(cursor.getColumnIndex(DV_KIND));
            String img = cursor.getString(cursor.getColumnIndex(DV_IMG));
            Double rate = cursor.getDouble(cursor.getColumnIndex(DV_RATE));
            String size = cursor.getString(cursor.getColumnIndex(DV_SIZE));
            String color = cursor.getString(cursor.getColumnIndex(DV_COLOR));
            int number = cursor.getInt(cursor.getColumnIndex(DV_NUMBER));
            products.add(new Product(id, name, review, money, kind, img, rate, size, color, number));
        }
        return products;
    }

    public void onAddUser(int id,String sdt, String password, String username, String avatar) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DV_IDUSER,id);
        contentValues.put(DV_SDT, sdt);
        contentValues.put(DV_PASSWORD, password);
        contentValues.put(DV_USERNAME, username);
        contentValues.put(DV_AVATAR, avatar);

        sqLiteDatabase.insert(TB_USER, null, contentValues);
        sqLiteDatabase.close();
        contentValues.clear();
    }

    public List<User> onGetAllUser() {
        List<User> users = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(false, TB_USER,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            int id=cursor.getInt(cursor.getColumnIndex(DV_IDUSER));
            String numberPhone = cursor.getString(cursor.getColumnIndex(DV_SDT));
            String password = cursor.getString(cursor.getColumnIndex(DV_PASSWORD));
            String username = cursor.getString(cursor.getColumnIndex(DV_USERNAME));
            String avatar = cursor.getString(cursor.getColumnIndex(DV_AVATAR));
            users.add(new User(id,numberPhone, password, username, avatar));
        }
        return users;
    }

    public void onUpdateUser(int id,String numberPhone, String password, String username, String avatar) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DV_SDT,numberPhone);
        contentValues.put(DV_PASSWORD, password);
        contentValues.put(DV_USERNAME, username);
        contentValues.put(DV_AVATAR, avatar);

        sqLiteDatabase.update(TB_USER, contentValues, "id=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
        ;
        contentValues.clear();
    }

    public void onDeleteAllProduct() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TB_SANPHAM, null, null);
    }

    public void onDeleteAllUser() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TB_USER, null, null);
    }

}

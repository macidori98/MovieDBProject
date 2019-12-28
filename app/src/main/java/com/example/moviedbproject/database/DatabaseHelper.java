package com.example.moviedbproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviedbproject.database.model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "movies_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(User.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertUser(String username, String password) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add them
        values.put(User.COLUMN_USERNAME, username);
        values.put(User.COLUMN_PASSWORD, password);

        // insert row
        long id = db.insert(User.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_ID, user.getId());
        values.put(User.COLUMN_PASSWORD, user.getPassword());
        values.put(User.COLUMN_USERNAME, user.getUsername());

        return db.update(User.TABLE_NAME,values, User.COLUMN_ID+ " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public User getUser(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(User.TABLE_NAME,
                new String[]{User.COLUMN_ID, User.COLUMN_USERNAME, User.COLUMN_PASSWORD},
                User.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        User user = new User(
                cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD))
        );
        cursor.close();
        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + User.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)));
                user.setUsername(cursor.getString(cursor.getColumnIndex(User.COLUMN_USERNAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }

        db.close();
        return userList;
    }
}

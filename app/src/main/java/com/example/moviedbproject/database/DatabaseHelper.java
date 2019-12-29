package com.example.moviedbproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.moviedbproject.database.model.FavouriteMovies;
import com.example.moviedbproject.database.model.Image;
import com.example.moviedbproject.database.model.User;
import com.example.moviedbproject.tmdb.model.Movies;
import com.example.moviedbproject.utils.Constant;

import java.nio.file.attribute.FileAttributeView;
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
        sqLiteDatabase.execSQL(Image.CREATE_TABLE);
        sqLiteDatabase.execSQL(FavouriteMovies.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + User.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Image.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouriteMovies.TABLE_NAME);
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

    public long insertFavouriteMovie(Movies movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FavouriteMovies.COLUMN_POPULARITY, movie.getPopularity());
        values.put(FavouriteMovies.COLUMN_VOTE_COUNT, movie.getVote_count());
        values.put(FavouriteMovies.COLUMN_VIDEO, movie.isVideo());
        values.put(FavouriteMovies.COLUMN_POSTER_PATH, movie.getPoster_path());
        values.put(FavouriteMovies.COLUMN_MOVIE_ID, movie.getId());
        values.put(FavouriteMovies.COLUMN_ADULT, movie.isAdult());
        values.put(FavouriteMovies.COLUMN_BACKDROP_PATH, movie.getBackdrop_path());
        values.put(FavouriteMovies.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginal_language());
        values.put(FavouriteMovies.COLUMN_ORIGINAL_TITLE, movie.getOriginal_title());
        values.put(FavouriteMovies.COLUMN_GENRE_IDS, String.valueOf(movie.getGenre_ids()));
        values.put(FavouriteMovies.COLUMN_TITLE, movie.getTitle());
        values.put(FavouriteMovies.COLUMN_VOTE_AVERAGE, movie.getVote_average());
        values.put(FavouriteMovies.COLUMN_OVERVIEW, movie.getOverview());
        values.put(FavouriteMovies.COLUMN_RELEASE_DATE, movie.getRelease_date());
        values.put(FavouriteMovies.COLUMN_USER_ID, Constant.CURRENT_USER.getId());

        long id = db.insert(FavouriteMovies.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public void deleteFavouriteMovie(Movies movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavouriteMovies.TABLE_NAME, FavouriteMovies.COLUMN_MOVIE_ID + " = ? AND "
                + FavouriteMovies.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(movie.getId()), String.valueOf(Constant.CURRENT_USER.getId())});
        db.close();
    }

    public long insertImage(String name, byte[] image){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Image.COLUMN_NAME, name);
        cv.put(Image.COLUMN_USER_ID, Constant.CURRENT_USER.getId());
        cv.put(Image.COLUMN_IMAGE, image);
        long id = db.insert(Image.TABLE_NAME, null, cv);
        db.close();
        return id;
    }

    public int updateImage(Image image){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Image.COLUMN_ID, image.getId());
        values.put(Image.COLUMN_NAME, image.getName());
        values.put(Image.COLUMN_USER_ID, image.getUserId());
        values.put(Image.COLUMN_IMAGE, image.getImage());

        return db.update(Image.TABLE_NAME,values, User.COLUMN_ID+ " = ?",
                new String[]{String.valueOf(image.getId())});
    }

    public Image getImage(long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Image.TABLE_NAME,
                new String[]{Image.COLUMN_ID, Image.COLUMN_NAME, Image.COLUMN_USER_ID, Image.COLUMN_IMAGE},
                Image.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.getCount() <= 0)
                return null;

        byte[] image1 = null;//cursor.getBlob(1);
        long lId = cursor.getLong(cursor.getColumnIndex(Image.COLUMN_ID));
        String sName = cursor.getString(cursor.getColumnIndex(Image.COLUMN_NAME));
        int iUserId = cursor.getInt(cursor.getColumnIndex(Image.COLUMN_USER_ID));
        Image image = new Image(lId, sName, iUserId, image1);
        cursor.close();
        return image;
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

    public boolean isUsersFavMovie(Movies movie){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + FavouriteMovies.TABLE_NAME + " WHERE "
                + FavouriteMovies.COLUMN_USER_ID + " = " + String.valueOf(Constant.CURRENT_USER.getId())
                + " AND " + FavouriteMovies.COLUMN_MOVIE_ID + " = " + String.valueOf(movie.getId());
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.getCount() <= 0) {
            db.close();
            return false;
        }
        db.close();
        return true;
    }

    public List<Movies> getUserFavMovies(){
        SQLiteDatabase db = this.getWritableDatabase();
        List<Movies> moviesList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + FavouriteMovies.TABLE_NAME + " WHERE "
                + FavouriteMovies.COLUMN_USER_ID + " = " + String.valueOf(Constant.CURRENT_USER.getId());
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movies movie = new Movies();
                movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(FavouriteMovies.COLUMN_POPULARITY)));
                movie.setVote_count(cursor.getLong(cursor.getColumnIndex(FavouriteMovies.COLUMN_VOTE_COUNT)));
                movie.setVideo(false);
                movie.setPoster_path(cursor.getString(cursor.getColumnIndex(FavouriteMovies.COLUMN_POSTER_PATH)));
                movie.setId(cursor.getLong(cursor.getColumnIndex(FavouriteMovies.COLUMN_MOVIE_ID)));
                movie.setAdult(false);
                movie.setBackdrop_path(cursor.getString(cursor.getColumnIndex(FavouriteMovies.COLUMN_BACKDROP_PATH)));
                movie.setOriginal_language(cursor.getString(cursor.getColumnIndex(FavouriteMovies.COLUMN_ORIGINAL_LANGUAGE)));
                movie.setOriginal_title(cursor.getString(cursor.getColumnIndex(FavouriteMovies.COLUMN_ORIGINAL_TITLE)));
                movie.setGenre_ids(null);
                movie.setTitle(cursor.getString(cursor.getColumnIndex(FavouriteMovies.COLUMN_TITLE)));
                movie.setVote_average(cursor.getFloat(cursor.getColumnIndex(FavouriteMovies.COLUMN_VOTE_AVERAGE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavouriteMovies.COLUMN_OVERVIEW)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndex(FavouriteMovies.COLUMN_RELEASE_DATE)));
                moviesList.add(movie);
            } while (cursor.moveToNext());
        }

        db.close();
        return moviesList;
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

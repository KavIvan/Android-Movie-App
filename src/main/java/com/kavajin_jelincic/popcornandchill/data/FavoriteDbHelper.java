package com.kavajin_jelincic.popcornandchill.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.kavajin_jelincic.popcornandchill.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favorite.db";
    private static final int DATABASE_VERSION = 1;
    public static final String LOGTAG =" FAVORITE";

    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public FavoriteDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void open(){
        Log.i(LOGTAG, "Database opened");
        db = dbhandler.getWritableDatabase();
    }
    public void close(){
        Log.i(LOGTAG, "Database closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE" + FavoriteContract.FavoritesEntry.TABLE_NAME + " {" +
                FavoriteContract.FavoritesEntry. _ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavoriteContract.FavoritesEntry.COLUMN_MOVIEID + "INTEGER, " +
                FavoriteContract.FavoritesEntry.COLUMN_TITLE + "TEXT NOT NULL, " +
                FavoriteContract.FavoritesEntry.COLUMN_USERRATING + "REAL NOT NULL, " +
                FavoriteContract.FavoritesEntry.COLUMN_PLOT_SYNOPSIS + "TEXT NOT NULL " +
                "}; ";
        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

       sqLiteDatabase.execSQL("DROP TABLE IF EXISTS" + FavoriteContract.FavoritesEntry.TABLE_NAME);
       onCreate(sqLiteDatabase);
    }

    public void addFavorite(Movie movie){
        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoritesEntry.COLUMN_MOVIEID, movie.getId());
        values.put(FavoriteContract.FavoritesEntry.COLUMN_TITLE, movie.getOriginalTitle());
        values.put(FavoriteContract.FavoritesEntry.COLUMN_USERRATING, movie.getVoteAverage());
        values.put(FavoriteContract.FavoritesEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(FavoriteContract.FavoritesEntry.COLUMN_PLOT_SYNOPSIS, movie.getOverview());

        db.insert(FavoriteContract.FavoritesEntry.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteFavorite (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavoritesEntry.TABLE_NAME,  FavoriteContract.FavoritesEntry.COLUMN_MOVIEID+ "=" +id, null);
    }
    public List<Movie> getAllFavorite(){
        String[] columns ={
                FavoriteContract.FavoritesEntry._ID,
                FavoriteContract.FavoritesEntry.COLUMN_MOVIEID,
                FavoriteContract.FavoritesEntry.COLUMN_TITLE,
                FavoriteContract.FavoritesEntry.COLUMN_USERRATING,
                FavoriteContract.FavoritesEntry.COLUMN_POSTER_PATH,
                FavoriteContract.FavoritesEntry.COLUMN_PLOT_SYNOPSIS,
        };

        String sortOrder =
                FavoriteContract.FavoritesEntry._ID + "ASCENDING";
        List<Movie> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this
                .getReadableDatabase();

        Cursor cursor = db.query(FavoriteContract.FavoritesEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                Movie movie = new Movie();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoritesEntry.COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoritesEntry.COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoritesEntry.COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoritesEntry.COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoritesEntry.COLUMN_PLOT_SYNOPSIS)));

                favoriteList.add(movie);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteList;
    }
}

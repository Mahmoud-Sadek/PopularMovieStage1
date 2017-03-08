package com.sadek.apps.popularmoviestage1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.sadek.apps.popularmoviestage1.model.Movie;

import java.util.ArrayList;

/**
 * Created by Mahmoud on 4/25/2016.
 */
public class FavoriteAdapter {
    FavoriteHelper favoriteHelper;
    Context context;

    public FavoriteAdapter(Context context) {
        favoriteHelper = new FavoriteHelper(context);
        this.context = context;
    }


    public long insertData(String poster_id, String POSTER, String TITEL, String DATE, String VOTE, String OVERVIEW) {
        SQLiteDatabase sqLiteDatabase = favoriteHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FavoriteHelper.POSTER, POSTER);
        contentValues.put(FavoriteHelper.TITEL, TITEL);
        contentValues.put(FavoriteHelper.DATE, DATE);
        contentValues.put(FavoriteHelper.VOTE, VOTE);
        contentValues.put(FavoriteHelper.OVERVIEW, OVERVIEW);
        contentValues.put(FavoriteHelper.POSTER_ID, poster_id);

        long id = sqLiteDatabase.insert(FavoriteHelper.TABLE_NAME, null, contentValues);
        return id;
    }

    public ArrayList<Movie> getAllData() {
        SQLiteDatabase db = favoriteHelper.getWritableDatabase();
        String[] columns = {FavoriteHelper.UID, FavoriteHelper.POSTER, FavoriteHelper.TITEL, FavoriteHelper.DATE, FavoriteHelper.VOTE, FavoriteHelper.OVERVIEW, FavoriteHelper.POSTER_ID};
        Cursor cursor2 = db.query(FavoriteHelper.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Movie> movieData = new ArrayList<Movie>();
        while (cursor2.moveToNext()) {
            String POSTER = cursor2.getString(1);
            String TITEL = cursor2.getString(2);
            String DATE = cursor2.getString(3);
            String VOTE = cursor2.getString(4);
            String OVERVIE = cursor2.getString(5);
            String poster_id = cursor2.getInt(6) + "";
            movieData.add(new Movie(TITEL, POSTER, OVERVIE, VOTE, DATE, poster_id));
        }
        return movieData;
    }

    public String getData(String Poster_Id) {
        SQLiteDatabase db = favoriteHelper.getWritableDatabase();
        String[] columns = {FavoriteHelper.UID, FavoriteHelper.POSTER, FavoriteHelper.TITEL, FavoriteHelper.DATE, FavoriteHelper.VOTE, FavoriteHelper.OVERVIEW};
        Cursor cursor = db.query(FavoriteHelper.TABLE_NAME, columns, FavoriteHelper.POSTER_ID + "='" + Poster_Id + "'", null, null, null, null, null);
        String data = null;
        while (cursor.moveToNext()) {
            int index1 = cursor.getColumnIndex(FavoriteHelper.POSTER);
            data = cursor.getString(index1);
        }

        return data;
    }

    static class FavoriteHelper extends SQLiteOpenHelper {
        private final static String DATABASE_NAME = "favoritetabase";
        private final static int DATABASE_VERSION = 1;
        private final static String TABLE_NAME = "movietable";
        private final static String UID = "_id";
        private final static String POSTER = "poster";
        private final static String TITEL = "titel";
        private final static String DATE = "date";
        private final static String VOTE = "vote";
        private final static String OVERVIEW = "overview";
        private final static String POSTER_ID = "POSTER_ID";
        private final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + POSTER + " VARCHAR(255) ," + TITEL + " VARCHAR(255) ," + DATE + " VARCHAR(255) ," + VOTE + " VARCHAR(255) ," + OVERVIEW + " VARCHAR(255) ," + POSTER_ID + " INTEGER);";
        private final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
        private Context context;

        public FavoriteHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_TABLE);
                Toast.makeText(context, "database CREATED", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "Sorry there is Error in create your database \n" + e, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(DROP_TABLE);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Toast.makeText(context, "Sorry there is Error in upgrade your database \n" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
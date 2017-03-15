package com.sadek.apps.popularmoviestage1.database.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


/**
 * Created by Mahmoud Sadek on 3/11/2017.
 */
public class FavouriteDbHelper extends SQLiteOpenHelper {


    public final static String DATABASE_NAME = "favoritetabase";
    public final static int DATABASE_VERSION = 1;
    public final static String TABLE_NAME = "movietable";
    public final static String UID = "_id";
    public final static String POSTER = "poster";
    public final static String TITEL = "titel";
    public final static String DATE = "date";
    public final static String VOTE = "vote";
    public final static String OVERVIEW = "overview";
    public final static String POSTER_ID = "POSTER_ID";
    public final static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + POSTER + " VARCHAR(255) ," + TITEL + " VARCHAR(255) ," + DATE + " VARCHAR(255) ," + VOTE + " VARCHAR(255) ," + OVERVIEW + " VARCHAR(255) ," + POSTER_ID + " INTEGER);";
    public final static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public Context context;

    public FavouriteDbHelper(Context context) {
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

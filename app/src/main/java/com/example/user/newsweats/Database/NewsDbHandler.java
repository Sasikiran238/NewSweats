package com.example.user.newsweats.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */

//  database handler for News
public class NewsDbHandler extends SQLiteOpenHelper {

//    Db version
    private  static final int Database_version=1;
//    DB name
    private static final String Database_name="News";
//    Id
    private static final String id_key = "Id";
//    Tablble Name
    private static final String Table_NAME="newslist";
//    Elements name
    private static final String searchdata="allData";

//    Constuctor for Database name and version
    public NewsDbHandler(Context context) {

        super(context, Database_name, null, Database_version);

    }

//    Create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String Db_query= "CREATE TABLE "+ Table_NAME +
                "("+id_key+
                " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                searchdata +
                " TEXT)";
        db.execSQL(Db_query);

    }

//    Upgare table by version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+Table_NAME);
        onCreate(db);

    }

//    Inser New String in row
    public boolean insertNews ( String search ) {

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(searchdata, search);
        long stat=db.insert(Table_NAME, null, contentValues);

        return stat>0;

    }

//    Check news Available or not
    public boolean checkNews(){

        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " +Table_NAME , null);

        return cursor.getCount()>0;

    }

//    get News from Db check with    available? (true) image :: (false) nodata
    public ArrayList<String> getNews(){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " +Table_NAME , null);
        ArrayList<String> newsList = new ArrayList<>();

        if(cursor.getCount()>0){

            if (cursor.moveToFirst()){

                newsList.add(cursor.getString(cursor.getColumnIndex(searchdata)));

                while(cursor.moveToNext()){

                    newsList.add(cursor.getString(cursor.getColumnIndex(searchdata)));

                }

            }

        }else {
            newsList.add("noData");
        }

        return newsList;

    }

}

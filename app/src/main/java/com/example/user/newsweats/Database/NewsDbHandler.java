package com.example.user.newsweats.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.newsweats.Models.NewsItems;

import java.util.ArrayList;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */

//  database handler for News
public class NewsDbHandler extends SQLiteOpenHelper {

//    Db version
private static final int Databaseversion = 1;
//    DB name
    private static final String Database_name="News";
//    Id
private static final String idkey = "Id";
//    Tablble Name
private static final String TableNAME = "newslist";
//    Elements name
    private static final String searchdata="allData";
    private static final String title = "title";
    private static final String discription = "discr";
    private static final String images = "image";
    private static final String detailLink = "link";
    NewsItems dBdata;


//    Constuctor for Database name and version
    public NewsDbHandler(Context context) {

        super(context, Database_name, null, Databaseversion);

    }

//    Create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("DBLOG", "Oncreate Table");
        String Db_query = "CREATE TABLE " + TableNAME +
                "(" + idkey +
                " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                searchdata + " TEXT ," +
                title + " TEXT ," +
                discription + " TEXT ," +
                images + " TEXT ," +
                detailLink + " TEXT " +
                ")";
        Log.e("DBLOG", "Oncreate Table created");
        db.execSQL(Db_query);

    }

//    Upgare table by version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + TableNAME);
        onCreate(db);

    }

//    Inser New String in row
public boolean insertNews(String search, String tiltle, String disc, String image, String link) {

        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(searchdata, search);
    contentValues.put(title, tiltle);
    contentValues.put(discription, disc);
    contentValues.put(images, image);
    contentValues.put(detailLink, link);
    long stat = db.insert(TableNAME, null, contentValues);

    db.close();
        return stat>0;

    }

//    Check news Available or not
    public boolean checkNews(){

        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + TableNAME, null);

        cursor.close();
        database.close();
        return cursor.getCount()>0;

    }

//    get News from Db check with    available? (true) image :: (false) nodata
public ArrayList<NewsItems> getNews() {

        SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("SELECT * FROM " + TableNAME, null);

    ArrayList<NewsItems> newsList = new ArrayList<>();

        if(cursor.getCount()>0){
            if (cursor.moveToFirst()) {

                do {

                    Log.e("value", cursor.getString(cursor.getColumnIndex(title)));
                    String tit = cursor.getString(cursor.getColumnIndex(title));
                    String dicp = cursor.getString(cursor.getColumnIndex(discription));
                    String image = cursor.getString(cursor.getColumnIndex(images));
                    String detail = cursor.getString(cursor.getColumnIndex(detailLink));
                    dBdata = new NewsItems(tit, dicp, image, detail);
                    newsList.add(dBdata);


                } while (cursor.moveToNext());
            }

        }
    cursor.close();
    db.close();
        return newsList;

    }


    public ArrayList<NewsItems> search(String search) {

        SQLiteDatabase searchDB = this.getReadableDatabase();
        Cursor cursor = null;
        ArrayList<NewsItems> dBdataArrayList = new ArrayList<>();

        if (searchdata != null && searchdata.length() > 0) {

            String sql = "SELECT * FROM " + TableNAME + " WHERE " + searchdata + " LIKE '%" + search + "%'";
            cursor = searchDB.rawQuery(sql, null);

        }

        if (cursor.moveToFirst()) {

            do {

                Log.e("value", cursor.getString(cursor.getColumnIndex(title)));
                String tit = cursor.getString(cursor.getColumnIndex(title));
                String dicp = cursor.getString(cursor.getColumnIndex(discription));
                String image = cursor.getString(cursor.getColumnIndex(images));
                String detail = cursor.getString(cursor.getColumnIndex(detailLink));
                dBdata = new NewsItems(tit, dicp, image, detail);
                dBdataArrayList.add(dBdata);


            } while (cursor.moveToNext());
        }

        return dBdataArrayList;
    }
}

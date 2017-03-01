package com.example.user.newsweats.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */

//  database handler for Images
public class ImageDbHandler extends SQLiteOpenHelper {

    //    Db version
    private static final int Database_version = 1;
    //    DB name
    private static final String Database_name = "Image";
    //    Id
    private static final String id_key = "Id";
    //    Table name
    private static final String Table_NAME = "imagelist";
    //    data element
    private static final String searchdata = "allData";

    //    constructor for Db name and version
    public ImageDbHandler(Context context) {

        super(context, Database_name, null, Database_version);

    }

    //    create table
    @Override
    public void onCreate(SQLiteDatabase db) {

        String Db_query = "CREATE TABLE " + Table_NAME +
                "(" + id_key +
                " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                searchdata +
                " TEXT)";
        db.execSQL(Db_query);

    }

    //    Upgrade Table by version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS" + Table_NAME);
        onCreate(db);

    }

    //    insert image in row
    public boolean insertImage(String search) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(searchdata, search);
        long stat = db.insert(Table_NAME, null, contentValues);

        return stat > 0;
    }

    //  check image url available or not
    public boolean checkImage() {

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from " + Table_NAME, null);

        return cursor.getCount() > 0;
    }

    //    get image from Db check with    available? (true) image :: (false) nodata
    public ArrayList<String> getImage() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + Table_NAME, null);
        ArrayList<String> imageList = new ArrayList<>();
        if (cursor.getCount() > 0) {

            if (cursor.moveToFirst()) {
                imageList.add(cursor.getString(cursor.getColumnIndex(searchdata)));
                while (cursor.moveToNext()) {
                    imageList.add(cursor.getString(cursor.getColumnIndex(searchdata)));
                }
            }
        } else {
            imageList.add("noData");
        }

        return imageList;
    }

}

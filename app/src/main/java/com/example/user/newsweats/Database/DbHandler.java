package com.example.user.newsweats.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 21/2/17.
 */

public class DbHandler extends SQLiteOpenHelper {

    private  static final int Database_version=1;
    private static final String Database_name="News";
    private static final String id_key = "Id";
    private static final String Table_NAME="newslist";

    private static final String searchdata="allData";

    private static  final String title="title";
    private static final String discp="disc";
    private static final String date="date";


    public DbHandler(Context context) {
        super(context, Database_name, null, Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String Db_query= "CREATE TABLE "+ Table_NAME +
                "("+id_key+" INTEGER PRIMARY KEY AUTOINCREMENT ," + searchdata + " TEXT," +
                title + " TEXT," + discp + " TEXT," + date + " TEXT)";
        db.execSQL(Db_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+Table_NAME);
        onCreate(db);
    }
    public boolean insertuser (String title, String search, String discp, String date) {
//        log_key=logname;
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(title, title);
        contentValues.put(searchdata, search);
        contentValues.put(discp, discp);
        contentValues.put(date, date);
        long stat=db.insert(Table_NAME, null, contentValues);
//        db.compileStatement("INSERT INTO "+Table_NAME+" ( ID" +log_name+","+name_key+","+email_key+","+pass_key+") "
//                +"VALUES (1, logname ,name ,shs@jhsd.com ,763548314)");
        return stat>0;
    }
}

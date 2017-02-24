package com.example.user.newsweats.Database;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 20/2/17.
 */

public class Preferences {

    Context con;
    SharedPreferences sharedPreferences;
    SharedPreferences settingShare;
    SharedPreferences.Editor editor;

    public Preferences(Context context) {
        this.con=context;
        sharedPreferences=context.getSharedPreferences("Logstate",con.MODE_PRIVATE);
        settingShare=context.getSharedPreferences("settings",con.MODE_PRIVATE);

        editor=settingShare.edit();
        editor=sharedPreferences.edit();

    }

    public  void setloggedin(boolean login){
         editor.putBoolean("loginmode",login);
        editor.commit();

    }
    public void setSettings(String theme){

    }

    public  boolean loggedin(){

        return  sharedPreferences.getBoolean("loginmode",false);
    }

}

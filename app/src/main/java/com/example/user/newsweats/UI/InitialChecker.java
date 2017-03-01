package com.example.user.newsweats.UI;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.user.newsweats.Controllers.NetworkConnectionChecker;
import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.Login;
import com.example.user.newsweats.R;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */
//Initial Page for check network Connetion

public class InitialChecker extends AppCompatActivity {


    NetworkConnectionChecker networkConnectionChecker;
    Preferences logpref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        networkConnectionChecker =new NetworkConnectionChecker(InitialChecker.this);
        logpref=new Preferences(InitialChecker.this);

        if(networkConnectionChecker.isNetworkAvailable()){

            Intent intent=new Intent(InitialChecker.this, Login.class);
            startActivity(intent);
            finish();

        }else {

            if(logpref.loggedin()) {

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Check Network Connection")
                        .setContentText("You maynot be get latest news")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent=new Intent(InitialChecker.this, Login.class);
                                startActivity(intent);
                            }
                        })
                        .show();

            }else {

                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Check Network Connection")
                        .setContentText("Sorry ! For Initial Login Network Needed")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                finishAffinity();
                            }
                        })
                        .show();

            }
        }

    }
}

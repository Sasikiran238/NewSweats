package com.example.user.newsweats.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.newsweats.Controllers.ConChecker;
import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.Login;
import com.example.user.newsweats.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Registration extends AppCompatActivity {

    EditText txt_email;
    EditText txt_name;
    EditText txt_pass;
    EditText txt_repass;

    Button but_register;

    Pattern pattern;
    Matcher matcher;


    //    Firebase
    FirebaseAuth mAuth;
//            private FirebaseAuth.AuthStateListener myaythstate;


    Preferences registerPreference;
    ConChecker conChecker;
//    private FirebaseAuth.AuthStateListener myaythstate;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txt_email = (EditText) findViewById(R.id.editText_email);
        txt_name = (EditText) findViewById(R.id.editText_name);
        txt_pass = (EditText) findViewById(R.id.editText_pass);
        txt_repass = (EditText) findViewById(R.id.editText_repass);
        mAuth = FirebaseAuth.getInstance();
        conChecker = new ConChecker(Registration.this);
        registerPreference = new Preferences(Registration.this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);

        but_register = (Button) findViewById(R.id.button_register);


        txt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                matcher = pattern.matcher(txt_email.getText().toString());

                if (matcher.matches() == true) {
                    Toast.makeText(Registration.this, "valid", Toast.LENGTH_SHORT).show();
                } else {

                    txt_email.setError("Enter Valid Email");
                }
            }
        });
        but_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txt_name.getText().toString();

                String email = txt_email.getText().toString();
                String pass = txt_pass.getText().toString();
                String repass = txt_repass.getText().toString();
                if (pass != null && !pass.isEmpty() && repass != null && !repass.isEmpty() && name != null && !name.isEmpty() && email != null && !email.isEmpty()) {

                    if (pass.length() >= 6) {
                        if (pass.equals(repass)) {
                            if (conChecker.isNetworkAvailable()) {


                                mAuth.createUserWithEmailAndPassword(email, pass)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if (task.isSuccessful()) {
                                                    Toast.makeText(Registration.this, "Successfully registered !!! Login to Continue ..", Toast.LENGTH_LONG).show();
                                                    registerPreference.setloggedin(true);
                                                    startActivity(new Intent(Registration.this, Login.class));
                                                } else {
                                                    Toast.makeText(Registration.this, "Registration Error", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });

                            } else {
                                new SweetAlertDialog(Registration.this, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("Check Network Connection")
                                        .setContentText("Sorry !!")
                                        .setConfirmText("Ok")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                sDialog.dismissWithAnimation();
                                                finish();
                                            }
                                        })
                                        .show();
                            }
                        } else {
                            txt_repass.setError("Enter Same Password");
                        }
                    } else {
                        txt_pass.setError("Password Should Contain 6 character");
                    }

                } else {
                    txt_name.setError("Enter Missing Value");
                }


            }
        });
//        myaythstate=new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                if(firebaseAuth.getCurrentUser()!=null){
//
//
//                    startActivity(new Intent(Registration.this, Login.class));
//
//                }
//
//
//
//
//            }
//        };

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.register_parent);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            }
        });

    }

    //    public void onStart() {
//        super.onStart();
//        mAuth.addAuthStateListener(myaythstate);
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();


//        mAuth.addAuthStateListener(myaythstate);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}

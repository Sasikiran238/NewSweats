package com.example.user.newsweats;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.newsweats.Controllers.NetworkConnectionChecker;
import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.UI.MainTabbedAct;
import com.example.user.newsweats.UI.Registration;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by sasikiran on 28/2/17.
 * version 1.0
 */
public class Login extends AppCompatActivity {


    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 1;
    Preferences pref;
    Context context;
    Button but_gmail;
    Button but_signup;
    NetworkConnectionChecker networkConnectionChecker;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    Button butLogin;
    EditText txtEmail;
    EditText txtPass;
    SweetAlertDialog pDialog;
    CheckBox check;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth.AuthStateListener myaythstate;
    private int passwordNotVisible=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context=Login.this;
        pDialog= new SweetAlertDialog(Login.this, SweetAlertDialog.PROGRESS_TYPE);
        mAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();
        networkConnectionChecker =new NetworkConnectionChecker(context);
        pref=new Preferences(Login.this);
        check=(CheckBox)findViewById(R.id.checkBox);
        but_gmail=(Button)findViewById(R.id.button_gmail);
        but_signup=(Button)findViewById(R.id.button_signin);
        butLogin=(Button)findViewById(R.id.button_Login);
        txtEmail=(EditText)findViewById(R.id.editText_logname);
        txtPass=(EditText)findViewById(R.id.editText_pass);

        but_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Login.this, Registration.class);
                startActivity(intent);

            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check.isChecked()){

                    txtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordNotVisible = 0;

                }else {

                    txtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible = 1;

                }

            }
        });

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Loging in...");
                pDialog.setCancelable(false);
                pDialog.show();
               String user=txtEmail.getText().toString();
               String pass=txtPass.getText().toString();

                if(networkConnectionChecker.isNetworkAvailable()) {

                    mAuth.signInWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
                                        pref.setloggedin(true);
                                        Intent i = new Intent(Login.this, MainTabbedAct.class);
                                        startActivity(i);

                                    } else {

                                        Toast.makeText(Login.this, "Wrong Username Password", Toast.LENGTH_SHORT).show();

                                    }

                                if(pDialog.isShowing()){

                                    pDialog.dismissWithAnimation();
                                }

                                }
                            });
                }else {

                    new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Check Network Connection")
                            .setContentText("Sorry!")
                            .setConfirmText("Ok")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();

                                }
                            })
                            .show();

                }

            }

        });

        if(networkConnectionChecker.isNetworkAvailable()) {

            but_gmail.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Siging with Gmail...");
                    pDialog.setCancelable(false);
                    pDialog.show();
                    signIn();
                }
            });

        }else{
            new SweetAlertDialog(Login.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Check Network Connection")
                    .setContentText("Sorry !!")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();

                        }
                    })
                    .show();
        }

        myaythstate=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!=null){

                  if(pDialog.isShowing()){
                    pDialog.dismissWithAnimation();}
                    pref.setloggedin(true);
                    Intent in=new Intent(Login.this,MainTabbedAct.class);
                    startActivity(in);
                }




            }
        };


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


                        Toast.makeText(Login.this,"Error",Toast.LENGTH_SHORT).show();


                    }
                }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_logparent);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            }
        });
        }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(myaythstate);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });


    }


}

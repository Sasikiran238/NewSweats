package com.example.user.newsweats.UI;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.user.newsweats.Database.Preferences;
import com.example.user.newsweats.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    EditText txt_email;
    EditText txt_name;
    EditText txt_logname;
    EditText txt_pass;
    EditText txt_repass;

    Button but_register;

    Pattern pattern;
    Matcher matcher;



//    Firebase
            FirebaseAuth mAuth;
            private FirebaseAuth.AuthStateListener myaythstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        txt_email=(EditText)findViewById(R.id.editText_email);
        txt_name=(EditText)findViewById(R.id.editText_name);
        txt_pass=(EditText)findViewById(R.id.editText_pass);
        txt_repass=(EditText)findViewById(R.id.editText_repass);
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);

        but_register=(Button)findViewById(R.id.button_register);

        mAuth= FirebaseAuth.getInstance();

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

                if(matcher.matches()==true){
                    Toast.makeText(Registration.this,"valid",Toast.LENGTH_SHORT).show();
                }else{

                    txt_email.setError("Enter Valid Email");
                }
            }
        });
        but_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name=txt_name.getText().toString();
                String logname=txt_logname.getText().toString();
                String email=txt_email.getText().toString();
                String pass=txt_pass.getText().toString();
                String repass=txt_repass.getText().toString();
                if(pass !=null && !pass.isEmpty() && repass !=null && !repass.isEmpty()&& name !=null && !name.isEmpty() && logname!=null && !logname.isEmpty() && email!=null && !email.isEmpty()){

                    if(pass.equals(repass)){

                        mAuth.createUserWithEmailAndPassword(email,pass);




                    }else {
                        txt_repass.setError("Enter Same Password");
                    }


                }
                else{
                    txt_name.setError("Enter Missing Value");
                }



                Toast.makeText(Registration.this,"Success",Toast.LENGTH_SHORT).show();
            }
        });

        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.register_parent);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(linearLayout.getWindowToken(), 0);
            }
        });

    }
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(myaythstate);
    }
}

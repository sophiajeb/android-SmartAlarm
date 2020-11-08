package com.example.smartalert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.lang.invoke.ConstantCallSite;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    EditText emailId, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;
    Button changeLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

    /*    Intent i = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(i);*/

        //allazw th glwssa toy actionBar. an den thn alla3w, 8a parei th glwssa pou exei ti systhma
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));


        changeLang= findViewById(R.id.changeLang);
        changeLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //anoigei dialog gia na dei3ei th lista me tis glwsses apo tis opoies mporeis na diale3eis mia
                showChangeLanguageDiaog();
            }
        });

       mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.emailId);
        password = findViewById(R.id.password);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if (email.isEmpty()) {
                    emailId.setError("Please enter Email");
                    emailId.requestFocus();
                } else if (pwd.isEmpty()) {
                    password.setError("Please enter password");
                    password.requestFocus();
                } else if (email.isEmpty() && pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Your credentials are required", Toast.LENGTH_SHORT).show();
                } else if (!(email.isEmpty() && pwd.isEmpty())) {
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                                       @Override
                                                                                                       public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                           if (!task.isSuccessful()){
                                                                                                               Toast.makeText(MainActivity.this, "SignUp Unsuccessful, Please try again", Toast.LENGTH_SHORT).show();
                                                                                                           } else {
                                                                                                               startActivity(new Intent(MainActivity.this, SecondActivity.class));
                                                                                                           }
                                                                                                       }
                                                                                                   }
                    );
                } else {
                    Toast.makeText(MainActivity.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });

    }

    private void showChangeLanguageDiaog() {
        //pinakas tvn glwsswn pou uparxoun mesa sto dialog poy 8a emfanizetai
        final String[] listItems = {"English", "Ελληνικά"};

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle("Choose Language...");
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i==0){
                    //Greek
                    setLocale("en");
                    recreate();
                } else if (i==1) {
                    //English
                    setLocale("el-rGR");
                    recreate();
                }
                //kleinoume to dialog otan exei epilegei h glwssa
                dialog.dismiss();

            }

    });
        AlertDialog mdialog = mBuilder.create();
        //emfanizoume to dialog
        mdialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        // apouhkeuoume ta data sta shared preferences
        SharedPreferences.Editor editor =  getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();

    }

    //fortwnw th glwssa pou exei apouhketei sta prefferences
    public void loadLocale(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

}

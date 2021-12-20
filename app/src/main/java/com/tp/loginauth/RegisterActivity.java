package com.tp.loginauth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView emailID;
    private TextView usrID;
    private TextView phoneID;
    private TextView passID;
    private TextView confirmPassID;
    private Button signUpID;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide(); //hide the title bar
        setContentView(R.layout.register_activity);

        emailID=findViewById(R.id.emailID);
        usrID=findViewById(R.id.usrID);
        phoneID=findViewById(R.id.phoneID);
        passID=findViewById(R.id.passID);
        confirmPassID=findViewById(R.id.confirmPassID);
        signUpID=findViewById(R.id.signUpID);
        signUpID.setOnClickListener((View.OnClickListener) this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpID:
//                startActivity(new Intent(this, MainActivity.class));
                registerUser();
                break;
            //else{
            //  Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            //}
        }
    }
            private void registerUser () {
                String email = emailID.getText().toString().trim();
                String mobile = phoneID.getText().toString().trim();
                String pass = passID.getText().toString().trim();
                String conPass = confirmPassID.getText().toString().trim();
                String name = usrID.getText().toString().trim();

                if (!isValidMail(email)) {
                    emailID.setError("Valid Email is required");
                    emailID.requestFocus();
                    return;
                }
                if(!isValidMobile(mobile)){
                    phoneID.setError("Valid Mobile Number is required");
                    phoneID.requestFocus();
                    return;
                }


                if (name.isEmpty()) {
                    usrID.setError("Name is required");
                    usrID.requestFocus();
                    return;
                }


                if (!isValidPassword(pass)) {
                    passID.setError("Valid Password is required");
                    passID.requestFocus();
                    return;
                }

                if (!conPass.equals(pass)) {
                    passID.setError("Password does not match");
                    passID.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                mAuth
                        .createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                            "Registration successful!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);

                                    // if the user created intent to login activity
                                    Intent intent
                                            = new Intent(RegisterActivity.this,
                                            MainActivity.class);
                                    startActivity(intent);
                                }
                                else {

                                    // Registration failed
                                    Toast.makeText(
                                            getApplicationContext(),
                                            "Registration failed!!"
                                                    + " User already exists!",
                                            Toast.LENGTH_LONG)
                                            .show();

                                    // hide the progress bar
                                    progressBar.setVisibility(View.GONE);
                                }

                            }
                        });
            }

    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidMobile(String phone) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[0-9]{10}");

        return !TextUtils.isEmpty(phone) && PASSWORD_PATTERN.matcher(phone).matches();
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9]{6,16}");

        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }

}
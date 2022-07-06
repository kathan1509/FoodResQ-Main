package com.example.foodresq;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    public FirebaseAuth mAuth;
    // email validation
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        MaterialButton loginButton = findViewById(R.id.btnLogIn);
        MaterialTextView signup = findViewById(R.id.tvSignUp);
        EditText email = findViewById(R.id.edtEmailLi);
        EditText password = findViewById(R.id.edtPassword);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LogIn.this, SignUp.class);
                startActivity(i);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUserAccount(email.getText().toString(), password.getText().toString());
            }
        });

    }

    public void loginUserAccount(String userEmail, String pwd)
    {
        if(TextUtils.isEmpty(userEmail)){
            Toast.makeText(getApplicationContext(), "Email can't be Empty!", Toast.LENGTH_LONG).show();
            return;

        }

        if(TextUtils.isEmpty(pwd)){
            Toast.makeText(getApplicationContext(), "Password can't be empty!", Toast.LENGTH_LONG).show();
            return;
        }
        //login
        if (userEmail.matches(emailPattern)){
            mAuth.signInWithEmailAndPassword(userEmail, pwd).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                                "Login successful!!",
                                                Toast.LENGTH_LONG)
                                        .show();
                                // if login is successful
                                // intent to home activity
                                Intent intent
                                        = new Intent(LogIn.this,
                                        HomeRestaurant.class);
                                startActivity(intent);
                            }

                            else {

                                // login failed
                                Toast.makeText(getApplicationContext(),
                                                "Invalid UserID or password!",
                                                Toast.LENGTH_LONG)
                                        .show();
                            }
                        }

                    }
            );
        } else {
            Toast.makeText(getApplicationContext(),"Invalid Email!", Toast.LENGTH_LONG).show();
        }
    }
}
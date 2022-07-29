package com.example.foodresq;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LogIn extends AppCompatActivity {

    public FirebaseAuth mAuth;
    // email validation
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userType;
    TextView forgetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mAuth = FirebaseAuth.getInstance();
        MaterialButton loginButton = findViewById(R.id.btnLogIn);
        MaterialTextView signup = findViewById(R.id.tvSignUp);
        EditText email = findViewById(R.id.edtEmailLi);
        EditText password = findViewById(R.id.edtPassword);
        forgetPass = findViewById(R.id.tbForgetPass);

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

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LogIn.this, "Hello", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogIn.this, ForgetPassword.class);
                startActivity(intent);
            }
        });



    }

    public void loginUserAccount(String userEmail, String pwd) {
        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(getApplicationContext(), "Email can't be Empty!", Toast.LENGTH_LONG).show();
            return;

        }

        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "Password can't be empty!", Toast.LENGTH_LONG).show();
            return;
        }
        //login
        if (userEmail.matches(emailPattern)) {
            mAuth.signInWithEmailAndPassword(userEmail, pwd).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                                "Login successful!!",
                                                Toast.LENGTH_LONG)
                                        .show();
                                // if login is successful
                                // intent to home activity
                                //checkUserType
                                db.collection("User")
                                        .whereEqualTo("uEmailID", userEmail)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (DocumentSnapshot document : task.getResult()) {
                                                        userType = document.get("uType").toString();
                                                        //Toast.makeText(LogIn.this, userType, Toast.LENGTH_LONG).show();



                                                        if (userType.equals("Restaurant")) {

                                                            Intent intent
                                                                    = new Intent(LogIn.this,
                                                                    HomeRestaurant.class);
                                                            startActivity(intent);
                                                        } else {
                                                            Intent intent
                                                                    = new Intent(LogIn.this,
                                                                    HomeNGO.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                }
                                            }
                                        });
                            } else {
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
            Toast.makeText(getApplicationContext(), "Invalid Email!", Toast.LENGTH_LONG).show();
        }
    }

    /*public void sendPasswordReset() {
        // [START send_password_reset]
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = "user@example.com";

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
        // [END send_password_reset]
    }
*/
}
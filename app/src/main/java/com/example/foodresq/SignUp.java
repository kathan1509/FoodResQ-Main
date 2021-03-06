package com.example.foodresq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.Document;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SignUp.this, LogIn.class);
        startActivity(i);
        finish();
    }

    String[] typeOfUser = {"Restaurant", "NGO"};
    String userIs;
    private EditText emailText, passwordText, addressText, contactText;
    private Button submitBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Spinner userType = findViewById(R.id.userType);

        userType.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,typeOfUser);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(arrayAdapter);

        mAuth = FirebaseAuth.getInstance();

        emailText = findViewById(R.id.edtEmailSu);
        passwordText = findViewById(R.id.edtPassword);
        addressText = findViewById(R.id.edtAddress);
        contactText = findViewById(R.id.edtContact);
        submitBtn = findViewById(R.id.btnSignUp);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                registerNewUser();
            }
        });
    }


    public void registerNewUser() {
        String userEmail, pwd, address, contact;
        userEmail = emailText.getText().toString();
        pwd = passwordText.getText().toString();
        address = addressText.getText().toString();
        contact = contactText.getText().toString();

        database = FirebaseFirestore.getInstance();

        // email validation
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (TextUtils.isEmpty(userEmail)) {
            Toast.makeText(getApplicationContext(), "Email can't be empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "Password can't be empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Address can't be empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(contact)) {
            Toast.makeText(getApplicationContext(), "Contact can't be empty!", Toast.LENGTH_LONG).show();
            return;
        }

        if(userEmail.matches(emailPattern)){
            mAuth.createUserWithEmailAndPassword(userEmail, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        addDataToFirestore(userIs, userEmail, address, contact);
                        //Toast.makeText(getApplicationContext(), "User Created.", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(SignUp.this,LogIn.class );
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Registration failed!",Toast.LENGTH_LONG).show();
                    }

                }

            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Invalid email!", Toast.LENGTH_SHORT).show();
        }

    }

    public void addDataToFirestore(String uType, String uEmail, String uAddress, String uContact )
    {
        CollectionReference userDatabase = database.collection("User");

        UserProfile userProfile = new UserProfile(uType, uEmail,uAddress, uContact);

        userDatabase.add(userProfile).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(SignUp.this, " User Created.", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUp.this, "Fail to create user \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selectedUserType = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), selectedUserType, Toast.LENGTH_LONG).show();
        userIs = selectedUserType;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
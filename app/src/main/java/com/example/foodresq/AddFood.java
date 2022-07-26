package com.example.foodresq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Locale;

public class AddFood extends AppCompatActivity {

    private EditText foodTypeTxt, foodQtyTxt, bestBeforeTxt, descriptionTxt;
    private Button addFoodBtn;
    private String foodOrderStatus = "pending";
    private FirebaseFirestore database;
    private FirebaseUser user;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodTypeTxt = findViewById(R.id.edtFoodType);
        foodQtyTxt = findViewById(R.id.edtQuantity);
        bestBeforeTxt = findViewById(R.id.edtBestB);
        descriptionTxt = findViewById(R.id.edtDescription);
        addFoodBtn = findViewById(R.id.BtnPost);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);}

        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFoodDetails();
                // notification message
                postNotification();
            }

        });
    }

    public void addFoodDetails()
    {
        String foodType, foodQty, bestBefore, description;
        foodType = foodTypeTxt.getText().toString();
        foodQty = foodQtyTxt.getText().toString();
        bestBefore = bestBeforeTxt.getText().toString();
        description = descriptionTxt.getText().toString();

        database = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getEmail();

        if (TextUtils.isEmpty(foodType) && TextUtils.isEmpty(foodQty) && TextUtils.isEmpty(bestBefore) && TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "All field must not empty!", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            createFoodPost(foodType,foodQty, bestBefore, description, foodOrderStatus, currentUser);

            Intent intent = new Intent(AddFood.this,HomeRestaurant.class );
            startActivity(intent);
        }
    }

    public void createFoodPost(String foodType,String foodQty,String bestBefore,String description, String foodOrderStatus, String currentUser){

        CollectionReference createFoodDatabase = database.collection("Food Details");

        FoodDetails foodDetails = new FoodDetails(foodType, foodQty, bestBefore, description, foodOrderStatus, currentUser);

        createFoodDatabase.add(foodDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(AddFood.this, " Post Created.", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddFood.this, "Fail to create user \n" + e, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void postNotification()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My Notification")
                .setSmallIcon(R.drawable.foodresq_logo)
                .setContentTitle("Food Post Created")
                .setContentText("New food post has been created.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AddFood.this);
        managerCompat.notify(1, builder.build());
    }

}
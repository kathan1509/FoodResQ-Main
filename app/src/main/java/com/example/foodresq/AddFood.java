package com.example.foodresq;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;

public class AddFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String[] FoodQty = {"lb", "pieces"};
    TextInputEditText date;
    DatePickerDialog datePickerDialog;

    private EditText foodTypeTxt, foodQtyTxt, bestBeforeTxt, descriptionTxt;
    private Button addFoodBtn;
    private String foodOrderStatus = "pending",qtyType;
    private FirebaseFirestore database;
    private FirebaseUser user;
    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        date = findViewById(R.id.edtBestB);
        foodTypeTxt = findViewById(R.id.edtFoodType);
        foodQtyTxt = findViewById(R.id.edtQuantity);
        bestBeforeTxt = findViewById(R.id.edtBestB);
        descriptionTxt = findViewById(R.id.edtDescription);
        addFoodBtn = findViewById(R.id.BtnPost);

        //Spinner
        Spinner food_qty_spin = findViewById(R.id.food_qty_spinner);
        food_qty_spin.setOnItemSelectedListener(this);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, FoodQty);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        food_qty_spin.setAdapter(arrayAdapter);

        //DatePicker
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AddFood.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        addFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFoodDetails();
                // notification message
                postNotification();
            }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedQtyType = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(), selectedUserType, Toast.LENGTH_LONG).show();
        qtyType = selectedQtyType;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void addFoodDetails()
    {
    public void addFoodDetails() {
        String foodType, foodQty, bestBefore, description;
        foodType = foodTypeTxt.getText().toString();
        foodQty = foodQtyTxt.getText().toString() + " " + qtyType;
        bestBefore = bestBeforeTxt.getText().toString();
        description = descriptionTxt.getText().toString();

        database = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getEmail();

        if (TextUtils.isEmpty(foodType) && TextUtils.isEmpty(foodQty) && TextUtils.isEmpty(bestBefore) && TextUtils.isEmpty(description)) {
            Toast.makeText(getApplicationContext(), "All field must not empty!", Toast.LENGTH_LONG).show();
            return;
        } else {
            createFoodPost(foodType, foodQty, bestBefore, description, foodOrderStatus, currentUser);
        database.collection("User")
                .whereEqualTo("uEmailID",currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful())
                        {
                            for (DocumentSnapshot document : task.getResult())
                            {
                                currentUser = document.getString("uName");
                            }if (TextUtils.isEmpty(foodType) && TextUtils.isEmpty(foodQty) && TextUtils.isEmpty(bestBefore) && TextUtils.isEmpty(description)) {
                            Toast.makeText(getApplicationContext(), "All field must not empty!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            createFoodPost(foodType,foodQty, bestBefore, description, foodOrderStatus, currentUser);

            Intent intent = new Intent(AddFood.this, HomeRestaurant.class);
            startActivity(intent);
        }
                            Intent intent = new Intent(AddFood.this,HomeRestaurant.class );
                            startActivity(intent);
                        }
                        }
                    }
                });



    }

    public void createFoodPost(String foodType, String foodQty, String bestBefore, String description, String foodOrderStatus, String currentUser) {

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

    public void postNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "My Notification")
                .setSmallIcon(R.drawable.foodresq_logo)
                .setContentTitle("Food Post Created")
                .setContentText("New food post has been created.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(AddFood.this);
        managerCompat.notify(1, builder.build());
    }

}
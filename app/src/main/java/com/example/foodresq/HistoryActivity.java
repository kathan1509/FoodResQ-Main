package com.example.foodresq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView activityRV;
    private ArrayList<ActivityModel> activityModelArrayList;
    private String resName, orderStatus, foodQty, foodType, foodDescription,itemID;
    private String screen = "History";

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        activityRV = findViewById(R.id.idRVActivity);

        // fetching data from fireStore collection
        database.collection("Food Details")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            activityModelArrayList = new ArrayList<ActivityModel>();
                            for (DocumentSnapshot document : task.getResult()) {
                                String currentUser = user.getEmail();

                                    resName = document.getString("userName");
                                    orderStatus = document.getString("foodOrderStatus");
                                    foodQty = document.getString("foodQty");
                                    foodType = document.getString("foodType");
                                    foodDescription = document.getString("description");

                                    if(document.getString("userNGO").isEmpty()){
                                        if (currentUser.equals(document.getString("user"))) {
                                            if (document.getString("foodOrderStatus").equals("done")) {
                                                activityModelArrayList.add(new ActivityModel(resName, orderStatus,foodQty, foodType, foodDescription,itemID));
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (currentUser.equals(document.getString("userNGO"))) {
                                            if (document.getString("foodOrderStatus").equals("done")) {
                                                activityModelArrayList.add(new ActivityModel(resName, orderStatus,foodQty, foodType, foodDescription,itemID));
                                            }
                                        }

                                        if (currentUser.equals(document.getString("user"))) {
                                            if (document.getString("foodOrderStatus").equals("done")) {
                                                activityModelArrayList.add(new ActivityModel(resName, orderStatus,foodQty, foodType, foodDescription,itemID));
                                            }
                                        }

                                    }


                            }
                            ActivityAdapter activityAdapter = new ActivityAdapter(HistoryActivity.this, activityModelArrayList, screen);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL, false);

                            activityRV.setLayoutManager(linearLayoutManager);
                            activityRV.setAdapter(activityAdapter);
                        }
                    }
                });
    }
}

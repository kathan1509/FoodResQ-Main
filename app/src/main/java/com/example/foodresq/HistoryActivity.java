package com.example.foodresq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private RecyclerView activityRV;
    private ArrayList<ActivityModel> activityModelArrayList;
    private String resName, foodQty, foodType, foodDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        activityRV = findViewById(R.id.idRVActivity);

        activityModelArrayList = new ArrayList<ActivityModel>();
        activityModelArrayList.add(new ActivityModel(resName, foodQty, foodType, foodDescription));

        ActivityAdapter activityAdapter = new ActivityAdapter(HistoryActivity.this, activityModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this, LinearLayoutManager.VERTICAL, false);

        activityRV.setLayoutManager(linearLayoutManager);
        activityRV.setAdapter(activityAdapter);

    }
}
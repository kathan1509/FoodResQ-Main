package com.example.foodresq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeNGO extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    private RecyclerView activityRV;
    private ArrayList<ActivityModel> activityModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_ngo);

        /*Button logoutbtn = findViewById(R.id.btnLogOut);*/

        // on click event handling using Intent
        /*logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign-Out
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(HomeNGO.this, LogIn.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
            }
        });*/

        activityRV = findViewById(R.id.idRVActivity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        //Arraylist
        activityModelArrayList = new ArrayList<>();
        activityModelArrayList.add(new ActivityModel("The Grand Mehfil", "5", "Indian", "We have 5lb of curry that we want to be out before tomorrow evening"));

        ActivityAdapter activityAdapter = new ActivityAdapter(this, activityModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        activityRV.setLayoutManager(linearLayoutManager);
        activityRV.setAdapter(activityAdapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent_home = new Intent(HomeNGO.this, HomeNGO.class);
                startActivity(intent_home);
                break;
            case R.id.nav_activity:
                Intent intent = new Intent(HomeNGO.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(HomeNGO.this, ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_history:
                Intent intent2 = new Intent(HomeNGO.this, HistoryActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_faq:
                Intent intent4 = new Intent(HomeNGO.this, FAQ.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i = new Intent(HomeNGO.this, LogIn.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
        return false;
    }

}
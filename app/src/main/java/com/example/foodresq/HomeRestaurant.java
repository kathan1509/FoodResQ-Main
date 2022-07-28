package com.example.foodresq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeRestaurant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ExtendedFloatingActionButton myFabBtn;
    private RecyclerView activityRV;
    private ArrayList<ActivityModel> activityModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_restaurant);

        activityRV = findViewById(R.id.idRVActivity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        myFabBtn = findViewById(R.id.add_fab);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //Arraylist
        activityModelArrayList = new ArrayList<>();
        activityModelArrayList.add(new ActivityModel("The Grand Mehfil", "5", "Indian", "We have 5lb of curry that we want to be out before tomorrow evening"));

        ActivityAdapter activityAdapter = new ActivityAdapter(this, activityModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        activityRV.setLayoutManager(linearLayoutManager);
        activityRV.setAdapter(activityAdapter);

        navigationView.setNavigationItemSelectedListener(this);

        myFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeRestaurant.this, AddFood.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                Intent intent_home = new Intent(HomeRestaurant.this, HomeRestaurant.class);
                startActivity(intent_home);
                break;
            case R.id.nav_activity:
                Intent intent = new Intent(HomeRestaurant.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent1 = new Intent(HomeRestaurant.this, ProfileActivity.class);
                startActivity(intent1);
                break;
            case R.id.nav_history:
                Intent intent2 = new Intent(HomeRestaurant.this, HistoryActivity.class);
                startActivity(intent2);
                break;
            case R.id.nav_faq:
                Intent intent4 = new Intent(HomeRestaurant.this, FAQ.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent i = new Intent(HomeRestaurant.this, LogIn.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;
        }
        return true;
    }
}
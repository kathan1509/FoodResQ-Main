package com.example.foodresq;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomeRestaurant extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    ExtendedFloatingActionButton myFabBtn;
    private RecyclerView activityRV;
    private ArrayList<ActivityModel> activityModelArrayList;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore database = FirebaseFirestore.getInstance();
    private String resName, foodQty, foodType, foodDescription;

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

        // fetching data from fireStore collection
        String currentUser = user.getEmail();
        database.collection("User")
                .whereEqualTo("uEmailID", currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                resName = document.getString("uName");
                            }
                            String getUser = resName;
                            database.collection("Food Details")
                                    .whereEqualTo("user", getUser)
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            if (task.isSuccessful()) {
                                                activityModelArrayList = new ArrayList<ActivityModel>();
                                                for (DocumentSnapshot document : task.getResult()) {
                                                    foodQty = document.getString("foodQty");
                                                    foodType = document.getString("foodType");
                                                    foodDescription = document.getString("description");
                                                    //if (document.getString("foodOrderStatus").equals("pending"))
                                                    activityModelArrayList.add(new ActivityModel(resName, foodQty, foodType, foodDescription));
                                                }

                                                ActivityAdapter activityAdapter = new ActivityAdapter(HomeRestaurant.this, activityModelArrayList);
                                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeRestaurant.this, LinearLayoutManager.VERTICAL, false);

                                                activityRV.setLayoutManager(linearLayoutManager);
                                                activityRV.setAdapter(activityAdapter);
                                            }
                                        }
                                    });
                        }
                    }
                });

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
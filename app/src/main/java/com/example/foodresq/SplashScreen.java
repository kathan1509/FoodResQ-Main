package com.example.foodresq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent in = new Intent(SplashScreen.this,LogIn.class);
                startActivity(in);
                finish();
            }
        }).start();
    }
}
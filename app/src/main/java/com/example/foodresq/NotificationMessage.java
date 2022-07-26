package com.example.foodresq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationMessage extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_message);

        textView = findViewById(R.id.tvNotificationMsg);
        //getting the notification message
        String message=getIntent().getStringExtra("message");
        textView.setText(message);
    }
}
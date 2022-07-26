package com.example.foodresq;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {

    private TextView userEmailId, userName, userContact, userAddress;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (user != null) {
            userEmailId = (TextView) findViewById(R.id.emailtv);
            userName = (TextView) findViewById(R.id.name);
            userContact = (TextView) findViewById(R.id.contacttv);
            userAddress = (TextView) findViewById(R.id.addresstv);

            getUserProfile();

        }
    }

    public void getUserProfile() {
        String currentUser = user.getEmail();

        db.collection("User")
                .whereEqualTo("uEmailID", currentUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                userEmailId.append((CharSequence) document.get("uEmailID"));
                                //userEmailId.setText((CharSequence) document.get("uEmailID"));
                                userName.append((CharSequence) document.get("uName"));
                                userContact.append((CharSequence) document.get("uContact").toString());
                                userAddress.append((CharSequence) document.get("uAddress"));
                            }
                        }
                    }
                });
    }
}


package com.example.foodresq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ActivityModel> activityModelArrayList;
    String activityPage;

    FirebaseFirestore database = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String userNgo = user.getEmail();
    String itemId="";
    String location = " ", userEmail;

    //Constructor
    public ActivityAdapter(Context context, ArrayList<ActivityModel> activityModelArrayList, String page) {
        this.context = context;
        this.activityPage = page;
        this.activityModelArrayList = activityModelArrayList;
    }

    @NonNull
    @Override
    public ActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activitycard_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityAdapter.ViewHolder holder, int position) {
        ActivityModel model = activityModelArrayList.get(position);
        holder.Res_Name.setText(model.getRes_name());
        holder.Order_Status.setText(model.getOrder_Status());
        holder.Food_Qty.setText(model.getFood_qty());
        holder.Food_Type.setText(model.getFood_type());
        holder.Food_Desc.setText(model.getFood_desc());
        holder.item_ID.setText(model.getItem_ID());
        if(model.getOrder_Status().equalsIgnoreCase("pending")) {
            holder.AcceptButton.setVisibility(View.VISIBLE);
            holder.LocationButton.setVisibility(View.GONE);
            holder.DoneButton.setVisibility(View.GONE);

            if(activityPage.equals("Restaurant")){
                holder.DeleteButton.setVisibility(View.VISIBLE);
                holder.AcceptButton.setVisibility(View.GONE);
            }

        }
        else if(model.getOrder_Status().equalsIgnoreCase("onGoing")) {
            holder.AcceptButton.setVisibility(View.GONE);
            holder.LocationButton.setVisibility(View.VISIBLE);
            holder.DoneButton.setVisibility(View.VISIBLE);
            holder.DoneButton.setVisibility(View.GONE);

            if(holder.item_ID.getText().toString().equalsIgnoreCase(user.getEmail())){
                holder.DeleteButton.setVisibility(View.GONE);
                holder.AcceptButton.setVisibility(View.GONE);
                holder.LocationButton.setVisibility(View.GONE);
                holder.DoneButton.setVisibility(View.GONE);
            }
        }
        else {
            holder.AcceptButton.setVisibility(View.GONE);
            holder.LocationButton.setVisibility(View.GONE);
            holder.DoneButton.setVisibility(View.GONE);
        }


        holder.AcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Accept Button code goes here
                holder.AcceptButton.setVisibility(View.GONE);
                holder.LocationButton.setVisibility(View.VISIBLE);
                holder.DoneButton.setVisibility(View.VISIBLE);

                itemId = holder.item_ID.getText().toString();

                database.collection("Food Details")
                        .whereEqualTo("user", itemId)
                        .whereEqualTo("description", holder.Food_Desc.getText().toString())
                        .whereEqualTo("foodQty", holder.Food_Qty.getText().toString())
                        .whereEqualTo("userName",holder.Res_Name.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for(DocumentSnapshot document : task.getResult())
                                    {
                                        document.getReference().update("userNGO",userNgo);
                                        document.getReference().update("foodOrderStatus", "onGoing");
                                    }

                                }
                            }
                        });
            }
        });

        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete Button code goes here
                holder.AcceptButton.setVisibility(View.GONE);
                holder.LocationButton.setVisibility(View.GONE);
                holder.DoneButton.setVisibility(View.GONE);
                holder.DeleteButton.setVisibility(View.GONE);

                itemId = holder.item_ID.getText().toString();
                System.out.println("checkpoint" + holder.item_ID.getText().toString());
                database.collection("Food Details")
                        .whereEqualTo("user", holder.item_ID.getText().toString())
                        .whereEqualTo("description", holder.Food_Desc.getText().toString())
                        .whereEqualTo("foodQty", holder.Food_Qty.getText().toString())
                        .whereEqualTo("userName",holder.Res_Name.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for(DocumentSnapshot document : task.getResult())
                                    {
                                        //document.getReference().update("userNGO",userNgo);
                                        document.getReference().update("foodOrderStatus", "Delete");
                                    }

                                }
                            }
                        });
            }
        });

        holder.LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Location button code goes here

                database.collection("Food Details")
                        .whereEqualTo("user", holder.item_ID.getText().toString())
                        .whereEqualTo("description", holder.Food_Desc.getText().toString())
                        .whereEqualTo("foodQty", holder.Food_Qty.getText().toString())
                        .whereEqualTo("userName",holder.Res_Name.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for(DocumentSnapshot document : task.getResult())
                                    {
                                        userEmail = document.getString("user");
                                    }
                                    System.out.println(userEmail);
                                    database.collection("User")
                                            .whereEqualTo("uEmailID",userEmail)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        for(DocumentSnapshot document : task.getResult())
                                                        {
                                                            location = document.getString("uAddress");
                                                        }
                                                        System.out.println(location);
                                                        Uri gmmIntentUri = Uri.parse("http://maps.google.co.in/maps?q=" + location);
                                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                                        mapIntent.setPackage("com.google.android.apps.maps");
                                                        context.startActivity(mapIntent);
                                                    }
                                                }
                                            });
                                }

                            }
                        });

            }
        });

        holder.DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.LocationButton.setVisibility(View.GONE);
                holder.DoneButton.setVisibility(View.GONE);

                // Done Button code goes here
                database.collection("Food Details")
                        .whereEqualTo("user", holder.item_ID.getText().toString())
                        .whereEqualTo("description", holder.Food_Desc.getText().toString())
                        .whereEqualTo("foodQty", holder.Food_Qty.getText().toString())
                        .whereEqualTo("userName",holder.Res_Name.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for(DocumentSnapshot document : task.getResult())
                                    {
                                        document.getReference().update("foodOrderStatus", "done");
                                    }
                                }
                            }
                        });
            }
        });
    }


    @Override
    public int getItemCount() {
        return activityModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Res_Name, Food_Qty, Food_Type, Food_Desc, Order_Status, item_ID;
        private MaterialButton AcceptButton, LocationButton, DoneButton, DeleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Res_Name = itemView.findViewById(R.id.res_name);
            Order_Status = itemView.findViewById(R.id.tvOrderStatus);
            Food_Qty = itemView.findViewById(R.id.food_qty);
            Food_Type = itemView.findViewById(R.id.food_type);
            Food_Desc = itemView.findViewById(R.id.desc_food);
            item_ID = itemView.findViewById(R.id.itemID);
            AcceptButton = itemView.findViewById(R.id.btnAccept);
            LocationButton = itemView.findViewById(R.id.btnLocation);
            DoneButton = itemView.findViewById(R.id.btnDone);
            DeleteButton = itemView.findViewById(R.id.btnDelete);

            if (activityPage.equals("NGO"))
                AcceptButton.setVisibility(View.VISIBLE);

            if (activityPage.equals("Restaurant"))
                DeleteButton.setVisibility(View.VISIBLE);


        }
    }
}

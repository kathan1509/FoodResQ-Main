package com.example.foodresq;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ActivityModel> activityModelArrayList;

    //Constructor
    public ActivityAdapter(Context context, ArrayList<ActivityModel> activityModelArrayList) {
        this.context = context;
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
        holder.Food_Qty.setText(model.getFood_qty());
        holder.Food_Type.setText(model.getFood_type());
        holder.Food_Desc.setText(model.getFood_desc());

        holder.AcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Accept Button code goes here
                holder.AcceptButton.setVisibility(View.GONE);
                holder.LocationButton.setVisibility(View.VISIBLE);
            }
        });

        holder.LocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Location button code goes here
                Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194?q=91 Dixon Street");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);
            }
        });

        holder.DoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Done Button code goes here
            }
        });
    }


    @Override
    public int getItemCount() {
        return activityModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Res_Name, Food_Qty, Food_Type, Food_Desc;
        private MaterialButton AcceptButton, LocationButton, DoneButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Res_Name = itemView.findViewById(R.id.res_name);
            Food_Qty = itemView.findViewById(R.id.food_qty);
            Food_Type = itemView.findViewById(R.id.food_type);
            Food_Desc = itemView.findViewById(R.id.desc_food);
            AcceptButton = itemView.findViewById(R.id.btnAccept);
            LocationButton = itemView.findViewById(R.id.btnLocation);
            DoneButton = itemView.findViewById(R.id.btnDone);
        }
    }
}

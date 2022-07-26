package com.example.foodresq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    public int getItemCount() {
        return activityModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Res_Name, Food_Qty, Food_Type, Food_Desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Res_Name = itemView.findViewById(R.id.res_name);
            Food_Qty = itemView.findViewById(R.id.food_qty);
            Food_Type = itemView.findViewById(R.id.food_type);
            Food_Desc = itemView.findViewById(R.id.desc_food);

        }
    }
}

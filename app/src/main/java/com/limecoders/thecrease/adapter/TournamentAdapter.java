package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.FixturesActivity;
import com.limecoders.thecrease.models.TournamentModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TournamentAdapter extends RecyclerView.Adapter<TournamentAdapter.ViewHolder> {

    private Context context;
    private List<TournamentModel> models = new ArrayList<>();

    public TournamentAdapter(Context context, List<TournamentModel> models){
        this.context = context;
        this.models = models;
    }

    public void setModels(List<TournamentModel> models){
        this.models = models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tour_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TournamentModel model = models.get(position);

        holder.teams.setText(String.valueOf(model.getTeams())+" teams");
        holder.name.setText(model.getTourName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, FixturesActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name, teams;
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tournName);
            teams = itemView.findViewById(R.id.tourTeams);
            imageView = itemView.findViewById(R.id.tournImage);

        }
    }
}

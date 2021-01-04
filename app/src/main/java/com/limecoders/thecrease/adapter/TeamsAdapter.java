package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.TeamProfileActivity;
import com.limecoders.thecrease.activities.dashboards.TeamDBActivity;
import com.limecoders.thecrease.models.TeamModel;
import com.limecoders.thecrease.models.Users;

import java.util.ArrayList;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    private Context context;
    private List<Users> models = new ArrayList<>();

    public TeamsAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.teams_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users model = models.get(position);

        holder.teamName.setText(model.getTeamName());
        holder.teamLevel.setText(model.getLevel());
        holder.teamRanking.setText(String.valueOf(model.getRank()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Working", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, TeamProfileActivity.class)
                        .putExtra("teamId",model.getId()).putExtra("teamName",model.getTeamName()));
            }
        });
    }

    public void setModels(List<Users> models){
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView teamName, teamRanking, teamLevel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            teamName = itemView.findViewById(R.id.teamName);
            teamRanking = itemView.findViewById(R.id.teamRank);
            teamLevel = itemView.findViewById(R.id.teamLevel);

        }
    }
}

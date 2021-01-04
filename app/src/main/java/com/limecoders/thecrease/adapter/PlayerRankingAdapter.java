package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.PlayerRankingModel;
import com.limecoders.thecrease.models.Users;

import java.util.List;

public class PlayerRankingAdapter extends RecyclerView.Adapter<PlayerRankingAdapter.ViewHolder> {

    private Context context;
    private List<Users> models;

    public PlayerRankingAdapter(Context context, List<Users> models){
        this.context = context;
        this.models = models;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.players_head_item, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.players_ranking_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position!=0) {
            Users model = models.get(position-1);

            holder.rank.setText(String.valueOf(position));
            holder.rating.setText(String.valueOf(model.getRating()));
            holder.name.setText(model.getName());
            holder.team.setText(String.valueOf(model.getTeamName()));
        }
    }


    @Override
    public int getItemCount() {
        return models.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rank, name, rating, team;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.rankTV);
            name = itemView.findViewById(R.id.nameTV);
            rating = itemView.findViewById(R.id.ratingTV);
            team = itemView.findViewById(R.id.teamTV);
        }
    }
}

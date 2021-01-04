package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.RankingModel;
import com.limecoders.thecrease.models.Users;

import java.util.ArrayList;
import java.util.List;

public class TeamRankingAdapter extends RecyclerView.Adapter<TeamRankingAdapter.ViewHolder> {

    private Context context;
    private List<Users> models = new ArrayList<>();

    public TeamRankingAdapter(Context context, List<Users> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.head_tranking_table, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.tranking_itemview, parent, false);
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

            holder.teamPos.setText(String.valueOf(position));
            holder.teamRating.setText(String.valueOf(model.getRating()));
            holder.teamName.setText(model.getTeamName());
            holder.teamMatches.setText(String.valueOf(model.getMatchesPlayed()));
            holder.teamPoints.setText(String.valueOf(model.getRating()));
        }
    }

    public void setModels(List<Users> models){
        this.models = models;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return models.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView teamName, teamPos, teamMatches, teamRating, teamPoints;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            teamMatches = itemView.findViewById(R.id.matchTV);
            teamName = itemView.findViewById(R.id.teamTV);
            teamPos = itemView.findViewById(R.id.posTV);
            teamPoints = itemView.findViewById(R.id.ptsTV);
            teamRating = itemView.findViewById(R.id.ratingTV);

        }
    }
}

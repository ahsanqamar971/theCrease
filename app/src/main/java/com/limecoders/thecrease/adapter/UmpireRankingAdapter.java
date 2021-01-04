package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.UmpireModel;
import com.limecoders.thecrease.models.Users;

import java.util.List;

public class UmpireRankingAdapter extends RecyclerView.Adapter<UmpireRankingAdapter.ViewHolder> {

    private Context context;
    private List<Users> models;

    public UmpireRankingAdapter(Context context, List<Users> models){
        this.context = context;
        this.models = models;
    }

    public void setModels(List<Users> models){
        this.models = models;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.umpire_head_item, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.umpire_ranking_item, parent, false);
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

            holder.rank.setText(String.valueOf(model.getRank()));
            holder.matches.setText(String.valueOf(model.getMatchesPlayed()));
            holder.name.setText(model.getName());
        }
    }

    @Override
    public int getItemCount() {
        return models.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, matches, rank;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameTV);
            matches = itemView.findViewById(R.id.matches);
            rank = itemView.findViewById(R.id.rankTV);

        }
    }
}

package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.MatchDetailModel;

import java.util.List;

public class MatchDetailAdapter extends RecyclerView.Adapter<MatchDetailAdapter.ViewHolder> {

    private Context context;
    private List<MatchDetailModel> matchDetailModels;
    private boolean isBat;

    public MatchDetailAdapter(Context context,List<MatchDetailModel> matchDetailModels, boolean isBat){
        this.context = context;
        this.matchDetailModels = matchDetailModels;
        this.isBat = isBat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.match_detail_head, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.match_detail_item, parent, false);
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
            MatchDetailModel model = matchDetailModels.get(position-1);

            holder.playerName.setText(model.getPlayerName());
            holder.runs.setText(model.getRuns());
            holder.four.setText(model.getFours());
            holder.six.setText(model.getSixes());
            holder.ballBy.setText(model.getBallBy());
            holder.balls.setText(model.getBallsPlayed());
            holder.strikeRate.setText(model.getStrikeRate());
            holder.catchBy.setText(model.getCatchBy());
            holder.fow.setText(model.getFow());
        }

    }

    @Override
    public int getItemCount() {
        return matchDetailModels.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView playerName, runs, balls, four, six, strikeRate, fow, catchBy, ballBy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            playerName = itemView.findViewById(R.id.playerName);
            runs = itemView.findViewById(R.id.runs);
            balls = itemView.findViewById(R.id.balls);
            four = itemView.findViewById(R.id.four);
            six = itemView.findViewById(R.id.six);
            strikeRate = itemView.findViewById(R.id.strikeRate);
            fow = itemView.findViewById(R.id.fow);
            catchBy = itemView.findViewById(R.id.catchBy);
            ballBy = itemView.findViewById(R.id.bowlBy);
        }
    }
}

package com.limecoders.thecrease.activities.dashboards.adapters;

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

public class BatsmanAdapter extends RecyclerView.Adapter<BatsmanAdapter.ViewHolder> {

    private Context context;
    private List<MatchDetailModel> matchDetailModels;

    public BatsmanAdapter(Context context, List<MatchDetailModel> matchDetailModels){
        this.context = context;
        this.matchDetailModels = matchDetailModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.match_detail_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchDetailModel model = matchDetailModels.get(position);

        if(model.isBowled()){
            holder.catchBy.setText("Bowled");
            holder.fow.setText(model.getFow());
            holder.ballBy.setText(model.getBallBy());

        }else if(model.isCatch()){
            holder.catchBy.setText(model.getCatchBy());
            holder.fow.setText(model.getFow());
            holder.ballBy.setText(model.getBallBy());
        }else if(model.isLbw()){
            holder.catchBy.setText("LBW");
            holder.fow.setText(model.getFow());
            holder.ballBy.setText(model.getBallBy());
        }else if(model.isStump()){
            holder.catchBy.setText("Stumps");
            holder.fow.setText(model.getFow());
            holder.ballBy.setText(model.getBallBy());
        }else if(model.isRunOut()){
            holder.catchBy.setText("RunOut by"+model.getCatchBy());
            holder.fow.setText(model.getFow());
        }else if(model.isRetire()){
            holder.catchBy.setText("Retired");
            holder.fow.setText(model.getFow());
            holder.ballBy.setText(model.getBallBy());
        }else if(model.isOther()){
            holder.catchBy.setText("Other");
            holder.fow.setText(model.getFow());
            holder.ballBy.setText(model.getBallBy());
        }else{
            holder.catchBy.setVisibility(View.GONE);
            holder.fow.setVisibility(View.GONE);
            holder.ballBy.setVisibility(View.GONE);
        }

        String sr = String.valueOf((Double.parseDouble(model.getRuns())/Double.parseDouble(model.getBallsPlayed()))*100);

        holder.playerName.setText(model.getPlayerName());
        holder.runs.setText(model.getRuns());
        holder.four.setText(model.getFours());
        holder.six.setText(model.getSixes());
        holder.balls.setText(model.getBallsPlayed());
        holder.strikeRate.setText(String.valueOf(sr));

    }

    @Override
    public int getItemCount() {
        return matchDetailModels.size();
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

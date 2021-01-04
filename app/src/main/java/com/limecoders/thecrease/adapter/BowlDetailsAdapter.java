package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.MatchDetailActivity;
import com.limecoders.thecrease.models.MatchDetailModel;

import java.util.List;

public class BowlDetailsAdapter extends RecyclerView.Adapter<BowlDetailsAdapter.ViewHolder> {

    private Context context;
    private List<MatchDetailModel> matchDetailModels;

    public BowlDetailsAdapter(Context context, List<MatchDetailModel> matchDetailModels){
        this.context = context;
        this.matchDetailModels = matchDetailModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.bowl_detail_head, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.bowl_detail_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position!=0) {
            MatchDetailModel model = matchDetailModels.get(position-1);

            holder.playerName.setText(model.getPlayerName());
            holder.maiden.setText("0");
            holder.runs.setText(model.getRuns());
            holder.overs.setText(model.getOvers());
            holder.economy.setText(model.getEconomy());
            holder.wickets.setText(model.getWickets());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return matchDetailModels.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView playerName, wickets, runs, overs, maiden, economy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            playerName = itemView.findViewById(R.id.playerName);
            wickets = itemView.findViewById(R.id.wickets);
            runs = itemView.findViewById(R.id.runs);
            overs = itemView.findViewById(R.id.overs);
            maiden = itemView.findViewById(R.id.maiden);
            economy = itemView.findViewById(R.id.econ);
        }
    }
}

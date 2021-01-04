package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.TourTableModel;
import com.limecoders.thecrease.models.TournamentModel;

import java.util.List;

public class TournTableAdapter extends RecyclerView.Adapter<TournTableAdapter.ViewHolder> {

    private Context context;
    private List<TourTableModel> models;

    public TournTableAdapter(Context context, List<TourTableModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public TournTableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.tourn_head_table, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.tourn_table_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position!=0) {
            TourTableModel model = models.get(position-1);

            holder.rank.setText(model.getRank());
            holder.nrTV.setText(model.getNoResults());
            holder.teamName.setText(model.getTeamName());
            holder.matchesTV.setText(model.getMatchesPlayed());
            holder.winTV.setText(model.getMatchesWin());
            holder.tieTV.setText(model.getMatchesTie());
            holder.loseTV.setText(model.getMatchesLose());
            holder.ptsTV.setText(model.getPoints());
            holder.nrrTV.setText(model.getNetRating());
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return models.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView rank, teamName, matchesTV, winTV, loseTV, tieTV, ptsTV, nrTV, nrrTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.rankTV);
            teamName = itemView.findViewById(R.id.teamTV);
            matchesTV = itemView.findViewById(R.id.mTV);
            winTV = itemView.findViewById(R.id.wTV);
            loseTV = itemView.findViewById(R.id.lTV);
            tieTV = itemView.findViewById(R.id.tTV);
            ptsTV = itemView.findViewById(R.id.ptsTV);
            nrrTV = itemView.findViewById(R.id.nrrTV);
            nrTV = itemView.findViewById(R.id.nrTV);

        }
    }
}

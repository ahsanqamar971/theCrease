package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.TournamentModel;

import java.util.List;

public class CurrentTournamentAdapter extends RecyclerView.Adapter<CurrentTournamentAdapter.ViewHolder> {

    private Context context;
    private List<FixturesModel> fixturesModels;

    public CurrentTournamentAdapter(Context context, List<FixturesModel> fixturesModels){
        this.context = context;
        this.fixturesModels = fixturesModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.team_tourn_head, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.team_tour_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public void setFixturesModels(List<FixturesModel> fixturesModels){
        this.fixturesModels = fixturesModels;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FixturesModel model = fixturesModels.get(position);

        holder.srNo.setText(String.valueOf(position+1));
        holder.team1.setText(model.getTeam1());
        holder.team2.setText(model.getTeam2());
        holder.date.setText(model.getDateTime());
        holder.time.setText(model.getDateTime());
        holder.venue.setText(model.getVenue());
    }

    @Override
    public int getItemCount() {
        return fixturesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView srNo, team1, team2, date, time, venue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            team1 = itemView.findViewById(R.id.team1TV);
            team2 = itemView.findViewById(R.id.team2TV);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            venue = itemView.findViewById(R.id.venue);
        }
    }
}

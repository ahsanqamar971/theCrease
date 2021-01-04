package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.ScoreModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MatchApprovalAdapter extends RecyclerView.Adapter<MatchApprovalAdapter.ViewHolder> {

    private Context context;
    private List<ScoreModel> scoreModels;

    public MatchApprovalAdapter(Context context, List<ScoreModel> scoreModels){
        this.context = context;
        this.scoreModels = scoreModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.match_app_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ScoreModel model = scoreModels.get(position);

        holder.leagueName.setText(model.getLeagueName());
        holder.team1Name.setText(model.getTeam1Name());
        holder.team2Name.setText(model.getTeam2Name());
        holder.team1Overs.setText(String.valueOf(model.getOvers1()));
        holder.team2Overs.setText(String.valueOf(model.getOvers2()));
        holder.matchCondition.setText(model.getMatchCondition());
        holder.venue.setText(model.getVenue());
        holder.team1Score.setText(String.valueOf(model.getScore1()));
        holder.team2Score.setText(String.valueOf(model.getScore2()));
        holder.wonTV.setText(model.getWon());
        holder.date.setText(model.getDateTime());
        holder.momName.setText(model.getMomName());


        if(model.getTeam1Image().equals("")){
            holder.team1Image.setImageResource(R.drawable.dummy_image);
        }else {
            Picasso.get().load(model.getTeam1Image()).into(holder.team1Image);
        }
        if(model.getTeam2Image().equals("")){
            holder.team2Image.setImageResource(R.drawable.dummy_image);
        }else {
            Picasso.get().load(model.getTeam2Image()).into(holder.team2Image);
        }
        if(model.getMomPlayerImage().equals("")){
            holder.momImage.setImageResource(R.drawable.dummy_image);
        }else {
            Picasso.get().load(model.getMomPlayerImage()).into(holder.momImage);
        }
    }

    @Override
    public int getItemCount() {
        return scoreModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView leagueName, team1Name, team2Name, matchCondition, venue, team1Score, team2Score, team1Overs,
                team2Overs, wonTV, date, momName;

        private ImageView team1Image, team2Image, leagueMore, momImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leagueName = itemView.findViewById(R.id.leagueName);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            matchCondition = itemView.findViewById(R.id.matchConditionTV);
            venue = itemView.findViewById(R.id.matchVenue);
            team1Score = itemView.findViewById(R.id.score1);
            team2Score = itemView.findViewById(R.id.score2);
            team1Overs = itemView.findViewById(R.id.overs1);
            team2Overs = itemView.findViewById(R.id.overs2);
            wonTV = itemView.findViewById(R.id.wonTV);
            date = itemView.findViewById(R.id.dateTimeTV);
            momName = itemView.findViewById(R.id.momName);

            momImage = itemView.findViewById(R.id.momImage);
            team1Image = itemView.findViewById(R.id.team1Image);
            team2Image = itemView.findViewById(R.id.team2Image);
            leagueMore = itemView.findViewById(R.id.leagueMore);

        }
    }
}

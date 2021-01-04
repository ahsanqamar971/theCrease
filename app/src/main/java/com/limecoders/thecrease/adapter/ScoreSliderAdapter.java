package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.islamkhsh.CardSliderAdapter;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.ScoreModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreSliderAdapter extends RecyclerView.Adapter<ScoreSliderAdapter.ViewHolder> {
    
    private Context context;
    private List<ScoreModel> scoreModelList;
    
    public ScoreSliderAdapter(Context context, List<ScoreModel> scoreModelList){
        this.context = context;
        this.scoreModelList = scoreModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.score_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        ScoreModel model = scoreModelList.get(i);

        holder.leagueName.setText(model.getLeagueName());
        holder.dateTimeTV.setText(model.getDateTime());
        holder.momNameTV.setText(model.getMomName());
        holder.score1TV.setText(String.valueOf(model.getScore1()));
        holder.score2TV.setText(String.valueOf(model.getScore2()));
        holder.overs1TV.setText(String.valueOf(model.getOvers1()));
        holder.overs2TV.setText(String.valueOf(model.getOvers2()));
        holder.venueTV.setText(model.getVenue());
        holder.team1NameTV.setText(model.getTeam1Name());
        holder.team2NameTV.setText(model.getTeam2Name());
        holder.wonTV.setText(model.getWon());

        if(model.getTeam1Image().equals("")){
            holder.team1Image.setImageResource(R.drawable.dummy_image);
        }else {
            Glide.with(context).load(model.getTeam1Image()).into(holder.team1Image);
        }

        if(model.getTeam2Image().equals("")){
            holder.team2Image.setImageResource(R.drawable.dummy_image);
        }else {
            Glide.with(context).load(model.getTeam2Image()).into(holder.team2Image);
        }

        if(model.getMomPlayerImage().equals("")){
            holder.momPlayerImage.setImageResource(R.drawable.dummy_image);
        }else {
            Glide.with(context).load(model.getMomPlayerImage()).into(holder.momPlayerImage);
        }
    }

    @Override
    public int getItemCount() {
        return scoreModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView leagueName, venueTV, score1TV, score2TV, overs1TV, overs2TV, team1NameTV, team2NameTV,
                wonTV, dateTimeTV, momNameTV;
        private CircleImageView team1Image, team2Image, momPlayerImage;
        private ImageView leagueMore;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leagueName = itemView.findViewById(R.id.leagueName);
            leagueMore = itemView.findViewById(R.id.leagueMore);
            venueTV = itemView.findViewById(R.id.matchVenue);
            score1TV = itemView.findViewById(R.id.score1);
            score2TV = itemView.findViewById(R.id.score2);
            overs1TV = itemView.findViewById(R.id.overs1);
            overs2TV = itemView.findViewById(R.id.overs2);
            team1NameTV = itemView.findViewById(R.id.team1Name);
            team2NameTV = itemView.findViewById(R.id.team2Name);
            wonTV = itemView.findViewById(R.id.wonTV);
            dateTimeTV = itemView.findViewById(R.id.dateTimeTV);
            momNameTV = itemView.findViewById(R.id.playerName);
            team1Image = itemView.findViewById(R.id.team1Image);
            team2Image = itemView.findViewById(R.id.team2Image);
            momPlayerImage = itemView.findViewById(R.id.profileImage);
            
        }
    }
}

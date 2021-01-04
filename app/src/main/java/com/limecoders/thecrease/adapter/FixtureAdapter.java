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
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.FixturesModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.ViewHolder> {

    private List<FixturesModel> fixturesModels;
    private Context context;

    public FixtureAdapter(Context context, List<FixturesModel> fixtureModels) {
        this.context = context;
        this.fixturesModels = fixtureModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tourn_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FixturesModel model = fixturesModels.get(position);

        holder.dateTime.setText(model.getDateTime());
        holder.matchCondition.setText(model.getMatchCondition());
        holder.team1Name.setText(model.getTeam1());
        holder.team2Name.setText(model.getTeam2());
        holder.venue.setText(model.getVenue());

        holder.leagueMore.setVisibility(View.GONE);

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
    }

    @Override
    public int getItemCount() {
        return fixturesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView team1Name, team2Name, venue, matchCondition, dateTime;
        private CircleImageView team1Image, team2Image;
        private ImageView leagueMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leagueMore = itemView.findViewById(R.id.leagueMore);
            team1Image = itemView.findViewById(R.id.team1Image);
            team2Image = itemView.findViewById(R.id.team2Image);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            venue = itemView.findViewById(R.id.matchVenue);
            matchCondition = itemView.findViewById(R.id.matchConditionTV);
            dateTime = itemView.findViewById(R.id.dateTimeTV);

        }
    }
}

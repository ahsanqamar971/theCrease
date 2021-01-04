package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.MatchDetailActivity;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MatchSliderAdapter extends com.github.islamkhsh.CardSliderAdapter<MatchSliderAdapter.ViewHolder> {

    private Context context;
    private List<MatchRequestModel> models;

    private String team1Name, team2Name, umpire1Name, umpire2Name, team2Image, team1Image, momPlayerName, momPlayerImage;

    public MatchSliderAdapter(Context context, List<MatchRequestModel> fixturesModels){
        this.context = context;
        models = fixturesModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.tourn_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void bindVH(ViewHolder holder, int i) {
        MatchRequestModel model = models.get(i);

        holder.dateTime.setText(model.getDate()+"\t\t"+model.getTime());
        holder.matchCondition.setText(model.getMatchCondition());
        holder.venue.setText(model.getVenue());



        if(model.getTeamId().equals("1")){
            holder.team1Name.setText("Team 1");
            holder.team2Name.setText("Team 2");
            holder.team1Image.setImageResource(R.drawable.dummy_image);
            holder.team2Image.setImageResource(R.drawable.dummy_image);
        }else if(model.getTeamId().equals("3")){
            holder.team1Name.setText("Team 3");
            holder.team2Name.setText("Team 4");
            holder.team1Image.setImageResource(R.drawable.dummy_image);
            holder.team2Image.setImageResource(R.drawable.dummy_image);
        }else if(model.getTeamId().equals("5")){
            holder.team1Name.setText("Team 4");
            holder.team2Name.setText("Team 5");
            holder.team1Image.setImageResource(R.drawable.dummy_image);
            holder.team2Image.setImageResource(R.drawable.dummy_image);
        }else if(model.getTeamId().equals("7")){
            holder.team1Name.setText("Team 7");
            holder.team2Name.setText("Team 8");
            holder.team1Image.setImageResource(R.drawable.dummy_image);
            holder.team2Image.setImageResource(R.drawable.dummy_image);
        }else {
            getTeam1Info(model.getTeamId(),holder);
            getTeam2Info(model.getTeam2Id(),holder);

            getMomPlayer(model.getMomPlayerId());
            getUmpire1Info(model.getUmpire1Id(),holder);
            getUmpire2Info(model.getUmpire2Id(),holder);
        }

        holder.leagueMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                team1Name, team2Name, umpire1Name, umpire2Name, team2Image, team1Image, momPlayerName, momPlayerImage
                context.startActivity(new Intent(context, MatchDetailActivity.class)
                .putExtra("matchId",model.getId()).putExtra("team1Name",team1Name)
                        .putExtra("team2Name",team2Name).putExtra("umpire1Name",umpire1Name)
                        .putExtra("umpire2Name",umpire2Name).putExtra("team1Image",team1Image)
                        .putExtra("team2Image",team2Image).putExtra("momPlayerName",momPlayerName)
                        .putExtra("momPlayerImage",momPlayerImage));
            }
        });

    }
    public void getUmpire1Info(String id, ViewHolder holder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                umpire1Name = dataSnapshot.child(IConstants.NAME).getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }public void getUmpire2Info(String id, ViewHolder holder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                umpire2Name = dataSnapshot.child(IConstants.NAME).getValue().toString();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void getMomPlayer(String id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                momPlayerImage = snapshot.child(IConstants.PROFILEIMAGE).getValue().toString();
                momPlayerName = snapshot.child(IConstants.NAME).getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getTeam1Info(String id, ViewHolder holder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                team1Name = dataSnapshot.child(IConstants.NAME).getValue().toString();
                team1Image = dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString();
                    holder.team1Name.setText(dataSnapshot.child(IConstants.NAME).getValue().toString());
                    if(dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString().equals("")){
                        holder.team1Image.setImageResource(R.drawable.dummy_image);
                    }else {
                        Picasso.get().load(dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString())
                                .into(holder.team1Image);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getTeam2Info(String id, ViewHolder holder){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                team2Name = dataSnapshot.child(IConstants.NAME).getValue().toString();
                team2Image = dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString();
                    holder.team2Name.setText(dataSnapshot.child(IConstants.NAME).getValue().toString());
                    if(dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString().equals("")){
                        holder.team2Image.setImageResource(R.drawable.dummy_image);
                    }else {
                        Picasso.get().load(dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString())
                                .into(holder.team2Image);
                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
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
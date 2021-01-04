package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.dashboards.adapters.BallsAdapter;
import com.limecoders.thecrease.adapter.BowlDetailsAdapter;
import com.limecoders.thecrease.adapter.MatchDetailAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.MatchDetailModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MatchDetailActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MatchDetailAdapter matchDetailAdapter;
    private List<MatchDetailModel> matchDetailModels = new ArrayList<>();

    private TextView leagueName, momPlayerName, team1Name, team2Name, team1Overs, team2Overs, team1Score, team2Score,
    matchCondition, venue, wonBy, dateTime, umpire;
    private ImageView team1Image, team2Image, momPlayerImage;

    private String team1Names, team2Names, umpire1Name, umpire2Name, team2Images, team1Images, momPlayerNames, momPlayerImages;

    private RecyclerView bowlDetRecyclerView;
    private BowlDetailsAdapter bowlDetailsAdapter;
    private List<MatchDetailModel> bowlDetModel = new ArrayList<>();

    private String matchId;

    private TextView firstInnings, secondInnings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);

        Utils.initpDialog(this,"Please wait");
        Utils.showpDialog();

        if(getIntent().hasExtra("matchId")){
            team2Images = getIntent().getStringExtra("team2Image");
            team1Images = getIntent().getStringExtra("team1Image");
            momPlayerImages = getIntent().getStringExtra("momPlayerImage");
            momPlayerNames = getIntent().getStringExtra("momPlayerName");
            team1Names = getIntent().getStringExtra("team1Name");
            team2Names = getIntent().getStringExtra("team2Name");
            umpire1Name = getIntent().getStringExtra("umpire1Name");
            umpire2Name = getIntent().getStringExtra("umpire2Name");
            matchId = getIntent().getStringExtra("matchId");
        }

        firstInnings = findViewById(R.id.inings1);
        secondInnings = findViewById(R.id.inings2);
        umpire = findViewById(R.id.umpire);
        leagueName = findViewById(R.id.leagueName);
        momPlayerName = findViewById(R.id.playerName);
        team1Name = findViewById(R.id.team1Name);
        team2Name = findViewById(R.id.team2Name);
        team1Overs = findViewById(R.id.overs1);
        team2Overs = findViewById(R.id.overs2);
        team1Score = findViewById(R.id.score1);
        team2Score = findViewById(R.id.score2);
        matchCondition = findViewById(R.id.matchConditionTV);
        venue = findViewById(R.id.matchVenue);
        wonBy = findViewById(R.id.wonTV);
        dateTime = findViewById(R.id.dateTimeTV);
        team1Image = findViewById(R.id.team1Image);
        team2Image = findViewById(R.id.team2Image);
        momPlayerImage = findViewById(R.id.profileImage);

        recyclerView = findViewById(R.id.matchDetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        bowlDetRecyclerView = findViewById(R.id.bowlDetRecylcerView);
        bowlDetRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bowlDetRecyclerView.setHasFixedSize(true);

        firstInnings.setTypeface(null, Typeface.BOLD);
        secondInnings.setTypeface(null, Typeface.NORMAL);

        firstInnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstInnings.setTypeface(null, Typeface.BOLD);
                secondInnings.setTypeface(null, Typeface.NORMAL);
            }
        });

        secondInnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstInnings.setTypeface(null, Typeface.NORMAL);
                secondInnings.setTypeface(null, Typeface.BOLD);
            }
        });

        if(team1Names!=null) {
            getBatsmanDetails();
            getBowlerDetails();

            getMatchDetail();
        }
        if(team1Names==null){
            Utils.hidepDialog();
            bowlDetModel.add(new MatchDetailModel("1", "Bowler 1", "20", "", "", "",
                    "", "", "", "", false,
            false, false, false, false, false, false,
            "4", "3", "5", "3-20", "", "",
                    "", "", "", "team2","","","",""));

            bowlDetModel.add(new MatchDetailModel("1", "Bowler 2", "40", "", "", "",
                    "", "", "", "", false,
                    false, false, false, false, false, false,
                    "4", "1", "10", "1-40", "", "",
                    "", "", "", "team2","","","",""));

            bowlDetModel.add(new MatchDetailModel("1", "Bowler 3", "35", "", "", "",
                    "", "", "", "", false,
                    false, false, false, false, false, false,
                    "4", "0", "8.87", "0-35", "", "",
                    "", "", "", "team2","","","",""));

            bowlDetModel.add(new MatchDetailModel("1", "Bowler 4", "28", "", "", "",
                    "", "", "", "", false,
                    false, false, false, false, false, false,
                    "3", "0", "7", "3-28", "", "",
                    "", "", "", "team2","","","",""));

            bowlDetModel.add(new MatchDetailModel("1", "Bowler 5", "20", "", "", "",
                    "", "", "", "", false,
                    false, false, false, false, false, false,
                    "1", "0", "20", "0-20", "", "",
                    "", "", "", "team2","","","",""));


            bowlDetailsAdapter = new BowlDetailsAdapter(MatchDetailActivity.this, bowlDetModel);
            bowlDetRecyclerView.setAdapter(bowlDetailsAdapter);

            matchDetailModels.add(new MatchDetailModel("1", "Bat 1", "52", "26", "4", "3",
                    "200", "89-2", "Fielder 5", "Bowler 1", true,
                    false, false, false, false, false, false,
                    "", "0", "", "", "", "",
                    "", "", "", "team1","","","",""));

            matchDetailModels.add(new MatchDetailModel("1", "Bat 2", "30", "20", "2", "1",
                    "1%0", "59-1", "", "Bowler 1", false,
                    false, true, false, false, false, false,
                    "", "0", "", "", "", "",
                    "", "", "", "team1","","","",""));

            matchDetailModels.add(new MatchDetailModel("1", "Bat 3", "9", "9", "4", "3",
                    "100", "93-3", "", "Bowler 1", false,
                    true, false, false, false, false, false,
                    "", "0", "", "", "", "",
                    "", "", "", "team1","","","",""));

            matchDetailModels.add(new MatchDetailModel("1", "Bat 4", "22", "17", "4", "3",
                    "200", "", "", "", false,
                    false, false, false, false, false, false,
                    "", "0", "", "", "", "",
                    "", "", "", "team1","","","",""));

            matchDetailModels.add(new MatchDetailModel("1", "Bat 5", "42", "24", "4", "3",
                    "200", "", "", "", false,
                    false, false, false, false, false, false,
                    "", "0", "", "", "", "",
                    "", "", "", "team1","","","",""));


            matchDetailAdapter = new MatchDetailAdapter(MatchDetailActivity.this,matchDetailModels,false);
            recyclerView.setAdapter(matchDetailAdapter);
        }

    }

    private MatchRequestModel requestModel;

    private void getMatchDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                requestModel = new MatchRequestModel(dataSnapshot.child("id").getValue().toString(),
                        dataSnapshot.child("teamId").getValue().toString(), dataSnapshot.child("teamName").getValue().toString(),
                        dataSnapshot.child("type").getValue().toString(), dataSnapshot.child("venue").getValue().toString(),
                        dataSnapshot.child("date").getValue().toString(), dataSnapshot.child("time").getValue().toString(),
                        Boolean.parseBoolean(dataSnapshot.child("isRejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("umpire1Rejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("umpire1Accepted").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("umpire2Rejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("umpire2Accepted").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("scorerAccepted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("scorerRejected").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("isCompleted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isFinished").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("teamAccepted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("teamRejected").getValue().toString()),
                        dataSnapshot.child("team1PlayersId").getValue().toString(), dataSnapshot.child("team2PlayersId").getValue().toString(),
                        dataSnapshot.child("team2Id").getValue().toString(),dataSnapshot.child("umpire1Id").getValue().toString(),
                        dataSnapshot.child("umpire2Id").getValue().toString(),dataSnapshot.child("scorerId").getValue().toString(),
                        dataSnapshot.child("score1").getValue().toString(),dataSnapshot.child("score2").getValue().toString(),
                        dataSnapshot.child("momPlayerId").getValue().toString(),dataSnapshot.child("won").getValue().toString(),
                        dataSnapshot.child("matchCondition").getValue().toString(),dataSnapshot.child("leagueName").getValue().toString(),
                        dataSnapshot.child("overs1").getValue().toString(),dataSnapshot.child("overs2").getValue().toString(),
                        dataSnapshot.child("firstInnings").getValue().toString(),dataSnapshot.child("tossWon").getValue().toString(),dataSnapshot.child("batFirst").getValue().toString());

                leagueName.setText(requestModel.getLeagueName());
                team2Name.setText(team2Names);
                team1Name.setText(team1Names);
                umpire.setText("Umpire1: "+umpire1Name+"\nUmpire2: "+umpire2Name);
                momPlayerName.setText(momPlayerNames);
                team1Score.setText(requestModel.getScore1());
                team2Score.setText(requestModel.getScore2());
                team1Overs.setText(requestModel.getOvers1());
                team2Overs.setText(requestModel.getOvers2());
                matchCondition.setText(requestModel.getMatchCondition());
                venue.setText(requestModel.getVenue());
                wonBy.setText(requestModel.getWon());
                dateTime.setText(requestModel.getDate());

                Picasso.get().load(momPlayerImages).into(momPlayerImage);
                Picasso.get().load(team1Images).into(team1Image);
                Picasso.get().load(team2Images).into(team2Image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getBowlerDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId).child(IConstants.BALLDETAILS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(team1Name)){
                        bowlDetModel.add(new MatchDetailModel(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("playerName").getValue().toString(),
                                dataSnapshot.child("runs").getValue().toString(), dataSnapshot.child("ballsPlayed").getValue().toString(),
                                dataSnapshot.child("fours").getValue().toString(), dataSnapshot.child("sixes").getValue().toString(),
                                dataSnapshot.child("strikeRate").getValue().toString(), dataSnapshot.child("fow").getValue().toString(),
                                dataSnapshot.child("catchBy").getValue().toString(), dataSnapshot.child("ballBy").getValue().toString(),
                                Boolean.parseBoolean(dataSnapshot.child("isCatch").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isBowled").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isLbw").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isRetire").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isRunOut").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isOther").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isStump").getValue().toString()), dataSnapshot.child("overs").getValue().toString(),
                                dataSnapshot.child("wickets").getValue().toString(), dataSnapshot.child("economy").getValue().toString(),
                                dataSnapshot.child("bb").getValue().toString(), dataSnapshot.child("fRunOut").getValue().toString(),
                                dataSnapshot.child("fCatches").getValue().toString(), dataSnapshot.child("fStumps").getValue().toString(),
                                dataSnapshot.child("ballStatus").getValue().toString(),dataSnapshot.child("fielderName").getValue().toString(),
                                dataSnapshot.child("teamName").getValue().toString(),dataSnapshot.child("playerId").getValue().toString(),
                                dataSnapshot.child("bowlerId").getValue().toString(),dataSnapshot.child("status").getValue().toString(),
                                dataSnapshot.child("timeStamp").getValue().toString()));
                    }
                }

                bowlDetailsAdapter = new BowlDetailsAdapter(MatchDetailActivity.this, bowlDetModel);
                bowlDetRecyclerView.setAdapter(bowlDetailsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void getBatsmanDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId).child(IConstants.BALLDETAILS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(team1Name)){
                        matchDetailModels.add(new MatchDetailModel(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("playerName").getValue().toString(),
                                dataSnapshot.child("runs").getValue().toString(), dataSnapshot.child("ballsPlayed").getValue().toString(),
                                dataSnapshot.child("fours").getValue().toString(), dataSnapshot.child("sixes").getValue().toString(),
                                dataSnapshot.child("strikeRate").getValue().toString(), dataSnapshot.child("fow").getValue().toString(),
                                dataSnapshot.child("catchBy").getValue().toString(), dataSnapshot.child("ballBy").getValue().toString(),
                                Boolean.parseBoolean(dataSnapshot.child("isCatch").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isBowled").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isLbw").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isRetire").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isRunOut").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isOther").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isStump").getValue().toString()), dataSnapshot.child("overs").getValue().toString(),
                                dataSnapshot.child("wickets").getValue().toString(), dataSnapshot.child("economy").getValue().toString(),
                                dataSnapshot.child("bb").getValue().toString(), dataSnapshot.child("fRunOut").getValue().toString(),
                                dataSnapshot.child("fCatches").getValue().toString(), dataSnapshot.child("fStumps").getValue().toString(),
                                dataSnapshot.child("ballStatus").getValue().toString(),dataSnapshot.child("fielderName").getValue().toString(),
                                dataSnapshot.child("teamName").getValue().toString(),dataSnapshot.child("playerId").getValue().toString(),
                                dataSnapshot.child("bowlerId").getValue().toString(),dataSnapshot.child("status").getValue().toString(),
                                dataSnapshot.child("timeStamp").getValue().toString()));
                    }
                }

                matchDetailAdapter = new MatchDetailAdapter(MatchDetailActivity.this,matchDetailModels,false);
                recyclerView.setAdapter(matchDetailAdapter);
                Utils.hidepDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
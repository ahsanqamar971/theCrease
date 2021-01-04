package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.dashboards.adapters.BallsAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.BatsmanAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.MatchDetailModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.Users;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class StartMatchbActivity extends AppCompatActivity {

    private TextView innings1, innings2, overs;
    private Button saveBall, endInnings;

    private Spinner bowlerSpinner, ballStatSpinner, runsSpinner, dimissTypeSpinner, fielderSpinner,
    newBatSpinner;

    private RadioGroup batRG;
    private boolean batChecked = false;
    private RadioButton bat1, bat2;

    private String umpire1Name, umpire2Name, team1Name, team2Name, matchId, oversStr;

    private String ballsPlayed = "0", fours ="0", sixes ="0", fow ="0", wickets ="0", economy ="0", bb ="0",
            fRunOut ="0", fCatches ="0", fStumps ="0", runs ="0";

    private String ballsPlayed2 ="0", fours2 ="0", sixes2 ="0", fow2 ="0", wickets2 ="0", economy2 ="0", bb2 ="0",
            fRunOut2 = "0", fCatches2 = "0", fStumps2 = "0", runs2 = "0", overs1 = "0";

    private String tossWon, batFirst, bowlTeam;
    private String score1, score2;
    private String bat1Id = "", bat2Id = "", bowlId = "";

    private List<Users> team1SquadList = new ArrayList<>();

    private List<Users> team2SquadList = new ArrayList<>();

    private List<MatchDetailModel> bowlerDetail = new ArrayList<>();
    private List<MatchDetailModel> batsmanDetail = new ArrayList<>();

    private RecyclerView overRecyclerView, batsmanRecyclerView;
    private BatsmanAdapter batsmanAdapter;
    private BallsAdapter ballsAdapter;

    private boolean isSecondInnings = false;
    private String teamName;

    private List<Users> users = new ArrayList<>();

    private String team1PlayersId = "", team2PlayersId = "";

    private boolean getDetails = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_matchb);

        if(getIntent().hasExtra("team1Name")){
            isSecondInnings = getIntent().getBooleanExtra("isSecondInnings",false);
            matchId = getIntent().getStringExtra("matchId");
            team1Name = getIntent().getStringExtra("team1Name");
            team2Name = getIntent().getStringExtra("team2Name");
            umpire1Name = getIntent().getStringExtra("umpire1Name");
            umpire2Name = getIntent().getStringExtra("umpire2Name");
            batFirst = getIntent().getStringExtra("batFirst");
            tossWon = getIntent().getStringExtra("tossWon");
            bowlTeam = getIntent().getStringExtra("bowlTeam");
            team1PlayersId = getIntent().getStringExtra("team1PlayersId");
            team2PlayersId = getIntent().getStringExtra("team2PlayersId");
        }

        if(isSecondInnings){
            teamName = team1Name;
            team1Name = team2Name;
            team2Name = teamName;
        }

        innings1 = findViewById(R.id.innings1);
        innings2 = findViewById(R.id.innings2);
        overs = findViewById(R.id.currentOver);
        saveBall = findViewById(R.id.saveBall);
        endInnings = findViewById(R.id.endInnings);
        bowlerSpinner = findViewById(R.id.bowlerSpinner);
        ballStatSpinner = findViewById(R.id.ballStatSpinner);
        runsSpinner = findViewById(R.id.runsSpinner);
        dimissTypeSpinner = findViewById(R.id.dismissTypeSpinner);
        fielderSpinner = findViewById(R.id.fielderSpinner);
        newBatSpinner = findViewById(R.id.newBatSpinner);
        batRG = findViewById(R.id.batRG);
        bat1 = findViewById(R.id.bat1);
        bat2 = findViewById(R.id.bat2);

        overRecyclerView = findViewById(R.id.overRecyclerView);
        overRecyclerView.setLayoutManager(new GridLayoutManager(this,6));
        overRecyclerView.setHasFixedSize(true);

        batsmanRecyclerView = findViewById(R.id.matchDetRecyclerView);
        batsmanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        batsmanRecyclerView.setHasFixedSize(true);

        innings1.setText("0\t"+team1Name);
        innings2.setText("Yet To Bat\t\t\t"+team2Name);
        overs.setText("0");

        clickListeners();

        getScore();
        getStrikerNonStriker();

        getBatSquad();
        getBowlSquad();

//        getPlayersDetail();

        getBallDetails();

    }

    private Users bowler;
    public void getBowlerId(String bowlerName){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(snapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")
                    && snapshot.child(IConstants.NAME).getValue().toString().equals(bowlerName)
                    && snapshot.child(IConstants.TEAMNAME).getValue().toString().equals(bowlTeam)){
                        bowler = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
                                snapshot.child(IConstants.ROLE).getValue().toString(), snapshot.child(IConstants.AGE).getValue().toString(),
                                snapshot.child(IConstants.SEX).getValue().toString(), snapshot.child(IConstants.NAME).getValue().toString(),
                                snapshot.child(IConstants.RELIGION).getValue().toString(), snapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                snapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.INTEAM).getValue().toString()),
                                snapshot.child(IConstants.BATTYPE).getValue().toString(), snapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                snapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.RANK).getValue().toString()),
                                snapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                snapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), snapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.TOURNR).getValue().toString()), snapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                snapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                snapshot.child(IConstants.TOUR1).getValue().toString(), snapshot.child(IConstants.TOUR2).getValue().toString(),
                                snapshot.child(IConstants.EXPERIENCE).getValue().toString(), snapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                snapshot.child(IConstants.EDUCATION).getValue().toString(), snapshot.child(IConstants.COURSES).getValue().toString(),
                                snapshot.child(IConstants.BOWLSTYLE).getValue().toString(), snapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.BATHS).getValue().toString()), snapshot.child(IConstants.DEBUT).getValue().toString(),
                                snapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(snapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.ECON).getValue().toString()),
                                snapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.RATING).getValue().toString()), snapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                snapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                snapshot.child(IConstants.ADDRESS).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString()));
                    }
                }
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                if(getDetails) {
                    saveEveryBall(bowlerSpinner.getSelectedItem().toString(), bat1.getText().toString(),
                            ballStatSpinner.getSelectedItem().toString(), runsSpinner.getSelectedItem().toString(),
                            dimissTypeSpinner.getSelectedItem().toString(), fielderSpinner.getSelectedItem().toString(),
                            newBatSpinner.getSelectedItem().toString());
                }
//                    }
//                },1500);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.hidepDialog();
            }
        });
    }


    private Users batsman;
    public void getBatsmanId(String batsmanName){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(snapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")
                            && snapshot.child(IConstants.NAME).getValue().toString().equals(batsmanName)
                            && snapshot.child(IConstants.TEAMNAME).getValue().toString().equals(team1Name)){
                        batsman = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
                                snapshot.child(IConstants.ROLE).getValue().toString(), snapshot.child(IConstants.AGE).getValue().toString(),
                                snapshot.child(IConstants.SEX).getValue().toString(), snapshot.child(IConstants.NAME).getValue().toString(),
                                snapshot.child(IConstants.RELIGION).getValue().toString(), snapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                snapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.INTEAM).getValue().toString()),
                                snapshot.child(IConstants.BATTYPE).getValue().toString(), snapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                snapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.RANK).getValue().toString()),
                                snapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                snapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), snapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.TOURNR).getValue().toString()), snapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                snapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                snapshot.child(IConstants.TOUR1).getValue().toString(), snapshot.child(IConstants.TOUR2).getValue().toString(),
                                snapshot.child(IConstants.EXPERIENCE).getValue().toString(), snapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                snapshot.child(IConstants.EDUCATION).getValue().toString(), snapshot.child(IConstants.COURSES).getValue().toString(),
                                snapshot.child(IConstants.BOWLSTYLE).getValue().toString(), snapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.BATHS).getValue().toString()), snapshot.child(IConstants.DEBUT).getValue().toString(),
                                snapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(snapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.ECON).getValue().toString()),
                                snapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.RATING).getValue().toString()), snapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                snapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                snapshot.child(IConstants.ADDRESS).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.hidepDialog();
            }
        });
    }

    private void getStrikerNonStriker() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(users.size()>0){
                    users.clear();
                }

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                    if (snapshot.child(IConstants.TEAMNAME).getValue().toString().equals(batFirst) &&
                    Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()) &&
                            snapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player") &&
                            snapshot.child(IConstants.INFO).getValue().toString().toLowerCase().equals("playing")){
                        users.add(new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
                                snapshot.child(IConstants.ROLE).getValue().toString(), snapshot.child(IConstants.AGE).getValue().toString(),
                                snapshot.child(IConstants.SEX).getValue().toString(), snapshot.child(IConstants.NAME).getValue().toString(),
                                snapshot.child(IConstants.RELIGION).getValue().toString(), snapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                snapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.INTEAM).getValue().toString()),
                                snapshot.child(IConstants.BATTYPE).getValue().toString(), snapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                snapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.RANK).getValue().toString()),
                                snapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                snapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), snapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.TOURNR).getValue().toString()), snapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                snapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                snapshot.child(IConstants.TOUR1).getValue().toString(), snapshot.child(IConstants.TOUR2).getValue().toString(),
                                snapshot.child(IConstants.EXPERIENCE).getValue().toString(), snapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                snapshot.child(IConstants.EDUCATION).getValue().toString(), snapshot.child(IConstants.COURSES).getValue().toString(),
                                snapshot.child(IConstants.BOWLSTYLE).getValue().toString(), snapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.BATHS).getValue().toString()), snapshot.child(IConstants.DEBUT).getValue().toString(),
                                snapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(snapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.ECON).getValue().toString()),
                                snapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.RATING).getValue().toString()), snapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                snapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                snapshot.child(IConstants.ADDRESS).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString())));
                    }
                }

                getBatsmanDetail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getBallDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId).child(IConstants.BALLDETAILS);

        reference.orderByChild("timeStamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(bowlerDetail.size()>0){
                    bowlerDetail.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(team1Name)){
                        bowlerDetail.add(new MatchDetailModel(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("playerName").getValue().toString(),
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

                ballsAdapter = new BallsAdapter(StartMatchbActivity.this,bowlerDetail);
                overRecyclerView.setAdapter(ballsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    String totalRuns1 = "0", totalFours1 = "0", totalSixes1 = "0", totalBallsPlayed1 = "0";
    String totalRuns2 = "0", totalFours2 = "0", totalSixes2 = "0", totalBallsPlayed2 = "0";

    private List<MatchDetailModel> batsman2Detail = new ArrayList<>();

    private void getBatsmanDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId).child(IConstants.BALLDETAILS);

        reference.orderByChild("timeStamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(batsman2Detail.size()>0){
                    batsman2Detail.clear();
                }

                if(batsmanDetail.size()>0){
                    batsmanDetail.clear();
                }
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(batFirst)){
                        batsmanDetail.add(new MatchDetailModel(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("playerName").getValue().toString(),
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

                        if(bat1Id.equals(dataSnapshot.child("playerId").getValue().toString())){
                            String run = dataSnapshot.child("runs").getValue().toString();
                            totalRuns1 = String.valueOf(Integer.parseInt(totalRuns1) + Integer.parseInt(run));
                            String four = dataSnapshot.child("fours").getValue().toString();
                            totalFours1 = String.valueOf(Integer.parseInt(totalFours1) + Integer.parseInt(four));
                            String six = dataSnapshot.child("sixes").getValue().toString();
                            totalSixes1 = String.valueOf(Integer.parseInt(totalSixes1) + Integer.parseInt(six));
                            String ballsPlayed = dataSnapshot.child("ballsPlayed").getValue().toString();
                            totalBallsPlayed1 = String.valueOf(Integer.parseInt(totalBallsPlayed1) + Integer.parseInt(ballsPlayed));
                        }else if(bat2Id.equals(dataSnapshot.child("playerId").getValue().toString())){
                            String run = dataSnapshot.child("runs").getValue().toString();
                            totalRuns2 = String.valueOf(Integer.parseInt(totalRuns2) + Integer.parseInt(run));
                            String four = dataSnapshot.child("fours").getValue().toString();
                            totalFours2 = String.valueOf(Integer.parseInt(totalFours2) + Integer.parseInt(four));
                            String six = dataSnapshot.child("sixes").getValue().toString();
                            totalSixes2 = String.valueOf(Integer.parseInt(totalSixes2) + Integer.parseInt(six));
                            String ballsPlayed = dataSnapshot.child("ballsPlayed").getValue().toString();
                            totalBallsPlayed2 = String.valueOf(Integer.parseInt(totalBallsPlayed2) + Integer.parseInt(ballsPlayed));
                        }
                    }
                }

                if(batsmanDetail.size()==0){
                    for(int i=0;i<users.size();i++) {
                        if(i==0) {
                            batsmanDetail.add(new MatchDetailModel(users.get(i).getId(), users.get(i).getName(), "0", "0", "0", "0",
                                    "0", "", "", "", false,
                                    false, false, false, false, false, false,
                                    "", "", "", "", "", "",
                                    "", "", "", users.get(i).getTeamName(), bat1Id,bowlId,"playing",""));
                        }else{

                            batsmanDetail.add(new MatchDetailModel(users.get(i).getId(), users.get(i).getName(), "0", "0", "0", "0",
                                    "0", "", "", "", false,
                                    false, false, false, false, false, false,
                                    "", "", "", "", "", "",
                                    "", "", "", users.get(i).getTeamName(), bat2Id,bowlId,"playing",""));
                        }
                    }
                    bat1.setText(users.get(0).getName());
                    bat2.setText(users.get(1).getName());
                }
                if(batsmanDetail.size()==1){
                    for(int i=0;i<users.size();i++){
                        if(!users.get(i).getName().equals(batsmanDetail.get(0).getPlayerName())) {
                            batsmanDetail.add(new MatchDetailModel(users.get(i).getId(), users.get(i).getName(), "0", "0", "0", "0",
                                    "0", "", "", "", false,
                                    false, false, false, false, false, false,
                                    "", "", "", "", "", "",
                                    "", "", "", users.get(i).getTeamName(),bat2Id,bowlId,"playing",""));
                        }
                    }
                    bat1.setText(batsmanDetail.get(0).getPlayerName());
                    bat2.setText(batsmanDetail.get(1).getPlayerName());
                }
                if(batsmanDetail.size()>=2) {
                    for (int i = 0; i < users.size(); i++) {
                        if (users.get(i).getInfo().equals("playing") && users.get(i).getTeamName().equals(batFirst)) {
                            bat1.setText(users.get(0).getName());
                            bat2.setText(users.get(1).getName());
                        }
                    }
                }

                batsmanAdapter = new BatsmanAdapter(StartMatchbActivity.this, batsmanDetail);
                batsmanRecyclerView.setAdapter(batsmanAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Utils.hidepDialog();
            }
        });
    }

    private void getBatSquad() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(team1SquadList.size()>0){
                    team1SquadList.clear();
                }

                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (snapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")
                            &&  snapshot.child(IConstants.TEAMNAME).getValue().toString().equals(batFirst)
                            &&  (team1PlayersId.contains(snapshot.child("id").getValue().toString())
                            ||  team2PlayersId.contains(snapshot.child("id").getValue().toString()))) {
                        team1SquadList.add(new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
                                snapshot.child(IConstants.ROLE).getValue().toString(), snapshot.child(IConstants.AGE).getValue().toString(),
                                snapshot.child(IConstants.SEX).getValue().toString(), snapshot.child(IConstants.NAME).getValue().toString(),
                                snapshot.child(IConstants.RELIGION).getValue().toString(), snapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                snapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.INTEAM).getValue().toString()),
                                snapshot.child(IConstants.BATTYPE).getValue().toString(), snapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                snapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.RANK).getValue().toString()),
                                snapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                snapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), snapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.TOURNR).getValue().toString()), snapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                snapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                snapshot.child(IConstants.TOUR1).getValue().toString(), snapshot.child(IConstants.TOUR2).getValue().toString(),
                                snapshot.child(IConstants.EXPERIENCE).getValue().toString(), snapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                snapshot.child(IConstants.EDUCATION).getValue().toString(), snapshot.child(IConstants.COURSES).getValue().toString(),
                                snapshot.child(IConstants.BOWLSTYLE).getValue().toString(), snapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.BATHS).getValue().toString()), snapshot.child(IConstants.DEBUT).getValue().toString(),
                                snapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(snapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.ECON).getValue().toString()),
                                snapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.RATING).getValue().toString()), snapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                snapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                snapshot.child(IConstants.ADDRESS).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString())));

                    }
                }
                ArrayList<String> team1PlayerNameList = new ArrayList<>();
                ArrayList<String> team1PlayerIdList = new ArrayList<>();
                team1PlayerNameList.add("None");
                for (int i=0;i<team1SquadList.size();i++){
                    team1PlayerNameList.add(team1SquadList.get(i).getName());
                    team1PlayerIdList.add(team1SquadList.get(i).getId());
                }

                ArrayAdapter<String> newBatAdapter = new ArrayAdapter<String>(StartMatchbActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        team1PlayerNameList);

                newBatSpinner.setAdapter(newBatAdapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getBowlSquad() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(team2SquadList.size()>0){
                    team2SquadList.clear();
                }

                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (snapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")
                            &&  snapshot.child(IConstants.TEAMNAME).getValue().toString().equals(bowlTeam)
                            &&  (team1PlayersId.contains(snapshot.child("id").getValue().toString())
                            ||  team2PlayersId.contains(snapshot.child("id").getValue().toString()))) {
                        team2SquadList.add(new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
                                snapshot.child(IConstants.ROLE).getValue().toString(), snapshot.child(IConstants.AGE).getValue().toString(),
                                snapshot.child(IConstants.SEX).getValue().toString(), snapshot.child(IConstants.NAME).getValue().toString(),
                                snapshot.child(IConstants.RELIGION).getValue().toString(), snapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                snapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.INTEAM).getValue().toString()),
                                snapshot.child(IConstants.BATTYPE).getValue().toString(), snapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                snapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.RANK).getValue().toString()),
                                snapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                snapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), snapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.TOURNR).getValue().toString()), snapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                snapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                snapshot.child(IConstants.TOUR1).getValue().toString(), snapshot.child(IConstants.TOUR2).getValue().toString(),
                                snapshot.child(IConstants.EXPERIENCE).getValue().toString(), snapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                snapshot.child(IConstants.EDUCATION).getValue().toString(), snapshot.child(IConstants.COURSES).getValue().toString(),
                                snapshot.child(IConstants.BOWLSTYLE).getValue().toString(), snapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(snapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.BATHS).getValue().toString()), snapshot.child(IConstants.DEBUT).getValue().toString(),
                                snapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(snapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(snapshot.child(IConstants.ECON).getValue().toString()),
                                snapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(snapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(snapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(snapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(snapshot.child(IConstants.RATING).getValue().toString()), snapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                snapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                snapshot.child(IConstants.ADDRESS).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString())));
                    }
                }

                ArrayList<String> team2PlayerNameList = new ArrayList<>();
                ArrayList<String> team2PlayerIdList = new ArrayList<>();
                ArrayList<String> fielderList = new ArrayList<>();
                fielderList.add("None");
                for (int i=0;i<team2SquadList.size();i++){
                    fielderList.add(team2SquadList.get(i).getName());
                    team2PlayerNameList.add(team2SquadList.get(i).getName());
                    team2PlayerIdList.add(team2SquadList.get(i).getId());
                }
                ArrayAdapter<String> newBatAdapter = new ArrayAdapter<String>(StartMatchbActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        team2PlayerNameList);

                ArrayAdapter<String> fielder = new ArrayAdapter<String>(StartMatchbActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        fielderList);

                bowlerSpinner.setAdapter(newBatAdapter);
                fielderSpinner.setAdapter(fielder);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<MatchDetailModel> matchDetailModels = new ArrayList<>();
    private void getPlayersDetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId).child(IConstants.BALLDETAILS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
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
                    overs1 = dataSnapshot.child("overs").getValue().toString();
                }
                overs.setText(overs1);
                int counter =0, counter2=0;
                for (int i=0;i<matchDetailModels.size();i++){
                    if(matchDetailModels.get(i).getPlayerName().equals(bat1.getText().toString())){
                        counter = counter + 1;
                        ballsPlayed = matchDetailModels.get(i).getBallsPlayed();
                        fours = matchDetailModels.get(i).getFours();
                        sixes = matchDetailModels.get(i).getSixes();
                        fow = matchDetailModels.get(i).getFow();
                        wickets = matchDetailModels.get(i).getWickets();
                        economy = matchDetailModels.get(i).getEconomy();
                        bb = matchDetailModels.get(i).getBb();
                        fRunOut = matchDetailModels.get(i).getfRonOut();
                        fCatches = matchDetailModels.get(i).getfCatches();
                        fStumps = matchDetailModels.get(i).getfStumps();
                        runs = matchDetailModels.get(i).getRuns();
                    }else if(matchDetailModels.get(i).getPlayerName().equals(bat2.getText().toString())){
                        counter2 = counter2 +1;
                        ballsPlayed2 = matchDetailModels.get(i).getBallsPlayed();
                        fours2 = matchDetailModels.get(i).getFours();
                        sixes2 = matchDetailModels.get(i).getSixes();
                        fow2 = matchDetailModels.get(i).getFow();
                        wickets2 = matchDetailModels.get(i).getWickets();
                        economy2 = matchDetailModels.get(i).getEconomy();
                        bb2 = matchDetailModels.get(i).getBb();
                        fRunOut2 = matchDetailModels.get(i).getfRonOut();
                        fCatches2 = matchDetailModels.get(i).getfCatches();
                        fStumps2 = matchDetailModels.get(i).getfStumps();
                        runs2 = matchDetailModels.get(i).getRuns();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private MatchRequestModel requestModel;

    private void getScore() {
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

                if(requestModel.getScore1().equals("")){
                    innings1.setText("0-0\t(0.0)\t\t\t\t" + team1Name);
                }else {
                    innings1.setText(requestModel.getScore1() + "-0\t(" + requestModel.getOvers1() + ")\t\t" + team1Name);
                }
                innings2.setText("Yet To Bat\t\t\t\t"+ team2Name);
                if(requestModel.getOvers1().equals("")){
                    overs.setText("0.0");
                }else {
                    overs.setText(requestModel.getOvers1());
                }
                score1 = requestModel.getScore1();
                score2 = requestModel.getScore2();
                oversStr = requestModel.getOvers2();

                if(score1.equals("")){
                    score1 = "0";
                }
                if(score2.equals("")){
                    score2 = "0";
                }
                if(oversStr.equals("")){
                    oversStr = "0";
                }

                if(dataSnapshot.child("firstInnings").getValue().toString().equals("Completed")){

                    startActivity(new Intent(StartMatchbActivity.this,StartMatchcActivity.class)
                            .putExtra("team1Name", team1Name).putExtra("team2Name", team2Name)
                            .putExtra("umpire1Name", umpire1Name).putExtra("umpire2Name", umpire2Name)
                            .putExtra("matchId", matchId).putExtra("tossWon",tossWon)
                            .putExtra("batFirst",bowlTeam).putExtra("bowlTeam",batFirst)
                            .putExtra("team1PlayersId",team2PlayersId)
                            .putExtra("team2PlayersId",team1PlayersId));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickListeners() {
        batRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                bat1 = findViewById(batRG.getCheckedRadioButtonId());
                batChecked = true;
            }
        });

        saveBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getDetails = true;

                if(getDetails){
                    getPlayersDetail();
                }


                if(ballStatSpinner.getSelectedItemId()==0) {
                    Toast.makeText(StartMatchbActivity.this, "Please select ball status", Toast.LENGTH_SHORT).show();
                }else if(!batChecked){
                    Toast.makeText(StartMatchbActivity.this, "Please select batsman", Toast.LENGTH_SHORT).show();
                } else {
                    Utils.initpDialog(StartMatchbActivity.this,"Loading, please wait");
                    Utils.showpDialog();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
                    getBatsmanId(bat1.getText().toString());
                    getBowlerId(bowlerSpinner.getSelectedItem().toString());
//                        }
//                    },1500);


                }

            }
        });

        endInnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                        .child(matchId);

                HashMap<String,Object> map = new HashMap<>();
                map.put("firstInnings","completed");

                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(StartMatchbActivity.this,StartMatchcActivity.class)
                                    .putExtra("team1Name", team1Name).putExtra("team2Name", team2Name)
                                    .putExtra("umpire1Name", umpire1Name).putExtra("umpire2Name", umpire2Name)
                                    .putExtra("matchId", matchId).putExtra("tossWon",tossWon)
                                    .putExtra("batFirst",bowlTeam).putExtra("bowlTeam",batFirst)
                                    .putExtra("team1PlayersId",team2PlayersId)
                                    .putExtra("team2PlayersId",team1PlayersId));
                            Toast.makeText(StartMatchbActivity.this, "First Innings Ended", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }


    private void saveEveryBall(String bowlerName, String batName, String ballStatus, String runsSpinner, String dismissType,
                               String fielderName, String newBatName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId).child(IConstants.BALLDETAILS);

        getDetails = false;


        overs1 = String.valueOf(Double.parseDouble(overs.getText().toString()) + 0.1);

        String id = UUID.randomUUID().toString();

        long time = System.currentTimeMillis();

        HashMap<String,Object> map = new HashMap<>();
//        <item>None</item>
//        <item>Bowled</item>
//        <item>Caught</item>
//        <item>Stump</item>
//        <item>Run Out</item>
//        <item>LBW</item>
//        <item>Retire</item>
//        <item>Other</item>
        if(dismissType.equals("None")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "playing");
            wickets = "0";
            map.put("fow", "0");
        }else if(dismissType.equals("Bowled")){
            map.put("isCatch",false);
            map.put("isBowled",true);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
            map.put("fow", runsSpinner+"/1");
        }else if(dismissType.equals("Caught")){
            map.put("isCatch",true);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
            map.put("fow", runsSpinner+"/1");
        }else if(dismissType.equals("Stump")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",true);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
            map.put("fow", runsSpinner+"/1");
        }else if(dismissType.equals("Run Out")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",true);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
            map.put("fow", runsSpinner+"/1");
        }else if(dismissType.equals("LBW")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",true);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
            map.put("fow", runsSpinner+"/1");
        }else if(dismissType.equals("Retire")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",true);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
            map.put("fow", runsSpinner+"/1");
        }else if(dismissType.equals("Other")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",true);
            map.put("status", "notPlaying");
            wickets = "1";
            map.put("fow", runsSpinner+"/1");
        }

        if(bat1.getText().toString().equals(batName)) {
//            if(ballsPlayed==null){
                ballsPlayed = "1";
                fours = "0";
                sixes = "0";
//            }
//            ballsPlayed = String.valueOf(Integer.parseInt(ballsPlayed) + 1);
            runs = String.valueOf(runsSpinner);
            if (runsSpinner.equals("4")) {
                fours = "1";
            } else if (runsSpinner.equals("6")) {
                sixes = "1";
            }

            String strikeRate = String.valueOf((Integer.parseInt(runs)/1)*100);

            bat1Id = batsman.getId();
            bowlId = bowler.getId();

            map.put("id", id);
            map.put("playerName", batName);
            map.put("playerId",batsman.getId());
            map.put("runs", runsSpinner);
            map.put("ballsPlayed", ballsPlayed);
            map.put("fours", fours);
            map.put("sixes", sixes);
            map.put("catchBy", fielderName);
            map.put("ballBy", bowlerName);
            map.put("overs", overs1);
            map.put("wickets", wickets);
            map.put("economy", economy);
            map.put("bb", bb);
            map.put("fRunOut", fRunOut);
            map.put("fCatches", fCatches);
            map.put("fStumps", fStumps);
            map.put("ballStatus", ballStatus);
            map.put("fielderName",fielderName);
            map.put("teamName",batFirst);
            map.put("strikeRate",strikeRate);
            map.put("bowlerId",bowler.getId());
            map.put("timeStamp",String.valueOf(time));

            reference.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()) {
                        saveOvers(overs1, runs);
//                        saveOnProfile(ballsPlayed,runsSpinner, batName, id, batsman.getId(),fours, sixes, fow, fielderName,
//                                bowlerName,overs1, wickets,economy,bb, fRunOut, fCatches,fStumps, ballStatus,
//                                batFirst,bowler.getId(), dismissType);
//                        Toast.makeText(StartMatchbActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                    Utils.hidepDialog();
                }
            });
        }else if(batName.equals(bat2.getText().toString())){
//            if(ballsPlayed2==null){
                ballsPlayed2 = "1";
                fours2 = "0";
                sixes2 = "0";
//            }
            runs = String.valueOf(Integer.parseInt(runsSpinner));

            if (runsSpinner.equals("4")) {
                fours2 = "1";
            } else if (runsSpinner.equals("6")) {
                sixes2 = "1";
            }

            String strikeRate = String.valueOf((Integer.parseInt(runs2)/Integer.parseInt(ballsPlayed2))*100);

            bowlId = bowler.getId();
            bat2Id = batsman.getId();

            map.put("id", id);
            map.put("playerName", batName);
            map.put("playerId",batsman.getId());
            map.put("runs", runsSpinner);
            map.put("ballsPlayed", ballsPlayed2);
            map.put("fours", fours2);
            map.put("sixes", sixes2);
            map.put("catchBy", fielderName);
            map.put("ballBy", bowlerName);
            map.put("overs", overs1);
            map.put("wickets", wickets2);
            map.put("economy", economy2);
            map.put("bb", bb2);
            map.put("fRunOut", fRunOut2);
            map.put("fCatches", fCatches2);
            map.put("fStumps", fStumps2);
            map.put("ballStatus", ballStatus);
            map.put("fielderName",fielderName);
            map.put("teamName",batFirst);
            map.put("strikeRate",strikeRate);
            map.put("bowlerId",bowler.getId());
            map.put("timeStamp",String.valueOf(time));

            reference.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        saveOvers(overs1,runs);
//                        saveOnProfile(ballsPlayed2,runsSpinner, batName, id, batsman.getId(),fours2, sixes2, fow2, fielderName,
//                                bowlerName,overs1, wickets2,economy2,bb2, fRunOut2, fCatches2,fStumps2, ballStatus,
//                                batFirst,bowler.getId(),dismissType);
                        Toast.makeText(StartMatchbActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                    }
                    Utils.hidepDialog();
                }
            });
        }
    }

    private void saveOnProfile(String ballsPlayed2, String runs2, String batName, String id, String batId, String
            fours2, String sixes2, String fow2, String fielderName, String bowlerName, String overs1, String wickets2,
                               String economy2, String bb2, String fRunOut2, String fCatches2, String fStumps2,
                               String ballStatus, String batFirst, String bowlId, String dismissType) {



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(batId).child("matches");
        HashMap<String,Object> map = new HashMap<>();

        if(dismissType.equals("None")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "playing");
            wickets = "0";
        }else if(dismissType.equals("Bowled")){
            map.put("isCatch",false);
            map.put("isBowled",true);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
        }else if(dismissType.equals("Caught")){
            map.put("isCatch",true);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
        }else if(dismissType.equals("Stump")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",true);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
        }else if(dismissType.equals("Run Out")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",true);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
        }else if(dismissType.equals("LBW")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",true);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
        }else if(dismissType.equals("Retire")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",true);
            map.put("isRunOut",false);
            map.put("isOther",false);
            map.put("status", "notPlaying");
            wickets = "1";
        }else if(dismissType.equals("Other")){
            map.put("isCatch",false);
            map.put("isBowled",false);
            map.put("isLbw",false);
            map.put("isStump",false);
            map.put("isRetire",false);
            map.put("isRunOut",false);
            map.put("isOther",true);
            map.put("status", "notPlaying");
            wickets = "1";
        }

//        if(bat1.getText().toString().equals(batName)) {
            if (ballsPlayed == null) {
                ballsPlayed = "0";
                fours = "0";
                sixes = "0";
            }
            ballsPlayed = String.valueOf(Integer.parseInt(ballsPlayed) + 1);
            runs = String.valueOf(Integer.parseInt(runs) + Integer.parseInt(runsSpinner.getSelectedItem().toString()));
            if (runsSpinner.equals("4")) {
                fours = String.valueOf(Integer.parseInt(fours) + 1);
            } else if (runsSpinner.equals("6")) {
                sixes = String.valueOf(Integer.parseInt(sixes) + 1);
            }

            String strikeRate = String.valueOf((Integer.parseInt(runs) / Integer.parseInt(ballsPlayed)) * 100);

            map.put("id", id);
            map.put("playerName", batName);
            map.put("playerId", batId);
            map.put("runs", runs);
            map.put("ballsPlayed", ballsPlayed);
            map.put("fours", fours);
            map.put("sixes", sixes);
            map.put("fow", fow2);
            map.put("catchBy", fielderName);
            map.put("ballBy", bowlerName);
            map.put("overs", overs1);
            map.put("wickets", wickets2);
            map.put("economy", economy2);
            map.put("bb", bb2);
            map.put("fRunOut", fRunOut2);
            map.put("fCatches", fCatches2);
            map.put("fStumps", fStumps2);
            map.put("ballStatus", ballStatus);
            map.put("fielderName", fielderName);
            map.put("teamName", batFirst);
            map.put("strikeRate", strikeRate);
            map.put("bowlerId", bowlId);

            reference.child(id).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
//        }
    }

    private void saveOvers(String overs, String runs) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId);

        score1 = String.valueOf(Integer.parseInt(score1) +Integer.parseInt(runs));

        HashMap<String, Object> map = new HashMap<>();
        map.put("overs1",overs.toString());
        map.put("score1",score1);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("OverAdded","OverAdded");
                }
            }
        });
    }

}








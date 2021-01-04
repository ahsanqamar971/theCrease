package com.limecoders.thecrease.activities.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.activities.dashboards.adapters.CurrentTournamentAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.DefaultSquadAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.FullSquadAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.MatchRequestAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.PlayerRequestAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.PlayerSuggestionAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.TeamFixtureAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.TeamPendingAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.TourRequestAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.PlayerRequestModel;
import com.limecoders.thecrease.models.TourModel;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.RegisterActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TeamDBActivity extends AppCompatActivity {

    //Profile
    private TextView profileTv, requestTv, playTv, teamNameTv, teamLevelTv, addressTv, veneueTv, coachNumberTv, squadNumberTv,
    emailTv, numberTv, captainTv, pitchesTv;
    private ImageView teamImage;
    private Button editProfileBtn;
    private RelativeLayout teamInfoLayout;
    private FirebaseAuth auth;
    private String currentTeamId;
    private Users userModel;

    private String teamName;

    private LinearLayout matchReqLayout, tourReqLayout, playerReqLayout, playerSugLayout;

    private RecyclerView matchReqRecyclerView, tourReqRecyclerView, playerReqRecyclerView, playerSugRecyclerView;

    private MatchRequestAdapter matchRequestAdapter;
    private List<MatchRequestModel> matchRequestModels = new ArrayList<>();

    private TourRequestAdapter tourRequestAdapter;
    private List<TourModel> tournamentModels = new ArrayList<>();

    private List<Users> users = new ArrayList<>();
    private PlayerSuggestionAdapter playerSuggestionAdapter;

    private LinearLayout playLayout, squadLayout;
    private TextView sqaud, tournament, fixtures;
    private RecyclerView defSqRecyclerView, squadRecyclerView,tournRecyclerView,team_tourn_RV;

    private PlayerRequestAdapter playerRequestAdapter;

    private List<Users> playersList = new ArrayList<>();
    private DefaultSquadAdapter defaultSquadAdapter;
    private FullSquadAdapter fullSquadAdapter;

    private CurrentTournamentAdapter currentTournamentAdapter;
    private List<FixturesModel> fixturesModels = new ArrayList<>();

    private TeamFixtureAdapter teamFixtureAdapter;

    public static RelativeLayout addMatchLayout;

    private TextView date, month, year, hour, min, overs, venue;
    private Spinner oppSpinner, umpire1Spinner, umpire2Spinner, scorerSpinner;
    private Button sendButton;

    private ImageView addBtn;
    private TextView noTournMoreData;

    private boolean isUserVeiw;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_db);

        Utils.initpDialog(this,"Please Wait");
        Utils.showpDialog();
        auth = FirebaseAuth.getInstance();
        currentTeamId = auth.getCurrentUser().getUid();


        preferences = getSharedPreferences("com.limecoders.thecrease",MODE_PRIVATE);

        noTournMoreData = findViewById(R.id.noMoreData);

        oppSpinner = findViewById(R.id.oppSpinner);
        umpire1Spinner = findViewById(R.id.umpire1Spinner);
        umpire2Spinner = findViewById(R.id.umpire2Spinner);
        scorerSpinner = findViewById(R.id.scorerSpinner);
        sendButton = findViewById(R.id.sendReqBtn);

        addBtn = findViewById(R.id.addBtn);

        addMatchLayout = findViewById(R.id.addMatchLayout);
        squadLayout = findViewById(R.id.squadLayout);
        playLayout = findViewById(R.id.playLayout);
        matchReqLayout = findViewById(R.id.matchReqLayout);
        tourReqLayout = findViewById(R.id.tourReqLayout);
        playerReqLayout = findViewById(R.id.playerReqLayout);
        playerSugLayout = findViewById(R.id.playerSugLayout);

        sqaud = findViewById(R.id.squad);
        tournament = findViewById(R.id.tournaments);
        fixtures = findViewById(R.id.fixtures);

        //Play RecyclerViews
        defSqRecyclerView = findViewById(R.id.defSqRecyclerView);
        defSqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        defSqRecyclerView.setHasFixedSize(true);

        squadRecyclerView = findViewById(R.id.squadRecyclerView);
        squadRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        squadRecyclerView.setHasFixedSize(true);

        tournRecyclerView = findViewById(R.id.tournRecyclerView);
        tournRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tournRecyclerView.setHasFixedSize(true);

        team_tourn_RV = findViewById(R.id.team_tourn_RV);
        team_tourn_RV.setLayoutManager(new LinearLayoutManager(this));
        team_tourn_RV.setHasFixedSize(true);

        // Request RecyclerViews
        matchReqRecyclerView = findViewById(R.id.matchReqRecyclerView);
        matchReqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchReqRecyclerView.setHasFixedSize(true);

        tourReqRecyclerView = findViewById(R.id.tourReqRecyclerView);
        tourReqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourReqRecyclerView.setHasFixedSize(true);

        playerReqRecyclerView = findViewById(R.id.playerReqRecyclerView);
        playerReqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerReqRecyclerView.setHasFixedSize(true);

        playerSugRecyclerView = findViewById(R.id.playerSugRecyclerView);
        playerSugRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        playerSugRecyclerView.setHasFixedSize(true);

        teamInfoLayout = findViewById(R.id.teamInfoLayout);
        profileTv = findViewById(R.id.profile);
        requestTv = findViewById(R.id.request);
        playTv = findViewById(R.id.play);
        teamNameTv = findViewById(R.id.teamName);
        teamLevelTv = findViewById(R.id.level);
        addressTv = findViewById(R.id.city);
        emailTv = findViewById(R.id.email);
        numberTv = findViewById(R.id.phone);
        veneueTv = findViewById(R.id.venue);
        captainTv = findViewById(R.id.capName);
        coachNumberTv = findViewById(R.id.coachNumber);
        squadNumberTv = findViewById(R.id.squadNumber);
        pitchesTv = findViewById(R.id.pracPitches);
        teamImage = findViewById(R.id.teamImage);
        editProfileBtn = findViewById(R.id.editProfileBtn);

        profileTv.setTypeface(null, Typeface.BOLD);
        requestTv.setTypeface(null, Typeface.NORMAL);
        playTv.setTypeface(null, Typeface.NORMAL);

        if(getIntent().hasExtra("isUserView")){
            isUserVeiw = getIntent().getBooleanExtra("isUserView",false);
        }
        if(isUserVeiw){
            profileTv.setVisibility(View.GONE);
            requestTv.setVisibility(View.GONE);
            playTv.setVisibility(View.GONE);
            editProfileBtn.setVisibility(View.GONE);
        }

        clickListeners();

        getTeamInfo();
        
        getAllMatchesRequests();
        getAllTournamentRequests();
        getAllPlayers();

        getCurrentTournament();
        getAllFixtures();
        getPendingRequest();
        
    }

    private List<MatchRequestModel> fixturesModelList = new ArrayList<>();
    private void getAllFixtures() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if((dataSnapshot.child("team2Id").getValue().toString().equals(auth.getCurrentUser().getUid())
                    || dataSnapshot.child("teamId").getValue().toString().equals(auth.getCurrentUser().getUid()))
                    && (Boolean.parseBoolean(dataSnapshot.child("teamAccepted").getValue().toString())
                    && !Boolean.parseBoolean(dataSnapshot.child("teamRejected").getValue().toString())))
                    fixturesModelList.add(new MatchRequestModel(dataSnapshot.child("id").getValue().toString(),
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
                            dataSnapshot.child("firstInnings").getValue().toString(),dataSnapshot.child("tossWon").getValue().toString(),dataSnapshot.child("batFirst").getValue().toString()));
                }
                teamFixtureAdapter = new TeamFixtureAdapter(TeamDBActivity.this,fixturesModelList);
                tournRecyclerView.setAdapter(teamFixtureAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getCurrentTournament() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(auth.getCurrentUser().getUid()).child(IConstants.TOURNAMENTS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(Boolean.parseBoolean(dataSnapshot.child("isEnded").getValue().toString())){
                        getFixtures(reference);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getFixtures(DatabaseReference reference) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    fixturesModels.add(new FixturesModel(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("team1Name").getValue().toString(),
                            dataSnapshot.child("team2Name").getValue().toString(), dataSnapshot.child("venue").getValue().toString(),
                            dataSnapshot.child("tournId").getValue().toString(), dataSnapshot.child("tournName").getValue().toString(),
                            dataSnapshot.child("matchCondition").getValue().toString(), dataSnapshot.child("team1Image").getValue().toString(),
                            dataSnapshot.child("team2Image").getValue().toString(), dataSnapshot.child("dateTime").getValue().toString(),
                            dataSnapshot.child("overs").getValue().toString()));
                }
                currentTournamentAdapter = new CurrentTournamentAdapter(TeamDBActivity.this,fixturesModels);
                team_tourn_RV.setAdapter(currentTournamentAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<PlayerRequestModel> pendingModel = new ArrayList<>();

    private void getPendingRequest() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.PLAYERREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(!Boolean.parseBoolean(dataSnapshot.child("isTeam").getValue().toString())
                    && !Boolean.parseBoolean(dataSnapshot.child(IConstants.ISAPPROVED).getValue().toString())
                    && dataSnapshot.child("teamId").getValue().toString().equals(auth.getCurrentUser().getUid())) {
                        pendingModel.add(new PlayerRequestModel(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("playerName").getValue().toString(),
                                dataSnapshot.child("rank").getValue().toString(), dataSnapshot.child("playerId").getValue().toString(),
                                dataSnapshot.child("batHand").getValue().toString(), dataSnapshot.child("batStyle").getValue().toString(),
                                dataSnapshot.child("bowlHand").getValue().toString(), dataSnapshot.child("bowlStyle").getValue().toString(),
                                dataSnapshot.child("teamId").getValue().toString(), Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isTeam").getValue().toString())));
                    }
                }


                playerRequestAdapter = new PlayerRequestAdapter(TeamDBActivity.this, pendingModel);
                playerReqRecyclerView.setAdapter(playerRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private List<Users> defaultPlayers = new ArrayList<>();
    private int playerCount = 0;

    private void getAllPlayers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                playerCount = 0;

                if(users.size()>0){
                    users.clear();
                }if (playersList.size()>0){
                    playersList.clear();
                }if (defaultPlayers.size()>0){
                    defaultPlayers.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")
                    && !dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(userModel.getTeamName())) {
                        users.add(new Users(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child(IConstants.EMAIL).getValue().toString(),
                                dataSnapshot.child(IConstants.ROLE).getValue().toString(), dataSnapshot.child(IConstants.AGE).getValue().toString(),
                                dataSnapshot.child(IConstants.SEX).getValue().toString(), dataSnapshot.child(IConstants.NAME).getValue().toString(),
                                dataSnapshot.child(IConstants.RELIGION).getValue().toString(), dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                dataSnapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.INTEAM).getValue().toString()),
                                dataSnapshot.child(IConstants.BATTYPE).getValue().toString(), dataSnapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.RANK).getValue().toString()),
                                dataSnapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                dataSnapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), dataSnapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.TOURNR).getValue().toString()), dataSnapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                dataSnapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                dataSnapshot.child(IConstants.TOUR1).getValue().toString(), dataSnapshot.child(IConstants.TOUR2).getValue().toString(),
                                dataSnapshot.child(IConstants.EXPERIENCE).getValue().toString(), dataSnapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                dataSnapshot.child(IConstants.EDUCATION).getValue().toString(), dataSnapshot.child(IConstants.COURSES).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLSTYLE).getValue().toString(), dataSnapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.BATHS).getValue().toString()), dataSnapshot.child(IConstants.DEBUT).getValue().toString(),
                                dataSnapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(dataSnapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.ECON).getValue().toString()),
                                dataSnapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.RATING).getValue().toString()), dataSnapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                dataSnapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                dataSnapshot.child(IConstants.ADDRESS).getValue().toString(),Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child(IConstants.ISREJECTED).getValue().toString())));
                    }
                    if(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(userModel.getTeamName())
                    && Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString())
                    && dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")){
                        defaultPlayers.add(new Users(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child(IConstants.EMAIL).getValue().toString(),
                                dataSnapshot.child(IConstants.ROLE).getValue().toString(), dataSnapshot.child(IConstants.AGE).getValue().toString(),
                                dataSnapshot.child(IConstants.SEX).getValue().toString(), dataSnapshot.child(IConstants.NAME).getValue().toString(),
                                dataSnapshot.child(IConstants.RELIGION).getValue().toString(), dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                dataSnapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.INTEAM).getValue().toString()),
                                dataSnapshot.child(IConstants.BATTYPE).getValue().toString(), dataSnapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.RANK).getValue().toString()),
                                dataSnapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                dataSnapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), dataSnapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.TOURNR).getValue().toString()), dataSnapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                dataSnapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                dataSnapshot.child(IConstants.TOUR1).getValue().toString(), dataSnapshot.child(IConstants.TOUR2).getValue().toString(),
                                dataSnapshot.child(IConstants.EXPERIENCE).getValue().toString(), dataSnapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                dataSnapshot.child(IConstants.EDUCATION).getValue().toString(), dataSnapshot.child(IConstants.COURSES).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLSTYLE).getValue().toString(), dataSnapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.BATHS).getValue().toString()), dataSnapshot.child(IConstants.DEBUT).getValue().toString(),
                                dataSnapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(dataSnapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.ECON).getValue().toString()),
                                dataSnapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.RATING).getValue().toString()), dataSnapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                dataSnapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                dataSnapshot.child(IConstants.ADDRESS).getValue().toString(),Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child(IConstants.ISREJECTED).getValue().toString())));
                        playerCount = playerCount +1;
                    }
                    if(Boolean.parseBoolean(dataSnapshot.child(IConstants.INTEAM).getValue().toString())
                            && dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")
                    && dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(userModel.getTeamName())){
                        playersList.add(new Users(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child(IConstants.EMAIL).getValue().toString(),
                                dataSnapshot.child(IConstants.ROLE).getValue().toString(), dataSnapshot.child(IConstants.AGE).getValue().toString(),
                                dataSnapshot.child(IConstants.SEX).getValue().toString(), dataSnapshot.child(IConstants.NAME).getValue().toString(),
                                dataSnapshot.child(IConstants.RELIGION).getValue().toString(), dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                dataSnapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.INTEAM).getValue().toString()),
                                dataSnapshot.child(IConstants.BATTYPE).getValue().toString(), dataSnapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.RANK).getValue().toString()),
                                dataSnapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                dataSnapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), dataSnapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.TOURNR).getValue().toString()), dataSnapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                dataSnapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                dataSnapshot.child(IConstants.TOUR1).getValue().toString(), dataSnapshot.child(IConstants.TOUR2).getValue().toString(),
                                dataSnapshot.child(IConstants.EXPERIENCE).getValue().toString(), dataSnapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                dataSnapshot.child(IConstants.EDUCATION).getValue().toString(), dataSnapshot.child(IConstants.COURSES).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLSTYLE).getValue().toString(), dataSnapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.BATHS).getValue().toString()), dataSnapshot.child(IConstants.DEBUT).getValue().toString(),
                                dataSnapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(dataSnapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.ECON).getValue().toString()),
                                dataSnapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.RATING).getValue().toString()), dataSnapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                dataSnapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                dataSnapshot.child(IConstants.ADDRESS).getValue().toString(),Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child(IConstants.ISREJECTED).getValue().toString())));

                    }
                }
                playerSuggestionAdapter = new PlayerSuggestionAdapter(TeamDBActivity.this,users);
                playerSugRecyclerView.setAdapter(playerSuggestionAdapter);


                defaultSquadAdapter = new DefaultSquadAdapter(TeamDBActivity.this,defaultPlayers);
                defSqRecyclerView.setAdapter(defaultSquadAdapter);

                fullSquadAdapter = new FullSquadAdapter(TeamDBActivity.this,playersList);
                squadRecyclerView.setAdapter(fullSquadAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllTournamentRequests() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.TOURNAMENTREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(tournamentModels.size()>0){
                    tournamentModels.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child("team1Id").getValue().toString().equals(auth.getCurrentUser().getUid()))

                        tournamentModels.add(new TourModel(dataSnapshot.child("id").getValue().toString(),
                                dataSnapshot.child("teamIds").getValue().toString(), dataSnapshot.child("teamPlayers").getValue().toString(),
                                dataSnapshot.child("date").getValue().toString(), dataSnapshot.child("venue").getValue().toString(),
                                dataSnapshot.child("time").getValue().toString(), dataSnapshot.child("type").getValue().toString(),
                                dataSnapshot.child("level").getValue().toString(), Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isRejected").getValue().toString()),Boolean.parseBoolean(dataSnapshot.child("scorerApproved").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("scorerRejected").getValue().toString()),dataSnapshot.child("tourName").getValue().toString()));

                }
                tourRequestAdapter = new TourRequestAdapter(TeamDBActivity.this,tournamentModels,true);
                matchReqRecyclerView.setAdapter(tourRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllMatchesRequests() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(matchRequestModels.size()>0){
                    matchRequestModels.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child("team2Id").getValue().toString().equals(auth.getCurrentUser().getUid())
                    && !Boolean.parseBoolean(dataSnapshot.child("teamRejected").getValue().toString())
                    && !Boolean.parseBoolean(dataSnapshot.child("teamAccepted").getValue().toString())) {

                        matchRequestModels.add(new MatchRequestModel(dataSnapshot.child("id").getValue().toString(),
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
                                dataSnapshot.child("teamId").getValue().toString(),dataSnapshot.child("umpire1Id").getValue().toString(),
                                dataSnapshot.child("umpire2Id").getValue().toString(),dataSnapshot.child("scorerId").getValue().toString(),
                                dataSnapshot.child("score1").getValue().toString(),dataSnapshot.child("score2").getValue().toString(),
                                dataSnapshot.child("momPlayerId").getValue().toString(),dataSnapshot.child("won").getValue().toString(),
                                dataSnapshot.child("matchCondition").getValue().toString(),dataSnapshot.child("leagueName").getValue().toString(),
                                dataSnapshot.child("overs1").getValue().toString(),dataSnapshot.child("overs2").getValue().toString(),
                                dataSnapshot.child("firstInnings").getValue().toString(),dataSnapshot.child("tossWon").getValue().toString(),dataSnapshot.child("batFirst").getValue().toString()));
                    }

                }
                matchRequestAdapter = new MatchRequestAdapter(TeamDBActivity.this,matchRequestModels,false,userModel.getTeamName());
                matchReqRecyclerView.setAdapter(matchRequestAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTeamInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(currentTeamId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userModel = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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
                        snapshot.child(IConstants.ADDRESS).getValue().toString(),Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                        Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString()));

                if(userModel.getProfileImage().equals("")){
                    teamImage.setImageResource(R.drawable.dummy_image);
                }else {
                    Picasso.get().load(userModel.getProfileImage()).into(teamImage);
                }

                teamName = userModel.getTeamName();
                teamNameTv.setText(userModel.getTeamName());
                teamLevelTv.setText(userModel.getLevel());
                addressTv.setText(userModel.getAddress());
                emailTv.setText(userModel.getEmail());
                numberTv.setText(userModel.getPhoneNumber());
                captainTv.setText(userModel.getInfo());
                coachNumberTv.setText(String.valueOf(userModel.getCoachNumber()));
                pitchesTv.setText(String.valueOf(userModel.getPitchNumber()));
                squadNumberTv.setText(String.valueOf(userModel.getSquadNumber()));
                veneueTv.setText(userModel.getVenue());
                Utils.hidepDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickListeners() {

        sqaud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn.setVisibility(View.GONE);
                addMatchLayout.setVisibility(View.GONE);
                team_tourn_RV.setVisibility(View.GONE);
                tournRecyclerView.setVisibility(View.GONE);
                squadLayout.setVisibility(View.VISIBLE);
                noTournMoreData.setVisibility(View.GONE);

                sqaud.setTypeface(null, Typeface.BOLD);
                tournament.setTypeface(null, Typeface.NORMAL);
                fixtures.setTypeface(null, Typeface.NORMAL);
            }
        });

        tournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noTournMoreData.setVisibility(View.VISIBLE);
                addBtn.setVisibility(View.GONE);
                addMatchLayout.setVisibility(View.GONE);
                team_tourn_RV.setVisibility(View.VISIBLE);
                tournRecyclerView.setVisibility(View.GONE);
                squadLayout.setVisibility(View.GONE);

                sqaud.setTypeface(null, Typeface.NORMAL);
                tournament.setTypeface(null, Typeface.BOLD);
                fixtures.setTypeface(null, Typeface.NORMAL);
            }
        });
        fixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noTournMoreData.setVisibility(View.GONE);
                addBtn.setVisibility(View.VISIBLE);
                addMatchLayout.setVisibility(View.GONE);
                team_tourn_RV.setVisibility(View.GONE);
                tournRecyclerView.setVisibility(View.VISIBLE);
                squadLayout.setVisibility(View.GONE);

                sqaud.setTypeface(null, Typeface.NORMAL);
                fixtures.setTypeface(null, Typeface.BOLD);
                tournament.setTypeface(null, Typeface.NORMAL);
            }
        });

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TeamDBActivity.this, RegisterActivity.class)
                        .putExtra("isEdit",true).putExtra("email",emailTv.getText().toString())
                        .putExtra("name",teamNameTv.getText().toString()).putExtra("sex",userModel.getSex())
                        .putExtra("age",userModel.getAge()).putExtra("religion",userModel.getReligion())
                        .putExtra("address",addressTv.getText().toString()).putExtra("role",userModel.getRole())
                        .putExtra("image",userModel.getProfileImage()));
            }
        });

        profileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn.setVisibility(View.GONE);
                addMatchLayout.setVisibility(View.GONE);
                teamInfoLayout.setVisibility(View.VISIBLE);

                profileTv.setTypeface(null, Typeface.BOLD);
                requestTv.setTypeface(null, Typeface.NORMAL);
                playTv.setTypeface(null, Typeface.NORMAL);
            }
        });
        requestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMatchLayout.setVisibility(View.GONE);

                addBtn.setVisibility(View.GONE);
                playLayout.setVisibility(View.GONE);
                teamInfoLayout.setVisibility(View.GONE);
                matchReqLayout.setVisibility(View.VISIBLE);
                tourReqLayout.setVisibility(View.VISIBLE);
                playerReqLayout.setVisibility(View.VISIBLE);
                playerSugLayout.setVisibility(View.VISIBLE);

                requestTv.setTypeface(null, Typeface.BOLD);
                profileTv.setTypeface(null, Typeface.NORMAL);
                playTv.setTypeface(null, Typeface.NORMAL);
            }
        });
        playTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMatchLayout.setVisibility(View.GONE);

                addBtn.setVisibility(View.GONE);
                team_tourn_RV.setVisibility(View.GONE);
                tournRecyclerView.setVisibility(View.GONE);
                squadLayout.setVisibility(View.VISIBLE);

                sqaud.setTypeface(null, Typeface.BOLD);
                tournament.setTypeface(null, Typeface.NORMAL);
                fixtures.setTypeface(null, Typeface.NORMAL);

                playLayout.setVisibility(View.VISIBLE);
                teamInfoLayout.setVisibility(View.GONE);
                matchReqLayout.setVisibility(View.GONE);
                tourReqLayout.setVisibility(View.GONE);
                playerReqLayout.setVisibility(View.GONE);
                playerSugLayout.setVisibility(View.GONE);

                playTv.setTypeface(null, Typeface.BOLD);
                requestTv.setTypeface(null, Typeface.NORMAL);
                profileTv.setTypeface(null, Typeface.NORMAL);

                getAllUsers();


            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                clearAddMoreFields();

                List<String> team1PlayersId = new ArrayList<>();
                for(int i=0;i<defaultPlayers.size();i++){
                    team1PlayersId.add(defaultPlayers.get(i).getId());
                }

                date = findViewById(R.id.date);
                month = findViewById(R.id.month);
                year = findViewById(R.id.year);
                hour = findViewById(R.id.hour);
                min = findViewById(R.id.min);
                overs = findViewById(R.id.overs);
                venue = findViewById(R.id.addVenue);

                addBtn.setVisibility(View.GONE);
                if (TextUtils.isEmpty(venue.getText().toString()) || TextUtils.isEmpty(overs.getText().toString())
                        || TextUtils.isEmpty(date.getText().toString()) || TextUtils.isEmpty(month.getText().toString())
                        || TextUtils.isEmpty(year.getText().toString()) || TextUtils.isEmpty(hour.getText().toString())
                        || TextUtils.isEmpty(min.getText().toString())) {
                    Toast.makeText(TeamDBActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else{

                    if (Integer.parseInt(date.getText().toString()) > 31) {
                        Toast.makeText(TeamDBActivity.this, "Incorrect Date", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(month.getText().toString()) > 12) {
                        Toast.makeText(TeamDBActivity.this, "Incorrect month", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(hour.getText().toString()) > 24) {
                        Toast.makeText(TeamDBActivity.this, "Incorrect hour", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(min.getText().toString()) > 60) {
                        Toast.makeText(TeamDBActivity.this, "Incorrect min", Toast.LENGTH_SHORT).show();
                    } else {

                        Utils.initpDialog(TeamDBActivity.this,"Sending match request");
                        Utils.showpDialog();

                        //opposite team selection
                        String oppositeTeamName = oppSpinner.getSelectedItem().toString();
                        String oppTeamId = teamsIdArray.get(oppSpinner.getSelectedItemPosition());

                        //umpire 1 selection
                        String umpire1Name = umpire1Spinner.getSelectedItem().toString();
                        String umpire1Id = umpireIdArray.get(umpire1Spinner.getSelectedItemPosition());

                        //umpire 2 selection
                        String umpire2Name = umpire2Spinner.getSelectedItem().toString();
                        String umpire2Id = umpireIdArray.get(umpire2Spinner.getSelectedItemPosition());

                        //scorer selection
                        String scorerName = scorerSpinner.getSelectedItem().toString();
                        String scorerId = scorerIdArray.get(scorerSpinner.getSelectedItemPosition());

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);

                        String id = UUID.randomUUID().toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("teamId", auth.getCurrentUser().getUid());
                        map.put("teamName", userModel.getTeamName());
                        map.put("venue",venue.getText().toString());
                        map.put("type", overs.getText().toString());
                        map.put("date", date.getText().toString() + "/" + month.getText().toString() + "/" + year.getText().toString());
                        map.put("time", hour.getText().toString() + ":" + min.getText().toString());
                        map.put("isApproved", false);
                        map.put("isRejected", false);
                        map.put("umpire1Accepted",false);
                        map.put("umpire1Rejected",false);
                        map.put("umpire2Accepted",false);
                        map.put("umpire2Rejected",false);
                        map.put("scorerAccepted", false);
                        map.put("scorerRejected",false);
                        map.put("isCompleted",false);
                        map.put("isFinished",false);
                        map.put("teamAccepted",false);
                        map.put("teamRejected",false);
                        map.put("score1","");
                        map.put("score2","");
                        map.put("momPlayerId","");
                        map.put("won","");
                        map.put("matchCondition","Upcoming");
                        map.put("leagueName","");
                        map.put("overs1","");
                        map.put("team1PlayersId", team1PlayersId.toString());
                        map.put("team2PlayersId", "");
                        map.put("team2Id", oppTeamId);
                        map.put("umpire1Id", umpire1Id);
                        map.put("umpire2Id", umpire2Id);
                        map.put("scorerId", scorerId);
                        map.put("overs2","");
                        map.put("firstInnings","");
                        map.put("tossWon","");
                        map.put("batFirst","");

                        new SweetAlertDialog(TeamDBActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Squad Alert")
                                .setContentText("Default XI will be your Playing XI in this match.")
                                .setConfirmText("Okay")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                reference.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            tournRecyclerView.setVisibility(View.VISIBLE);
                                                            Toast.makeText(TeamDBActivity.this, "Request Sent", Toast.LENGTH_SHORT).show();
                                                        }
                                                        addMatchLayout.setVisibility(View.GONE);
                                                        Utils.hidepDialog();
                                                    }
                                                });
                                            }
                                        },3000);

                                    }
                                }).setCancelText("No")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                }).show();

                    }
                }
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playerCount==11) {
                    tournRecyclerView.setVisibility(View.GONE);
                    addMatchLayout.setVisibility(View.VISIBLE);
                    addBtn.setVisibility(View.GONE);
                }else if(playerCount>11){
                    Toast.makeText(TeamDBActivity.this, "You cannot select more than 11 players", Toast.LENGTH_SHORT).show();
                }else if(playerCount<11){
                    Toast.makeText(TeamDBActivity.this, "Please select 11 players", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    ArrayList<String> teamsArray = new ArrayList<>();
    ArrayList<String> teamsIdArray = new ArrayList<>();
    ArrayList<String> umpireIdArray = new ArrayList<>();
    ArrayList<String> scorerIdArray = new ArrayList<>();
    ArrayList<String> umpireArray = new ArrayList<>();
    ArrayList<String> scorerArray = new ArrayList<>();

    private void getAllUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(teamsArray.size()>0){
                    teamsArray.clear();
                    teamsIdArray.clear();
                }
                if(umpireArray.size()>0){
                    umpireArray.clear();
                    umpireIdArray.clear();
                }
                if(scorerArray.size()>0){
                    scorerArray.clear();
                    scorerIdArray.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("team")
                    && !dataSnapshot.child("id").getValue().toString().equals(auth.getCurrentUser().getUid())){
                        teamsArray.add(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString());
                        teamsIdArray.add(dataSnapshot.child("id").getValue().toString());
                    }
                    if(dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("umpire")){
                        umpireArray.add(dataSnapshot.child(IConstants.NAME).getValue().toString());
                        umpireIdArray.add(dataSnapshot.child("id").getValue().toString());
                    }
                    if(dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("scorer")){
                        scorerArray.add(dataSnapshot.child(IConstants.NAME).getValue().toString());
                        scorerIdArray.add(dataSnapshot.child("id").getValue().toString());
                    }

                }


                ArrayAdapter<String> teamsSpinnerAdapter = new ArrayAdapter<String>(TeamDBActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        teamsArray);
                ArrayAdapter<String> umpire1SpinnerAdapter = new ArrayAdapter<String>(TeamDBActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        umpireArray);
                ArrayAdapter<String> umpire2SpinnerAdapter = new ArrayAdapter<String>(TeamDBActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        umpireArray);
                ArrayAdapter<String> scorerSpinnerAdapter = new ArrayAdapter<String>(TeamDBActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        scorerArray);

                oppSpinner.setAdapter(teamsSpinnerAdapter);
                umpire1Spinner.setAdapter(umpire1SpinnerAdapter);
                umpire2Spinner.setAdapter(umpire2SpinnerAdapter);
                scorerSpinner.setAdapter(scorerSpinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);

        MenuItem item = menu.findItem(R.id.logout);

        if(isUserVeiw){
            item.setVisible(false);
        }
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if(item.getItemId()==R.id.logout){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("role","");
            editor.apply();

            auth.signOut();
            startActivity(new Intent(TeamDBActivity.this, DashBoardActivity.class));
            finishAffinity();
            finish();
        }

        return true;
    }

    public void clearAddMoreFields(){
        date.setText("");
        month.setText("");
        year.setText("");
        min.setText("");
        hour.setText("");
        overs.setText("");
        venue.setText("");
    }
}
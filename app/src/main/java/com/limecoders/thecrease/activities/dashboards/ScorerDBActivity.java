package com.limecoders.thecrease.activities.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.activities.dashboards.adapters.ScoreMatchesAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.ScorerMatchReqAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.ScorerTourReqAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.TourModel;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.RegisterActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ScorerDBActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private SharedPreferences preferences;

    private TextView profile, notifications, score;

    private TextView name, age, city, email, experience, gender, number, education, role;
    private ImageView profileImage;

    private String scorerId;

    private Button editBtn;

    private Users scorer;

    private RelativeLayout profileLayout;
    private LinearLayout notificationLayout;
    private LinearLayout scoreLayout;

    private RecyclerView matchReqRecyclerView, tourReqRecyclerView, scoreReyclerView;
    private List<MatchRequestModel> matchRequestModels = new ArrayList<>();
    private ScorerMatchReqAdapter scorerMatchReqAdapter;

    private List<TourModel> tourModels = new ArrayList<>();
    private ScorerTourReqAdapter scorerTourReqAdapter;

    private List<MatchRequestModel> scoreReqModel = new ArrayList<>();
    private ScoreMatchesAdapter scoreMatchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer_db);

        auth = FirebaseAuth.getInstance();

        Utils.initpDialog(this,"Loading, please wait");
        Utils.showpDialog();

        preferences = getSharedPreferences("com.limecoders.thecrease",MODE_PRIVATE);

        scorerId = auth.getCurrentUser().getUid();

        profile = findViewById(R.id.profile);
        notifications = findViewById(R.id.notifications);
        score = findViewById(R.id.score);

        profileLayout = findViewById(R.id.profileLayout);
        notificationLayout = findViewById(R.id.notifyLayout);
        scoreLayout = findViewById(R.id.scoreLayout);

        editBtn = findViewById(R.id.editBtn);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        city = findViewById(R.id.city);
        email = findViewById(R.id.email);
        experience = findViewById(R.id.experience);
        gender = findViewById(R.id.gender);
        number = findViewById(R.id.number);
        education = findViewById(R.id.education);
        role = findViewById(R.id.role);
        profileImage = findViewById(R.id.profileImage);

        profile.setTypeface(null, Typeface.BOLD);
        notifications.setTypeface(null, Typeface.NORMAL);
        score.setTypeface(null, Typeface.NORMAL);

        matchReqRecyclerView = findViewById(R.id.matchReqRecyclerView);
        matchReqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchReqRecyclerView.setHasFixedSize(true);

        tourReqRecyclerView = findViewById(R.id.tourReqRecyclerView);
        tourReqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourReqRecyclerView.setHasFixedSize(true);

        scoreReyclerView = findViewById(R.id.scoreRecyclerView);
        scoreReyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreReyclerView.setHasFixedSize(true);

        getMatchRequests();
        getAllTournamentRequests();
        getScorerInfo();

        clickListeners();

        getScoreMatches();

    }

    private void getScoreMatches() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(scoreReqModel.size()>0){
                    scoreReqModel.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child("scorerId").getValue().toString().equals(scorerId) &&
                            !Boolean.parseBoolean(dataSnapshot.child("scorerRejected").getValue().toString())
                            && Boolean.parseBoolean(dataSnapshot.child("scorerAccepted").getValue().toString())) {

                        scoreReqModel.add(new MatchRequestModel(dataSnapshot.child("id").getValue().toString(),
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

                }
                scoreMatchesAdapter = new ScoreMatchesAdapter(ScorerDBActivity.this,scoreReqModel);
                scoreReyclerView.setAdapter(scoreMatchesAdapter);
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
                if(tourModels.size()>0){
                    tourModels.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child("team1Id").getValue().toString().equals(auth.getCurrentUser().getUid()))

                        tourModels.add(new TourModel(dataSnapshot.child("id").getValue().toString(),
                                dataSnapshot.child("teamIds").getValue().toString(), dataSnapshot.child("teamPlayers").getValue().toString(),
                                dataSnapshot.child("date").getValue().toString(), dataSnapshot.child("venue").getValue().toString(),
                                dataSnapshot.child("time").getValue().toString(), dataSnapshot.child("type").getValue().toString(),
                                dataSnapshot.child("level").getValue().toString(), Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isRejected").getValue().toString()),Boolean.parseBoolean(dataSnapshot.child("scorerApproved").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("scorerRejected").getValue().toString()),dataSnapshot.child("tourName").getValue().toString()));

                }
                scorerTourReqAdapter = new ScorerTourReqAdapter(ScorerDBActivity.this,tourModels);
                matchReqRecyclerView.setAdapter(scorerTourReqAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMatchRequests() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(matchRequestModels.size()>0){
                    matchRequestModels.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child("scorerId").getValue().toString().equals(scorerId) &&
                            !Boolean.parseBoolean(dataSnapshot.child("scorerRejected").getValue().toString())
                            && !Boolean.parseBoolean(dataSnapshot.child("scorerAccepted").getValue().toString())) {

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
                                dataSnapshot.child("team2Id").getValue().toString(),dataSnapshot.child("umpire1Id").getValue().toString(),
                                dataSnapshot.child("umpire2Id").getValue().toString(),dataSnapshot.child("scorerId").getValue().toString(),
                                dataSnapshot.child("score1").getValue().toString(),dataSnapshot.child("score2").getValue().toString(),
                                dataSnapshot.child("momPlayerId").getValue().toString(),dataSnapshot.child("won").getValue().toString(),
                                dataSnapshot.child("matchCondition").getValue().toString(),dataSnapshot.child("leagueName").getValue().toString(),
                                dataSnapshot.child("overs1").getValue().toString(),dataSnapshot.child("overs2").getValue().toString(),
                                dataSnapshot.child("firstInnings").getValue().toString(),dataSnapshot.child("tossWon").getValue().toString(),dataSnapshot.child("batFirst").getValue().toString()));
                    }

                }
                scorerMatchReqAdapter = new ScorerMatchReqAdapter(ScorerDBActivity.this,matchRequestModels);
                matchReqRecyclerView.setAdapter(scorerMatchReqAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void clickListeners() {

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ScorerDBActivity.this, RegisterActivity.class)
                        .putExtra("isEdit",true).putExtra("email",scorer.getEmail())
                        .putExtra("name",scorer.getName()).putExtra("sex",scorer.getSex())
                        .putExtra("age",scorer.getAge()).putExtra("religion",scorer.getReligion())
                        .putExtra("address",scorer.getAddress()).putExtra("role",scorer.getRole())
                        .putExtra("image",scorer.getProfileImage()));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profileLayout.setVisibility(View.VISIBLE);
                notificationLayout.setVisibility(View.GONE);
                scoreLayout.setVisibility(View.GONE);

                profile.setTypeface(null, Typeface.BOLD);
                notifications.setTypeface(null, Typeface.NORMAL);
                score.setTypeface(null, Typeface.NORMAL);
            }
        });

        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notificationLayout.setVisibility(View.VISIBLE);
                profileLayout.setVisibility(View.GONE);
                scoreLayout.setVisibility(View.GONE);

                notifications.setTypeface(null, Typeface.BOLD);
                profile.setTypeface(null, Typeface.NORMAL);
                score.setTypeface(null, Typeface.NORMAL);
            }
        });

        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                scoreLayout.setVisibility(View.VISIBLE);
                notificationLayout.setVisibility(View.GONE);
                profileLayout.setVisibility(View.GONE);

                score.setTypeface(null, Typeface.BOLD);
                notifications.setTypeface(null, Typeface.NORMAL);
                profile.setTypeface(null, Typeface.NORMAL);
            }
        });
    }

    private void getScorerInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(scorerId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                scorer = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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

                name.setText(scorer.getName());
                age.setText(scorer.getAge());
                email.setText(scorer.getEmail());
                number.setText(scorer.getPhoneNumber());
                city.setText(scorer.getAddress());
                experience.setText(scorer.getExperience());
                education.setText(scorer.getEducation());
                gender.setText(scorer.getSex());
                role.setText(scorer.getRole());

                if (scorer.getProfileImage().equals("")){
                    profileImage.setImageResource(R.drawable.dummy_image);
                }else {
                    Picasso.get().load(scorer.getProfileImage()).into(profileImage);
                }

                Utils.hidepDialog();

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
            startActivity(new Intent(ScorerDBActivity.this, DashBoardActivity.class));
            finishAffinity();
            finish();
        }

        return true;
    }
}
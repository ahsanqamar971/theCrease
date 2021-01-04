package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.limecoders.thecrease.activities.dashboards.adapters.SquadAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import okhttp3.internal.Util;

public class StartMatchActivity extends AppCompatActivity {

    private EditText day, year, month, overs, venue;
    private TextView team1Squad, team2Squad;

    private String team1Name, team2Name, umpire1Name, umpire2Name;

    private RadioGroup tossRG, batFirstRG;
    private RadioButton tossTeam1, tossTeam2, batTeam1, batTeam2;

    private boolean isToss = false, isBat = false;

    public static int batsman = 0;
    public static int bowler = 0;

    private String matchCondition;

    private Button startMatch;
    private String team1PlayersId = "", team2PlayersId = "";

    private List<Users> team1SquadList = new ArrayList<>();

    private List<Users> team2SquadList = new ArrayList<>();

    private RecyclerView team1SquadRV, team2SquadRV;
    private SquadAdapter team1Adapter;
    private SquadAdapter team2Adapter;

    private String matchId;

    private String tossTeam, batTeam, bowlTeam;
    public static List<String> batId = new ArrayList<String>();
    public static List<String> bowlId = new ArrayList<String>();

    private boolean isSecondInnings = false;

    private String batFirst, tossWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_match);

        Utils.initpDialog(this,"Loading, please wait");
        Utils.showpDialog();

        if(getIntent().hasExtra("team1Name")){
            matchId = getIntent().getStringExtra("matchId");
            team1Name = getIntent().getStringExtra("team1Name");
            team2Name = getIntent().getStringExtra("team2Name");
            umpire1Name = getIntent().getStringExtra("umpire1Name");
            umpire2Name = getIntent().getStringExtra("umpire2Name");
        }

        if(getIntent().hasExtra("isSecondInnings")){
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

        getMatchDetails();

        day = findViewById(R.id.date);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        overs = findViewById(R.id.overs);
        venue = findViewById(R.id.addVenue);
        team1Squad = findViewById(R.id.team1Squad);
        team2Squad = findViewById(R.id.team2Squad);
        tossRG = findViewById(R.id.tossRG);
        batFirstRG = findViewById(R.id.batFirstRG);
        tossTeam1 = findViewById(R.id.team1);
        tossTeam2 = findViewById(R.id.team2);
        batTeam1 = findViewById(R.id.batTeam1);
        batTeam2 = findViewById(R.id.batTeam2);
        startMatch = findViewById(R.id.startMatch);

        team1Squad.setText(team1Name+" XI");
        team2Squad.setText(team2Name+" XI");
        tossTeam1.setText(team1Name);
        tossTeam2.setText(team2Name);
        batTeam1.setText(team1Name);
        batTeam2.setText(team2Name);

        team1SquadRV = findViewById(R.id.team1SquadRV);
        team1SquadRV.setLayoutManager(new LinearLayoutManager(this));
        team1SquadRV.setHasFixedSize(true);

        team2SquadRV = findViewById(R.id.team2SquadRV);
        team2SquadRV.setLayoutManager(new LinearLayoutManager(this));
        team2SquadRV.setHasFixedSize(true);

        clickListeners();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getTeam1Squad();
                getTeam2Squad();
            }
        },1000);

    }

    private void getMatchDetails() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team1PlayersId = snapshot.child("team1PlayersId").getValue().toString();
                team2PlayersId = snapshot.child("team2PlayersId").getValue().toString();
                matchCondition = snapshot.child("matchCondition").getValue().toString();

                if(matchCondition.equals("Live")){
                    if(snapshot.child("batFirst").getValue().toString().equals(team1Name))
                    {
                        bowlTeam = team2Name;
                    }else {
                        bowlTeam = team1Name;
                    }
                    startActivity(new Intent(StartMatchActivity.this, StartMatchbActivity.class)
                            .putExtra("team1Name", team1Name).putExtra("team2Name", team2Name)
                            .putExtra("umpire1Name", umpire1Name).putExtra("umpire2Name", umpire2Name)
                            .putExtra("matchId", matchId).putExtra("tossWon",snapshot.child("tossWon").getValue().toString())
                            .putExtra("batFirst",snapshot.child("batFirst").getValue().toString()).putExtra("bowlTeam",bowlTeam)
                            .putExtra("team1PlayersId",snapshot.child("team1PlayersId").getValue().toString())
                            .putExtra("team2PlayersId",snapshot.child("team2PlayersId").getValue().toString())
                    .putExtra("isSecondInnings",isSecondInnings));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTeam1Squad() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(team1SquadList.size()>0){
                    team1SquadList.clear();
                }

                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (team1PlayersId.contains(snapshot.child("id").getValue().toString())
                    && !snapshot.child(IConstants.INFO).getValue().toString().toLowerCase().equals("playing")) {
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

                team1Adapter = new SquadAdapter(StartMatchActivity.this, team1SquadList,matchId);
                team1SquadRV.setAdapter(team1Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTeam2Squad() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(team2SquadList.size()>0){
                    team2SquadList.clear();
                }

                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (team2PlayersId.contains(snapshot.child("id").getValue().toString())
                            && !snapshot.child(IConstants.INFO).getValue().toString().toLowerCase().equals("playing")) {
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

                team2Adapter = new SquadAdapter(StartMatchActivity.this, team2SquadList,matchId);

                team2SquadRV.setAdapter(team2Adapter);
                Utils.hidepDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickListeners() {

        batFirstRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isBat = true;
                batTeam1 = findViewById(batFirstRG.getCheckedRadioButtonId());
                if(batTeam1.getText().toString().equals(team1Name)){
                    batTeam = team1Name;
                    bowlTeam = team2Name;
                }else if(batTeam1.getText().toString().equals(team2Name)){
                    batTeam = team2Name;
                    bowlTeam = team2Name;
                }

                setBatFirst(batTeam1.getText().toString());

            }
        });

        tossRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isToss = true;
                tossTeam1 = findViewById(tossRG.getCheckedRadioButtonId());
                tossTeam = tossTeam1.getText().toString();
                setTossWon(tossTeam);
            }
        });

        startMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(day.getText().toString()) || TextUtils.isEmpty(month.getText().toString()) ||
                        TextUtils.isEmpty(year.getText().toString()) || TextUtils.isEmpty(overs.getText().toString()) ||
                        TextUtils.isEmpty(venue.getText().toString()) || !isBat || !isToss){
                    Toast.makeText(StartMatchActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    if(batsman==0 || batsman<2){
                        Toast.makeText(StartMatchActivity.this, "Please select striker & non-striker", Toast.LENGTH_SHORT).show();
                    }else if(batsman>2){
                        Toast.makeText(StartMatchActivity.this, "Please select only two batsman", Toast.LENGTH_SHORT).show();
                    }else if(bowler==0 || bowler>1){
                        Toast.makeText(StartMatchActivity.this, "Please select 1 bowler", Toast.LENGTH_SHORT).show();
                    }else {
                        changeMatchCondition();
                        //start Match
                    }
                }
            }
        });
    }

    private void changeMatchCondition() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("matchCondition","Live");

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(StartMatchActivity.this, StartMatchbActivity.class)
                            .putExtra("team1Name", team1Name).putExtra("team2Name", team2Name)
                            .putExtra("umpire1Name", umpire1Name).putExtra("umpire2Name", umpire2Name)
                            .putExtra("matchId", matchId).putExtra("tossWon",tossTeam)
                            .putExtra("batFirst",batTeam).putExtra("bowlTeam",bowlTeam));

                }
            }
        });
    }

    private void setBatFirst(String toString) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId);
        HashMap<String,Object> map = new HashMap<>();

        map.put("batFirst",toString);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("batFirst","batFirst");
                }
            }
        });
    }

    private void setTossWon(String tossTeam) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId);
        HashMap<String,Object> map = new HashMap<>();

        map.put("tossWon",tossTeam);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("tossWon","tossWon");
                }
            }
        });
    }

}
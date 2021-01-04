package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.TeamRankingAdapter;
import com.limecoders.thecrease.adapter.TeamsAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.RankingModel;
import com.limecoders.thecrease.models.Users;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TeamRankingActivity extends AppCompatActivity {

    private RecyclerView t20RecyclerView;
    private RecyclerView t10RcyclerView;
    private RecyclerView oneDayRecyclerView;
    private RecyclerView otherRecyclerView;

    private TeamRankingAdapter adapter;

    private List<Users> model = new ArrayList<>();

    private TextView t10UpdatedTV, t20updateTV, oneDayupdatedTV, otherupdatedTV;
    private TextView club, universities, others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_ranking);

        club = findViewById(R.id.club);
        universities = findViewById(R.id.uni);
        others = findViewById(R.id.others);
        t20RecyclerView = findViewById(R.id.t20_recyclerView);
        t10RcyclerView = findViewById(R.id.t10_recyclerView);
        oneDayRecyclerView = findViewById(R.id.oneDay_recyclerView);
        otherRecyclerView = findViewById(R.id.other_recyclerView);
        t10UpdatedTV = findViewById(R.id.t10updatedTV);
        t20updateTV = findViewById(R.id.t20updatedTV);
        oneDayupdatedTV = findViewById(R.id.oneDayupdatedTV);
        otherupdatedTV = findViewById(R.id.otherupdatedTV);

        t20RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        t10RcyclerView.setLayoutManager(new LinearLayoutManager(this));
        oneDayRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        otherRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        t20RecyclerView.setHasFixedSize(true);
        t10RcyclerView.setHasFixedSize(true);
        oneDayRecyclerView.setHasFixedSize(true);
        otherRecyclerView.setHasFixedSize(true);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        t10UpdatedTV.setText(simpleDateFormat.format(date));
        t20updateTV.setText(simpleDateFormat.format(date));
        oneDayupdatedTV.setText(simpleDateFormat.format(date));
        otherupdatedTV.setText(simpleDateFormat.format(date));

        club.setTypeface(null,Typeface.BOLD);

        getTeamsRanking();
        clickListeners();
    }

    private List<Users> models = new ArrayList<>();

    private void clickListeners() {
        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(model.size()>0){
                    model.clear();
                }
                for(int i=0;i<models.size();i++){
                    if(models.get(i).getLevel().equals("Club")){
                        Users model1 = models.get(i);
                        model.add(model1);
                    }
                }
                adapter.setModels(model);

                club.setTypeface(null,Typeface.BOLD);
                others.setTypeface(null,Typeface.NORMAL);
                universities.setTypeface(null,Typeface.NORMAL);
            }
        });

        universities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.size()>0){
                    model.clear();
                }
                for(int i=0;i<models.size();i++){
                    if(models.get(i).getLevel().equals("Universities")){
                        Users model1 = models.get(i);
                        model.add(model1);
                    }
                }
                adapter.setModels(model);

                universities.setTypeface(null,Typeface.BOLD);
                others.setTypeface(null,Typeface.NORMAL);
                club.setTypeface(null,Typeface.NORMAL);
            }
        });

        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(model.size()>0){
                    model.clear();
                }
                for(int i=0;i<models.size();i++){
                    if(models.get(i).getLevel().equals("Others") || models.get(i).getLevel().equals("Franchise")
                    || models.get(i).getLevel().equals("School") || models.get(i).getLevel().equals("College")){
                        Users model1 = models.get(i);
                        model.add(model1);
                    }
                }
                adapter.setModels(model);

                others.setTypeface(null,Typeface.BOLD);
                club.setTypeface(null,Typeface.NORMAL);
                universities.setTypeface(null,Typeface.NORMAL);
            }
        });
    }

    private void getTeamsRanking() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (models.size()>0){
                    models.clear();
                }

                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (snapshot.child(IConstants.ROLE).getValue().toString().equals("Team")) {
                        models.add(new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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
                                Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString())));
                    }
                }
                if(model.size()>0){
                    model.clear();
                }
                for(int i=0;i<models.size();i++){
                    if(models.get(i).getLevel().equals("Club")){
                        Users model1 = models.get(i);
                        model.add(model1);
                    }
                }


                adapter = new TeamRankingAdapter(TeamRankingActivity.this,model);

                t20RecyclerView.setAdapter(adapter);
                t10RcyclerView.setAdapter(adapter);
                oneDayRecyclerView.setAdapter(adapter);
                otherRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}
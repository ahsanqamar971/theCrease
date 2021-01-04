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
import com.limecoders.thecrease.adapter.PlayerRankingAdapter;
import com.limecoders.thecrease.adapter.PlayersAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.PlayerRankingModel;
import com.limecoders.thecrease.models.Users;

import java.util.ArrayList;
import java.util.List;

public class PlayerRankingActivity extends AppCompatActivity {

    private RecyclerView batRecyclerView, bowlRecyclerView, wkRecyclerView, arRecyclerView;
    private PlayerRankingAdapter adapter;
    private TextView club, other, universities;

    private List<Users> models = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_ranking);

        club = findViewById(R.id.club);
        other = findViewById(R.id.others);
        universities = findViewById(R.id.uni);
        batRecyclerView = findViewById(R.id.bat_recyclerView);
        bowlRecyclerView = findViewById(R.id.bowl_recyclerView);
        wkRecyclerView = findViewById(R.id.wk_recyclerView);
        arRecyclerView = findViewById(R.id.ar_recyclerView);

        batRecyclerView.setLayoutManager(new LinearLayoutManager(PlayerRankingActivity.this));
        bowlRecyclerView.setLayoutManager(new LinearLayoutManager(PlayerRankingActivity.this));
        wkRecyclerView.setLayoutManager(new LinearLayoutManager(PlayerRankingActivity.this));
        arRecyclerView.setLayoutManager(new LinearLayoutManager(PlayerRankingActivity.this));

        batRecyclerView.setHasFixedSize(true);
        bowlRecyclerView.setHasFixedSize(true);
        wkRecyclerView.setHasFixedSize(true);
        arRecyclerView.setHasFixedSize(true);

        club.setTypeface(null, Typeface.BOLD);

        getAllPlayersRating();

        clickListeners();

    }

    private void clickListeners() {
        club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                club.setTypeface(null, Typeface.BOLD);
                other.setTypeface(null,Typeface.NORMAL);
                universities.setTypeface(null,Typeface.NORMAL);
            }
        });

        universities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                club.setTypeface(null, Typeface.NORMAL);
                other.setTypeface(null,Typeface.NORMAL);
                universities.setTypeface(null,Typeface.BOLD);
            }
        });

        other.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                club.setTypeface(null, Typeface.NORMAL);
                other.setTypeface(null,Typeface.BOLD);
                universities.setTypeface(null,Typeface.NORMAL);
            }
        });
    }

    private void getAllPlayersRating() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(models.size()>0){
                    models.clear();
                }

                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (snapshot.child(IConstants.ROLE).getValue().toString().equals("Player")) {
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
                                snapshot.child(IConstants.ADDRESS).getValue().toString(), Boolean.parseBoolean(snapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString())));
                    }
                }

                adapter = new PlayerRankingAdapter(PlayerRankingActivity.this,models);
                batRecyclerView.setAdapter(adapter);
                bowlRecyclerView.setAdapter(adapter);
                wkRecyclerView.setAdapter(adapter);
                arRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
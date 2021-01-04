package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.Users;

public class ProfileActivity extends AppCompatActivity {

    private TextView name, age, sex, role, email, number, teamName, city, batHand, batStyle, bowlHand,
            bowlStyle, batHandTV, bowlHandTV, batStyleTV, bowlStyleTV;
    private LinearLayout batHandLayout, bowlHandLayout, batStyleLayout, bowlStyleLayout;

    private String id;

    private Users users;

    private TextView t20TV, t10TV, othersTV;

    private TextView debutTV, matchesTV;

    private TextView runsTV, rankTV, batAVGTV, batHSTV, batSRTV, inningsTV, hunderdTV, fiftyTV;

    private TextView wicketsTV, bowlAVGTV, bbTV, bowlRankTV, bowlSRTV, econTV, threesTV, fivesTV;

    private TextView wkRankTV, wkCatchesTV, wkStumpsTV, wkRunOutTV;

    private TextView fCatchesTV, fRunOutTV;

    private CheckBox captainCB, viceCapCB;

    private TextView allRounderRankTV, momAwardsTV, mosAwardsTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Utils.initpDialog(this,"Please wait");
        Utils.showpDialog();

        if(getIntent().hasExtra("id")){
            id = getIntent().getStringExtra("id");
        }

        t20TV = findViewById(R.id.t20TV);
        t10TV = findViewById(R.id.t10TV);
        othersTV = findViewById(R.id.otherTV);
        debutTV = findViewById(R.id.debutTV);
        matchesTV = findViewById(R.id.matchesTV);
        runsTV = findViewById(R.id.runsTV);
        rankTV = findViewById(R.id.rankTV);
        batAVGTV = findViewById(R.id.avgTV);
        batHSTV = findViewById(R.id.hsTV);
        batSRTV = findViewById(R.id.srTV);
        inningsTV = findViewById(R.id.inningsTV);
        hunderdTV = findViewById(R.id.hunderedTV);
        fiftyTV = findViewById(R.id.fiftyTV);
        wicketsTV = findViewById(R.id.wicketsTV);
        bowlAVGTV = findViewById(R.id.bowlAVGTV);
        bowlSRTV = findViewById(R.id.bowlSRTV);
        bbTV = findViewById(R.id.bbTV);
        econTV = findViewById(R.id.econTV);
        bowlRankTV = findViewById(R.id.bowlRankTV);
        threesTV = findViewById(R.id.threeWicketsTV);
        fivesTV = findViewById(R.id.fiveWicketsTV);
        wkRankTV = findViewById(R.id.wkRankTV);
        wkCatchesTV = findViewById(R.id.catchesTV);
        wkStumpsTV = findViewById(R.id.stumpsTV);
        wkRunOutTV = findViewById(R.id.runOutTV);
        fCatchesTV = findViewById(R.id.fcatchesTV);
        fRunOutTV = findViewById(R.id.frunoutTV);
        captainCB = findViewById(R.id.captain);
        viceCapCB = findViewById(R.id.viceCaptain);
        allRounderRankTV = findViewById(R.id.allRounderRankTV);
        momAwardsTV = findViewById(R.id.momAwardTV);
        mosAwardsTV = findViewById(R.id.mosAwardTV);

        captainCB.setChecked(true);
        captainCB.setClickable(false);
        viceCapCB.setClickable(false);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        sex = findViewById(R.id.gender);
        email = findViewById(R.id.email);
        number = findViewById(R.id.phone);
        city = findViewById(R.id.city);
        teamName = findViewById(R.id.team);
        role = findViewById(R.id.role);
        batHand = findViewById(R.id.batHand);
        batStyle = findViewById(R.id.batStyle);
        bowlHand = findViewById(R.id.bowlHand);
        bowlStyle = findViewById(R.id.bowlStyle);
        batHandTV = findViewById(R.id.batHandTV);
        bowlHandTV = findViewById(R.id.bowlHandTV);
        batStyleTV = findViewById(R.id.batStyleTV);
        bowlStyleTV = findViewById(R.id.bowlStyleTV);
        batHandLayout = findViewById(R.id.batHandLayout);
        bowlHandLayout = findViewById(R.id.bowlHandLayout);
        batStyleLayout = findViewById(R.id.batStyleLayout);
        bowlStyleLayout = findViewById(R.id.bowlStyleLayout);

        getInfo();
    }

    private void getInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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

                if(users.getRole().toLowerCase().equals("team")){
                    batHandTV.setText("Squads: ");
                    batStyleTV.setText("Umpires: ");
                    bowlHandLayout.setVisibility(View.GONE);
                    bowlStyleLayout.setVisibility(View.GONE);

                    role.setText(users.getRole());
                    name.setText(users.getTeamName());

                }else if(users.getRole().toLowerCase().equals("umpire")){
                    bowlStyleLayout.setVisibility(View.GONE);
                    bowlHandLayout.setVisibility(View.GONE);
                    batHandLayout.setVisibility(View.GONE);
                    batStyleLayout.setVisibility(View.GONE);

                    name.setText(users.getName());
                    role.setText(users.getRole());

                }else if(users.getRole().toLowerCase().equals("player")){

                    batHand.setText(users.getBatType());
                    batStyle.setText(users.getBatStyle());
                    bowlHand.setText(users.getBowlType());
                    bowlStyle.setText(users.getBowlStyle());
                    teamName.setText(users.getTeamName());
                    name.setText(users.getName());
                    role.setText(users.getPlayerRole());

                }
                city.setText(users.getAddress());
                age.setText(users.getAge());
                email.setText(users.getEmail());
                number.setText(users.getPhoneNumber());
                sex.setText(users.getSex());

                Utils.hidepDialog();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
Utils.hidepDialog();
            }
        });
    }
}
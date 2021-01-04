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
import com.limecoders.thecrease.activities.dashboards.adapters.UmpireReqAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.RegisterActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CoachDBActivity extends AppCompatActivity {

    private TextView profile, matches;

    private TextView name, age, sex, email, number, role, education, experience,address;
    private ImageView imageView;

    FirebaseAuth auth;
    private Users coachModel;

    String coachId;

    private Button profileEditBtn;

    private RelativeLayout profileLayout;
    private LinearLayout requestLayout;
    private RecyclerView reqRecyclerView;
    private UmpireReqAdapter umpireReqAdapter;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_db);

        Utils.initpDialog(this,"Please wait");
        Utils.showpDialog();

        preferences = getSharedPreferences("com.limecoders.thecrease",MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();
        coachId = auth.getCurrentUser().getUid();

        profile = findViewById(R.id.profile);
        matches = findViewById(R.id.matches);

        profileLayout = findViewById(R.id.profileLayout);
        requestLayout = findViewById(R.id.requestsLayout);

        reqRecyclerView = findViewById(R.id.coach_Req_RecyclerView);
        reqRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reqRecyclerView.setHasFixedSize(true);

        profileEditBtn = findViewById(R.id.editBtn);
        name = findViewById(R.id.adminName);
        sex = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        number = findViewById(R.id.phone);
        address = findViewById(R.id.city);
        role = findViewById(R.id.role);
        education = findViewById(R.id.education);
        experience = findViewById(R.id.experience);
        imageView = findViewById(R.id.adminImage);

        getAdminInfo(coachId);
        profile.setTypeface(null, Typeface.BOLD);
        matches.setTypeface(null, Typeface.NORMAL);

        clickListeners();

    }

    private void clickListeners() {
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestLayout.setVisibility(View.GONE);
                profileLayout.setVisibility(View.VISIBLE);

                profile.setTypeface(null, Typeface.BOLD);
                matches.setTypeface(null, Typeface.NORMAL);
            }
        });

        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestLayout.setVisibility(View.VISIBLE);
                profileLayout.setVisibility(View.GONE);

                Utils.initpDialog(CoachDBActivity.this,"Loading, please wait.");
                Utils.showpDialog();
                getMatchRequests();

                profile.setTypeface(null, Typeface.NORMAL);
                matches.setTypeface(null, Typeface.BOLD);
            }
        });


        profileEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CoachDBActivity.this, RegisterActivity.class).
                        putExtra("isEdit",true).putExtra("email",coachModel.getEmail())
                        .putExtra("name",coachModel.getName()).putExtra("sex",coachModel.getSex())
                        .putExtra("age",coachModel.getAge()).putExtra("religion",coachModel.getReligion())
                        .putExtra("address",coachModel.getAddress()).putExtra("role",coachModel.getRole())
                        .putExtra("image",coachModel.getProfileImage()));
            }
        });
    }

    private List<MatchRequestModel> matchRequestModelList = new ArrayList<>();

    private void getMatchRequests() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    matchRequestModelList.add(new MatchRequestModel(dataSnapshot.child("id").getValue().toString(),
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
                umpireReqAdapter = new UmpireReqAdapter(CoachDBActivity.this, matchRequestModelList,"");
                reqRecyclerView.setAdapter(umpireReqAdapter);
                Utils.hidepDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAdminInfo(String coachId) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(coachId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                coachModel = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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

                name.setText(coachModel.getName());
                age.setText(coachModel.getAge()+" years");
                sex.setText(coachModel.getSex());
                email.setText(coachModel.getEmail());
                number.setText(coachModel.getPhoneNumber());
                role.setText(coachModel.getRole());
                education.setText(coachModel.getEducation());
                experience.setText(coachModel.getExperience()+" years");
                address.setText(coachModel.getAddress());

                if(coachModel.getProfileImage().equals("")){
                    imageView.setImageResource(R.drawable.dummy_image);
                }else {
                    Picasso.get().load(coachModel.getProfileImage()).into(imageView);
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
            startActivity(new Intent(CoachDBActivity.this, DashBoardActivity.class));
            finishAffinity();
            finish();
        }

        return true;
    }
}
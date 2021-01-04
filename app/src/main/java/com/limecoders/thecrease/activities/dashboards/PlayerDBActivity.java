package com.limecoders.thecrease.activities.dashboards;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
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
import com.limecoders.thecrease.activities.dashboards.adapters.TeamPendingAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.TeamSuggestionsAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.PlayerRequestModel;
import com.limecoders.thecrease.models.TeamModel;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.RegisterActivity;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PlayerDBActivity extends AppCompatActivity {

    private RelativeLayout playerInfoLayout;
    private LinearLayout teamSugLayout, penReqLayout;
    private TextView playerName, playerAge, city, email, phoneNumber, role, gender, teamName, batHand, batStyle, bowlHand, bowlStyle;
    private RecyclerView teamSugRecyclerView, penReqRecyclerView;

    private TextView profile, request;

    private Users player;

    private List<Users> suggestionModel = new ArrayList<>();
    private TeamSuggestionsAdapter suggestionsAdapter;

    private List<PlayerRequestModel> pendingModel = new ArrayList<>();
    private TeamPendingAdapter pendingAdapter;

    private Button editBtn;

    private boolean isUserView;

    private FirebaseAuth auth;

    private ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_db);

        auth = FirebaseAuth.getInstance();

        Utils.initpDialog(this,"Loading, please wait");
        Utils.showpDialog();

        if (getIntent().hasExtra("isUserView")){
            isUserView = getIntent().getBooleanExtra("isUserView",false);
        }

        request = findViewById(R.id.request);
        profile = findViewById(R.id.profile);


        profile.setTypeface(null, Typeface.BOLD);
        request.setTypeface(null, Typeface.NORMAL);

        profileImage = findViewById(R.id.profileImage);
        editBtn = findViewById(R.id.editBtn);
        playerInfoLayout = findViewById(R.id.playerInfoLayout);
        penReqLayout = findViewById(R.id.penReqLayout);
        playerName = findViewById(R.id.username);
        playerAge = findViewById(R.id.age);
        city = findViewById(R.id.city);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phone);
        role = findViewById(R.id.role);
        gender = findViewById(R.id.gender);
        teamName = findViewById(R.id.team);
        batHand = findViewById(R.id.batHand);
        batStyle = findViewById(R.id.batStyle);
        bowlHand = findViewById(R.id.bowlHand);
        bowlStyle = findViewById(R.id.bowlStyle);

        if(isUserView){
            request.setVisibility(View.GONE);
            profile.setVisibility(View.GONE);

        }

        getPlayerInfo();

        teamSugRecyclerView = findViewById(R.id.teamSugRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        teamSugRecyclerView.setLayoutManager(layoutManager);
        teamSugRecyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(teamSugRecyclerView.getContext(),
                layoutManager.getOrientation());
        teamSugRecyclerView.addItemDecoration(dividerItemDecoration);

        penReqRecyclerView = findViewById(R.id.penReqRecyclerView);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        penReqRecyclerView.setLayoutManager(layoutManager1);
        penReqRecyclerView.setHasFixedSize(true);

        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(penReqRecyclerView.getContext(),
                layoutManager1.getOrientation());
        penReqRecyclerView.addItemDecoration(dividerItemDecoration1);



        clickListeners();


    }

    private void getPendingRequest() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.PLAYERREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(pendingModel.size()>0){
                    pendingModel.clear();
                }
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(Boolean.parseBoolean(dataSnapshot.child("isTeam").getValue().toString())) {
                        pendingModel.add(new PlayerRequestModel(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child("playerName").getValue().toString(),
                                dataSnapshot.child("rank").getValue().toString(), dataSnapshot.child("playerId").getValue().toString(),
                                dataSnapshot.child("batHand").getValue().toString(), dataSnapshot.child("batStyle").getValue().toString(),
                                dataSnapshot.child("bowlHand").getValue().toString(), dataSnapshot.child("bowlStyle").getValue().toString(),
                                dataSnapshot.child("teamId").getValue().toString(), Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isTeam").getValue().toString())));
                    }
                }


                pendingAdapter = new TeamPendingAdapter(PlayerDBActivity.this, pendingModel, player);
                penReqRecyclerView.setAdapter(pendingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPlayerInfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(auth.getCurrentUser().getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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

                    playerName.setText(player.getName());
                    playerAge.setText(player.getAge()+" years");
                    city.setText(player.getAddress());
                    email.setText(player.getEmail());
                    phoneNumber.setText(player.getPhoneNumber());
                    role.setText(player.getPlayerRole());
                    teamName.setText(player.getTeamName());
                    gender.setText(player.getSex());
                    batHand.setText(player.getBatType());
                    batStyle.setText(player.getBatStyle());
                    bowlHand.setText(player.getBowlType());
                    bowlStyle.setText(player.getBowlStyle());

                    if(player.getProfileImage().equals("")){
                        profileImage.setImageResource(R.drawable.dummy_image);
                    }else {
                        Picasso.get().load(player.getProfileImage()).into(profileImage);
                    }

                    Utils.hidepDialog();
                getAllTeams();
                getPendingRequest();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getAllTeams() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    if (snapshot.child(IConstants.ROLE).getValue().toString().equals("Team") &&
                    !snapshot.child(IConstants.TEAMNAME).getValue().toString().equals(player.getTeamName())) {
                        suggestionModel.add(new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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

                suggestionsAdapter = new TeamSuggestionsAdapter(PlayerDBActivity.this, suggestionModel, player);
                teamSugRecyclerView.setAdapter(suggestionsAdapter);

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

                startActivity(new Intent(PlayerDBActivity.this, RegisterActivity.class)
                        .putExtra("isEdit",true).putExtra("email",player.getEmail())
                        .putExtra("name",player.getName()).putExtra("sex",player.getSex())
                        .putExtra("age",player.getAge()).putExtra("religion",player.getReligion())
                        .putExtra("address",player.getAddress()).putExtra("role",player.getRole())
                        .putExtra("image",player.getProfileImage()));


            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playerInfoLayout.setVisibility(View.VISIBLE);
                penReqLayout.setVisibility(View.GONE);

                profile.setTypeface(null, Typeface.BOLD);
                request.setTypeface(null, Typeface.NORMAL);
            }
        });

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                playerInfoLayout.setVisibility(View.GONE);
                penReqLayout.setVisibility(View.VISIBLE);

                request.setTypeface(null, Typeface.BOLD);
                profile.setTypeface(null, Typeface.NORMAL);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu, menu);

        MenuItem item = menu.findItem(R.id.logout);

        if(isUserView){
            item.setVisible(false);
        }
        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.logout){
            auth.signOut();
            startActivity(new Intent(PlayerDBActivity.this, DashBoardActivity.class));
            finishAffinity();
            finish();
        }

        return true;
    }

}
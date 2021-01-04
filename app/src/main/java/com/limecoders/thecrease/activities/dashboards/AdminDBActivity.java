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
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.activities.dashboards.adapters.MatchApprovalAdapter;
import com.limecoders.thecrease.activities.dashboards.adapters.RegApprovalAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.ScoreModel;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.RegisterActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminDBActivity extends AppCompatActivity {

    private TextView profile, requests, matches;
    private TextView name, city, email, number, age, gender, role, education, experience;
    private ImageView imageView;
    private Users adminModel;

    private TextView noDataFound, noDatatFound2;

    private Button editBtn;

    private RelativeLayout profileLayout;
    private LinearLayout requestLayout, matchesLayout;

    private FirebaseAuth auth;
    private String userId;

    private List<Users> usersList = new ArrayList<>();
    private RecyclerView reqRecyclerView;
    private RegApprovalAdapter regApprovalAdapter;

    private List<ScoreModel> scoreModels = new ArrayList<>();
    private RecyclerView matchReqRecyclerView;
    private MatchApprovalAdapter matchApprovalAdapter;

    private String religion;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_db);

        auth = FirebaseAuth.getInstance();
        userId = auth.getCurrentUser().getUid();
        preferences = getSharedPreferences("com.limecoders.thecrease",MODE_PRIVATE);


        noDatatFound2 = findViewById(R.id.noData2);
        noDataFound = findViewById(R.id.noData);
        editBtn = findViewById(R.id.editBtn);
        profileLayout = findViewById(R.id.adminProfileLayout);
        requestLayout = findViewById(R.id.reg_requestLayout);
        matchesLayout = findViewById(R.id.match_approval_layout);
        profile = findViewById(R.id.profile);
        requests = findViewById(R.id.requests);
        matches = findViewById(R.id.matches);
        name = findViewById(R.id.adminName);
        imageView = findViewById(R.id.adminImage);
        city = findViewById(R.id.city);
        email = findViewById(R.id.email);
        number = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        gender = findViewById(R.id.gender);
        role = findViewById(R.id.role);
        education = findViewById(R.id.education);
        experience = findViewById(R.id.experience);

        reqRecyclerView = findViewById(R.id.requestRecyclerView);
        reqRecyclerView.setLayoutManager(new LinearLayoutManager(AdminDBActivity.this));
        reqRecyclerView.setHasFixedSize(true);

        matchReqRecyclerView = findViewById(R.id.matchReqRecyclerView);
        matchReqRecyclerView.setLayoutManager(new LinearLayoutManager(AdminDBActivity.this));
        matchReqRecyclerView.setHasFixedSize(true);

        getAllRegistrationRequests();
        getAllMatchApprovalRequest();

        profileLayout.setVisibility(View.VISIBLE);
        requestLayout.setVisibility(View.GONE);
        matchesLayout.setVisibility(View.GONE);
        profile.setTypeface(null, Typeface.BOLD);
        requests.setTypeface(null, Typeface.NORMAL);
        matches.setTypeface(null, Typeface.NORMAL);

        getAdminInfo();
        clickListeners();
    }

    private void getAllMatchApprovalRequest() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHES);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(scoreModels.size()>0){
                    scoreModels.clear();
                }

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    scoreModels.add(dataSnapshot.getValue(ScoreModel.class));
                }
                if(scoreModels.size()==0){
                    noDatatFound2.setVisibility(View.VISIBLE);
                }
                matchApprovalAdapter = new MatchApprovalAdapter(AdminDBActivity.this,scoreModels);
                matchReqRecyclerView.setAdapter(matchApprovalAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAllRegistrationRequests() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (usersList.size()>0){
                    usersList.clear();
                }

                for(DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    if (!Boolean.parseBoolean(snapshot.child(IConstants.ISAPPROVED).getValue().toString())
                    && !Boolean.parseBoolean(snapshot.child(IConstants.ISREJECTED).getValue().toString())) {
                        usersList.add(new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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
                if(usersList.size()==0){
                    noDataFound.setVisibility(View.VISIBLE);
                }
                regApprovalAdapter = new RegApprovalAdapter(AdminDBActivity.this,usersList);
                reqRecyclerView.setAdapter(regApprovalAdapter);
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
                startActivity(new Intent(AdminDBActivity.this, RegisterActivity.class)
                .putExtra("isEdit",true).putExtra("email",email.getText().toString())
                .putExtra("name",name.getText().toString()).putExtra("sex",gender.getText().toString())
                .putExtra("age",age.getText().toString()).putExtra("religion",religion)
                .putExtra("address",city.getText().toString()).putExtra("role",role.getText().toString())
                .putExtra("image",adminModel.getProfileImage()));
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                profileLayout.setVisibility(View.VISIBLE);
                requestLayout.setVisibility(View.GONE);
                matchesLayout.setVisibility(View.GONE);

                profile.setTypeface(null, Typeface.BOLD);
                requests.setTypeface(null, Typeface.NORMAL);
                matches.setTypeface(null, Typeface.NORMAL);
            }
        });


        requests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestLayout.setVisibility(View.VISIBLE);
                profileLayout.setVisibility(View.GONE);
                matchesLayout.setVisibility(View.GONE);

                requests.setTypeface(null, Typeface.BOLD);
                profile.setTypeface(null, Typeface.NORMAL);
                matches.setTypeface(null, Typeface.NORMAL);
            }
        });


        matches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                matchesLayout.setVisibility(View.VISIBLE);
                profileLayout.setVisibility(View.GONE);
                requestLayout.setVisibility(View.GONE);

                matches.setTypeface(null, Typeface.BOLD);
                profile.setTypeface(null, Typeface.NORMAL);
                requests.setTypeface(null, Typeface.NORMAL);
            }
        });
    }

    public void getAdminInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                adminModel = new Users(snapshot.child("id").getValue().toString(), snapshot.child(IConstants.EMAIL).getValue().toString(),
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


                if(adminModel.getProfileImage().equals("null")){
                    imageView.setImageResource(R.drawable.dummy_image);
                }else {
                    Picasso.get().load(adminModel.getProfileImage()).into(imageView);
                }

                name.setText(adminModel.getName());
                city.setText(adminModel.getAddress());
                email.setText(adminModel.getEmail());
                number.setText(adminModel.getPhoneNumber());
                age.setText(adminModel.getAge());
                role.setText(adminModel.getRole());
                gender.setText(adminModel.getSex());
                education.setText(adminModel.getEducation());
                experience.setText(adminModel.getExperience());
                religion = adminModel.getReligion();




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
            startActivity(new Intent(AdminDBActivity.this,DashBoardActivity.class));
            finishAffinity();
            finish();
        }

        return true;
    }
}
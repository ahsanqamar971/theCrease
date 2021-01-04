package com.limecoders.thecrease.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.activities.dashboards.TeamDBActivity;
import com.limecoders.thecrease.constant.IConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CoachSignUpActivity extends AppCompatActivity {

    private Button backBtn, applyBtn;
    private TextView addCounterTv, negCounterTv;
    private EditText numberET, coachingET, eduET, experienceET;
    private Spinner teamSpinner;
    private RadioGroup cricBGRG;
    private RadioButton yesRB, noRB;

    private boolean isSelected = false;

    private FirebaseAuth firebaseAuth;
    private int addCounter;
    private String name, password, email, religion, address, age, sex, role, imageURl;

    private String team, coachText, edu, number, experience;

    private ArrayList<String> teamsArray = new ArrayList<>();
    private ArrayList<String> teamsIdArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_signup);

        firebaseAuth = FirebaseAuth.getInstance();

        Utils.initpDialog(this,"Please wait");

        getIntentData();

        getAllTeams();

        backBtn = findViewById(R.id.backBtn);
        applyBtn = findViewById(R.id.applyBtn);
        addCounterTv = findViewById(R.id.plusCounter);
        negCounterTv = findViewById(R.id.negCounter);
        numberET = findViewById(R.id.number);
        coachingET = findViewById(R.id.coachingText);
        eduET = findViewById(R.id.eduET);
        experienceET = findViewById(R.id.experienceET);
        teamSpinner = findViewById(R.id.teamSpinner);
        cricBGRG = findViewById(R.id.cricBgRG);

        addCounter = 1;

        clickListeneres();
        
    }

    private void clickListeneres() {

        cricBGRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                yesRB = findViewById(cricBGRG.getCheckedRadioButtonId());
                isSelected = true;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CoachSignUpActivity.super.onBackPressed();
            }
        });
        
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        addCounterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCounter = addCounter + 1;
                experienceET.setText(String.valueOf(addCounter));
            }
        });

        negCounterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addCounter>1) {
                    addCounter = addCounter - 1;
                    experienceET.setText(String.valueOf(addCounter));
                }
            }
        });


    }

    private void getData() {

        team = teamSpinner.getSelectedItem().toString();
        number = numberET.getText().toString();
        experience = experienceET.getText().toString();
        coachText = coachingET.getText().toString();
        edu = eduET.getText().toString();

        if(validateEntries(team, number, experience, coachText, edu) || !isSelected){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else {
            Utils.showpDialog();
            registerUser(name, password, email, role, age, sex, religion, team,
                    number, experience, coachText, edu);
        }
    }
    private void registerUser(String name, String password, String email, String role, String age, String sex,
                              String religion, String team, String number, String experience,
                              String coachText, String edu){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            user.sendEmailVerification();
                            uploadData(firebaseAuth.getCurrentUser().getUid(), name, email, role, age, sex, religion,
                                    team, number, experience, coachText, edu);
                        }
                    }
                });
    }

    private void uploadData(String uid, String name, String email, String role, String age, String sex, String religion,
                            String team, String number, String experience, String coachText, String edu) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String year = simpleDateFormat.format(date).split("/")[2];

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        HashMap<String,Object> map = new HashMap<>();
        map.put("id",uid);
        map.put(IConstants.NAME,name);
        map.put(IConstants.TEAMNAME, team);
        map.put(IConstants.PROFILEIMAGE,imageURl);
        map.put(IConstants.EMAIL, email);
        map.put(IConstants.AGE, age);
        map.put(IConstants.SEX, sex);
        map.put(IConstants.ADDRESS,address);
        map.put(IConstants.RELIGION, religion);
        map.put(IConstants.PHONENUMBER, number);
        map.put(IConstants.INFO,"");
        map.put(IConstants.HOMEGROUND,"");
        map.put(IConstants.EXPERIENCE, experience);
        map.put(IConstants.PREFERENCE, "");
        map.put(IConstants.EDUCATION, edu);
        map.put(IConstants.ROLE, role);
        map.put(IConstants.VENUE,"");
        map.put(IConstants.RANK,0);
        map.put(IConstants.ISINSQUAD,false);
        map.put(IConstants.REGISTEREDYEAR,year);
        map.put(IConstants.COURSES,coachText);
        map.put(IConstants.BATSTYLE,"");
        map.put(IConstants.BOWLTYPE,"");
        map.put(IConstants.BATTYPE,"");
        map.put(IConstants.TOUR1,"");
        map.put(IConstants.TOUR2,"");
        map.put(IConstants.BOWLSTYLE,"");
        map.put(IConstants.CRICKETBACKGROUND,yesRB.getText().toString());
        map.put(IConstants.SQUADNUMBER,0);
        map.put(IConstants.COACHNUMBER,0);
        map.put(IConstants.LEVEL,"");
        map.put(IConstants.PITCHCOUNTER,0);
        map.put(IConstants.MATCHES,0);
        map.put(IConstants.RUNS,0);
        map.put(IConstants.BATAVG,0);
        map.put(IConstants.BATSR,0);
        map.put(IConstants.BATHS,0);
        map.put(IConstants.DEBUT,"");
        map.put(IConstants.RECENT,"");
        map.put(IConstants.BOWLAVG,0);
        map.put(IConstants.BOWLSR,0);
        map.put(IConstants.ECON,0);
        map.put(IConstants.BB,0);
        map.put(IConstants.WKCATCHES,0);
        map.put(IConstants.WKSTUMPS,0);
        map.put(IConstants.WKRUNOUT,0);
        map.put(IConstants.FCATCHES,0);
        map.put(IConstants.FRUNOUT,0);
        map.put(IConstants.MOMAWARDS,0);
        map.put(IConstants.MOSAWARDS,0);
        map.put(IConstants.MATCHESNR,0);
        map.put(IConstants.MATCHESLOSE,0);
        map.put(IConstants.MATCHESPLAYED,0);
        map.put(IConstants.MATCHESWON,0);
        map.put(IConstants.MATCHESTIE,0);
        map.put(IConstants.MATCHSCORED,0);
        map.put(IConstants.TOURLOSE,0);
        map.put(IConstants.TOURNR,0);
        map.put(IConstants.TOURPLAYED,0);
        map.put(IConstants.TOURWON,0);
        map.put(IConstants.TOURTIE,0);
        map.put(IConstants.RATING,0);
        map.put(IConstants.INTEAM,false);
        map.put(IConstants.ISAPPROVED,false);
        map.put(IConstants.PLAYERROLE,"");
        map.put(IConstants.ISREJECTED,false);

        reference.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.hidepDialog();
                    new SweetAlertDialog(CoachSignUpActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Thank you!")
                            .setContentText(getString(R.string.thankyou))
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    firebaseAuth.signOut();
                                    startActivity(new Intent(CoachSignUpActivity.this, DashBoardActivity.class));
                                    finishAffinity();
                                    finish();
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                    Log.i("Registered","Registered");
                }
            }
        });


    }

    private boolean validateEntries(String team, String number, String experience, String coachText, String edu) {
        return TextUtils.isEmpty(team) || TextUtils.isEmpty(number) || TextUtils.isEmpty(coachText) ||
                TextUtils.isEmpty(edu) || TextUtils.isEmpty(String.valueOf(experience));
    }

    private void getIntentData(){
//        .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
//                .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
//                .putExtra("role",role)

        address = getIntent().getStringExtra("address");
        imageURl = getIntent().getStringExtra("imageURL");
        name = getIntent().getStringExtra("fullName");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        religion = getIntent().getStringExtra("religion");
        age = getIntent().getStringExtra("age");
        sex = getIntent().getStringExtra("sex");
        role = getIntent().getStringExtra("role");

    }

    public void getAllTeams(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (teamsArray.size()>0){
                    teamsArray.clear();
                }
                if (teamsIdArray.size()>0){
                    teamsIdArray.clear();
                }

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("team")){
                        teamsArray.add(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString());
                        teamsIdArray.add(dataSnapshot.child("id").getValue().toString());
                    }
                }

                ArrayAdapter<String> scorerSpinnerAdapter = new ArrayAdapter<String>(CoachSignUpActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,
                        teamsArray);

                teamSpinner.setAdapter(scorerSpinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
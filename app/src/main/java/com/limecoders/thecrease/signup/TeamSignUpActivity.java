package com.limecoders.thecrease.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.constant.IConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TeamSignUpActivity extends AppCompatActivity {

    private EditText squadNumberET, coachNumberET, venueET, numberET, pitchNumberET;
    private Spinner typeSpinner;
    private RadioGroup hfgRG;
    private RadioButton yesRB, noRB;
    private boolean isSelected = false;
    private Button backBtn, applyBtn;
    private TextView paddCounterTv, pnegCounterTv, caddCounterTv, cnegCounterTv, ptAddCounter, ptNegCounter;
    private String venue, homeGroundFacility, type, number;
    private int playerCounter, coachCounter, pitchCounter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_signup);

        mAuth = FirebaseAuth.getInstance();
        getIntentData();

        Utils.initpDialog(this,"Please wait");

        pitchNumberET = findViewById(R.id.pitchesNumber);
        ptAddCounter = findViewById(R.id.paddCounter);
        ptNegCounter = findViewById(R.id.pnegCounter);
        squadNumberET = findViewById(R.id.squadNumber);
        coachNumberET = findViewById(R.id.coachNumber);
        venueET = findViewById(R.id.venue);
        numberET = findViewById(R.id.number);
        typeSpinner = findViewById(R.id.typeSpinner);
        hfgRG = findViewById(R.id.hgfRG);
//        yesRB = findViewById(R.id.yesRB);
//        noRB = findViewById(R.id.noRB);
        backBtn = findViewById(R.id.backBtn);
        applyBtn = findViewById(R.id.applyBtn);
        paddCounterTv = findViewById(R.id.plusCounter);
        pnegCounterTv = findViewById(R.id.negCounter);
        caddCounterTv = findViewById(R.id.cplusCounter);
        cnegCounterTv = findViewById(R.id.cnegCounter);

        playerCounter = 15;
        coachCounter = 2;
        pitchCounter = 1;

        squadNumberET.setText(String.valueOf(playerCounter));
        coachNumberET.setText(String.valueOf(coachCounter));

        clickListeners();

    }

    private void clickListeners() {

        hfgRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                yesRB = findViewById(hfgRG.getCheckedRadioButtonId());
                isSelected = true;
            }
        });

        ptAddCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pitchCounter = pitchCounter + 1;
                pitchNumberET.setText(String.valueOf(pitchCounter));
            }
        });

        ptNegCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pitchCounter>1){
                    pitchCounter = pitchCounter - 1;
                    pitchNumberET.setText(String.valueOf(pitchCounter));
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TeamSignUpActivity.super.onBackPressed();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(squadNumberET.getText().toString()) || TextUtils.isEmpty(venueET.getText().toString()) ||
                        TextUtils.isEmpty(coachNumberET.getText().toString()) || TextUtils.isEmpty(pitchNumberET.getText().toString()) ||
                        TextUtils.isEmpty(numberET.getText().toString()) || !isSelected){
                    Toast.makeText(TeamSignUpActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();

                }else {
                    getData();
                }
            }
        });

        //player adding counter
        paddCounterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerCounter = playerCounter + 1;
                squadNumberET.setText(String.valueOf(playerCounter));
            }
        });
        pnegCounterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(playerCounter>15) {
                    playerCounter = playerCounter - 1;

                    squadNumberET.setText(String.valueOf(playerCounter));
                }
            }
        });

        //coach adding counter
        caddCounterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coachCounter = coachCounter + 1;

                coachNumberET.setText(String.valueOf(coachCounter));
            }
        });
        cnegCounterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(coachCounter>2) {
                    coachCounter = coachCounter - 1;
                    coachNumberET.setText(String.valueOf(coachCounter));
                }
            }
        });

    }

    private void getData() {

        homeGroundFacility = yesRB.getText().toString();
        venue = venueET.getText().toString();
        number = numberET.getText().toString();
        playerCounter = Integer.parseInt(squadNumberET.getText().toString());
        coachCounter = Integer.parseInt(coachNumberET.getText().toString());
        type = typeSpinner.getSelectedItem().toString();
        pitchCounter = Integer.parseInt(pitchNumberET.getText().toString());

        Log.i("yesRB",homeGroundFacility);

        if(validateEntries(venue, number, playerCounter, coachCounter, type)){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else {
            Utils.showpDialog();
            registerTeam(name, password, email,religion,age,sex,role,number,playerCounter,coachCounter,type,pitchCounter);

        }
    }

    private String name, password, email, address, religion, age, sex, role, imageURl;

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

    private void registerTeam(String name, String password, String email, String religion, String age, String sex,
                             String role, String number, int playerCounter, int coachCounter, String type,
                             int pitchCounter) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification();
                    uploadData(name,email,religion,age,sex,role,number,playerCounter,coachCounter,type,pitchCounter,mAuth.getCurrentUser().getUid(),venue);
                }else Toast.makeText(TeamSignUpActivity.this, "Some error occurred! Try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String name, String email, String religion, String age, String sex, String role, String number,
                            int playerCounter, int coachCounter, String type, int pitchCounter, String uid, String venue) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String year = simpleDateFormat.format(date).split("/")[2];

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        HashMap<String,Object> map = new HashMap<>();
        map.put("id",uid);
        map.put(IConstants.NAME,"");
        map.put(IConstants.TEAMNAME,name);
        map.put(IConstants.PROFILEIMAGE,imageURl);
        map.put(IConstants.EMAIL,email);
        map.put(IConstants.AGE,age);
        map.put(IConstants.SEX, sex);
        map.put(IConstants.ADDRESS,address);
        map.put(IConstants.RELIGION,religion);
        map.put(IConstants.PHONENUMBER,number);
        map.put(IConstants.INFO,"");
        map.put(IConstants.HOMEGROUND,homeGroundFacility);
        map.put(IConstants.EXPERIENCE,"");
        map.put(IConstants.PREFERENCE,"");
        map.put(IConstants.EDUCATION,"");
        map.put(IConstants.ROLE,role);
        map.put(IConstants.VENUE,venue);
        map.put(IConstants.ISINSQUAD,false);
        map.put(IConstants.RANK,0);
        map.put(IConstants.REGISTEREDYEAR,year);
        map.put(IConstants.COURSES,"");
        map.put(IConstants.BATSTYLE,"");
        map.put(IConstants.BOWLTYPE,"");
        map.put(IConstants.BATTYPE,"");
        map.put(IConstants.TOUR1,"");
        map.put(IConstants.TOUR2,"");
        map.put(IConstants.BOWLSTYLE,"");
        map.put(IConstants.CRICKETBACKGROUND,"");
        map.put(IConstants.SQUADNUMBER,playerCounter);
        map.put(IConstants.COACHNUMBER,coachCounter);
        map.put(IConstants.LEVEL,type);
        map.put(IConstants.PITCHCOUNTER,pitchCounter);
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
                    new SweetAlertDialog(TeamSignUpActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Thank you!")
                            .setContentText(getString(R.string.thankyou))
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    mAuth.signOut();
                                    startActivity(new Intent(TeamSignUpActivity.this, DashBoardActivity.class));
                                    finishAffinity();
                                    finish();
                                  sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                }
            }
        });

    }

    private boolean validateEntries(String venue, String number, int playerCounter, int coachCounter, String type) {
        return TextUtils.isEmpty(venue) || TextUtils.isEmpty(String.valueOf(number)) ||
                TextUtils.isEmpty(String.valueOf(playerCounter)) ||
                TextUtils.isEmpty(String.valueOf(coachCounter)) || TextUtils.isEmpty(type);
    }
}
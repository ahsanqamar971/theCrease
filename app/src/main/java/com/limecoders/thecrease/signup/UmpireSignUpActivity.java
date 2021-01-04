package com.limecoders.thecrease.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import okhttp3.internal.Util;

public class UmpireSignUpActivity extends AppCompatActivity {

    private EditText tour1ET, tour2ET, numberET, eduET, experienceET;
    private TextView addCounter, negCounter;
    private Button backBtn, applyBtn;

    private String tour1, tour2, number, edu, experience;
    private int counter;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umpire_signup);

        mAuth = FirebaseAuth.getInstance();

        Utils.initpDialog(this,"Please wait");

        getIntentData();

        tour1ET = findViewById(R.id.tour1);
        tour2ET = findViewById(R.id.tour2);
        numberET = findViewById(R.id.number);
        eduET = findViewById(R.id.eduET);
        experienceET = findViewById(R.id.experienceET);
        addCounter = findViewById(R.id.plusCounter);
        negCounter = findViewById(R.id.negCounter);
        backBtn = findViewById(R.id.backBtn);
        applyBtn = findViewById(R.id.applyBtn);

        counter = 2;
        experienceET.setText(String.valueOf(counter));

        clickListeners();
    }

    private void clickListeners() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UmpireSignUpActivity.super.onBackPressed();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        addCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = counter + 1;
                experienceET.setText(String.valueOf(counter));
            }
        });

        negCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter>1){
                    counter = counter - 1;
                    experienceET.setText(String.valueOf(counter));
                }
            }
        });
    }

    private void getData() {
        tour1 = tour1ET.getText().toString();
        tour2 = tour2ET.getText().toString();
        experience = experienceET.getText().toString();
        number = numberET.getText().toString();
        edu = eduET.getText().toString();

        if(validateEntries(tour1, tour2, experience, number, edu)){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else {
            Utils.showpDialog();
            registerUmpire(name, password, email, religion, age, sex, role, tour1, tour2, experience, number, edu);
        }
    }

    private String name, password, address, email, religion, age, sex, role, imageURl;

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

    private void registerUmpire(String name, String password, String email, String religion, String age, String sex,
                                String role, String tour1, String tour2, String experience, String number, String edu) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    user.sendEmailVerification();
                    uploadData(mAuth.getCurrentUser().getUid(),name, email, religion, age, sex, role, tour1, tour2,
                            experience, number, edu);
                }else Toast.makeText(UmpireSignUpActivity.this, "Some error occurred! Try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String uid, String name, String email, String religion, String age, String sex, String role,
                            String tour1, String tour2, String experience, String number, String edu) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String year = simpleDateFormat.format(date).split("/")[2];

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        HashMap<String,Object> map = new HashMap<>();
        map.put("id",uid);
        map.put(IConstants.NAME,name);
        map.put(IConstants.TEAMNAME, "");
        map.put(IConstants.PROFILEIMAGE,imageURl);
        map.put(IConstants.EMAIL, email);
        map.put(IConstants.AGE, age);
        map.put(IConstants.SEX, sex);
        map.put(IConstants.ADDRESS,address);
        map.put(IConstants.RELIGION, religion);
        map.put(IConstants.PHONENUMBER, number);
        map.put(IConstants.INFO,"");
        map.put(IConstants.HOMEGROUND,"");
        map.put(IConstants.EXPERIENCE,experience);
        map.put(IConstants.PREFERENCE,"");
        map.put(IConstants.EDUCATION,edu);
        map.put(IConstants.ROLE, role);
        map.put(IConstants.VENUE,"");
        map.put(IConstants.RANK,0);
        map.put(IConstants.REGISTEREDYEAR,year);
        map.put(IConstants.COURSES,"");
        map.put(IConstants.BATSTYLE,"");
        map.put(IConstants.BOWLTYPE,"");
        map.put(IConstants.BATTYPE,"");
        map.put(IConstants.TOUR1,tour1);
        map.put(IConstants.TOUR2,tour2);
        map.put(IConstants.BOWLSTYLE,"");
        map.put(IConstants.CRICKETBACKGROUND,"");
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
        map.put(IConstants.ISINSQUAD,false);
        map.put(IConstants.ISREJECTED,false);

        reference.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.hidepDialog();
                    new SweetAlertDialog(UmpireSignUpActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Thank you!")
                            .setContentText(getString(R.string.thankyou))
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    mAuth.signOut();
                                    startActivity(new Intent(UmpireSignUpActivity.this, DashBoardActivity.class));
                                    finishAffinity();
                                    finish();
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                }
            }
        });

    }

    private boolean validateEntries(String tour1, String tour2, String experience, String number, String edu) {
        return TextUtils.isEmpty(tour1) || TextUtils.isEmpty(tour2) || TextUtils.isEmpty(number)
        || TextUtils.isEmpty(edu) || TextUtils.isEmpty(String.valueOf(experience));
    }
}
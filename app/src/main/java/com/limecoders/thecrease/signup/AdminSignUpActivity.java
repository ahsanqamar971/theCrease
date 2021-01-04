package com.limecoders.thecrease.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.limecoders.thecrease.activities.dashboards.AdminDBActivity;
import com.limecoders.thecrease.constant.IConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminSignUpActivity extends AppCompatActivity {

    private EditText numberET, eduET, expTextET;
    private Spinner preSpinner;
    private Button backBtn, applyBtn;

    private FirebaseAuth auth;

    private String number, edu, expText, preference, address, name, religion, age, email, role, sex, password, imageURl;

    private boolean isEdit;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        auth = FirebaseAuth.getInstance();

        Utils.initpDialog(this,"Please wait");

        getIntentData();

        numberET = findViewById(R.id.number);
        eduET = findViewById(R.id.eduET);
        expTextET = findViewById(R.id.expText);
        preSpinner = findViewById(R.id.preSpinner);
        backBtn = findViewById(R.id.backBtn);
        applyBtn = findViewById(R.id.applyBtn);


        if(isEdit){
            applyBtn.setText("Update");
        }

        clickListeners();
    }

    private void clickListeners() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdminSignUpActivity.super.onBackPressed();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.showpDialog();
                getData();
            }
        });
    }

    private void getData() {
        number = numberET.getText().toString();
        expText = expTextET.getText().toString();
        edu = eduET.getText().toString();
        preference = preSpinner.getSelectedItem().toString();

        if(validateEntries(number, edu,expText,preference)){
            Utils.hidepDialog();
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else {
            if(isEdit){
                updateInfo(name, email, age, sex, religion, number, expText, edu, preference);
            }else {
                registerUser(name, email, password, age, sex, role, religion, number, expText, edu, preference);
            }
//            Utils.showAlertDialog(AdminSignUpActivity.this);
        }

    }

    private void updateInfo(String name, String email, String age, String sex, String religion, String number,
                            String expText, String edu, String preference) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(auth.getCurrentUser().getUid());

        HashMap<String, Object> map = new HashMap<>();
        map.put(IConstants.NAME,name);
        map.put(IConstants.EMAIL,email);
        map.put(IConstants.AGE,age);
        map.put(IConstants.SEX,sex);
        map.put(IConstants.RELIGION,religion);
        map.put(IConstants.PHONENUMBER,number);
        map.put(IConstants.EXPERIENCE,expText);
        map.put(IConstants.EDUCATION,edu);
        map.put(IConstants.PREFERENCE,preference);


        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.hidepDialog();
                    new SweetAlertDialog(AdminSignUpActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Updated!")
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    startActivity(new Intent(AdminSignUpActivity.this, AdminDBActivity.class));
                                    finishAffinity();
                                    finish();
                                }
                            }).show();

                }else {
                    Utils.hidepDialog();
                }
            }
        });
    }

    private boolean validateEntries(String number, String edu, String expText, String preference) {
        return TextUtils.isEmpty(number) || TextUtils.isEmpty(edu)
                || TextUtils.isEmpty(expText) || TextUtils.isEmpty(preference);
    }

    private void registerUser(String name, String email, String password, String age, String sex, String role, String religion, String number, String expText, String edu, String preference){
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    user.sendEmailVerification();
                    uploadData(auth.getCurrentUser().getUid(),name, email,age, sex, role, religion, number, expText, edu, preference);
                }else {
                    Utils.hidepDialog();
                }
            }
        });
    }

    private void uploadData(String uid, String name, String email, String age, String sex, String role, String religion,
                            String number, String expText, String edu, String preference) {

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
        map.put(IConstants.EXPERIENCE, expText);
        map.put(IConstants.PREFERENCE, preference);
        map.put(IConstants.EDUCATION, edu);
        map.put(IConstants.ROLE, role);
        map.put(IConstants.VENUE,"");
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
        map.put(IConstants.ISINSQUAD,false);
        map.put(IConstants.ISAPPROVED,false);
        map.put(IConstants.PLAYERROLE,"");
        map.put(IConstants.ISREJECTED,false);

        reference.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                    Utils.hidepDialog();
                    new SweetAlertDialog(AdminSignUpActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Thank you!")
                            .setContentText(getString(R.string.thankyou))
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    auth.signOut();
                                    startActivity(new Intent(AdminSignUpActivity.this, DashBoardActivity.class));
                                    finishAffinity();
                                    finish();
                                }
                            }).show();

                    Log.i("Registered","Registered");
                }else {
                    Utils.hidepDialog();
                }
            }
        });


    }

    private void getIntentData(){
//        .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
//                .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
//                .putExtra("role",role)

        isEdit = getIntent().getBooleanExtra("isEdit",false);
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
}
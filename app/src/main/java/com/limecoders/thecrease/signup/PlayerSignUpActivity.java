package com.limecoders.thecrease.signup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.limecoders.thecrease.constant.IConstants;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlayerSignUpActivity extends AppCompatActivity {

    private Button backBtn, applyBtn;
    private EditText mobileET;
    private Spinner roleSpinner, batTypeSpinner, bowlTypeSpinner, teamSpinner;
    private RadioGroup batTypeRG, bowlTypeRG;
    private RadioButton batRightRB, batLeftRB, bowlRightRB, bowlLeftRB, bowlNoneRB;

    private String address;

    private boolean isBatSelected = false, isBowlSelected = false;

    private FirebaseAuth auth;

    private ArrayList<String> teamsArray = new ArrayList<>();
    private ArrayList<String> teamsIdArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_signup);

        auth = FirebaseAuth.getInstance();

        Utils.initpDialog(this,"Please wait");

        getIntentData();

        mobileET = findViewById(R.id.number);
        backBtn = findViewById(R.id.backBtn);
        applyBtn = findViewById(R.id.applyBtn);
        roleSpinner = findViewById(R.id.roleSpinner);
        batTypeSpinner = findViewById(R.id.batSpinner);
        bowlTypeSpinner = findViewById(R.id.bowlSpinner);
        teamSpinner = findViewById(R.id.teamSpinner);
        batTypeRG = findViewById(R.id.batTypeRG);
        bowlTypeRG = findViewById(R.id.bowlTypeRG);

        clickListeners();
        getAllTeams();

    }

    private void clickListeners() {

        bowlTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isBowlSelected = true;
                bowlLeftRB = findViewById(bowlTypeRG.getCheckedRadioButtonId());
            }
        });

        batTypeRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                isBatSelected = true;
                batRightRB = findViewById(batTypeRG.getCheckedRadioButtonId());
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerSignUpActivity.super.onBackPressed();
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }

    private String name, password, email, religion, age, sex, role, number, batType, playerRole, bowlType,
            batStyle, bowlStyle, teamName, imageURl;

    private void getData() {

        address = getIntent().getStringExtra("address");
        imageURl = getIntent().getStringExtra("imageURL");
        teamName = teamSpinner.getSelectedItem().toString();
        number = mobileET.getText().toString();
        batType = batRightRB.getText().toString();
        bowlType = bowlLeftRB.getText().toString();
        playerRole = roleSpinner.getSelectedItem().toString();
        batStyle = batTypeSpinner.getSelectedItem().toString();
        bowlStyle = bowlTypeSpinner.getSelectedItem().toString();

        if(TextUtils.isEmpty(number) || TextUtils.isEmpty(batType) || TextUtils.isEmpty(bowlType) ||
                playerRole.equals("Select") || batStyle.equals("Select") || bowlStyle.equals("Select") ||
                teamName.equals("Select") || !isBatSelected || !isBowlSelected){
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
        }else {
            Utils.showpDialog();
            registerPlayer(name, password, email, religion,age, sex, role, number, batType, playerRole, bowlType,
                    batStyle, bowlStyle);
        }

    }


    private void getIntentData(){
//        .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
//                .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
//                .putExtra("role",role)

        name = getIntent().getStringExtra("fullName");
        password = getIntent().getStringExtra("password");
        email = getIntent().getStringExtra("email");
        religion = getIntent().getStringExtra("religion");
        age = getIntent().getStringExtra("age");
        sex = getIntent().getStringExtra("sex");
        role = getIntent().getStringExtra("role");

    }

    private void registerPlayer(String name, String password, String email, String religion, String age, String sex,
                                String role, String number, String batType, String playerRole, String bowlType,
                                String batStyle, String bowlStyle) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = auth.getCurrentUser();
                    user.sendEmailVerification();
                    uploadData(auth.getCurrentUser().getUid(), name, email, religion, age, sex, role, number, batType,
                            playerRole, bowlType, batStyle, bowlStyle, teamName);
                }else Toast.makeText(PlayerSignUpActivity.this, "Some error occurred! Try again later", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String uid, String name, String email, String religion, String age, String sex,
                            String role, String number, String batType, String playerRole, String bowlType,
                            String batStyle, String bowlStyle, String teamName) {

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        String year = simpleDateFormat.format(date).split("/")[2];

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        HashMap<String,Object> map = new HashMap<>();
        map.put("id",uid);
        map.put(IConstants.NAME,name);
        map.put(IConstants.TEAMNAME, teamName);
        map.put(IConstants.PROFILEIMAGE,imageURl);
        map.put(IConstants.EMAIL, email);
        map.put(IConstants.AGE, age);
        map.put(IConstants.SEX, sex);
        map.put(IConstants.RELIGION, religion);
        map.put(IConstants.PHONENUMBER, number);
        map.put(IConstants.INFO,"");
        map.put(IConstants.HOMEGROUND,"");
        map.put(IConstants.EXPERIENCE,"");
        map.put(IConstants.PREFERENCE,"");
        map.put(IConstants.EDUCATION,"");
        map.put(IConstants.ROLE, role);
        map.put(IConstants.VENUE,"");
        map.put(IConstants.RANK,0);
        map.put(IConstants.REGISTEREDYEAR,year);
        map.put(IConstants.COURSES,"");
        map.put(IConstants.PLAYERROLE,playerRole);
        map.put(IConstants.BATSTYLE,batStyle);
        map.put(IConstants.BOWLTYPE,bowlType);
        map.put(IConstants.BATTYPE,batType);
        map.put(IConstants.TOUR1,"");
        map.put(IConstants.TOUR2,"");
        map.put(IConstants.BOWLSTYLE,bowlStyle);
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
        map.put(IConstants.ADDRESS,address);
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
        map.put(IConstants.ISAPPROVED,false);
        map.put(IConstants.ISINSQUAD,false);
        map.put(IConstants.INTEAM,true);
        map.put(IConstants.ISREJECTED,false);

        reference.child(uid).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Utils.hidepDialog();
                    new SweetAlertDialog(PlayerSignUpActivity.this,SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Thank you!")
                            .setContentText(getString(R.string.thankyou))
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    auth.signOut();
                                    startActivity(new Intent(PlayerSignUpActivity.this, DashBoardActivity.class));
                                    finishAffinity();
                                    finish();
                                  sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                }
            }
        });

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

                ArrayAdapter<String> scorerSpinnerAdapter = new ArrayAdapter<String>(PlayerSignUpActivity.this,
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
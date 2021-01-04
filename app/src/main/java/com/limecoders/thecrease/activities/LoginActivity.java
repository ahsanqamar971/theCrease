package com.limecoders.thecrease.activities;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.dashboards.AdminDBActivity;
import com.limecoders.thecrease.activities.dashboards.CoachDBActivity;
import com.limecoders.thecrease.activities.dashboards.PlayerDBActivity;
import com.limecoders.thecrease.activities.dashboards.ScorerDBActivity;
import com.limecoders.thecrease.activities.dashboards.TeamDBActivity;
import com.limecoders.thecrease.activities.dashboards.UmpireDBActivity;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.signup.CoachSignUpActivity;
import com.limecoders.thecrease.signup.RegisterActivity;
import com.limecoders.thecrease.signup.ScorerSignUpActivity;
import com.limecoders.thecrease.signup.UmpireSignUpActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {

    private TextView forgotPass;
    private Button signInBtn, signUpBtn;
    private EditText emailET, passwordET;
    private String email, password, role;

    private FirebaseAuth auth;
    private boolean isApproved;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        Utils.initpDialog(this,"Please wait");

        forgotPass = findViewById(R.id.forgotpassTv);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);

        clickListeners();

    }

    private void clickListeners() {
        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailET.getText().toString();
                password = passwordET.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else if (!Utils.isEmailVerified(email)){
                    Toast.makeText(LoginActivity.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
                }else {
                    Utils.showpDialog();
                    checkApproved(email, password);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            }
        });

    }

    private void openNextPage(String role) {

        preferences = getSharedPreferences("com.limecoders.thecrease",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("role",role);
        editor.apply();

        if(role.toLowerCase().equals("admin")){
            startActivity(new Intent(LoginActivity.this, AdminDBActivity.class));
        }else if(role.toLowerCase().equals("umpire")){
            startActivity(new Intent(LoginActivity.this, UmpireDBActivity.class));
        }else if(role.toLowerCase().equals("coach")){
            startActivity(new Intent(LoginActivity.this, CoachDBActivity.class));
        }else if(role.toLowerCase().equals("player")){
            startActivity(new Intent(LoginActivity.this, PlayerDBActivity.class));
        }else if(role.toLowerCase().equals("team")){
            startActivity(new Intent(LoginActivity.this, TeamDBActivity.class));
        }else if(role.toLowerCase().equals("scorer")){
            startActivity(new Intent(LoginActivity.this, ScorerDBActivity.class));
        }
    }

    private void signIn(String email, String password) {

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    openNextPage(role);
                    Log.i("Login","login");
                }else {
                    Utils.hidepDialog();
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void checkApproved(String email, String password) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.EMAIL).getValue().toString().equals(email)){
                        role = dataSnapshot.child(IConstants.ROLE).getValue().toString();
                        isApproved = Boolean.parseBoolean(dataSnapshot.child(IConstants.ISAPPROVED).getValue().toString());
                        if(isApproved){
                            signIn(email, password);
                        } else {
                            Utils.hidepDialog();
                            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Thank you!")
                                    .setContentText("Your application is in under progress. We will reach you soon. If you don't receive any email within 3 days, then contact us at thecrease6@gmail.com")
                                    .setConfirmText("Okay")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                        }
                                    }).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
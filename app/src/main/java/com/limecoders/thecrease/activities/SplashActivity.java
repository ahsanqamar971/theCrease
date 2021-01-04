package com.limecoders.thecrease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.dashboards.AdminDBActivity;
import com.limecoders.thecrease.activities.dashboards.CoachDBActivity;
import com.limecoders.thecrease.activities.dashboards.PlayerDBActivity;
import com.limecoders.thecrease.activities.dashboards.ScorerDBActivity;
import com.limecoders.thecrease.activities.dashboards.TeamDBActivity;
import com.limecoders.thecrease.activities.dashboards.UmpireDBActivity;

public class SplashActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private SharedPreferences preferences;

    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        auth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("com.limecoders.thecrease",MODE_PRIVATE);

        if(auth.getCurrentUser()==null){
            startActivity(new Intent(SplashActivity.this,DashBoardActivity.class));
            finishAffinity();
            finish();
        }else {
            role = preferences.getString("role","");
            openNextPage(role);
        }

    }

    private void openNextPage(String role) {
        if(role.toLowerCase().equals("admin")){
            startActivity(new Intent(SplashActivity.this, AdminDBActivity.class));
            finishAffinity();
            finish();
        }else if(role.toLowerCase().equals("umpire")){
            startActivity(new Intent(SplashActivity.this, UmpireDBActivity.class));
            finishAffinity();
            finish();
        }else if(role.toLowerCase().equals("coach")){
            startActivity(new Intent(SplashActivity.this, CoachDBActivity.class));
            finishAffinity();
            finish();
        }else if(role.toLowerCase().equals("player")){
            startActivity(new Intent(SplashActivity.this, PlayerDBActivity.class));
            finishAffinity();
            finish();
        }else if(role.toLowerCase().equals("team")){
            startActivity(new Intent(SplashActivity.this, TeamDBActivity.class));
            finishAffinity();
            finish();
        }else if(role.toLowerCase().equals("scorer")){
            startActivity(new Intent(SplashActivity.this, ScorerDBActivity.class));
            finishAffinity();
            finish();
        }else {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
        }
    }
}
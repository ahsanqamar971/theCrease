package com.limecoders.thecrease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.signup.RegisterActivity;

public class MenuActivity extends AppCompatActivity {

    private TextView settings, fixtures, tournaments, players, teams, ranking, contactus, privacypolicy;
    private Button signInBtn, signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        settings = findViewById(R.id.settings);
        fixtures = findViewById(R.id.fixtures);
        tournaments = findViewById(R.id.tournaments);
        players = findViewById(R.id.players);
        teams = findViewById(R.id.teams);
        ranking = findViewById(R.id.rankings);
        contactus = findViewById(R.id.contactus);
        privacypolicy = findViewById(R.id.privacypolicy);
        signInBtn = findViewById(R.id.signInBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        clickListeners();

    }

    private void clickListeners() {

        ranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,RankingActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, RegisterActivity.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
            }
        });

        players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this, PlayersActivity.class));
            }
        });

        teams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,TeamsActivity.class));
            }
        });

        tournaments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,TournamentsActivity.class));
            }
        });

        fixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,FixturesActivity.class));
            }
        });

        privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openURL("https://www.google.com");
            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MenuActivity.this,ContactUsActivity.class));
            }
        });

    }

    private void openURL(String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}
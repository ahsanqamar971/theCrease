package com.limecoders.thecrease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.limecoders.thecrease.R;

public class RankingActivity extends AppCompatActivity {

    private TextView teamRanking, playerRanking, umpireRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        teamRanking = findViewById(R.id.teamRankingTV);
        playerRanking = findViewById(R.id.playerRankingTV);
        umpireRanking = findViewById(R.id.umpireRankingTV);

        clickListeners();

    }

    private void clickListeners() {
        teamRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RankingActivity.this,TeamRankingActivity.class));
            }
        });

        playerRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RankingActivity.this, PlayerRankingActivity.class));
            }
        });

        umpireRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RankingActivity.this,UmpireRankingActivity.class));
            }
        });
    }
}
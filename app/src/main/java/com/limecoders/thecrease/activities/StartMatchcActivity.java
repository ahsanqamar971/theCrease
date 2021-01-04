package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.constant.IConstants;

import java.util.HashMap;

public class StartMatchcActivity extends AppCompatActivity {

    private TextView firstInninngs, target, overs;
    private Button startSecondInnings, endMatch;

    private String team1Name, team2Name, umpire1Name, umpire2Name, matchId, batFirst,tossWon,bowlTeam,team1PlayersId,team2PlayersId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_matchc);

        if(getIntent().hasExtra("team1Name")){
            matchId = getIntent().getStringExtra("matchId");
            team1Name = getIntent().getStringExtra("team1Name");
            team2Name = getIntent().getStringExtra("team2Name");
            umpire1Name = getIntent().getStringExtra("umpire1Name");
            umpire2Name = getIntent().getStringExtra("umpire2Name");
            batFirst = getIntent().getStringExtra("batFirst");
            tossWon = getIntent().getStringExtra("tossWon");
            bowlTeam = getIntent().getStringExtra("bowlTeam");
            team1PlayersId = getIntent().getStringExtra("team1PlayersId");
            team2PlayersId = getIntent().getStringExtra("team2PlayersId");
        }

        firstInninngs = findViewById(R.id.firstInnings);
        target = findViewById(R.id.target);
        overs = findViewById(R.id.overs);
        startSecondInnings = findViewById(R.id.startSecondInnings);
        endMatch = findViewById(R.id.endMatch);

        clickListeners();

    }

    private void clickListeners() {

        startSecondInnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartMatchcActivity.this,StartMatchActivity.class)
                        .putExtra("team1Name", team1Name).putExtra("team2Name", team2Name)
                        .putExtra("umpire1Name", umpire1Name).putExtra("umpire2Name", umpire2Name)
                        .putExtra("matchId", matchId).putExtra("tossWon",tossWon)
                        .putExtra("batFirst",bowlTeam).putExtra("bowlTeam",batFirst)
                        .putExtra("team1PlayersId",team2PlayersId)
                        .putExtra("team2PlayersId",team1PlayersId)
                        .putExtra("isSecondInnings",true));
            }
        });

        endMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                        .child(matchId);

                HashMap<String,Object> map = new HashMap<>();

                map.put("isRejected",true);
                map.put("umpireRejected",true);
                map.put("scorerRejected",true);

                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(StartMatchcActivity.this, "Match is Ended", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
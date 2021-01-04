package com.limecoders.thecrease.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.TournamentAdapter;
import com.limecoders.thecrease.models.TournamentModel;
import com.limecoders.thecrease.models.UmpireModel;

import java.util.ArrayList;
import java.util.List;

public class TournamentsActivity extends AppCompatActivity {

    private TextView upcoming, live, finished;

    private List<TournamentModel> models = new ArrayList<>();
    private List<TournamentModel> tournaModel = new ArrayList<>();

    private RecyclerView tourRecyclerView;
    private TournamentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournaments);

        live = findViewById(R.id.live);
        upcoming = findViewById(R.id.upcoming);
        finished = findViewById(R.id.finished);

        tourRecyclerView = findViewById(R.id.tourRecyclerView);
        tourRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tourRecyclerView.setHasFixedSize(true);

        finished.setTypeface(null, Typeface.BOLD);

        getAllTournaments();
        clickListeners();
    }

    private void clickListeners() {
        live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tournaModel.size()>0){
                    tournaModel.clear();
                }
                for(int i=0;i<models.size();i++){
                    if(models.get(i).getCondition().equals("live")){
                        TournamentModel model1 = models.get(i);
                        tournaModel.add(model1);
                    }
                }

                adapter.setModels(tournaModel);

                live.setTypeface(null, Typeface.BOLD);
                upcoming.setTypeface(null, Typeface.NORMAL);
                finished.setTypeface(null, Typeface.NORMAL);
            }
        });

        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tournaModel.size()>0){
                    tournaModel.clear();
                }
                for(int i=0;i<models.size();i++){
                    if(models.get(i).getCondition().equals("finished")){
                        TournamentModel model1 = models.get(i);
                        tournaModel.add(model1);
                    }
                }
                adapter.setModels(tournaModel);

                finished.setTypeface(null, Typeface.BOLD);
                upcoming.setTypeface(null, Typeface.NORMAL);
                live.setTypeface(null, Typeface.NORMAL);
            }
        });

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tournaModel.size()>0){
                    tournaModel.clear();
                }
                for(int i=0;i<models.size();i++){
                    if(models.get(i).getCondition().equals("upcoming")){
                        TournamentModel model1 = models.get(i);
                        tournaModel.add(model1);
                    }
                }
                adapter.setModels(tournaModel);


                upcoming.setTypeface(null, Typeface.BOLD);
                live.setTypeface(null, Typeface.NORMAL);
                finished.setTypeface(null, Typeface.NORMAL);
            }
        });

    }

    private void getAllTournaments() {
        models.add(new TournamentModel("1","Pakistan Super League", "", "",9,"Club","upcoming"));
        models.add(new TournamentModel("2","HBL League", "", "",6,"Other","live"));
        models.add(new TournamentModel("3","Gali Cricket League", "", "",8,"University","upcoming"));
        models.add(new TournamentModel("4","Meezan Bank Super League", "", "",7,"Club","finished"));
        models.add(new TournamentModel("5","Northern Super League", "", "",5,"University","live"));

        if(tournaModel.size()>0){
            tournaModel.clear();
        }
        for(int i=0;i<models.size();i++){
            if(models.get(i).getCondition().equals("finished")){
                TournamentModel model1 = models.get(i);
                tournaModel.add(model1);
            }
        }

        adapter = new TournamentAdapter(TournamentsActivity.this, tournaModel);
        tourRecyclerView.setAdapter(adapter);
    }
}
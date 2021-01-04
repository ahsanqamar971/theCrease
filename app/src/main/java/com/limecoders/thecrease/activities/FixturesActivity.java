package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.FixtureAdapter;
import com.limecoders.thecrease.adapter.NewsAdapter;
import com.limecoders.thecrease.adapter.TournTableAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.NewsModel;
import com.limecoders.thecrease.models.TourTableModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FixturesActivity extends AppCompatActivity {

    private List<FixturesModel> fixtureModels = new ArrayList<>();

    private RecyclerView fixRecyclerView;
    private FixtureAdapter adapter;

    private List<NewsModel> newsModels = new ArrayList<>();

    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;

    private RecyclerView tableRecyclerView;
    private TournTableAdapter tableAdapter;
    private List<TourTableModel> tableModels = new ArrayList<>();

    private TextView matchesTV, tableTV, finishedTV,  upcoming;
    private LinearLayout fluLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        fluLayout = findViewById(R.id.fluLayout);
        matchesTV = findViewById(R.id.matches);
        tableTV = findViewById(R.id.table);
        finishedTV = findViewById(R.id.finished);
        upcoming = findViewById(R.id.upcoming);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setHasFixedSize(true);

        tableRecyclerView = findViewById(R.id.tableRecyclerView);
        tableRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tableRecyclerView.setHasFixedSize(true);

        fixRecyclerView = findViewById(R.id.fixRecyclerView);
        fixRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        fixRecyclerView.setHasFixedSize(true);

        matchesTV.setTypeface(null, Typeface.BOLD);
        finishedTV.setTypeface(null, Typeface.BOLD);

        getAllFixtures();
        getTable();

        clickListeners();
    }

    private void clickListeners() {
        matchesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newsRecyclerView.setVisibility(View.GONE);
                fluLayout.setVisibility(View.VISIBLE);
                fixRecyclerView.setVisibility(View.VISIBLE);
                tableRecyclerView.setVisibility(View.GONE);

                matchesTV.setTypeface(null,Typeface.BOLD);
                tableTV.setTypeface(null,Typeface.NORMAL);

            }
        });

        tableTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableAdapter = new TournTableAdapter(FixturesActivity.this,tableModels);
                tableRecyclerView.setAdapter(tableAdapter);

                newsRecyclerView.setVisibility(View.GONE);
                fluLayout.setVisibility(View.GONE);
                fixRecyclerView.setVisibility(View.GONE);
                tableRecyclerView.setVisibility(View.VISIBLE);

                matchesTV.setTypeface(null,Typeface.NORMAL);
                tableTV.setTypeface(null,Typeface.BOLD);


            }
        });
        finishedTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finishedTV.setTypeface(null,Typeface.BOLD);
                upcoming.setTypeface(null,Typeface.NORMAL);

            }
        });

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                upcoming.setTypeface(null,Typeface.BOLD);
                finishedTV.setTypeface(null,Typeface.NORMAL);

            }
        });

    }

    private void getTable(){
        tableModels.add(new TourTableModel("1", "KK", "1", "16", "0",
                "10", "8", "2", "0", "2.2"));
        tableModels.add(new TourTableModel("2", "LQ", "1", "16", "0",
                "10", "8", "2", "0", "2.2"));
        tableModels.add(new TourTableModel("3", "MS", "1", "16", "0",
                "10", "8", "2", "0", "2.2"));
        tableModels.add(new TourTableModel("4", "PZ", "1", "16", "0",
                "10", "8", "2", "0", "2.2"));
        tableModels.add(new TourTableModel("5", "QG", "1", "16", "0",
                "10", "8", "2", "0", "2.2"));
        tableModels.add(new TourTableModel("6", "ISU", "1", "16", "0",
                "10", "8", "2", "0", "2.2"));
    }

    private void getAllFixtures() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(fixtureModels.size()>0){
                    fixtureModels.clear();
                }
                fixtureModels.add(new FixturesModel("1","MS", "LQ", "Lahore","123",
                        "Pakistan Super League","Upcoming","","","21-Jan 07:00 PM","20"));
                fixtureModels.add(new FixturesModel("2","KK", "ISU", "Dubai","123",
                        "Pakistan Nat League","Finished","","","24-Nov 07:00 PM","35"));
                fixtureModels.add(new FixturesModel("3","MS", "LQ", "Karachi","123",
                        "Pakistan League","Upcoming","","","23-Jan 07:00 PM","10"));
                fixtureModels.add(new FixturesModel("4","PZ", "SS", "Sydney","123",
                        "Super League","Finished","","","22-Dec 07:00 PM","20"));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new FixtureAdapter(FixturesActivity.this, fixtureModels);
        fixRecyclerView.setAdapter(adapter);

    }

}
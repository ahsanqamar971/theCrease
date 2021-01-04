package com.limecoders.thecrease.ui.score;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderViewPager;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.ScoreSliderAdapter;
import com.limecoders.thecrease.models.ScoreModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScoreFragment extends Fragment {

    private ScoreViewModel scoreViewModel;
    private RecyclerView scoreRecylcerView;
    private ScoreSliderAdapter scoreSliderAdapter;

    private TextView leagueName, venueTV, score1TV, score2TV, overs1TV, overs2TV, team1NameTV, team2NameTV,
    wonTV, dateTimeTV, momNameTV;
    private CircleImageView team1Image, team2Image, momPlayerImage;
    private ImageView leagueMore;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        scoreViewModel =
                ViewModelProviders.of(this).get(ScoreViewModel.class);

        View root = inflater.inflate(R.layout.fragment_score, container, false);

        leagueName = root.findViewById(R.id.leagueName);
        leagueMore = root.findViewById(R.id.leagueMore);
        venueTV = root.findViewById(R.id.matchVenue);
        score1TV = root.findViewById(R.id.score1);
        score2TV = root.findViewById(R.id.score2);
        overs1TV = root.findViewById(R.id.overs1);
        overs2TV = root.findViewById(R.id.overs2);
        team1NameTV = root.findViewById(R.id.team1Name);
        team2NameTV = root.findViewById(R.id.team2Name);
        wonTV = root.findViewById(R.id.wonTV);
        dateTimeTV = root.findViewById(R.id.dateTimeTV);
        momNameTV = root.findViewById(R.id.playerName);
        team1Image = root.findViewById(R.id.team1Image);
        team2Image = root.findViewById(R.id.team2Image);
        momPlayerImage = root.findViewById(R.id.profileImage);

        scoreRecylcerView = root.findViewById(R.id.scoreRecylcerView);
        scoreRecylcerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreRecylcerView.setHasFixedSize(true);

        scoreViewModel.getScoreList().observe(getViewLifecycleOwner(), new Observer<List<ScoreModel>>() {
            @Override
            public void onChanged(List<ScoreModel> scoreModels) {
                scoreSliderAdapter = new ScoreSliderAdapter(getActivity(),scoreModels);
                scoreRecylcerView.setAdapter(scoreSliderAdapter);
            }
        });


        return root;
    }
}
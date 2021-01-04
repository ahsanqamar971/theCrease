package com.limecoders.thecrease.ui.news;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderViewPager;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.NewsAdapter;
import com.limecoders.thecrease.adapter.NewsSliderAdapter;
import com.limecoders.thecrease.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private NewsViewModel newsViewModel;
    private RecyclerView newsRecyclerView;

    private List<NewsModel> newsModel;

    private NewsAdapter adapter;
    private TextView allNewsTV, matchRelatedTV, announcementsTV;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        newsViewModel =
                ViewModelProviders.of(this).get(NewsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_news, container, false);

        newsRecyclerView = root.findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        newsRecyclerView.setHasFixedSize(true);

        allNewsTV = root.findViewById(R.id.allNews);
        matchRelatedTV = root.findViewById(R.id.matchRelated);
        announcementsTV = root.findViewById(R.id.announcements);

        adapter = new NewsAdapter(getActivity());
        newsRecyclerView.setAdapter(adapter);

        allNewsTV.setTypeface(null, Typeface.BOLD);
        announcementsTV.setTypeface(null,Typeface.NORMAL);
        matchRelatedTV.setTypeface(null,Typeface.NORMAL);

        newsViewModel.getNewsModel().observe(getViewLifecycleOwner(), new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                newsModel = newsModels;
                for(int i=0;i<newsModel.size();i++){
                    if(newsModel.get(i).getMainCategory().equals("News")){
                        NewsModel newsModel1 = newsModel.get(i);
                        models.add(newsModel1);
                    }
                }
                adapter.setNewsModels(models);
            }
        });

        clickListneres();

        return root;
    }

    private List<NewsModel> models = new ArrayList<>();

    private void clickListneres() {

        allNewsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(models.size()>0){
                    models.clear();
                }
                for(int i=0;i<newsModel.size();i++){
                    if(newsModel.get(i).getMainCategory().equals("News")){
                        NewsModel newsModel1 = newsModel.get(i);
                        models.add(newsModel1);
                    }
                }

                adapter.setNewsModels(models);
                allNewsTV.setTypeface(null,Typeface.BOLD);
                announcementsTV.setTypeface(null,Typeface.NORMAL);
                matchRelatedTV.setTypeface(null,Typeface.NORMAL);
            }
        });

        announcementsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(models.size()>0){
                    models.clear();
                }
                for(int i=0;i<newsModel.size();i++){
                    if(newsModel.get(i).getMainCategory().equals("Announcement")){
                        NewsModel newsModel1 = newsModel.get(i);
                        models.add(newsModel1);
                    }
                }

                adapter.setNewsModels(models);
                allNewsTV.setTypeface(null,Typeface.NORMAL);
                announcementsTV.setTypeface(null,Typeface.BOLD);
                matchRelatedTV.setTypeface(null,Typeface.NORMAL);
            }
        });

        matchRelatedTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(models.size()>0){
                    models.clear();
                }
                for(int i=0;i<newsModel.size();i++){
                    if(newsModel.get(i).getMainCategory().equals("Match Related")){
                        NewsModel newsModel1 = newsModel.get(i);
                        models.add(newsModel1);
                    }
                }

                adapter.setNewsModels(models);
                allNewsTV.setTypeface(null,Typeface.NORMAL);
                announcementsTV.setTypeface(null,Typeface.NORMAL);
                matchRelatedTV.setTypeface(null,Typeface.BOLD);
            }
        });

    }

}
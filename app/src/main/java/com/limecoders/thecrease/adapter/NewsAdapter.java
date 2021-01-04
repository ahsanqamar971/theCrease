package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.NewsBlogActivity;
import com.limecoders.thecrease.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context context;
    private List<NewsModel> newsModels = new ArrayList<>();

    public NewsAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.allnews_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NewsModel model = newsModels.get(position);

        holder.leagueName.setText(model.getCategory());
        holder.writerName.setText(model.getWriterName());
        holder.dateTime.setText(model.getDateTime());
        holder.newsTitle.setText(model.getNewsTitle());

        if(model.getNewsImage().equals("")){
            holder.newsImage.setImageResource(R.drawable.dummy_image);
        }else {
            Picasso.get().load(model.getNewsImage()).into(holder.newsImage);
        }

        holder.leagueMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, NewsBlogActivity.class));
            }
        });


    }

    public void setNewsModels(List<NewsModel> newsModels){
        this.newsModels = newsModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView leagueName, writerName, dateTime, newsTitle;
        private ImageView leagueMore, newsImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leagueName = itemView.findViewById(R.id.leagueName);
            writerName = itemView.findViewById(R.id.writerName);
            dateTime = itemView.findViewById(R.id.dateTimeTV);
            newsTitle = itemView.findViewById(R.id.articleTitle);
            leagueMore = itemView.findViewById(R.id.newsMore);
            newsImage = itemView.findViewById(R.id.articleImage);
        }
    }
}

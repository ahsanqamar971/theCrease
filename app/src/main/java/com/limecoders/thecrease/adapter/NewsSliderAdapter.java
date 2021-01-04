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

import com.bumptech.glide.Glide;
import com.github.islamkhsh.CardSliderAdapter;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.NewsBlogActivity;
import com.limecoders.thecrease.models.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsSliderAdapter extends CardSliderAdapter<NewsSliderAdapter.ViewHolder> {

    private Context context;
    private List<NewsModel> models;

    public NewsSliderAdapter(Context context, List<NewsModel> models){
        this.context = context;
        this.models = models;
    }

    @Override
    public void bindVH(ViewHolder holder, int i) {
        NewsModel model = models.get(i);

        holder.leagueName.setText(model.getCategory());
        holder.newsTitle.setText(model.getNewsTitle());
        holder.writerName.setText(model.getWriterName());
        holder.date.setText(model.getDateTime());

        if(model.getNewsDesc().length()>30){
            String newDesc = model.getNewsDesc().substring(0,31) + "... Read More";
            holder.newsDesc.setText(newDesc);
        }else {
            holder.newsDesc.setText(model.getNewsDesc());
        }


        if(model.getNewsImage().equals("")){
            holder.newsImage.setImageResource(R.drawable.dummy_image);
        }else {
            Picasso.get().load(model.getNewsImage()).into(holder.newsImage);
//            Glide.with(context).load(model.getNewsImage()).into(holder.newsImage);
        }

        holder.newsMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, NewsBlogActivity.class));
            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.news_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView leagueName, newsTitle, newsDesc, writerName, date;
        private ImageView newsImage,newsMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leagueName = itemView.findViewById(R.id.leagueName);
            newsMore = itemView.findViewById(R.id.newsMore);
            newsTitle = itemView.findViewById(R.id.newsTitle);
            newsDesc = itemView.findViewById(R.id.newsDesc);
            writerName = itemView.findViewById(R.id.writerName);
            date = itemView.findViewById(R.id.dateTime);
            newsImage = itemView.findViewById(R.id.newsImage);

        }
    }
}

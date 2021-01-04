package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.MatchDetailAdapter;
import com.limecoders.thecrease.models.MatchDetailModel;

import java.util.List;

public class BallsAdapter extends RecyclerView.Adapter<BallsAdapter.ViewHolder> {

    private Context context;
    private List<MatchDetailModel> matchDetailModels;

    public BallsAdapter(Context context, List<MatchDetailModel> matchDetailModels){
        this.context = context;
        this.matchDetailModels = matchDetailModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.over_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchDetailModel model = matchDetailModels.get(position);

        holder.ballRun.setText(model.getRuns());
    }

    @Override
    public int getItemCount() {
        return matchDetailModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ballRun;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ballRun = itemView.findViewById(R.id.ballRun);

        }
    }
}

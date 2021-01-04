package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.TeamRequestModel;
import com.limecoders.thecrease.models.Users;

import java.util.List;

public class CoachReqAdapter extends RecyclerView.Adapter<CoachReqAdapter.ViewHolder> {

    private Context context;
    private List<TeamRequestModel> teamRequestModels;

    public CoachReqAdapter(Context context, List<TeamRequestModel> teamRequestModels){
        this.context = context;
        this.teamRequestModels = teamRequestModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.team_request_head, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.team_request_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamRequestModel model = teamRequestModels.get(position);


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return teamRequestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView srNo, teamName, rank, matches;
        private ImageView accept, reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            teamName = itemView.findViewById(R.id.teamTV);
            rank = itemView.findViewById(R.id.rankTV);
            matches = itemView.findViewById(R.id.matchesTV);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);


        }
    }
}

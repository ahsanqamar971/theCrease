package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;

public class PendingReqAdapter extends RecyclerView.Adapter<PendingReqAdapter.ViewHolder> {

    private Context context;

    public PendingReqAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.penreq_head_item, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.pending_request_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView srNo, team, rank, level, accept, decline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            team = itemView.findViewById(R.id.team);
            rank = itemView.findViewById(R.id.rankTV);
            level = itemView.findViewById(R.id.levelTV);
            accept = itemView.findViewById(R.id.yesTV);
            decline = itemView.findViewById(R.id.noTV);
        }
    }
}

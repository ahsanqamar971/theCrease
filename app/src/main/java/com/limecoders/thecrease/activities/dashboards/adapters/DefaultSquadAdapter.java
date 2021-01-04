package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.limecoders.thecrease.R;
import com.limecoders.thecrease.models.Users;

import java.util.List;

public class DefaultSquadAdapter extends RecyclerView.Adapter<DefaultSquadAdapter.ViewHolder> {

    private Context context;
    private List<Users> squadModels;

    public DefaultSquadAdapter(Context context, List<Users> squadModels){
        this.context = context;
        this.squadModels = squadModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.default_squad_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users model = squadModels.get(position);

        holder.srNo.setText(String.valueOf(position+1));
        holder.playerTv.setText(model.getName());
        holder.role.setText(model.getPlayerRole());
    }

    public void setSquadModels(List<Users> squadModels){
        this.squadModels = squadModels;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return squadModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView srNo, playerTv, role;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            playerTv = itemView.findViewById(R.id.playerTv);
            role = itemView.findViewById(R.id.role);

        }
    }
}

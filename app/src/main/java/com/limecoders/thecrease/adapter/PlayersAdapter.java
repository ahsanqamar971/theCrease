package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.ProfileActivity;
import com.limecoders.thecrease.activities.dashboards.PlayerDBActivity;
import com.limecoders.thecrease.models.PlayerModel;
import com.limecoders.thecrease.models.Users;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder> {

    private Context context;
    private List<Users> models;

    public PlayersAdapter(Context context, List<Users> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.player_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users model = models.get(position);

        if(model.getProfileImage().equals("")){
            holder.profileImage.setImageResource(R.drawable.dummy_image);
        }else {
            Glide.with(context).load(model.getProfileImage()).into(holder.profileImage);
        }

        holder.playerName.setText(model.getName());
        holder.playerLevel.setText(model.getLevel());
        holder.playerRank.setText(String.valueOf(model.getRank()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProfileActivity.class).putExtra("id",model.getId()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView playerName, playerRank, playerLevel;
        private CircleImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            playerName = itemView.findViewById(R.id.playerName);
            playerRank = itemView.findViewById(R.id.playerRank);
            playerLevel = itemView.findViewById(R.id.playerLevel);
            profileImage = itemView.findViewById(R.id.profileImage);

        }
    }
}

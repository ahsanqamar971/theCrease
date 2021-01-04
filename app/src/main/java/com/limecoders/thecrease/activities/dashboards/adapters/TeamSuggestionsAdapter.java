package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.TeamModel;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.AdminSignUpActivity;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TeamSuggestionsAdapter extends RecyclerView.Adapter<TeamSuggestionsAdapter.ViewHolder> {

    private List<Users> teamModels;
    private Context context;
    private Users playerInfo;

    public TeamSuggestionsAdapter(Context context, List<Users> teamModels, Users playerInfo){
        this.context = context;
        this.teamModels = teamModels;
        this.playerInfo = playerInfo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.teamsug_head_item, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.team_suggestion_item, parent, false);
            return new ViewHolder(v);
        }
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        if(i!=0) {
            Users model = teamModels.get(i-1);

            holder.srNo.setText(String.valueOf(i));
            holder.team.setText(model.getTeamName());
            holder.rank.setText(String.valueOf(model.getRank()));
            holder.level.setText(model.getLevel());

            holder.send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context,SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Already in a Team")
                            .setContentText("To send a request to a new team, you need to leave your previous team. Are you sure, you want to leave your team?")
                            .setConfirmText("Leave")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                    sendRequest(model);
                                }
                            }).setCancelText("No")
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();

                }
            });
        }
    }

    private void sendRequest(Users teamModel) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.PLAYERREQUEST);

        String id = UUID.randomUUID().toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("playerName", playerInfo.getName());
        map.put("rank", String.valueOf(playerInfo.getRank()));
        map.put("playerId", playerInfo.getId());
        map.put("batHand",playerInfo.getBatType());
        map.put("batStyle", playerInfo.getBatStyle());
        map.put("bowlHand", playerInfo.getBowlType());
        map.put("bowlStyle", playerInfo.getBowlStyle());
        map.put("teamId", teamModel.getId());
        map.put("isApproved", false);
        map.put("isRejected", false);
        map.put("isTeam",false);
        
        reference.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updatePlayerInfo(playerInfo.getId());
            }
        });
    }

    private void updatePlayerInfo(String id) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        HashMap<String,Object> map = new HashMap<>();
        map.put(IConstants.INTEAM,false);
        map.put(IConstants.ISINSQUAD,false);
        map.put(IConstants.TEAMNAME,"");

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return teamModels.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView srNo, team, rank, level, send;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            team = itemView.findViewById(R.id.teamTV);
            rank = itemView.findViewById(R.id.rankTV);
            level = itemView.findViewById(R.id.levelTV);
            send = itemView.findViewById(R.id.sendBtn);

        }
    }
}

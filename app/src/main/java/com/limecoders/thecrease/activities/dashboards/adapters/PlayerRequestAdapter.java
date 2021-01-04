package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.PlayerRankingAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.PlayerModel;
import com.limecoders.thecrease.models.PlayerRequestModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlayerRequestAdapter extends RecyclerView.Adapter<PlayerRequestAdapter.ViewHolder> {

    private Context context;
    private List<PlayerRequestModel> playerModels;

    public PlayerRequestAdapter(Context context, List<PlayerRequestModel> playerModels){
        this.context = context;
        this.playerModels = playerModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.player_request_head, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.player_request_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position!=0) {
            PlayerRequestModel model = playerModels.get(position-1);

            holder.srNo.setText(String.valueOf(position));
            holder.player.setText(model.getPlayerName());
            holder.rank.setText(String.valueOf(model.getRank()));
            holder.bat.setText(model.getBatHand() + " Hand\n" + model.getBatStyle());
            holder.bowl.setText(model.getBowlHand() + " Hand\n" + model.getBowlStyle());

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getTeamInfo(model);
                }
            });

            holder.decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rejectRequest(model);
                }
            });
        }
    }

    private void rejectRequest(PlayerRequestModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.PLAYERREQUEST);

        reference.child(model.getPlayerId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Rejected")
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                }
            }
        });
    }

    private void acceptRequest(PlayerRequestModel model, String teamName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.PLAYERREQUEST)
                .child(model.getId());

        HashMap<String, Object> map = new HashMap<>();
        map.put("isApproved",true);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                addPlayer(model,teamName);
            }
        });
    }

    private void addPlayer(PlayerRequestModel model, String teamName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(model.getPlayerId());

        String id = UUID.randomUUID().toString();

        HashMap<String,Object> map = new HashMap<>();
        map.put(IConstants.INTEAM,true);
        map.put(IConstants.TEAMNAME,teamName);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){

                    new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Accepted")
                            .setConfirmText("Okay")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            }).show();
                }
            }
        });

    }

    public void getTeamInfo(PlayerRequestModel id){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id.getTeamId());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                acceptRequest(id,snapshot.child(IConstants.TEAMNAME).getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return playerModels.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView srNo, player, rank, bat, bowl;
        private ImageView accept, decline;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            player = itemView.findViewById(R.id.playerTV);
            rank = itemView.findViewById(R.id.rankTV);
            bat = itemView.findViewById(R.id.batTV);
            bowl = itemView.findViewById(R.id.bowlTV);
            accept = itemView.findViewById(R.id.accept);
            decline = itemView.findViewById(R.id.reject);
        }
    }
}

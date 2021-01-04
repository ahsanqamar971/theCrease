package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.Users;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlayerSuggestionAdapter extends RecyclerView.Adapter<PlayerSuggestionAdapter.ViewHolder> {

    private Context context;
    private List<Users> usersList;

    public PlayerSuggestionAdapter(Context context, List<Users> usersList){
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=null;
        if(viewType == 0){
            v = LayoutInflater.from(context)
                    .inflate(R.layout.player_suggestion_head, parent, false);
            return new ViewHolder(v);
        }else{
            v = LayoutInflater.from(context)
                    .inflate(R.layout.player_suggestion_item, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(position!=0) {
            Users model = usersList.get(position-1);

            holder.srNo.setText(String.valueOf(position));
            holder.team.setText(model.getName());
            holder.rank.setText(String.valueOf(model.getRank()));
            holder.bat.setText(model.getBatType() + " Hand\n" + model.getBatStyle());
            holder.bowl.setText(model.getBowlType() + " Hand\n" + model.getBowlStyle());

            holder.sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.PLAYERREQUEST);

                    String id = UUID.randomUUID().toString();

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("playerName", model.getName());
                    map.put("rank", String.valueOf(model.getRank()));
                    map.put("playerId", model.getId());
                    map.put("batHand", model.getBatType());
                    map.put("batStyle", model.getBatStyle());
                    map.put("bowlHand", model.getBowlType());
                    map.put("bowlStyle", model.getBowlStyle());
                    map.put("teamId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    map.put("isApproved", false);
                    map.put("isRejected", false);
                    map.put("isTeam",true);

                    reference.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Sent")
                                        .setContentText("Your request is send to the team.")
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
            });
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView srNo, team, rank, bat, bowl;
        private Button sendBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srNo = itemView.findViewById(R.id.srNo);
            team = itemView.findViewById(R.id.teamTV);
            rank = itemView.findViewById(R.id.rankTV);
            bat = itemView.findViewById(R.id.batTV);
            bowl = itemView.findViewById(R.id.bowlTV);
            sendBtn = itemView.findViewById(R.id.sendBtn);

        }
    }
}

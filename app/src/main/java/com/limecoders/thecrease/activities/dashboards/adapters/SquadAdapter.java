package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.Users;

import java.util.HashMap;
import java.util.List;

import static com.limecoders.thecrease.activities.StartMatchActivity.batId;
import static com.limecoders.thecrease.activities.StartMatchActivity.batsman;
import static com.limecoders.thecrease.activities.StartMatchActivity.bowlId;
import static com.limecoders.thecrease.activities.StartMatchActivity.bowler;

public class SquadAdapter extends RecyclerView.Adapter<SquadAdapter.ViewHolder> {

    private Context context;
    private List<Users> users;
    private String batTeam;
    private String matchId;

    private MatchRequestModel matchRequestModel;

    public SquadAdapter(Context context, List<Users> users, String matchId){
        this.context = context;
        this.users = users;
        this.matchId = matchId;
        getMatchDetail();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.default_squad_item2, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users model = users.get(position);

        holder.srNo.setText(String.valueOf(position+1));
        holder.playerTv.setText(model.getName());
        holder.role.setText(model.getPlayerRole());

        if(model.getInfo().toLowerCase().equals("playing")){
            holder.checkbox.setChecked(true);
        }

        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    playing(model.getId(), holder);
                    if (batTeam.equals(model.getTeamName())) {
                        batId.add(model.getId());
                        batsman = batsman + 1;
                    } else {
                        bowlId.add(model.getId());
                        bowler = bowler + 1;
                    }
                } else {
                    notPlaying(model.getId(), holder);
                    if (batTeam.equals(model.getTeamName())) {
                        batId.remove(model.getId());
                        if (batsman > 0) {
                            batsman = batsman - 1;
                        }
                    } else {
                        bowlId.remove(model.getId());
                        if (bowler > 0) {
                            bowler = bowler - 1;
                        }
                    }
                }
            }
        });

    }

    private void notPlaying(String id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        HashMap<String,Object> map = new HashMap<>();
        map.put(IConstants.INFO,"notPlaying");

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    holder.checkbox.setChecked(false);
                    Log.i("inSquad","inSquad");
                }
            }
        });
    }

    private void playing(String id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        HashMap<String,Object> map = new HashMap<>();
        map.put(IConstants.INFO,"playing");

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    holder.checkbox.setChecked(true);
                    Log.i("inSquad","inSquad");
                }
            }
        });
    }

    public void getMatchDetail(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(matchId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                matchRequestModel = new MatchRequestModel(dataSnapshot.child("id").getValue().toString(),
                        dataSnapshot.child("teamId").getValue().toString(), dataSnapshot.child("teamName").getValue().toString(),
                        dataSnapshot.child("type").getValue().toString(), dataSnapshot.child("venue").getValue().toString(),
                        dataSnapshot.child("date").getValue().toString(), dataSnapshot.child("time").getValue().toString(),
                        Boolean.parseBoolean(dataSnapshot.child("isRejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("umpire1Rejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("umpire1Accepted").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("umpire2Rejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("umpire2Accepted").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("scorerAccepted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("scorerRejected").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("isCompleted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isFinished").getValue().toString()),
                        Boolean.parseBoolean(dataSnapshot.child("teamAccepted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("teamRejected").getValue().toString()),
                        dataSnapshot.child("team1PlayersId").getValue().toString(), dataSnapshot.child("team2PlayersId").getValue().toString(),
                        dataSnapshot.child("team2Id").getValue().toString(),dataSnapshot.child("umpire1Id").getValue().toString(),
                        dataSnapshot.child("umpire2Id").getValue().toString(),dataSnapshot.child("scorerId").getValue().toString(),
                        dataSnapshot.child("score1").getValue().toString(),dataSnapshot.child("score2").getValue().toString(),
                        dataSnapshot.child("momPlayerId").getValue().toString(),dataSnapshot.child("won").getValue().toString(),
                        dataSnapshot.child("matchCondition").getValue().toString(),dataSnapshot.child("leagueName").getValue().toString(),
                        dataSnapshot.child("overs1").getValue().toString(),dataSnapshot.child("overs2").getValue().toString(),
                        dataSnapshot.child("firstInnings").getValue().toString(),dataSnapshot.child("tossWon").getValue().toString(),dataSnapshot.child("batFirst").getValue().toString());

                batTeam = matchRequestModel.getBatFirst();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 11;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView srNo, playerTv, role;
        private CheckBox checkbox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkbox = itemView.findViewById(R.id.checkbox);
            srNo = itemView.findViewById(R.id.srNo);
            playerTv = itemView.findViewById(R.id.playerTv);
            role = itemView.findViewById(R.id.role);

        }
    }
}

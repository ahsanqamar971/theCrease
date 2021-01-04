package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.adapter.PlayerRankingAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.AdminSignUpActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MatchRequestAdapter extends RecyclerView.Adapter<MatchRequestAdapter.ViewHolder> {

    private Context context;
    private List<MatchRequestModel> requestModels;
    private boolean isTournament;
    private int playerCount = 0;
    private List<Users> defaultPlayerList = new ArrayList<>();
    private String teamName;
    private List<String> team1PlayerId = new ArrayList<>();

    public MatchRequestAdapter(Context context, List<MatchRequestModel> requestModels, boolean isTournament, String teamName){
        this.context = context;
        this.requestModels = requestModels;
        this.isTournament = isTournament;
        this.teamName = teamName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                    .inflate(R.layout.match_request_item, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MatchRequestModel model = requestModels.get(position);

        holder.teamName.setText(model.getTeamName());
        holder.srNo.setText(String.valueOf(position+1));
        holder.venue.setText(model.getVenue());
        holder.type.setText(model.getType());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTeamSquad(model);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectMatch(model);
            }
        });

    }

    private void getTeamSquad(MatchRequestModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    if (dataSnapshot.child(IConstants.ROLE).getValue().toString().toLowerCase().equals("player")
                        && dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(teamName)
                            && Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString())) {
                        defaultPlayerList.add(new Users(dataSnapshot.child("id").getValue().toString(), dataSnapshot.child(IConstants.EMAIL).getValue().toString(),
                                dataSnapshot.child(IConstants.ROLE).getValue().toString(), dataSnapshot.child(IConstants.AGE).getValue().toString(),
                                dataSnapshot.child(IConstants.SEX).getValue().toString(), dataSnapshot.child(IConstants.NAME).getValue().toString(),
                                dataSnapshot.child(IConstants.RELIGION).getValue().toString(), dataSnapshot.child(IConstants.PROFILEIMAGE).getValue().toString(),
                                dataSnapshot.child(IConstants.TEAMNAME).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.INTEAM).getValue().toString()),
                                dataSnapshot.child(IConstants.BATTYPE).getValue().toString(), dataSnapshot.child(IConstants.BATSTYLE).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLTYPE).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.RANK).getValue().toString()),
                                dataSnapshot.child(IConstants.LEVEL).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESWON).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESPLAYED).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MATCHESLOSE).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MATCHESTIE).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.MATCHESNR).getValue().toString()),
                                dataSnapshot.child(IConstants.REGISTEREDYEAR).getValue().toString(), dataSnapshot.child(IConstants.VENUE).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURWON).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURPLAYED).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.TOURLOSE).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.TOURTIE).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.TOURNR).getValue().toString()), dataSnapshot.child(IConstants.PHONENUMBER).getValue().toString(),
                                dataSnapshot.child(IConstants.INFO).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.MATCHSCORED).getValue().toString()),
                                dataSnapshot.child(IConstants.TOUR1).getValue().toString(), dataSnapshot.child(IConstants.TOUR2).getValue().toString(),
                                dataSnapshot.child(IConstants.EXPERIENCE).getValue().toString(), dataSnapshot.child(IConstants.PREFERENCE).getValue().toString(),
                                dataSnapshot.child(IConstants.EDUCATION).getValue().toString(), dataSnapshot.child(IConstants.COURSES).getValue().toString(),
                                dataSnapshot.child(IConstants.BOWLSTYLE).getValue().toString(), dataSnapshot.child(IConstants.CRICKETBACKGROUND).getValue().toString(),
                                Integer.parseInt(dataSnapshot.child(IConstants.SQUADNUMBER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.COACHNUMBER).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.PITCHCOUNTER).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.RUNS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BATAVG).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.BATSR).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.BATHS).getValue().toString()), dataSnapshot.child(IConstants.DEBUT).getValue().toString(),
                                dataSnapshot.child(IConstants.RECENT).getValue().toString(), Double.parseDouble(dataSnapshot.child(IConstants.BOWLAVG).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.BOWLSR).getValue().toString()), Double.parseDouble(dataSnapshot.child(IConstants.ECON).getValue().toString()),
                                dataSnapshot.child(IConstants.BB).getValue().toString(), Integer.parseInt(dataSnapshot.child(IConstants.WKCATCHES).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.WKSTUMPS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.WKRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.FCATCHES).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.FRUNOUT).getValue().toString()),
                                Integer.parseInt(dataSnapshot.child(IConstants.MOMAWARDS).getValue().toString()), Integer.parseInt(dataSnapshot.child(IConstants.MOSAWARDS).getValue().toString()),
                                Double.parseDouble(dataSnapshot.child(IConstants.RATING).getValue().toString()), dataSnapshot.child(IConstants.HOMEGROUND).getValue().toString(),
                                dataSnapshot.child(IConstants.PLAYERROLE).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.ISAPPROVED).getValue().toString()),
                                dataSnapshot.child(IConstants.ADDRESS).getValue().toString(), Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child(IConstants.ISREJECTED).getValue().toString())));
                        playerCount = playerCount + 1;
                        team1PlayerId.add(dataSnapshot.child("id").getValue().toString());
                    }
                }
                if(playerCount != 11){
                    Toast.makeText(context, "Please select only 11 players", Toast.LENGTH_SHORT).show();
                }else {
                    acceptMatch(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void rejectMatch(MatchRequestModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(model.getId());


        HashMap<String, Object> map = new HashMap<>();
        map.put("teamRejected",true);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
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

    private void acceptMatch(MatchRequestModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(model.getId());


        HashMap<String, Object> map = new HashMap<>();
        map.put("teamAccepted",true);
        map.put("team2PlayersId",team1PlayerId.toString());

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
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
        });;

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView teamName, type, srNo, venue, date, time;
        private Button accept, reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            teamName = itemView.findViewById(R.id.teamTV);
            type = itemView.findViewById(R.id.overs);
            srNo = itemView.findViewById(R.id.srNo);
            venue = itemView.findViewById(R.id.venue);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);

        }
    }
}

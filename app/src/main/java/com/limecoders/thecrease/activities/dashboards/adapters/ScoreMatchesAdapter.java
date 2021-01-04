package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import com.limecoders.thecrease.activities.StartMatchActivity;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class ScoreMatchesAdapter extends RecyclerView.Adapter<ScoreMatchesAdapter.ViewHolder> {

    private Context context;
    private List<MatchRequestModel> modelList;

    private String umpire2Name = "", umpire1Name = "";
    private String team1Name = "", team2Name = "";

    public ScoreMatchesAdapter(Context context, List<MatchRequestModel> modelList){
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.score_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MatchRequestModel model = modelList.get(position);

        holder.overs.setText("Overs: "+model.getType());
        holder.venue.setText(model.getVenue());
        holder.date.setText(model.getDate()+model.getTime());

        getUmpire1Info(model.getUmpire1Id(), holder);
        getUmpire2Info(model.getUmpire2Id(),holder);
        getTeam1Info(model.getTeamId(),holder);
        getTeam2Info(model.getTeam2Id(),holder);

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTeam1Players(model.getTeamId());
                setTeam2Players(model.getTeam2Id());
                context.startActivity(new Intent(context, StartMatchActivity.class)
                .putExtra("team1Name",team1Name).putExtra("team2Name",team2Name)
                .putExtra("umpire1Name",umpire1Name).putExtra("umpire2Name",umpire2Name)
                .putExtra("matchId",model.getId()));
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                        .child(model.getId());

                HashMap<String, Object> map = new HashMap<>();

                map.put("scorerRejected",true);

                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    private void setTeam2Players(String teamName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(teamName)
                    && Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString())){
                        setNotPlaying(reference, dataSnapshot.child("id").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setNotPlaying(DatabaseReference reference, String id) {
        HashMap<String,Object> map = new HashMap<>();
        map.put(IConstants.INFO,"notPlaying");

        reference.child(id).updateChildren(map);
    }

    private void setTeam1Players(String teamName) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child(IConstants.TEAMNAME).getValue().toString().equals(teamName)
                            && Boolean.parseBoolean(dataSnapshot.child(IConstants.ISINSQUAD).getValue().toString())){
                        setNotPlaying(reference, dataSnapshot.child("id").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTeam2Info(String team2Id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(team2Id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                team2Name = snapshot.child(IConstants.TEAMNAME).getValue().toString();
                holder.team2Name.setText(team2Name);
                if (snapshot.child(IConstants.PROFILEIMAGE).getValue().toString().equals("")){
                    holder.team2Image.setImageResource(R.drawable.dummy_image);
                }else {
                    Picasso.get().load(snapshot.child(IConstants.PROFILEIMAGE).getValue().toString()).into(holder.team2Image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getTeam1Info(String team1Id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(team1Id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                team1Name = snapshot.child(IConstants.TEAMNAME).getValue().toString();
                holder.team1Name.setText(team1Name);
                if (snapshot.child(IConstants.PROFILEIMAGE).getValue().toString().equals("")){
                    holder.team1Image.setImageResource(R.drawable.dummy_image);
                }else {
                    Picasso.get().load(snapshot.child(IConstants.PROFILEIMAGE).getValue().toString()).into(holder.team1Image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUmpire2Info(String umpire2Id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(umpire2Id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                umpire2Name = snapshot.child(IConstants.NAME).getValue().toString();

                holder.host.setText("Umpire1: "+umpire1Name+"\nUmpire2: "+umpire2Name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUmpire1Info(String umpire1Id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(umpire1Id);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                umpire1Name = snapshot.child(IConstants.NAME).getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView overs, venue, team1Name, team2Name, date, host;
        private ImageView team1Image, team2Image;
        private Button accept, reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            overs = itemView.findViewById(R.id.oversTV);
            venue = itemView.findViewById(R.id.venue);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            date = itemView.findViewById(R.id.dateTimeTV);
            host = itemView.findViewById(R.id.host);
            team1Image = itemView.findViewById(R.id.team1Image);
            team2Image = itemView.findViewById(R.id.team2Image);
            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);
        }
    }
}

package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.limecoders.thecrease.activities.dashboards.TeamDBActivity.addMatchLayout;

public class TeamFixtureAdapter extends RecyclerView.Adapter<TeamFixtureAdapter.ViewHolder> {

    private Context context;
    private List<MatchRequestModel> fixturesModels;

    private String team1Name, team2Name, umpire2Name, umpire1Name;

    public TeamFixtureAdapter(Context context, List<MatchRequestModel> fixturesModels){
        this.context = context;
        this.fixturesModels = fixturesModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.team_tourn_recyclerview, parent, false);
        return new ViewHolder(v);
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MatchRequestModel model = fixturesModels.get(position);

        holder.leagueName.setText(model.getLeagueName());
        holder.dateTime.setText(model.getDate()+"\t"+model.getTime());
        holder.venue.setText(model.getVenue());
        holder.overs.setText(model.getType());

        getTeam1Info(model.getTeamId(),holder);
        getTeam2Info(model.getTeam2Id(),holder);

        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                        .child(IConstants.FIXTURES).child(model.getId());
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Match is Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return fixturesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView leagueName, team1Name, team2Name, overs, venue, dateTime;
        private CircleImageView team1Image, team2Image;
        private Button cancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            leagueName = itemView.findViewById(R.id.leagueName);
            team1Image = itemView.findViewById(R.id.team1Image);
            team2Image = itemView.findViewById(R.id.team2Image);
            team1Name = itemView.findViewById(R.id.team1Name);
            team2Name = itemView.findViewById(R.id.team2Name);
            overs = itemView.findViewById(R.id.oversTV);
            venue = itemView.findViewById(R.id.matchVenue);
            dateTime = itemView.findViewById(R.id.dateTimeTV);
            cancel = itemView.findViewById(R.id.cancelBtn);

        }
    }
}

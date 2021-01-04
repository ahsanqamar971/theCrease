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
import com.limecoders.thecrease.models.TourModel;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ScorerTourReqAdapter extends RecyclerView.Adapter<ScorerTourReqAdapter.ViewHolder> {

    private Context context;
    private List<TourModel> requestModels;

    public ScorerTourReqAdapter(Context context, List<TourModel> requestModels){
        this.context = context;
        this.requestModels = requestModels;
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
        TourModel model = requestModels.get(position);

        holder.teamName.setText(model.getTourName());
        holder.srNo.setText(String.valueOf(position+1));
        holder.venue.setText(model.getVenue());
        holder.type.setText(model.getType());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptTournament(model);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectTournament(model);

            }
        });
    }


    private void acceptTournament(TourModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.TOURNAMENTREQUEST)
                .child(model.getId());


        HashMap<String, Object> map = new HashMap<>();
        map.put("scorerApproved",true);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    addTournament(model);
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

    private void addTournament(TourModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(IConstants.TOURNAMENTS);

        String id = UUID.randomUUID().toString();

        HashMap<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("tourName",model.getTourName());
        map.put("teamIds",model.getTeamIds());
        map.put("teamPlayers",model.getTeamPlayers());
        map.put("date",model.getDate());
        map.put("venue",model.getVenue());
        map.put("time",model.getTime());
        map.put("type",model.getType());
        map.put("level",model.getLevel());
        map.put("isApproved",true);
        map.put("isRejected",false);
        map.put("mosPlayer","");
        map.put("won","");
        map.put("TBD1","");
        map.put("TBD2","");
        map.put("TBD3","");
        map.put("TBD4","");
        map.put("TBD5","");
        map.put("TBD6","");
        map.put("isEnded",false);

        reference.child(id).setValue(map);
    }

    private void rejectTournament(TourModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.TOURNAMENTREQUEST)
                .child(model.getId());


        HashMap<String, Object> map = new HashMap<>();
        map.put("scorerRejected",true);

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

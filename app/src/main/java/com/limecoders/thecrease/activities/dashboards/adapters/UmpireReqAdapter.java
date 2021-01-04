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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.MatchRequestModel;

import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UmpireReqAdapter extends RecyclerView.Adapter<UmpireReqAdapter.ViewHolder> {

    private Context context;
    private List<MatchRequestModel> matchRequestModels;
    private String umpireId;

    public UmpireReqAdapter(Context context, List<MatchRequestModel> matchRequestModels, String umpireId){
        this.context = context;
        this.matchRequestModels = matchRequestModels;
        this.umpireId = umpireId;
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
        MatchRequestModel model = matchRequestModels.get(position);

        holder.teamName.setText(model.getTeamName());
        holder.srNo.setText(String.valueOf(position+1));
        holder.venue.setText(model.getVenue());
        holder.type.setText(model.getType());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());

        if((model.getUmpire1Id().equals(umpireId) || model.getUmpire2Id().equals(umpireId)) && model.isUmpire1Accepted()){
            holder.text.setText("Accepted");
            holder.text.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }else if((model.getUmpire1Id().equals(umpireId) || model.getUmpire2Id().equals(umpireId)) && model.isUmpire1Rejected()){
            holder.text.setText("Rejected");
            holder.text.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptMatch(model);
            }
        });

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectMatch(model);
            }
        });
    }

    private void rejectMatch(MatchRequestModel model) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST)
                .child(model.getId());


        HashMap<String, Object> map = new HashMap<>();
        if(model.getUmpire1Id().equals(umpireId)){
            map.put("umpire1Rejected",true);
        }else if(model.getUmpire2Id().equals(umpireId)){
            map.put("umpire2Rejected",true);
        }

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
        if(model.getUmpire1Id().equals(umpireId)){

            map.put("umpire1Accepted",true);
        }else if(model.getUmpire2Id().equals(umpireId)){

            map.put("umpire2Accepted",true);
        }

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
        return matchRequestModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView teamName, type, text, srNo, venue, date, time;
        private Button accept, reject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.text);
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

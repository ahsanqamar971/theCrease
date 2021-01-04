package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.Users;

import java.util.HashMap;
import java.util.List;

public class FullSquadAdapter extends RecyclerView.Adapter<FullSquadAdapter.ViewHolder> {

    private Context context;
    private List<Users> squadList;

    public FullSquadAdapter(Context context, List<Users> squadList){
        this.context = context;
        this.squadList = squadList;
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
        Users model = squadList.get(position);

        holder.srNo.setText(String.valueOf(position+1));
        holder.playerTv.setText(model.getName());
        holder.role.setText(model.getPlayerRole());

        if(model.isInSquad()){
            holder.checkBox.setChecked(true);
        }

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) inSquad(model.getId(),holder);
                else outSquad(model.getId(),holder);
            }
        });
    }

    private void outSquad(String id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        HashMap<String,Object> map = new HashMap<>();
        map.put(IConstants.ISINSQUAD,false);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    holder.checkBox.setChecked(false);
                    Log.i("inSquad","inSquad");
                }
            }
        });
    }

    private void inSquad(String id, ViewHolder holder) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                .child(id);

        HashMap<String,Object> map = new HashMap<>();
        map.put(IConstants.ISINSQUAD,true);

        reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    holder.checkBox.setChecked(true);
                    Log.i("inSquad","inSquad");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return squadList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView srNo, playerTv, role;
        private CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            srNo = itemView.findViewById(R.id.srNo);
            playerTv = itemView.findViewById(R.id.playerTv);
            role = itemView.findViewById(R.id.role);
        }
    }
}

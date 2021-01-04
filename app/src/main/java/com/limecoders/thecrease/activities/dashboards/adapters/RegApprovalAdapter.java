package com.limecoders.thecrease.activities.dashboards.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.DashBoardActivity;
import com.limecoders.thecrease.adapter.FixtureAdapter;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.Users;
import com.limecoders.thecrease.signup.AdminSignUpActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class RegApprovalAdapter extends RecyclerView.Adapter<RegApprovalAdapter.ViewHolder> {

    private Context context;
    private List<Users> users;

    public RegApprovalAdapter(Context context, List<Users> users){
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.reg_req_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users model = users.get(position);

        if(model.getRole().toLowerCase().equals("player")){
            holder.educationTV.setVisibility(View.GONE);
            holder.experienceTV.setText("Team: ");
            holder.experience.setText(model.getTeamName());

            holder.playerRoleLayout.setVisibility(View.VISIBLE);
            holder.batHandLayout.setVisibility(View.VISIBLE);
            holder.bowlTypeLayout.setVisibility(View.VISIBLE);
            holder.bowlHandLayout.setVisibility(View.VISIBLE);
            holder.batTypeLayout.setVisibility(View.VISIBLE);

            holder.playerRole.setText(model.getPlayerRole());
            holder.batType.setText(model.getBatStyle());
            holder.bowlStyle.setText(model.getBowlStyle());
            holder.batHand.setText(model.getBatType());
            holder.bowlHand.setText(model.getBowlType());

        }else if(model.getRole().toLowerCase().equals("team")){
            holder.experienceTV.setText("Squad: ");
            holder.experience.setText(String.valueOf(model.getSquadNumber()));
            holder.educationTV.setText("Coaching Staff: ");
            holder.education.setText(String.valueOf(model.getCoachNumber()));
            holder.batHandTV.setText("Pitches: ");
            holder.batHand.setText(String.valueOf(model.getPitchNumber()));
            holder.bowlHandTV.setText("Venue: ");
            holder.bowlHand.setText(model.getVenue());
            holder.batStyleTv.setText("Level: ");
            holder.batType.setText(model.getLevel());

            holder.playerRoleLayout.setVisibility(View.GONE);
            holder.bowlTypeLayout.setVisibility(View.GONE);

        }else if(model.getRole().toLowerCase().equals("admin")){

            holder.education.setText(model.getExperience());
            holder.experience.setText(model.getExperience());
            holder.batHandTV.setText("Preference: ");
            holder.batHand.setText(model.getPreference());

            holder.playerRoleLayout.setVisibility(View.GONE);
            holder.batHandLayout.setVisibility(View.VISIBLE);
            holder.bowlTypeLayout.setVisibility(View.GONE);
            holder.bowlHandLayout.setVisibility(View.GONE);
            holder.batTypeLayout.setVisibility(View.GONE);

        }else if(model.getRole().toLowerCase().equals("coach")){

            holder.education.setText(model.getEducation());
            holder.experience.setText(model.getExperience());
            holder.batHandTV.setText("Courses: ");
            holder.batHand.setText(model.getCourses());
            holder.bowlStyleTV.setText("Team: ");
            holder.bowlStyle.setText(model.getTeamName());

            holder.playerRoleLayout.setVisibility(View.GONE);
            holder.batHandLayout.setVisibility(View.VISIBLE);
            holder.bowlTypeLayout.setVisibility(View.VISIBLE);
            holder.bowlHandLayout.setVisibility(View.GONE);
            holder.batTypeLayout.setVisibility(View.GONE);

        }else if(model.getRole().toLowerCase().equals("umpire")){

            holder.batHandTV.setText("Tour 1: ");
            holder.batHand.setText(model.getTour1());
            holder.bowlStyleTV.setText("Tour 2: ");
            holder.bowlStyle.setText(model.getTour2());

            holder.playerRoleLayout.setVisibility(View.GONE);
            holder.batHandLayout.setVisibility(View.VISIBLE);
            holder.bowlTypeLayout.setVisibility(View.VISIBLE);
            holder.bowlHandLayout.setVisibility(View.GONE);
            holder.batTypeLayout.setVisibility(View.GONE);
        }else if(model.getRole().toLowerCase().equals("scorer")){

            holder.education.setText(model.getEducation());
            holder.experience.setText(model.getExperience());

            holder.playerRoleLayout.setVisibility(View.GONE);
            holder.batHandLayout.setVisibility(View.GONE);
            holder.bowlTypeLayout.setVisibility(View.GONE);
            holder.bowlHandLayout.setVisibility(View.GONE);
            holder.batTypeLayout.setVisibility(View.GONE);
        }

        if(model.getProfileImage().equals("")){
            holder.imageView.setImageResource(R.drawable.dummy_image);
        }else {
            Picasso.get().load(model.getProfileImage()).into(holder.imageView);
        }

        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.gender.setText(model.getSex());
        holder.age.setText(model.getAge());
        holder.email.setText(model.getEmail());
        holder.number.setText(model.getPhoneNumber());
        holder.role.setText(model.getRole());



        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                        .child(model.getId());

                HashMap<String, Object> map = new HashMap<>();

                map.put(IConstants.ISAPPROVED,true);

                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            new SweetAlertDialog(context,SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Approved!")
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

        holder.reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.USERS)
                        .child(model.getId());

                HashMap<String,Object> map = new HashMap<>();
                map.put(IConstants.ISREJECTED,true);

                reference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, "Rejected", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, email, age, number, address, role, playerRole, batType, batHand, bowlStyle, bowlHand,
        gender, education, experience;
        private ImageView imageView;

        private LinearLayout playerRoleLayout, batHandLayout, bowlHandLayout, batTypeLayout, bowlTypeLayout;

        private Button accept, reject;

        private TextView experienceTV, educationTV,batHandTV, bowlHandTV, batStyleTv, bowlStyleTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            experienceTV = itemView.findViewById(R.id.experienceTV);
            educationTV = itemView.findViewById(R.id.educationTV);
            batHandTV = itemView.findViewById(R.id.batHandTV);
            bowlHandTV = itemView.findViewById(R.id.bowlHandTV);
            batStyleTv = itemView.findViewById(R.id.batTypeTV);
            bowlStyleTV = itemView.findViewById(R.id.bowlTypeTV);

            accept = itemView.findViewById(R.id.accept);
            reject = itemView.findViewById(R.id.reject);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.city);
            email = itemView.findViewById(R.id.email);
            number = itemView.findViewById(R.id.number);
            age = itemView.findViewById(R.id.age);
            role = itemView.findViewById(R.id.role);
            playerRole = itemView.findViewById(R.id.playerRole);
            batHand = itemView.findViewById(R.id.batHand);
            batType = itemView.findViewById(R.id.batType);
            bowlHand = itemView.findViewById(R.id.bowlHand);
            bowlStyle = itemView.findViewById(R.id.bowlType);
            gender = itemView.findViewById(R.id.gender);
            education = itemView.findViewById(R.id.education);
            experience = itemView.findViewById(R.id.experience);
            imageView = itemView.findViewById(R.id.profileImage);

            batHandLayout = itemView.findViewById(R.id.batHandLayout);
            bowlHandLayout = itemView.findViewById(R.id.bowlHandLayout);
            batTypeLayout = itemView.findViewById(R.id.batTypeLayout);
            bowlTypeLayout = itemView.findViewById(R.id.bowlTypeLayout);
            playerRoleLayout = itemView.findViewById(R.id.playerRoleLayout);

        }
    }
}

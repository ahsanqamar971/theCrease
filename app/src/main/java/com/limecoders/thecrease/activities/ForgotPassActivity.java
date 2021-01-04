package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.internal.Util;

public class ForgotPassActivity extends AppCompatActivity {

    private EditText emailET;
    private TextView signInTV;
    private Button resetBtn;

    private String email;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        auth = FirebaseAuth.getInstance();
        Utils.initpDialog(this,"Please wait");

        emailET = findViewById(R.id.email);
        signInTV = findViewById(R.id.signInTV);
        resetBtn = findViewById(R.id.sendBtn);


        clickListeners();

    }

    private void clickListeners() {
        signInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPassActivity.super.onBackPressed();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailET.getText().toString();
                if(!TextUtils.isEmpty(email)) {
                    if (Utils.isEmailVerified(email))
                        Utils.showpDialog();
                        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()) {
                                    Utils.hidepDialog();
                                    new SweetAlertDialog(ForgotPassActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Email Sent")
                                            .setContentText("Reset password email has been sent to your entered email. Please check your email.")
                                            .setConfirmText("Okay")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    sweetAlertDialog.dismissWithAnimation();
                                                }
                                            }).show();
                                }else {
                                    Utils.hidepDialog();
                                }
                            }
                        });
                }
            }
        });
    }
}
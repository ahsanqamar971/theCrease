package com.limecoders.thecrease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.limecoders.thecrease.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ContactUsActivity extends AppCompatActivity {

    private TextView email, username, message;
    private Button contactUsBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        message = findViewById(R.id.message);
        contactUsBtn = findViewById(R.id.sendBtn);

        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(username.getText().toString())
            || TextUtils.isEmpty(message.getText().toString())){
                    Toast.makeText(ContactUsActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else {
                    new SweetAlertDialog(ContactUsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Email Sent")
                            .setContentText("Your message is delivered. Our team will contact you soon")
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
}
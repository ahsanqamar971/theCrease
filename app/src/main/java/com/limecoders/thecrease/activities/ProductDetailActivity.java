package com.limecoders.thecrease.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.signup.AdminSignUpActivity;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.UUID;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProductDetailActivity extends AppCompatActivity {

    private Button submit;

    private TextView addCounter, negCounter;
    private int counter;

    private ImageView proImage;
    private TextView title, desc, price, name;

    private EditText addressET, nameET, quantityET, numberET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        addressET = findViewById(R.id.address);
        nameET = findViewById(R.id.fullname);
        quantityET = findViewById(R.id.proQuantity);
        numberET = findViewById(R.id.number);
        addCounter = findViewById(R.id.plusCounter);
        negCounter = findViewById(R.id.negCounter);
        submit = findViewById(R.id.submit);
        proImage = findViewById(R.id.proImage);
        desc = findViewById(R.id.productDesc);
        title = findViewById(R.id.productTitle);
        price = findViewById(R.id.price);
        name = findViewById(R.id.productName);

        if(getIntent().hasExtra("image")) {
            Picasso.get().load(getIntent().getStringExtra("image")).into(proImage);
        }else {
            proImage.setImageResource(R.drawable.dummy_image);
        }

        desc.setText(getIntent().getStringExtra("model"));
        title.setText(getIntent().getStringExtra("name"));
        price.setText(getIntent().getStringExtra("price"));
        name.setText(getIntent().getStringExtra("title"));

        Utils.initpDialog(this, "Ordering, please wait!");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(quantityET.getText().toString()) || TextUtils.isEmpty(nameET.getText().toString())
                        || TextUtils.isEmpty(addressET.getText().toString()) || TextUtils.isEmpty(numberET.getText().toString())) {
                    Toast.makeText(ProductDetailActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Shop");

                    String id = UUID.randomUUID().toString();

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", id);
                    map.put("title", title.getText().toString());
                    map.put("price", price.getText().toString());
                    map.put("name", name.getText().toString());
                    map.put("quantity", quantityET.getText().toString());
                    map.put("address", addressET.getText().toString());
                    map.put("fullname", nameET.getText().toString());
                    map.put("number", numberET.getText().toString());

                    reference.child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                new SweetAlertDialog(ProductDetailActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Order placed!")
                                        .setContentText("Thank you for ordering. Your order is placed!!!")
                                        .setConfirmText("Okay")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                sweetAlertDialog.dismissWithAnimation();
                                                ProductDetailActivity.super.onBackPressed();
                                            }
                                        }).show();
                            }
                        }
                    });

                }
            }
        });

        addCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = counter + 1;
                quantityET.setText(String.valueOf(counter));
            }
        });

        negCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(counter>1) {
                    counter = counter - 1;
                    quantityET.setText(String.valueOf(counter));
                }
            }
        });

    }
}
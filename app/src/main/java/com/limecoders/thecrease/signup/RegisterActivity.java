package com.limecoders.thecrease.signup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.limecoders.thecrease.Utils;
import com.limecoders.thecrease.activities.LoginActivity;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.constant.IConstants;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.limecoders.thecrease.Utils.hidepDialog;
import static com.limecoders.thecrease.Utils.initpDialog;
import static com.limecoders.thecrease.Utils.showpDialog;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailET, passwordET, conPassET, nameET, religionET, agrET, addressET;
    private Spinner sexSpinner, roleSpinner;
    private Button signInBtn, nextBtn;

    private String email, password, conPass, name, religion, age, sex, role, address, image;

    private CircleImageView profileImage;

    private boolean isEdit = false;
    private TextView signUp, pass, conPassTV, registerTV, alreadyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initpDialog(this,"Please wait");

        alreadyAccount = findViewById(R.id.alreadyAccount);
        registerTV = findViewById(R.id.registerTV);
        signUp = findViewById(R.id.signUp);
        pass = findViewById(R.id.pass);
        conPassTV = findViewById(R.id.conPass);
        addressET = findViewById(R.id.address);
        profileImage = findViewById(R.id.profile_pic);
        emailET = findViewById(R.id.email);
        passwordET = findViewById(R.id.password);
        conPassET = findViewById(R.id.conPassword);
        agrET = findViewById(R.id.age);
        nameET = findViewById(R.id.username);
        religionET = findViewById(R.id.religion);
        sexSpinner = findViewById(R.id.sexSpinner);
        roleSpinner =  findViewById(R.id.roleSpinner);
        signInBtn = findViewById(R.id.signInBtn);
        nextBtn = findViewById(R.id.nextBtn);


        if(getIntent().hasExtra("isEdit")){
            email = getIntent().getStringExtra("email");
            name = getIntent().getStringExtra("name");
            age = getIntent().getStringExtra("age");
            religion = getIntent().getStringExtra("religion");
            sex = getIntent().getStringExtra("sex");
            address = getIntent().getStringExtra("address");
            isEdit = getIntent().getBooleanExtra("isEdit",false);
            role = getIntent().getStringExtra("role");
            image = getIntent().getStringExtra("image");
        }

        if(isEdit){

            registerTV.setText("Registered As: "+role);
            emailET.setText(email);
            nameET.setText(name);
            agrET.setText(age);
            religionET.setText(religion);
            addressET.setText(address);

            alreadyAccount.setVisibility(View.GONE);
            signInBtn.setVisibility(View.GONE);

            signUp.setText("Update Info");

            pass.setVisibility(View.GONE);
            passwordET.setVisibility(View.GONE);
            conPassTV.setVisibility(View.GONE);
            conPassET.setVisibility(View.GONE);

            registerTV.setVisibility(View.VISIBLE);
            roleSpinner.setVisibility(View.GONE);
        }

        clickListeners();

    }

    private void clickListeners() {

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finishAffinity();
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageURL!=null) {
                    getData();
                }else{
                    Toast.makeText(RegisterActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });

    }

    private void checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
            Log.i("SubmitOfferActivity","already allowed");

            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(RegisterActivity.this);
        }else{

            Log.i("SubmitOfferActivity","Allowed");
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},101);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==101 && grantResults[0]==PackageManager.PERMISSION_DENIED){
            checkPermission();
        }
    }

    private Uri imageUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            Uri imageURI = data.getData();

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);

                imageUri = result.getUri();
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                Picasso.get().load(imageUri).into(profileImage);

                uploadImage();
            }
        }
    }


    private UploadTask uploadTask;

    private String imageURL = null;

    private void uploadImage() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(IConstants.UPLOADS)
                .child(IConstants.PROFILES);
        showpDialog();
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + Utils.getExtension(this, imageUri));
        uploadTask = fileReference.putFile(imageUri);


        uploadTask
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        return fileReference.getDownloadUrl();
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            imageURL = downloadUri.toString();
                            hidepDialog();
                        } else {
                            hidepDialog();
                            Toast.makeText(RegisterActivity.this, getString(R.string.msgFailedToUplod), Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getData(){
        address = addressET.getText().toString();
        name = nameET.getText().toString();
        password = passwordET.getText().toString();
        conPass = conPassET.getText().toString();
        email = emailET.getText().toString();
        religion = religionET.getText().toString();
        age = agrET.getText().toString();
        sex = sexSpinner.getSelectedItem().toString();
        role = roleSpinner.getSelectedItem().toString();

        if(isEdit){
            sex = getIntent().getStringExtra("sex");
            role = getIntent().getStringExtra("role");
        }

        if(validateEntries(name, email, password, conPass, religion, age, sex, role)){
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
        }else if(!Utils.isEmailVerified(email)){
            emailET.setError("Enter a valid email");
        } else {
            if(password.equals(conPass)) {
                openNextPage(role);
            }else {
                Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*
        Admin, Team, Umpire, Coach, Player, Scorer
     */
    private void openNextPage(String role) {

        if(role.toLowerCase().equals("admin")){
            startActivity(new Intent(RegisterActivity.this,AdminSignUpActivity.class)
            .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
            .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
            .putExtra("role",role).putExtra("imageURL",imageURL).putExtra("address",address)
            .putExtra("isEdit",isEdit));
        }else if(role.toLowerCase().equals("umpire")){
            startActivity(new Intent(RegisterActivity.this,UmpireSignUpActivity.class)
                    .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
                    .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
                    .putExtra("role",role).putExtra("imageURL",imageURL).putExtra("address",address)
                    .putExtra("isEdit",isEdit));
        }else if(role.toLowerCase().equals("coach")){
            startActivity(new Intent(RegisterActivity.this,CoachSignUpActivity.class)
                    .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
                    .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
                    .putExtra("role",role).putExtra("imageURL",imageURL).putExtra("address",address)
                    .putExtra("isEdit",isEdit));
        }else if(role.toLowerCase().equals("player")){
            startActivity(new Intent(RegisterActivity.this,PlayerSignUpActivity.class)
                    .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
                    .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
                    .putExtra("role",role).putExtra("imageURL",imageURL).putExtra("address",address)
                    .putExtra("isEdit",isEdit));
        }else if(role.toLowerCase().equals("team")){
            startActivity(new Intent(RegisterActivity.this,TeamSignUpActivity.class)
                    .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
                    .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
                    .putExtra("role",role).putExtra("imageURL",imageURL).putExtra("address",address)
                    .putExtra("isEdit",isEdit));
        }else if(role.toLowerCase().equals("scorer")){
            startActivity(new Intent(RegisterActivity.this,ScorerSignUpActivity.class)
                    .putExtra("fullName",name).putExtra("password",password).putExtra("email",email)
                    .putExtra("religion",religion).putExtra("age",age).putExtra("sex",sex)
                    .putExtra("role",role).putExtra("imageURL",imageURL).putExtra("address",address)
                    .putExtra("isEdit",isEdit));
        }else {
            Toast.makeText(this, "Please select a role", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateEntries(String name, String email, String password, String conPass, String religion, String age, String sex, String role) {
        if(isEdit){
            return TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(religion) ||
                    TextUtils.isEmpty(age) || TextUtils.isEmpty(sex);
        }else {
            return TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(conPass) || TextUtils.isEmpty(religion) || TextUtils.isEmpty(age) ||
                    TextUtils.isEmpty(sex) || TextUtils.isEmpty(role);
        }
    }
}
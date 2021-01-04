package com.limecoders.thecrease.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.github.islamkhsh.CardSliderViewPager;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.ForgotPassActivity;
import com.limecoders.thecrease.adapter.MatchSliderAdapter;
import com.limecoders.thecrease.adapter.NewsSliderAdapter;
import com.limecoders.thecrease.adapter.ProductSliderAdapter;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.NewsModel;
import com.limecoders.thecrease.models.ProductModel;
import com.limecoders.thecrease.signup.RegisterActivity;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private MatchSliderAdapter matchSliderAdapter;
    private CardSliderViewPager matchSliderViewPager;

    private CardSliderViewPager proSliderViewPager;
    private ProductSliderAdapter productSliderAdapter;

    private Button contactUsBtn;
    private ImageView signUpImage;
    private EditText email, username, message;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        email = root.findViewById(R.id.email);
        username = root.findViewById(R.id.username);
        message = root.findViewById(R.id.message);
        contactUsBtn = root.findViewById(R.id.sendBtn);
        signUpImage = root.findViewById(R.id.signUpImage);
        matchSliderViewPager = root.findViewById(R.id.viewPager);
        proSliderViewPager = root.findViewById(R.id.viewPager2);


        homeViewModel.getFixturesModel().observe(getViewLifecycleOwner(), new Observer<List<MatchRequestModel>>() {
            @Override
            public void onChanged(List<MatchRequestModel> fixturesModels) {
                matchSliderAdapter = new MatchSliderAdapter(getActivity(),fixturesModels);
                matchSliderViewPager.setAdapter(matchSliderAdapter);
            }
        });

        homeViewModel.getProductModel().observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                productSliderAdapter = new ProductSliderAdapter(getActivity(),productModels);
                proSliderViewPager.setAdapter(productSliderAdapter);
            }
        });

        clickLisenters();

        return root;
    }

    private void clickLisenters() {

        signUpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RegisterActivity.class));
            }
        });

        contactUsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
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
        });

    }
}
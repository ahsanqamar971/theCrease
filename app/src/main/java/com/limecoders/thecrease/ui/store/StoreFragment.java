package com.limecoders.thecrease.ui.store;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.islamkhsh.CardSliderViewPager;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.ProductSliderAdapter;
import com.limecoders.thecrease.adapter.StoreAdapter;
import com.limecoders.thecrease.models.ProductModel;
import com.limecoders.thecrease.ui.home.HomeViewModel;

import java.util.List;

public class StoreFragment extends Fragment {

    private StoreViewModel storeViewModel;

    private RecyclerView productRecyclerView;
    private StoreAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        storeViewModel = ViewModelProviders.of(this)
                .get(StoreViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_store, container, false);

        productRecyclerView = root.findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productRecyclerView.setHasFixedSize(true);

        storeViewModel.getProductModel().observe(getViewLifecycleOwner(), new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                adapter = new StoreAdapter(getActivity(),productModels);
                productRecyclerView.setAdapter(adapter);
            }
        });

        return root;
    }
}
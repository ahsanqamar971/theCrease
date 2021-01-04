package com.limecoders.thecrease.ui.store;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.limecoders.thecrease.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class StoreViewModel extends ViewModel {

    private MutableLiveData<List<ProductModel>> productModel;
    private List<ProductModel> models;

    public StoreViewModel(){
        productModel = new MutableLiveData<>();
        models = new ArrayList<>();

        getAllProducts();
    }

    private void getAllProducts() {
        models.add(new ProductModel("1","Bat 1","Rampage 3.0","999",
                "12-May","https://www.slatergartrellsports.com.au/wp-content/uploads/2019/08/Rampage_P2000CB.jpg",
                "Bat"));

        models.add(new ProductModel("2","Boll 1","Boll 3.0","999",
                "13-May","https://images-na.ssl-images-amazon.com/images/I/51f0mqq4NzL._AC_SY400_.jpg",
                "Boll"));

        models.add(new ProductModel("3","Bat 2","Rampage 3.0","999",
                "12-May","https://www.slatergartrellsports.com.au/wp-content/uploads/2019/08/Rampage_P2000CB.jpg",
                "Bat"));

        models.add(new ProductModel("4","Bowl 2","Bool 3.0","999",
                "12-May","https://images-na.ssl-images-amazon.com/images/I/51f0mqq4NzL._AC_SY400_.jpg",
                "Boll"));

        productModel.setValue(models);
    }

    public MutableLiveData<List<ProductModel>> getProductModel(){
        return productModel;
    }
}

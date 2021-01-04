package com.limecoders.thecrease.ui.score;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.limecoders.thecrease.models.ScoreModel;

import java.util.ArrayList;
import java.util.List;

public class ScoreViewModel extends ViewModel {

    private MutableLiveData<List<ScoreModel>> listMutableLiveData;
    private List<ScoreModel> models;

    public ScoreViewModel() {

        listMutableLiveData = new MutableLiveData<>();
        models = new ArrayList<>();

        getAllMathesData();
    }

    private void getAllMathesData() {
        models.add(new ScoreModel("Pakistan Super League","Karachi","LQ","KK",
                "LQ won by 1 run","14-Nov","Fakhar Zaman","",""
        ,"","","Finished",210,208,20.0,20.0));


        models.add(new ScoreModel("Pakistan Super League","Karachi","LQ","KK",
                "LQ won by 1 run","14-Nov","Fakhar Zaman","",""
                ,"","","Finished",210,208,20.0,20.0));

        models.add(new ScoreModel("Pakistan Super League","Karachi","LQ","KK",
                "LQ won by 1 run","14-Nov","Fakhar Zaman","",""
                ,"","","Finished",210,208,20.0,20.0));

        models.add(new ScoreModel("Pakistan Super League","Karachi","LQ","KK",
                "LQ won by 1 run","14-Nov","Fakhar Zaman","",""
                ,"","","Finished",210,208,20.0,20.0));

        listMutableLiveData.setValue(models);
    }

    public MutableLiveData<List<ScoreModel>> getScoreList(){
        return listMutableLiveData;
    }

}
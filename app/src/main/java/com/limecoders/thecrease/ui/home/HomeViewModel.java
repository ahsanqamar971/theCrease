package com.limecoders.thecrease.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.limecoders.thecrease.constant.IConstants;
import com.limecoders.thecrease.models.FixturesModel;
import com.limecoders.thecrease.models.MatchRequestModel;
import com.limecoders.thecrease.models.NewsModel;
import com.limecoders.thecrease.models.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<MatchRequestModel>> fixturesModel;
    private List<MatchRequestModel> fixtureModels;

    private List<NewsModel> newsModels;
    private MutableLiveData<List<NewsModel>> newsModel;

    private List<ProductModel> productModels;
    private MutableLiveData<List<ProductModel>> productModel;

    public HomeViewModel() {

        fixtureModels = new ArrayList<>();
        fixturesModel = new MutableLiveData<>();

        newsModels = new ArrayList<>();
        newsModel = new MutableLiveData<>();

        productModels = new ArrayList<>();
        productModel = new MutableLiveData<>();

        getAllFixtures();
        getAllNews();
        getAllProducts();

    }

    private void getAllProducts() {
        productModels.add(new ProductModel("1","Bat 1","Rampage 3.0","999",
                "12-May","https://www.slatergartrellsports.com.au/wp-content/uploads/2019/08/Rampage_P2000CB.jpg",
                "Bat"));

        productModels.add(new ProductModel("2","Boll 1","Boll 3.0","999",
                "13-May","https://images-na.ssl-images-amazon.com/images/I/51f0mqq4NzL._AC_SY400_.jpg",
                "Boll"));

        productModels.add(new ProductModel("3","Bat 2","Rampage 3.0","999",
                "12-May","https://www.slatergartrellsports.com.au/wp-content/uploads/2019/08/Rampage_P2000CB.jpg",
                "Bat"));

        productModels.add(new ProductModel("4","Bowl 2","Bool 3.0","999",
                "12-May","https://images-na.ssl-images-amazon.com/images/I/51f0mqq4NzL._AC_SY400_.jpg",
                "Boll"));

        productModel.setValue(productModels);
    }

    private void getAllNews() {

        newsModels.add(new NewsModel("1","Rana Fawad","","22-10-2020",
                "Pakistan target aggressive cricket under new leadership",
                "head of his first series as Pakistan's new ODI captain, Babar Azam has said he wants to see \"aggressive and fearless cricket\" from his side as they embark on their ICC Men's Cricket World Cup Super League campaign.\n" +
                        "\n" +
                        "Pakistan begin their three-match ODI series against Zimbabwe on Friday, 30 October, which will mark their first matches in the format since Babar's appointment as skipper, following their 2-0 series win over Sri Lanka in October 2019. It will also mark the start of the qualification process for the 2023 ICC Men's Cricket World Cup for both sides, with 30 points up for grabs in the series.\n" +
                        "\n" +
                        "\"To keep up with the modern-day requirements of fast-paced cricket, we need to be super fit,\" Babar said. \"As the leaders of the group, both Shadab and I need to set an example with our fitness and commitment. We have a lot of cricket lined up and the only method for us to regain our spot at the top of the rankings is aggressive and fearless cricket.\"",
                "https://arysports.tv/wp-content/uploads/2020/05/Shadab-Khan-and-Babar-Azam.jpg",
                "National Cricket","Babar,shadab,pakistan,icc","News"));

        newsModels.add(new NewsModel("2","Ayaz Gujar","","23-10-2020",
                "Pakistan target aggressive cricket under new leadership",
                "head of his first series as Pakistan's new ODI captain, Babar Azam has said he wants to see \"aggressive and fearless cricket\" from his side as they embark on their ICC Men's Cricket World Cup Super League campaign.\n" +
                        "\n" +
                        "Pakistan begin their three-match ODI series against Zimbabwe on Friday, 30 October, which will mark their first matches in the format since Babar's appointment as skipper, following their 2-0 series win over Sri Lanka in October 2019. It will also mark the start of the qualification process for the 2023 ICC Men's Cricket World Cup for both sides, with 30 points up for grabs in the series.\n" +
                        "\n" +
                        "\"To keep up with the modern-day requirements of fast-paced cricket, we need to be super fit,\" Babar said. \"As the leaders of the group, both Shadab and I need to set an example with our fitness and commitment. We have a lot of cricket lined up and the only method for us to regain our spot at the top of the rankings is aggressive and fearless cricket.\"",
                "https://arysports.tv/wp-content/uploads/2020/05/Shadab-Khan-and-Babar-Azam.jpg",
                "National Cricket","Babar,shadab,pakistan,icc","News"));

        newsModels.add(new NewsModel("3","Ch. Bajwa","","29-10-2020",
                "Pakistan target aggressive cricket under new leadership",
                "head of his first series as Pakistan's new ODI captain, Babar Azam has said he wants to see \"aggressive and fearless cricket\" from his side as they embark on their ICC Men's Cricket World Cup Super League campaign.\n" +
                        "\n" +
                        "Pakistan begin their three-match ODI series against Zimbabwe on Friday, 30 October, which will mark their first matches in the format since Babar's appointment as skipper, following their 2-0 series win over Sri Lanka in October 2019. It will also mark the start of the qualification process for the 2023 ICC Men's Cricket World Cup for both sides, with 30 points up for grabs in the series.\n" +
                        "\n" +
                        "\"To keep up with the modern-day requirements of fast-paced cricket, we need to be super fit,\" Babar said. \"As the leaders of the group, both Shadab and I need to set an example with our fitness and commitment. We have a lot of cricket lined up and the only method for us to regain our spot at the top of the rankings is aggressive and fearless cricket.\"",
                "https://arysports.tv/wp-content/uploads/2020/05/Shadab-Khan-and-Babar-Azam.jpg",
                "National Cricket","Babar,shadab,pakistan,icc","Announcements"));

        newsModels.add(new NewsModel("4","Cap. Safdar","","20-10-2020",
                "Pakistan target aggressive cricket under new leadership",
                "head of his first series as Pakistan's new ODI captain, Babar Azam has said he wants to see \"aggressive and fearless cricket\" from his side as they embark on their ICC Men's Cricket World Cup Super League campaign.\n" +
                        "\n" +
                        "Pakistan begin their three-match ODI series against Zimbabwe on Friday, 30 October, which will mark their first matches in the format since Babar's appointment as skipper, following their 2-0 series win over Sri Lanka in October 2019. It will also mark the start of the qualification process for the 2023 ICC Men's Cricket World Cup for both sides, with 30 points up for grabs in the series.\n" +
                        "\n" +
                        "\"To keep up with the modern-day requirements of fast-paced cricket, we need to be super fit,\" Babar said. \"As the leaders of the group, both Shadab and I need to set an example with our fitness and commitment. We have a lot of cricket lined up and the only method for us to regain our spot at the top of the rankings is aggressive and fearless cricket.\"",
                "https://arysports.tv/wp-content/uploads/2020/05/Shadab-Khan-and-Babar-Azam.jpg",
                "National Cricket","Babar,shadab,pakistan,icc","Match Related"));

        newsModel.setValue(newsModels);
    }

    private void getAllFixtures() {

        fixtureModels.add(new MatchRequestModel("1", "1", "Team 1", "Club", "Lahore", "24-Oct",
                "18:00", false, true, false, true,
        false,true,true, false, true, true,
        true, false,"1", "2", "2", "1",
                "2", "90", "140-5", "136-4", "1",
                "Team 1 won by Team 2", "Finished", "Dummy League", "20", "20","","",""));

        fixtureModels.add(new MatchRequestModel("1", "3", "Team 3", "University", "Lahore", "15-Nov",
                "19:30", false, true, false, true,false,true,
                true, false, true, true,
                true,false,"3", "4", "4", "3",
                "4", "90", "155-5", "156-4", "2",
                "Team 3 won by Team 4", "Finished", "Dummy League", "20", "20","","",""));

        fixtureModels.add(new MatchRequestModel("1", "5", "Team 1", "Club", "Lahore", "24-Oct",
                "18:00", false, true, false, true,false,true,
                true, false, true, true,
                true,false,"5", "6", "6", "5",
                "6", "90", "140-5", "136-4", "3",
                "Team 1 won by Team 2", "Finished", "Dummy League", "20", "20","","",""));

        fixtureModels.add(new MatchRequestModel("1", "7", "Team 1", "Club", "Lahore", "24-Oct",
                "18:00", false, true, false, true,false,true,
                true, false, true, true,
                true,false,"7", "8", "8", "7",
                "8", "90", "140-5", "136-4", "4",
                "Team 1 won by Team 2", "Finished", "Dummy League", "20", "20","","",""));


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(IConstants.MATCHREQUEST);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if(Boolean.parseBoolean(dataSnapshot.child("isCompleted").getValue().toString()) &&
                    dataSnapshot.child("matchCondition").getValue().toString().equals("Finished")) {
                        fixtureModels.add(new MatchRequestModel(dataSnapshot.child("id").getValue().toString(),
                                dataSnapshot.child("teamId").getValue().toString(), dataSnapshot.child("teamName").getValue().toString(),
                                dataSnapshot.child("type").getValue().toString(), dataSnapshot.child("venue").getValue().toString(),
                                dataSnapshot.child("date").getValue().toString(), dataSnapshot.child("time").getValue().toString(),
                                Boolean.parseBoolean(dataSnapshot.child("isRejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isApproved").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("umpire1Rejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("umpire1Accepted").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("umpire2Rejected").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("umpire2Accepted").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("scorerAccepted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("scorerRejected").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("isCompleted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("isFinished").getValue().toString()),
                                Boolean.parseBoolean(dataSnapshot.child("teamAccepted").getValue().toString()), Boolean.parseBoolean(dataSnapshot.child("teamRejected").getValue().toString()),
                                dataSnapshot.child("team1PlayersId").getValue().toString(), dataSnapshot.child("team2PlayersId").getValue().toString(),
                                dataSnapshot.child("team2Id").getValue().toString(), dataSnapshot.child("umpire1Id").getValue().toString(),
                                dataSnapshot.child("umpire2Id").getValue().toString(), dataSnapshot.child("scorerId").getValue().toString(),
                                dataSnapshot.child("score1").getValue().toString(), dataSnapshot.child("score2").getValue().toString(),
                                dataSnapshot.child("momPlayerId").getValue().toString(), dataSnapshot.child("won").getValue().toString(),
                                dataSnapshot.child("matchCondition").getValue().toString(), dataSnapshot.child("leagueName").getValue().toString(),
                                dataSnapshot.child("overs1").getValue().toString(),dataSnapshot.child("overs2").getValue().toString(), dataSnapshot.child("firstInnings").getValue().toString(),
                                dataSnapshot.child("tossWon").getValue().toString(),dataSnapshot.child("batFirst").getValue().toString()));
                    }
                }
                fixturesModel.setValue(fixtureModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public MutableLiveData<List<ProductModel>> getProductModel(){
        return productModel;
    }

    public MutableLiveData<List<NewsModel>> getNewsModel(){
        return newsModel;
    }

    public MutableLiveData<List<MatchRequestModel>> getFixturesModel(){
        return fixturesModel;
    }

}
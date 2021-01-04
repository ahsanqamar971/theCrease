package com.limecoders.thecrease.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.limecoders.thecrease.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private MutableLiveData<List<NewsModel>> newsModel;
    private List<NewsModel> newsModels;

    public NewsViewModel() {
        newsModel = new MutableLiveData<>();
        newsModels = new ArrayList<>();

        getAllNews();
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
                "National Cricket","Babar,shadab,pakistan,icc","Announcement"));

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

    public MutableLiveData<List<NewsModel>> getNewsModel(){
        return newsModel;
    }
}
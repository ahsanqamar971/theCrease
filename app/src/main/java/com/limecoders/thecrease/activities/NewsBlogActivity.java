package com.limecoders.thecrease.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.islamkhsh.CardSliderViewPager;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.adapter.NewsSliderAdapter;
import com.limecoders.thecrease.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class NewsBlogActivity extends AppCompatActivity {

    private CardSliderViewPager newsSliderViewPager;
    private NewsSliderAdapter newsSliderAdapter;
    private List<NewsModel> newsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_blog);

        newsSliderViewPager = findViewById(R.id.viewPager);

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

        newsSliderAdapter = new NewsSliderAdapter(NewsBlogActivity.this, newsModels);
        newsSliderViewPager.setAdapter(newsSliderAdapter);

    }

}
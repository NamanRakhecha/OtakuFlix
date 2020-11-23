package com.example.jsouptest;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<AnimeModel> mList = new ArrayList<>();
    ArrayList vidLinkList= new ArrayList();
    ArrayList vidUrl= new ArrayList();
    ArrayList getLink= new ArrayList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("OtakuFlix");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);



        recyclerView = (RecyclerView) findViewById(R.id.Main_Recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List list = new List();
        list.execute();
        adapter= new Adapter(mList,this);
        recyclerView.setAdapter(adapter);


    }

    public class List extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Document document = Jsoup.connect("https://gogoanime.so/").get();
                Elements img = document.select("div.img");
                Elements title = document.select("p.name");
                Elements episode_no= document.select("p.episode");


               for(int i =0; i<title.size(); i++){
                   String extract_link = title.select("a").eq(i).attr("abs:href");
                   vidLinkList.add(extract_link);

               }
               for (int i= 0 ; i<vidLinkList.size();i++){
                   Document videoLinkDoc= Jsoup.connect(vidLinkList.get(i).toString()).get();

                   String vidStreamUrl = videoLinkDoc.getElementsByClass("play-video").get(0).getElementsByTag("iframe").get(0).attr("abs:src");
                   vidStreamUrl = vidStreamUrl.replaceAll("streaming.php","ajax.php");

                      vidUrl.add(vidStreamUrl);

               }
               for (int i=0; i<vidUrl.size();i++){
                   try {
                       Document page = Jsoup.connect(vidUrl.get(i).toString()).ignoreContentType(true).get();
                       JSONObject jsonObject = new JSONObject(page.text());
                       String qualityUrl = ((JSONObject)jsonObject.getJSONArray("source_bk").get(0)).getString("file");

                       getLink.add(qualityUrl);

                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }

                int size = img.size();
                for(int i=0; i<size; i++) {
                    String imgUrl = img.select("div.img").select("img").eq(i).attr("src");
                    String animetitle = title.select("a").eq(i).attr("title").toString();
                    String episode= episode_no.eq(i).text();
                    String vidLink = getLink.get(i).toString();
                    mList.add(new AnimeModel(imgUrl,animetitle,vidLink,episode));
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }

}
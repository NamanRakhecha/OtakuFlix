package com.example.jsouptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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

               /** for(int i=0; i<title.size(); i++){
                    String extract_link = title.select("a").eq(i).attr("abs:href");
                    //String video_Streaming_Page= "https://gogoanime.so"+extract_link;
                    Document videoLinkDoc= Jsoup.connect(extract_link).get();
                    String check = videoLinkDoc.toString();
                    Elements linkTag = videoLinkDoc.getElementsByClass("jw-media jw-reset");
                    String link= linkTag.select("video").attr("abs:src");
                    vidLinkList.add(extract_link);

                }**/

               for(int i =0; i<title.size(); i++){
                   String extract_link = title.select("a").eq(i).attr("abs:href");
                   vidLinkList.add(extract_link);

               }
               for (int i= 0 ; i<vidLinkList.size();i++){
                   Document videoLinkDoc= Jsoup.connect(vidLinkList.get(i).toString()).get();
                   Elements linkTag = videoLinkDoc.getElementsByTag("video");
                       String link= linkTag.select("video").eq(i).attr("abs:src");
                       vidUrl.add(link);

               }


                int size = img.size();
                for(int i=0; i<size; i++) {
                    String imgUrl = img.select("div.img").select("img").eq(i).attr("src");

                    String animetitle = title.select("a").eq(i).attr("title").toString();
                    String vidLink = vidUrl.get(i).toString();
                    mList.add(new AnimeModel(imgUrl,animetitle,vidLink));
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
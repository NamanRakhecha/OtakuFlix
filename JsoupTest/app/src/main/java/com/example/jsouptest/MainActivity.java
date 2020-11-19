package com.example.jsouptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;

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

                int size = img.size();
                for(int i=0; i<size; i++) {
                    String imgUrl = img.select("div.img").select("img").eq(i).attr("src");

                    String animetitle = title.select("a").eq(i).attr("title").toString();
                    mList.add(new AnimeModel(imgUrl,animetitle));
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
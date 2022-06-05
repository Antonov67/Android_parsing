package com.example.wiki_change;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WikiPage[] wikiPages;
    String[] paths;
    TextView textView;
    ListView listView;
    ArrayAdapter<String> adapter;
    Context context;
    Button reloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        textView  = findViewById(R.id.textView);
        listView = findViewById(R.id.list);
        reloadButton = findViewById(R.id.addButton);

        wikiPages = DB.getAllRecords(this);

        paths = new String[wikiPages.length];
        for (int i=0; i<paths.length; i++){
            paths[i] = wikiPages[i].getUrl();
        }

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, paths);
        listView.setAdapter(adapter);

        MyTask mt = new MyTask();

        mt.execute(paths);

        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.updateRecords(wikiPages,context);

                wikiPages = DB.getAllRecords(context);

                Log.d("wiki7777", " после " + Arrays.toString(wikiPages));
                for (int i=0; i<paths.length; i++){
                    paths[i] = wikiPages[i].getUrl() + "\n" + "Последняя дата правки в базе: "  + wikiPages[i].getDate() + "\n" + "Последняя дата правки на сайте: " + wikiPages[i].getNewDate();
                }
                adapter.notifyDataSetChanged();
            }
        });

    }

  class MyTask extends AsyncTask<String, Void, String> {
        String results[];

        @Override
        protected String doInBackground(String... params) {
            // params - это массив входных параметров
            // в params[0] будет хранится адрес сайта, который мы парсим

            String str = "null"; // Тут храним значение заголовка сайта
            //Log.d("wiki7777", Arrays.toString(params));
            Document doc = null; // Здесь хранится будет разобранный HTML документ

            for (int i=0;i<params.length; i++) {

             //   Log.d("wiki7777", i + "");
                try {
                    // Считываем заглавную страницу
                    doc = Jsoup.connect(params[i] + "&action=history").get();
                    Elements elements = doc.getElementsByClass("mw-index-pager-list-header-first mw-index-pager-list-header");
                    for (Element element : elements) {
                        str = element.getAllElements().text();
                        wikiPages[i].setNewDate(str);
                        paths[i] = paths[i] + "\n" + "Последняя дата правки в базе: "  + wikiPages[i].getDate() + "\n" + "Последняя дата правки на сайте: " + wikiPages[i].getNewDate();
                        //Log.d("wiki7777", str);

                    }
                } catch (IOException e) {
                    // Если не получилось считать
                    e.printStackTrace();
                }

            }
         //   Log.d("wiki7777", Arrays.toString(wikiPages));

            return str  ;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            adapter.notifyDataSetChanged();
        }


     }




}
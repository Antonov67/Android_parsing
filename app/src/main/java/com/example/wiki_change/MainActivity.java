package com.example.wiki_change;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView  = findViewById(R.id.textView);
        listView = findViewById(R.id.list);





        wikiPages = new WikiPage[]{new WikiPage("https://ru.wikipedia.org/w/index.php?title=Java"),
                                     new WikiPage("https://ru.wikipedia.org/w/index.php?title=Python"),
                                     new WikiPage("https://ru.wikipedia.org/w/index.php?title=C_Sharp")};

        paths = new String[wikiPages.length];
        for (int i=0; i<paths.length; i++){
            paths[i] = wikiPages[i].getUrl();
        }


        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, paths);
        listView.setAdapter(adapter);

        MyTask mt = new MyTask();

        mt.execute(paths);

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

                Log.d("wiki7777", i + "");
                try {
                    // Считываем заглавную страницу
                    doc = Jsoup.connect(params[i] + "&action=history").get();
                    Elements elements = doc.getElementsByClass("mw-index-pager-list-header-first mw-index-pager-list-header");
                    for (Element element : elements) {
                        str = element.getAllElements().text();
                        wikiPages[i].setDate(str);
                        paths[i] = str;
                        //Log.d("wiki7777", str);

                    }
                } catch (IOException e) {
                    // Если не получилось считать
                    e.printStackTrace();
                }

            }
            Log.d("wiki7777", Arrays.toString(wikiPages));
            return str;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            adapter.notifyDataSetChanged();
        }


     }
}
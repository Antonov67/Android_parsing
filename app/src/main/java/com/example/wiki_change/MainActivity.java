package com.example.wiki_change;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("wiki7777", "start");

        textView  = findViewById(R.id.textView);

        MyTask mt = new MyTask();
        mt.execute("https://ru.wikipedia.org/w/index.php?title=Java&action=history");




        //      String path = "https://ru.wikipedia.org/";

//        NetworkService.getInstance(path).getApi().getDate("Java", "history").enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if(response.code() == 200){
//                    Log.d("wiki777", "body");
//                    Document data = Jsoup.parse(response.body());
//                    Log.d("wiki777",data.toString());
//                }else {
//                    Log.d("wiki777", response.code() + "");
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("wiki777",t.getMessage());
//            }
//        });


    }

    class MyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            // params - это массив входных параметров
            // в params[0] будет хранится адрес сайта, который мы парсим

            String title = "null"; // Тут храним значение заголовка сайта

            Document doc = null; // Здесь хранится будет разобранный HTML документ
            try {
                // Считываем заглавную страницу
                doc = Jsoup.connect(params[0]).get();
                Elements elements = doc.getElementsByClass("mw-index-pager-list-header-first mw-index-pager-list-header");
                for (Element element : elements){
                    title = element.getAllElements().text();
                }
            } catch (IOException e) {
                // Если не получилось считать
                e.printStackTrace();
            }

            // Если всё считалось, что вытаскиваем из считанного HTML документа заголовок
//            if (doc != null)
//                title = doc.title();
//            else
//                title = "Error";

            // Передаем в метод onPostExecute считанный заголовок
            return title;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textView.setText(s);
        }
    }
}
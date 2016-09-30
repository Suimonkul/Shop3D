package appkg.kg.shop3d.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import appkg.kg.shop3d.R;
import appkg.kg.shop3d.adapter.RecyclerViewAdapter;
import appkg.kg.shop3d.model.Models;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModelsActivity extends AppCompatActivity {

    ArrayList<Models> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_models);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initUI();



    }

    private void initUI() {
        RecyclerView recyclerView;
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_models);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        assert recyclerView != null;
        recyclerView.setLayoutManager(manager);
        new DataDownloadModels(recyclerView).execute();

    }

    private class DataDownloadModels extends AsyncTask<Void, Void, Void> {

        Models models;


        OkHttpClient client = new OkHttpClient();
        JSONObject dataJsonObj = null;

        boolean active;
        String title, cover, description, img_first, img_second, img_third;
        int order;

        RecyclerView recyclerView;

        public DataDownloadModels(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Request request = new Request.Builder()
                    .url("http://192.168.0.177/api/v1/product/?format=json")
                    .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                assert response != null;
                dataJsonObj = new JSONObject(response.body().string());
                JSONArray jsonArray = dataJsonObj.getJSONArray("objects");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    active = jsonObject.getBoolean("active");

                    if (active) {
                        cover = jsonObject.getString("cover");
                        img_first = jsonObject.getString("image_first");
                        img_second = jsonObject.getString("image_second");
                        img_third = jsonObject.getString("image_third");
                        title = jsonObject.getString("title");
                        description = jsonObject.getString("description");
                        order = jsonObject.getInt("order");

                        models = new Models(title, description, cover, order, img_first, img_second, img_third);
                        list.add(models);
                    }

                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(list);
            recyclerView.setAdapter(adapter);

            super.onPostExecute(aVoid);
        }
    }

}

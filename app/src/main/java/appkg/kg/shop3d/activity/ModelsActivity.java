package appkg.kg.shop3d.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import appkg.kg.shop3d.R;
import appkg.kg.shop3d.adapter.RecyclerViewAdapter;
import appkg.kg.shop3d.helper.RecyclerScrollListener;
import appkg.kg.shop3d.helper.Utils;
import appkg.kg.shop3d.model.Models;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ModelsActivity extends AppCompatActivity {

    ArrayList<Models> list = new ArrayList<>();
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    RelativeLayout refresh_layout;
    RelativeLayout progress_layout;
    DataDownloadModels downloadModels;
    RecyclerScrollListener listener;
    Button refreshBtn;
    int page;
    String globalURL = "http://192.168.0.177/api/v1/product/?format=json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_models);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_models);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        assert fab != null;
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });


        initUI();
        checkConnection();
        load(0);

    }

    private void initUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_models);
        refresh_layout = (RelativeLayout) findViewById(R.id.refreshLayout);
        progress_layout = (RelativeLayout) findViewById(R.id.progress_models);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        assert recyclerView != null;
        recyclerView.setLayoutManager(manager);
        adapter = new RecyclerViewAdapter(list);
        recyclerView.setAdapter(adapter);
        refreshBtn = (Button) findViewById(R.id.btn_refresh);
        listener = new RecyclerScrollListener(manager) {
            @Override
            public void onLoadMore(int current_page) {
                load(current_page);
            }
        };
        recyclerView.addOnScrollListener(listener);
    }

    private void checkConnection() {
        if (Utils.isConnected(getApplicationContext())) {
            recyclerView.setVisibility(View.GONE);
            refresh_layout.setVisibility(View.VISIBLE);
        }

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                recyclerView.setAdapter(adapter);
                load(page);
                refresh_layout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    private void load(int page) {
        adapter = new RecyclerViewAdapter(list);
        if (Utils.isConnected(this)) {
            if (downloadModels != null) downloadModels.cancel(true);
            downloadModels = new DataDownloadModels(recyclerView, globalURL, page);
            downloadModels.execute();
        } else {
            recyclerView.setVisibility(View.GONE);
            refresh_layout.setVisibility(View.VISIBLE);
        }

    }

    private class DataDownloadModels extends AsyncTask<Void, Void, Void> {

        Models models;
        OkHttpClient client = new OkHttpClient();
        JSONObject dataJsonObj = null;
        boolean active;
        String title, cover, description, img_first, img_second, img_third;
        int order;
        int page;
        String url;
        String all;
        String limits = "&limit=6&offset=";
        RecyclerView recyclerView;

        public DataDownloadModels(RecyclerView recyclerView, String url, int page) {
            this.recyclerView = recyclerView;
            this.url = url;
            this.page = page;
        }

        @Override
        protected void onPreExecute() {
            all = url + limits + page;
            refresh_layout.setVisibility(View.GONE);
            progress_layout.setVisibility(View.VISIBLE);
            Log.d("LOAD", all);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Request request = new Request.Builder()
                    .url(all)
                    .build();

            Response response;

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

            if (list.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                progress_layout.setVisibility(View.GONE);
                refresh_layout.setVisibility(View.VISIBLE);
            } else if (!isCancelled() && adapter != null) {
                progress_layout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
            }

        }
    }

}

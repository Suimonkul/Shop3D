package appkg.kg.shop3d.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import appkg.kg.shop3d.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener, View.OnClickListener {

    private static final int LAYOUT = R.layout.activity_main;
    private static final int STYLE = R.style.AppTheme;
    private static final String TAG = "TAG";

    private SliderLayout mDemoSlider;

    HashMap<String, String> url_maps = new HashMap<String, String>();

    private Button btnModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(STYLE);
        setContentView(LAYOUT);

        initUI();
        initLOGIC();
        checkConnect();
    }


    private void checkConnect() {
        if (true) {
            new DataDownload().execute();
        } else {
            Log.d(TAG, "Connection false");
        }
    }

    private void initLOGIC() {

        btnModels.setOnClickListener(this);

    }

    private void initUI() {

        btnModels = (Button) findViewById(R.id.btnModels);

    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.btnModels:
                intent = new Intent(MainActivity.this, ModelsActivity.class);
                startActivity(intent);
                break;

        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(MainActivity.this, "" + slider.getUrl(), Toast.LENGTH_SHORT).show();
    }


    private class DataDownload extends AsyncTask<Void, Void, Void> {

        OkHttpClient client = new OkHttpClient();
        JSONObject dataJsonObj = null;

        boolean active;
        int id;
        String urls_image, title;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground");

            Request request = new Request.Builder()
                    .url("http://192.168.0.177/api/v1/advert/?format=json")
                    .build();

            Response response = null;

            try {
                response = client.newCall(request).execute();
                assert response != null;
                dataJsonObj = new JSONObject(response.body().string());
                JSONArray jsonArray = dataJsonObj.getJSONArray("objects");
//                JSONObject meta = dataJsonObj.getJSONObject("meta");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    active = jsonObject.getBoolean("active");

                    if (active) {
                        id = jsonObject.getInt("id");
                        urls_image = jsonObject.getString("image");
                        title = jsonObject.getString("title");
                        url_maps.put(title, urls_image);
                    }

                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d(TAG, "onPostExecute");
            initImageSlider();
            super.onPostExecute(aVoid);
        }
    }

    private void initImageSlider() {
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(8000);
        mDemoSlider.addOnPageChangeListener(this);
    }
}

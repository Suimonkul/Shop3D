package appkg.kg.shop3d.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

import appkg.kg.shop3d.R;
import appkg.kg.shop3d.model.Models;

public class DetailActivity extends AppCompatActivity implements ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener, View.OnClickListener {

    private static final int LAYOUT = R.layout.activity_detail;
    private static final int STYLE = R.style.AppTheme;
    private static final String TAG = "TAG";

    private SliderLayout mDemoSlider;
    private Models models;

    TextView dtTitle, dtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(STYLE);
        setContentView(LAYOUT);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initUI();
        initLOGIC();
        initImageSlider();

    }

    private void initLOGIC() {
        models = (Models) getIntent().getSerializableExtra("models");

        dtTitle.setText(models.getTitle());
        dtDescription.setText(models.getDescription());
    }

    private void initUI() {
        dtTitle = (TextView) findViewById(R.id.detail_title);
        dtDescription = (TextView) findViewById(R.id.detail_description);
    }

    private void initImageSlider() {

        HashMap<String, String> img = new HashMap<>();
        img.put("a", models.getImage_first());
        img.put("b", models.getImage_second());
        img.put("c", models.getImage_third());

        mDemoSlider = (SliderLayout) findViewById(R.id.slider);

        for (String name : img.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            Log.d("TAG", "Run Slider");
            textSliderView
                    .image(img.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setDuration(8000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {

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

    }
}


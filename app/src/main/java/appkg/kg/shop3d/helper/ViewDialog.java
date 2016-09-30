package appkg.kg.shop3d.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import appkg.kg.shop3d.R;

/**
 * Created by Suimonkul on 17.09.2016.
 */
public class ViewDialog extends Dialog {

    public Activity c;
    public Dialog d;
    public Button yes;
    public ImageView dig_img;
    String url;

    public ViewDialog(Activity a, String url) {
        super(a);
        this.c = a;
        this.url = url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        d = new Dialog(c);
        setContentView(R.layout.custom_dialog);
        dig_img = (ImageView) findViewById(R.id.dialog_image);
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = width;  // deprecated

        Log.d("SCREEN_SIZE",""+width+"  "+height);

        Picasso.with(c).load(url).into(dig_img);


    }

}

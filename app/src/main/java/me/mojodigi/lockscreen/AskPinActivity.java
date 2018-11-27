package me.mojodigi.lockscreen;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.mojodigi.lockscreen.utils.Helper;
import me.mojodigi.lockscreen.utils.SharedPreferenceUtils;


public class AskPinActivity extends AppCompatActivity implements View.OnClickListener{
    Date date;
    String pin = "";
    int position = 0;
    Calendar calendar;
  TextView datetime;
  Button btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0;
         ImageButton btn_delete;
         EditText et_enterpin;

         ImageView img_unlock;

    SharedPreferenceUtils sp ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = new SharedPreferenceUtils(this);
        calendar = Calendar.getInstance();
        date = new Date();
        setContentView(R.layout.activity_askpin);
        datetime= (TextView) findViewById(R.id.datetime);
        img_unlock= (ImageView) findViewById(R.id.img_unlock);
        DigitalClock digitalClock=findViewById(R.id.digitalClock);
         datetime.setTypeface(Helper.typeFace_CaviarBoldTime(AskPinActivity.this));
         digitalClock.setTypeface(Helper.typeFace_CaviarBoldTime(AskPinActivity.this));


              this.setTitle(getResources().getString(R.string.writepin));
         //set default wallpaper image
         // img_unlock.setImageDrawable(getResources().getDrawable(R.drawable.bg1));


        et_enterpin= (EditText) findViewById(R.id.editText1);
        btn_1 = (Button) findViewById(R.id.one_btn);
        btn_2 = (Button) findViewById(R.id.two_btn);
        btn_3 = (Button) findViewById(R.id.three_btn);
        btn_4 = (Button) findViewById(R.id.four_btn);
        btn_5 = (Button) findViewById(R.id.five_btn);
        btn_6 = (Button) findViewById(R.id.six_btn);
        btn_7 = (Button) findViewById(R.id.seven_btn);
        btn_8 = (Button) findViewById(R.id.eight_btn);
        btn_9 = (Button) findViewById(R.id.nine_btn);
        btn_0 = (Button) findViewById(R.id.zero_btn);
        btn_delete= (ImageButton) findViewById(R.id.back_btn);

        btn_0.setOnClickListener(this);
        btn_1.setOnClickListener(this);
        btn_2.setOnClickListener(this);
        btn_3.setOnClickListener(this);
        btn_4.setOnClickListener(this);
        btn_5.setOnClickListener(this);
        btn_6.setOnClickListener(this);
        btn_7.setOnClickListener(this);
        btn_8.setOnClickListener(this);
        btn_9.setOnClickListener(this);
        btn_delete.setOnClickListener(this);



        //set selected wallpaper image


        setDateAndTime();

    }
    private void setwallpaper() {

        if(img_unlock!=null)
        {
            String picturePath=sp.getStringValue(Helper.WALLPAPER_PATH,"");
            if(picturePath !=null && picturePath.length()>1 )
            {

//                Glide.with(AskPinActivity.this).load(Uri.parse(picturePath))
//                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                        .skipMemoryCache(true).placeholder(R.drawable.bg1).error(R.drawable.bg1)
//                        .into(img_unlock);



                Glide.with(AskPinActivity.this).load("file://" + picturePath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false).placeholder(R.drawable.bg1).error(R.drawable.bg1)
                        .into(img_unlock);



            }
            else
            {
                img_unlock.setImageDrawable(getResources().getDrawable(R.drawable.bg1));
            }
        }
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.zero_btn:
                pin = pin + "0";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.one_btn:
                pin = pin + "1";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.two_btn:
                pin = pin + "2";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.three_btn:
                pin = pin + "3";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.four_btn:
                pin = pin + "4";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;
            case R.id.five_btn:
                pin = pin + "5";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.six_btn:
                pin = pin + "6";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.seven_btn:
                pin = pin + "7";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.eight_btn:
                pin = pin + "8";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.nine_btn:
                pin = pin + "9";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.back_btn:
                if (pin.length() > 4) {
                    pin = pin.substring(0, 4);
                }

                if (pin.length() != 0) {
                    pin = pin.substring(0, pin.length() - 1);
                    et_enterpin.setText(pin);
                }
                break;






        }
        if(pin.length()==4)
        {
            String correctPin=sp.getStringValue(Helper.CONFIRMPIN,"");

            if(pin.equalsIgnoreCase(correctPin))
            {
                finish();
            }
            else
            {
                Toast.makeText(this, getResources().getString(R.string.wrongpin), Toast.LENGTH_SHORT).show();
                //TastyToast.makeText(getApplicationContext(), "Incorrect Pin!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                 et_enterpin.setText("");
                 pin="";
            }

        }
    }

    //system date
    public void setDateAndTime() {
        //system day
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        String dayName = sdf_.format(date);
        //system date
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String currentDate = sdf.format(calendar.getTime());
        datetime.setText(dayName + " , " + currentDate);
    }


    @Override
    protected void onPause() {
        super.onPause();

        ((LockApplication) getApplication()).lockScreenShow = false;
        // does  not allow  the menu  button to  list and switch  the screen;
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    @Override
    public void onAttachedToWindow() {


        // commented to handle  double click issue;

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
////                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        super.onAttachedToWindow();

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Intent i  =  new Intent(AskPinActivity.this,LockScreenActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this, "on resume", Toast.LENGTH_SHORT).show();
      //  setwallpaper();
        ((LockApplication) getApplication()).lockScreenShow = true;
    }
}

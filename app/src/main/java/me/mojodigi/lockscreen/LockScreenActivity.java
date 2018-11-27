package me.mojodigi.lockscreen;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.DigitalClock;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.bumptech.glide.load.engine.DiskCacheStrategy;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.mojodigi.lockscreen.adapter.NewsAdapter;
import me.mojodigi.lockscreen.model.NewsDataModel;
import me.mojodigi.lockscreen.utils.Helper;
import me.mojodigi.lockscreen.utils.LockScreen;
import me.mojodigi.lockscreen.utils.SeparatorDecoration;
import me.mojodigi.lockscreen.utils.SharedPreferenceUtils;


public class LockScreenActivity extends AppCompatActivity implements NewsAdapter.newsListener {

    private ArrayList<NewsDataModel> newsList;
    private NewsAdapter newsAdapter;
    private RecyclerView news_recycler_view;
    private int PREMISSION_REQUEST_CODE=100;
    Date date;
    Calendar calendar;
   TextView txt_dateandday;
    SharedPreferenceUtils sp ;
    ImageView backImageVIew;
EditText focusEdit;
TextView heading_News;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);
        setContentView(R.layout.test);
        date = new Date();
        focusEdit=findViewById(R.id.focusEdit);
        focusEdit.requestFocus();  //did ot work

        sp=new SharedPreferenceUtils(LockScreenActivity.this);
        calendar = Calendar.getInstance();
        news_recycler_view= (RecyclerView) findViewById(R.id.news_recycler_view);
        backImageVIew=findViewById(R.id.ll_layout);
        setwallpaper();
        txt_dateandday=findViewById(R.id.txt_dateandday);
        heading_News=findViewById(R.id.heading_News);
        DigitalClock digitalClock=findViewById(R.id.digitalClock);

        digitalClock.setTypeface(Helper.typeFace_CaviarBoldTime(LockScreenActivity.this));
        txt_dateandday.setTypeface(Helper.typeFace_CaviarBoldTime(LockScreenActivity.this));
         heading_News.setTypeface(Helper.typeFace_CORBEL(LockScreenActivity.this));




        //SeparatorDecoration decoration = new SeparatorDecoration(this, Color.GRAY, 1.5f);
       // news_recycler_view.addItemDecoration(decoration);

        LinearLayoutManager verticalLayoutManagaer = new LinearLayoutManager(LockScreenActivity.this, LinearLayoutManager.VERTICAL, false);
        news_recycler_view.setLayoutManager(verticalLayoutManagaer);

        newsList= new ArrayList<NewsDataModel>();
        setNewsdata();
        newsAdapter= new NewsAdapter(newsList,this,LockScreenActivity.this);
        news_recycler_view.setAdapter(newsAdapter);

        datee();
        UnlockBar unlock = (UnlockBar) findViewById(R.id.unlock);

        // Attach listener
        unlock.setOnUnlockListenerRight(new UnlockBar.OnUnlockListener() {
            @Override
            public void onUnlock()
            {
               //Toast.makeText(LockScreenActivity.this, "Right Action", Toast.LENGTH_SHORT).show();
               // finish();

                Intent i  =  new Intent(LockScreenActivity.this,AskPinActivity.class);
                startActivity(i);
                finish();



            }
        });


        unlock.setOnUnlockListenerLeft(new UnlockBar.OnUnlockListener() {
            @Override
            public void onUnlock()
            {
                //Toast.makeText(LockScreenActivity.this, "Left Action", Toast.LENGTH_SHORT).show();
                //finish();

                Intent i  =  new Intent(LockScreenActivity.this,AskPinActivity.class);
                startActivity(i);
                finish();


            }
        });


    }


    private void setwallpaper() {

        if(backImageVIew!=null)
        {
            String picturePath=sp.getStringValue(Helper.WALLPAPER_PATH,"");
            if(picturePath !=null && picturePath.length()>1 )
            {

                        Glide.with(LockScreenActivity.this).load("file://"+picturePath)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .skipMemoryCache(false).placeholder(R.drawable.nature).error(R.drawable.nature)
                        .into(backImageVIew);

                        }
            else
            {
                backImageVIew.setImageDrawable(getResources().getDrawable(R.drawable.nature));
            }
        }
    }

    @Override
    public void onAttachedToWindow() {

        // commented to handle double click issue;

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
////                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//
//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        super.onAttachedToWindow();

    }

    @Override
    protected void onResume() {
        super.onResume();


        LockScreen.getInstance().init(this,true);

        if(!LockScreen.getInstance().isActive()) {
            LockScreen.getInstance().active();
        }
           //sp.setValue(Helper.IS_SERVICE_STARTED,1);

        ((LockApplication) getApplication()).lockScreenShow = true;


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

    private void setNewsdata() {

        for(int i=0;i<3;i++)
        {
            NewsDataModel model  =  new NewsDataModel();
            // model.setNewsDate("12-12-2018");
            //model.setNewsDesc("As Delhi Chief Minist1er Arvind Kejriwal's sit-in protest along with his cabinet colleagues at the Lieutinent Governor's residence enters the fifth day, Congress has criticised both Centre and Delhi governmen");


            if(i==0) {
                model.setNewsDesc("Manage your files efficeintly");
                model.setNewsUrl("https://play.google.com/store/apps/details?id=com.mojodigi.filehunt");
            }

            if(i==1) {
                model.setNewsDesc("Latest News,breaking News");
                model.setNewsUrl("http://khulasa-news.com/");
            }
            if(i==2) {
                model.setNewsDesc("News in English,Hindi,Tamil..");
                model.setNewsUrl("http://www.dailyhunt.in/");
            }

            newsList.add(model);
        }

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void onNewsClicked(NewsDataModel newsDataModel) {


        String url=newsDataModel.getNewsUrl();

          Intent i  =  new Intent(LockScreenActivity.this,WebViewActivity.class);
          Helper.NEWSURL=url;
          startActivity(i);
          finish();

          }
    //system date
    public void datee() {
        //system day
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");
        String dayName = sdf_.format(date);
        //system date
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        String currentDate = sdf.format(calendar.getTime());
        txt_dateandday.setText(dayName + " , " + currentDate);
    }
}

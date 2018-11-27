package me.mojodigi.lockscreen;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import me.mojodigi.lockscreen.adapter.WallPaperAdapter;
import me.mojodigi.lockscreen.model.WallPaperModel;
import me.mojodigi.lockscreen.utils.Helper;
import me.mojodigi.lockscreen.utils.SharedPreferenceUtils;

public class WallPaperActivity extends Activity implements WallPaperAdapter.WallPaperListener{

    ImageView back_image;
    private AssetManager assetManager;
    String TAG=getClass().getSimpleName();
    private final static int BUFFER_SIZE = 1024;
    private RecyclerView app_recycler_view;
    private ArrayList<WallPaperModel> wallpaperlList;
    WallPaperAdapter wallPaperAdapter;
    Button wallButton;
    private String wallPaperPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        back_image=findViewById(R.id.back_image);
        wallButton=findViewById(R.id.wallButton);

        assetManager=getAssets();
        wallButton.setTypeface(Helper.typeFace_CORBEL(WallPaperActivity.this));
        wallpaperlList=new ArrayList<WallPaperModel>();
        app_recycler_view=  findViewById(R.id.app_recycler_view);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(WallPaperActivity.this, LinearLayoutManager.HORIZONTAL, false);


        if(assetManager!=null)
            listAllImages();
        app_recycler_view.setLayoutManager(horizontalLayoutManagaer);
        wallPaperAdapter =new WallPaperAdapter(wallpaperlList,WallPaperActivity.this,this);
        app_recycler_view.setAdapter(wallPaperAdapter);

        
        wallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wallPaperPath!=null) {
                    SharedPreferenceUtils sp = new SharedPreferenceUtils(WallPaperActivity.this);
                    sp.setValue(Helper.WALLPAPER_PATH,wallPaperPath);
                    Toast.makeText(WallPaperActivity.this, getResources().getString(R.string.wallpaper_success), Toast.LENGTH_SHORT).show();
                    finish();
                }

                
            }
        });

    }
    public void listAllImages()
    {
        try {

            File f =  new File(Environment.getExternalStorageDirectory()+"/.wallpapers");
            if(!f.exists())
                f.mkdir();

            OutputStream out = null;
            String[] imgPath = assetManager.list("wallpapers");
            for (int i = 0; i< imgPath.length; i++)
            {
                InputStream is = assetManager.open("wallpapers/"+imgPath[i]);
                String img=imgPath[i];
                System.out.print(""+img);
                Log.d(TAG, imgPath[i]);

                if(f.exists()) {
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/.wallpapers/" + imgPath[i]);
                    copyAssetFiles(is, out);
                }


                }
            listHiddenFiles();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
    private static void copyAssetFiles(InputStream in, OutputStream out) {
        try {

            byte[] buffer = new byte[BUFFER_SIZE];
            int read;

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }

            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void listHiddenFiles() {
        File[] fileArray = null;

        File f = new File(Environment.getExternalStorageDirectory() + "/.wallpapers/");
        try {
            if (f.exists() && f.isDirectory()) {
                if (f != null) {
                    fileArray = f.listFiles();
                }

                setWallPaperPriview(fileArray[0].getAbsoluteFile().toString());
                for(int i=0;i<fileArray.length;i++)
                {
                    WallPaperModel model=new WallPaperModel();
                    model.setFilePath(fileArray[i].getAbsolutePath());
                    wallpaperlList.add(model);
                }
            }
        }
        catch (Exception e)

        {

        }
    }
    public void setWallPaperPriview(String path)
    {

        Glide.with(WallPaperActivity.this).load(path).into(back_image);
        wallPaperPath=path;

    }


private Drawable getDrawable(InputStream inputStream)
{
    Drawable drawable = Drawable.createFromStream(inputStream, null);
    return drawable;
}

    private void setBackImage(InputStream is) {

        if(is!=null) {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            // Bitmap bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.bg1);
            back_image.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmapFromAssets(String fileName) {
        AssetManager assetManager = getAssets();

        InputStream istr = null;
        try {

            istr =assetManager.open("wallpapers/"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);

        return bitmap;
    }


    @Override
    public void onWallPaperSelected(WallPaperModel wallPaperModel) {

        if(wallPaperModel.getFilePath()!=null) {

            setWallPaperPriview(wallPaperModel.getFilePath());
        }
    }
}

package me.mojodigi.lockscreen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class Helper {

    public static  final  String SAVEPASS="savepasword";
    public static  final  String SAVEFILE="savefile.txt";
    public static  final  String CONFIRMPIN="confirmpin";
        public static final String WALLPAPER_PATH="SELECTED_IMAGE";
        public static final String IS_SERVICE_STARTED="service_status";
        public static  String NEWSURL=null;
    public static String  FileProtocol="file://";
        public static void datee(){

    }

    public static Typeface typeFace_adobe_caslonpro_Regular(Context ctx)
    {

        return Typeface.createFromAsset(ctx.getAssets(), "adobe_caslonpro_Regular.ttf");
    }
    public static Typeface typeFace_CORBEL(Context ctx)
    {

        return Typeface.createFromAsset(ctx.getAssets(), "CORBEL.TTF");
    }
    public static Typeface typeFace_CaviarBoldTime(Context ctx)
    {
        return Typeface.createFromAsset(ctx.getAssets(), "Caviar Dreams BoldTime.ttf");
    }


    public static Bitmap pathToBitmap(String path)
    {
        URL url = null;
        try {
            url = new URL(FileProtocol+path);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e){

        }
        return  null;
    }
}

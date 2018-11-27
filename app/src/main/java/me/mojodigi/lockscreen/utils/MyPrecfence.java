package me.mojodigi.lockscreen.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by prerna
 */

public class MyPrecfence {
    private static MyPrecfence preferences = null;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;


    String pin="pin";
    String confirmpin="confirmpin";

    public MyPrecfence(Context context) {
        setmPreferences(PreferenceManager.getDefaultSharedPreferences(context));
    }


    public SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public void setmPreferences(SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
    }


    public static MyPrecfence getActiveInstance(Context context) {
        if (preferences == null) {
            preferences = new MyPrecfence(context);
        }
        return preferences;
    }

    public String getPin() {
        return mPreferences.getString(this.pin, "");
    }

    public void setPin(String pin) {
        editor = mPreferences.edit();
        editor.putString(this.pin, pin);
        editor.commit();
    }

    public String getConfirmpin() {
        return mPreferences.getString(this.confirmpin, "");
    }

    public void setConfirmpin(String confirmpin) {
        editor = mPreferences.edit();
        editor.putString(this.confirmpin, confirmpin);
        editor.commit();
    }


}

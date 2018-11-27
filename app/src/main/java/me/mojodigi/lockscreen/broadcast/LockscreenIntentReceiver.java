package me.mojodigi.lockscreen.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import me.mojodigi.lockscreen.LockScreenActivity;
import me.mojodigi.lockscreen.utils.Helper;
import me.mojodigi.lockscreen.utils.LockScreen;
import me.mojodigi.lockscreen.utils.SharedPreferenceUtils;


public class LockscreenIntentReceiver extends BroadcastReceiver {

    // Handle actions and display Lockscreen
    SharedPreferenceUtils sp ;
    String LOGTAG="LOCKAPP";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOGTAG,"LockscreenIntentReceiver ->>broadcast Executed");


        sp = new SharedPreferenceUtils(context);
        {
            int service_status = sp.getIntValue(Helper.IS_SERVICE_STARTED, 0);
            //and enable  the
            Log.d(LOGTAG,"LockscreenIntentReceiver  service_status->>"+service_status);
            if(service_status==1)  // start the lock screen  if the user  has  enabled lock;
            {
                Log.d(LOGTAG,"LockscreenIntentReceiver  lock screen activity called->>");

                start_lockscreen(context);
            }
        }



    }

    // Display lock screen
    private void start_lockscreen(Context context) {

        Intent mIntent = new Intent(context, LockScreenActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mIntent);
    }

}
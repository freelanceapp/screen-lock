package me.mojodigi.lockscreen;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import me.mojodigi.lockscreen.utils.Helper;
import me.mojodigi.lockscreen.utils.LockScreen;
import me.mojodigi.lockscreen.utils.SharedPreferenceUtils;

public class MainActivity extends AppCompatActivity {
    private int PICK_IMAGE=100;
    int param;
    SharedPreferenceUtils sp ;
    ToggleButton toggleButton;
    String[] permissionsRequired = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;
    RelativeLayout enableLockLayout,changePinLayout,gallleryLayout,wallPaperLayout;
    Context ctx;
    boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        sp = new SharedPreferenceUtils(this);
        ctx=MainActivity.this;
     TextView pinTxtview,enableLocktext,wallPaperText,galleryTxt;
        permissionStatus =ctx.getSharedPreferences("permissionStatus", MODE_PRIVATE);
        enableLockLayout=(RelativeLayout) findViewById(R.id.enableLockLayout);

        changePinLayout=(RelativeLayout)findViewById(R.id.changePinLayout);
        gallleryLayout=(RelativeLayout)findViewById(R.id.gallleryLayout);
        wallPaperLayout=(RelativeLayout)findViewById(R.id.wallPaperLayout);
        pinTxtview=findViewById(R.id.pinTxtview);
        enableLocktext=findViewById(R.id.enableLocktext);
        wallPaperText=findViewById(R.id.wallPaperText);
        galleryTxt=findViewById(R.id.galleryTxt);

        pinTxtview.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
        enableLocktext.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
        wallPaperText.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
        galleryTxt.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));

        toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
        LockScreen.getInstance().init(this,true);
        if(LockScreen.getInstance().isActive()){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);

        }


        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {

                String correctPin = sp.getStringValue(Helper.CONFIRMPIN, "");
                if (correctPin.equalsIgnoreCase("") || correctPin.length() > 4) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.setpin), Toast.LENGTH_SHORT).show();
                    toggleButton.setChecked(false);
                    return;
                }

                if (checked) {
                    // LockScreen.getInstance().init(MainActivity.this,true);
                    boolean status = isAccissibilityEnabled();
                    System.out.print("" + status);
                    if (!status) {
                        permisssionDlg(MainActivity.this);
                    }
                 else {
                    LockScreen.getInstance().active();
                    sp.setValue(Helper.IS_SERVICE_STARTED, 1);
                }

            }

                else{
                    askPinDlg(MainActivity.this);
                    //LockScreen.getInstance().deactivate();
                    //sp.setValue(Helper.IS_SERVICE_STARTED,0);
                }
            }
        });





        wallPaperLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  param=1;
                askForPermission();

//                Intent i  =  new Intent(MainActivity.this,WallPaperActivity.class);
//                startActivity(i);


               }
        });
        gallleryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                param=2;
                askForPermission();
            }
        });








        changePinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               startActivity(new Intent(MainActivity.this,PinActivity.class));

            }
        });



    }
    public boolean isAccissibilityEnabled()
    {
        int accessibilityEnabled=0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(this.getContentResolver(), android.provider.Settings.Secure.ACCESSIBILITY_ENABLED);
            if(accessibilityEnabled==1)
                return  true;
            else  return  false;
        } catch (Settings.SettingNotFoundException e)
        {
            return false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        LockScreen.getInstance().init(this,true);
        if(LockScreen.getInstance().isActive() ){
            toggleButton.setChecked(true);
        }else{
            toggleButton.setChecked(false);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       //   code reference from -->> https://github.com/dakshbhatt21/a-computer-engineer
       //code Location in  my System-G:\Takendra Examples source code\pickImgPreAfterKitKat

      String strImagePath="";
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission((Activity) ctx, permissionsRequired[0]) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                if (param == 1)
                    openWallpaper();
                else if (param == 2)
                    openGallery();
            }
        }


            if (resultCode == RESULT_OK && requestCode == PICK_IMAGE)
            {
                if(data!=null)
                {
                     Uri uri =data.getData();
                     String auth=uri.getAuthority();
                     System.out.print(""+auth);
                    if (isKitKat && DocumentsContract.isDocumentUri(MainActivity.this, uri)) {

                        if ("com.android.externalstorage.documents".equals(uri.getAuthority())) {
                            String docId = DocumentsContract.getDocumentId(uri);
                            String[] split = docId.split(":");
                            String type = split[0];

                            if ("primary".equalsIgnoreCase(type)) {
                                strImagePath = Environment.getExternalStorageDirectory() + "/" + split[1];
                            } else {
                                Pattern DIR_SEPORATOR = Pattern.compile("/");
                                Set<String> rv = new HashSet<>();
                                String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
                                String rawSecondaryStoragesStr = System.getenv("SECONDARY_STORAGE");
                                String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");
                                if (TextUtils.isEmpty(rawEmulatedStorageTarget)) {
                                    if (TextUtils.isEmpty(rawExternalStorage)) {
                                        rv.add("/storage/sdcard0");
                                    } else {
                                        rv.add(rawExternalStorage);
                                    }
                                } else {
                                    String rawUserId;
                                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                        rawUserId = "";
                                    } else {
                                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
                                        String[] folders = DIR_SEPORATOR.split(path);
                                        String lastFolder = folders[folders.length - 1];
                                        boolean isDigit = false;
                                        try {
                                            Integer.valueOf(lastFolder);
                                            isDigit = true;
                                        } catch (NumberFormatException ignored) {
                                        }
                                        rawUserId = isDigit ? lastFolder : "";
                                    }
                                    if (TextUtils.isEmpty(rawUserId)) {
                                        rv.add(rawEmulatedStorageTarget);
                                    } else {
                                        rv.add(rawEmulatedStorageTarget + File.separator + rawUserId);
                                    }
                                }
                                if (!TextUtils.isEmpty(rawSecondaryStoragesStr)) {
                                    String[] rawSecondaryStorages = rawSecondaryStoragesStr.split(File.pathSeparator);
                                    Collections.addAll(rv, rawSecondaryStorages);
                                }
                                String[] temp = rv.toArray(new String[rv.size()]);

                                for (int i = 0; i < temp.length; i++) {
                                    File tempf = new File(temp[i] + "/" + split[1]);
                                    if (tempf.exists()) {
                                        strImagePath = temp[i] + "/" + split[1];
                                    }
                                }
                                sp.setValue(Helper.WALLPAPER_PATH, strImagePath);

                            }
                        }

                        if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                            String docId = DocumentsContract.getDocumentId(uri);
                            String[] split = docId.split(":");
                            String type = split[0];

                            Uri contentUri = null;
                            if ("image".equals(type)) {
                                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                            } else if ("video".equals(type)) {
                                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                            } else if ("audio".equals(type)) {
                                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                            }

                            String selection = "_id=?";
                            String[] selectionArgs = new String[]{
                                    split[1]
                            };

                            Cursor cursor = null;
                            String column = "_data";
                            String[] projection = {
                                    column
                            };

                            try {
                                cursor = getContentResolver().query(contentUri, projection, selection, selectionArgs,
                                        null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    int column_index = cursor.getColumnIndexOrThrow(column);
                                    strImagePath = cursor.getString(column_index);
                                }
                            } finally {
                                if (cursor != null)
                                    cursor.close();
                            }

                            sp.setValue(Helper.WALLPAPER_PATH, strImagePath);
                        }
                        else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                            String id = DocumentsContract.getDocumentId(uri);
                            Uri contentUri = ContentUris.withAppendedId(
                                    Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                            Cursor cursor = null;
                            String column = "_data";
                            String[] projection = {
                                    column
                            };

                            try {
                                cursor = getContentResolver().query(contentUri, projection, null, null,
                                        null);
                                if (cursor != null && cursor.moveToFirst()) {
                                    final int column_index = cursor.getColumnIndexOrThrow(column);
                                    strImagePath = cursor.getString(column_index);
                                    sp.setValue(Helper.WALLPAPER_PATH, strImagePath);
                                }
                            } finally {
                                if (cursor != null)
                                    cursor.close();
                            }
                        }



                    }
                    else if ("content".equalsIgnoreCase(uri.getScheme())) {
                        Cursor cursor = null;
                        String column = "_data";
                        String[] projection = {
                                column
                        };

                        try {
                            cursor = getContentResolver().query(uri, projection, null, null,
                                    null);
                            if (cursor != null && cursor.moveToFirst()) {
                                int column_index = cursor.getColumnIndexOrThrow(column);
                                strImagePath = cursor.getString(column_index);
                                sp.setValue(Helper.WALLPAPER_PATH, strImagePath);
                            }
                        } finally {
                            if (cursor != null)
                                cursor.close();
                        }
                    } else if ("file".equalsIgnoreCase(uri.getScheme()))
                    {
                        strImagePath = uri.getPath();
                        sp.setValue(Helper.WALLPAPER_PATH, strImagePath);
                    }






                }
            }


    }
    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null )
        {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }

    public  void permisssionDlg(Context ctx )
    {

        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.permission_dialog);
        // Set dialog title

        TextView ok=dialog.findViewById(R.id.ok);
        TextView permissionTxt=dialog.findViewById(R.id.permissionTxt);
        TextView close=dialog.findViewById(R.id.close);

         ok.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
         permissionTxt.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
         close.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));



         ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 LockScreen.getInstance().active();
                 sp.setValue(Helper.IS_SERVICE_STARTED,1);
                 dialog.dismiss();

             }
         });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toggleButton.setChecked(false);
            }
        });


        dialog.show();
    }

    public  void askPinDlg(Context ctx )
    {

        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.askpin_dialog);
        dialog.setCancelable(false);

        TextView ok=dialog.findViewById(R.id.ok);
        final TextView pinEdit=dialog.findViewById(R.id.pinEdit);
        TextView close=dialog.findViewById(R.id.close);

        ok.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
        pinEdit.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
        close.setTypeface(Helper.typeFace_CORBEL(MainActivity.this));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(sp!=null) {
                    if (pinEdit.getText().toString().length() == 4) {
                        if (sp.getStringValue(Helper.CONFIRMPIN, "").equals(pinEdit.getText().toString())) {
                            LockScreen.getInstance().deactivate();
                            sp.setValue(Helper.IS_SERVICE_STARTED, 0);
                            dialog.dismiss();
                        } else
                            pinEdit.setError(getResources().getString(R.string.wrongpin));


                    }
                    else {
                        pinEdit.setError(getResources().getString(R.string.enterpin));
                    }
                }

            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                toggleButton.setChecked(true);
            }
        });


        dialog.show();
    }




    public void askForPermission()
    {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(ctx, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale((Activity) ctx, permissionsRequired[1])) {
                //Show Information about why you need the permission

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Permissions");
                builder.setMessage(ctx.getString(R.string.app_name) + " needs to access your storage.");

                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                          ActivityCompat.requestPermissions((Activity) ctx, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Need Permissions");
                builder.setMessage(MainActivity.this.getString(R.string.app_name) + " app need stoarge permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", MainActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        Toast.makeText(MainActivity.this, "Go to Permissions to Grant storage access", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                //just request the permission
                ActivityCompat.requestPermissions((Activity) ctx, permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], false);
            editor.commit();
        } else {
            //You already have the permission, just go ahead.
           if(param==1)
               openWallpaper();
           else  if(param==2)
               openGallery();

        }
    }

    public void openGallery() {


//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


        if (isKitKat) {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_IMAGE);
        }


    }


    public void openWallpaper()
        {
//        Intent intent = new Intent();
//        intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
//        startActivity(intent);

               Intent i  =  new Intent(MainActivity.this,WallPaperActivity.class);
               startActivity(i);

    }

    //new
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                if(param==1)
                    openWallpaper();
                else if(param==2)
                    openGallery();

            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) ctx, permissionsRequired[0]) || ActivityCompat.shouldShowRequestPermissionRationale((Activity) ctx, permissionsRequired[1])) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Need Permissions");
                builder.setMessage(ctx.getString(R.string.app_name) + " app needs storage permission.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        ActivityCompat.requestPermissions((Activity) ctx,permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(ctx, "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        }


    }
    //new
}








package me.mojodigi.lockscreen;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PipedInputStream;

import me.mojodigi.lockscreen.utils.Helper;
import me.mojodigi.lockscreen.utils.MyPrecfence;
import me.mojodigi.lockscreen.utils.SharedPreferenceUtils;


public class PinActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_delete, btn_done;
    EditText et_enterpin;
    TextView txt_cancel, txt_heading,confirmTextHeading,changepinTxt;
    int position = 0;
    String pin = "";
    String confirmpin = "";
    String chnagepin = "";
    LinearLayout imageviewlayout, imageviewlayout1, imageviewlayout2;
    String changedpin, oldpin;
    //confirm password
    Button btn_1_confirm, btn_2_confirm, btn_3_confirm, btn_4_confirm, btn_5_confirm, btn_6_confirm, btn_7_confirm,
            btn_8_confirm, btn_9_confirm, btn_0_confirm, btn_delete_confirm, btn_done_confirm;
    EditText et_enterpin_confirm;
    TextView txt_cancel_confirm;


    //change Password
    Button btn_1_change, btn_2_change, btn_3_change, btn_4_change, btn_5_change, btn_6_change, btn_7_change,
            btn_8_change, btn_9_change, btn_0_change, btn_delete_change, btn_done_change;
    EditText et_enterpin_change;
    TextView txt_cancel_change;
    SharedPreferenceUtils sp ;
    String cPath,data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        getSupportActionBar().hide();
        sp = new SharedPreferenceUtils(this);

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_2 =(Button) findViewById(R.id.btn_2);
        btn_3 = (Button)findViewById(R.id.btn_3);
        btn_4 =(Button) findViewById(R.id.btn_4);
        btn_5 = (Button)findViewById(R.id.btn_5);
        btn_6 = (Button)findViewById(R.id.btn_6);
        btn_7 = (Button)findViewById(R.id.btn_7);
        btn_8 = (Button)findViewById(R.id.btn_8);
        btn_9 = (Button)findViewById(R.id.btn_9);
        btn_0 = (Button)findViewById(R.id.btn_0);

        et_enterpin =(EditText) findViewById(R.id.et_enterpin);
        txt_cancel = (TextView) findViewById(R.id.txt_cancel);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_done = (Button) findViewById(R.id.btn_done);

        imageviewlayout = (LinearLayout) findViewById(R.id.imageviewlayout);
        txt_heading = (TextView) findViewById(R.id.txt_heading);
        confirmTextHeading = (TextView) findViewById(R.id.confirmTextHeading);
        changepinTxt = (TextView) findViewById(R.id.changepinTxt);


        txt_heading.setTypeface(Helper.typeFace_CORBEL(PinActivity.this));
        confirmTextHeading.setTypeface(Helper.typeFace_CORBEL(PinActivity.this));
        changepinTxt.setTypeface(Helper.typeFace_CORBEL(PinActivity.this));


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
        txt_cancel.setOnClickListener(this);
        btn_done.setOnClickListener(this);


        //ids and click for confirm password
        btn_1_confirm = (Button) findViewById(R.id.btn_1_confirm);
        btn_2_confirm = (Button)findViewById(R.id.btn_2_confirm);
        btn_3_confirm = (Button)findViewById(R.id.btn_3_confirm);
        btn_4_confirm = (Button)findViewById(R.id.btn_4_confirm);
        btn_5_confirm = (Button)findViewById(R.id.btn_5_confirm);
        btn_6_confirm = (Button)findViewById(R.id.btn_6_confirm);
        btn_7_confirm = (Button)findViewById(R.id.btn_7_confirm);
        btn_8_confirm = (Button) findViewById(R.id.btn_8_confirm);
        btn_9_confirm = (Button) findViewById(R.id.btn_9_confirm);
        btn_0_confirm = (Button) findViewById(R.id.btn_0_confirm);
        btn_delete_confirm = (Button) findViewById(R.id.btn_delete_confirm);
        btn_done_confirm = (Button) findViewById(R.id.btn_done_confirm);
        et_enterpin_confirm = (EditText) findViewById(R.id.et_enterpin_confirm);
        txt_cancel_confirm = (TextView) findViewById(R.id.txt_cancel_confirm);
        imageviewlayout1 = (LinearLayout) findViewById(R.id.imageviewlayout1);

        btn_0_confirm.setOnClickListener(this);
        btn_1_confirm.setOnClickListener(this);
        btn_2_confirm.setOnClickListener(this);
        btn_3_confirm.setOnClickListener(this);
        btn_4_confirm.setOnClickListener(this);
        btn_5_confirm.setOnClickListener(this);
        btn_6_confirm.setOnClickListener(this);
        btn_7_confirm.setOnClickListener(this);
        btn_8_confirm.setOnClickListener(this);
        btn_9_confirm.setOnClickListener(this);
        btn_delete_confirm.setOnClickListener(this);
        txt_cancel_confirm.setOnClickListener(this);
        btn_done_confirm.setOnClickListener(this);


        //ids and click for change PIN

        btn_1_change = (Button) findViewById(R.id.btn_1_change);
        btn_2_change = (Button) findViewById(R.id.btn_2_change);
        btn_3_change = (Button) findViewById(R.id.btn_3_change);
        btn_4_change = (Button) findViewById(R.id.btn_4_change);
        btn_5_change = (Button) findViewById(R.id.btn_5_change);
        btn_6_change = (Button) findViewById(R.id.btn_6_change);
        btn_7_change = (Button) findViewById(R.id.btn_7_change);
        btn_8_change = (Button) findViewById(R.id.btn_8_change);
        btn_9_change = (Button) findViewById(R.id.btn_9_change);
        btn_0_change = (Button) findViewById(R.id.btn_0_change);
        et_enterpin_change = (EditText) findViewById(R.id.et_enterpin_change);
        txt_cancel_change = (TextView) findViewById(R.id.txt_cancel_change);
        btn_delete_change = (Button) findViewById(R.id.btn_delete_change);
        btn_done_change = (Button) findViewById(R.id.btn_done_change);
        imageviewlayout2 = (LinearLayout) findViewById(R.id.imageviewlayout2);

        btn_0_change.setOnClickListener(this);
        btn_1_change.setOnClickListener(this);
        btn_2_change.setOnClickListener(this);
        btn_3_change.setOnClickListener(this);
        btn_4_change.setOnClickListener(this);
        btn_5_change.setOnClickListener(this);
        btn_6_change.setOnClickListener(this);
        btn_7_change.setOnClickListener(this);
        btn_8_change.setOnClickListener(this);
        btn_9_change.setOnClickListener(this);
        btn_delete_change.setOnClickListener(this);
        txt_cancel_change.setOnClickListener(this);
        btn_done_change.setOnClickListener(this);

        //condition for chnage PIN
        changedpin = MyPrecfence.getActiveInstance(this).getConfirmpin();


        changedpin=sp.getStringValue(Helper.CONFIRMPIN,"");
        System.out.print(""+changedpin);



    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_0:
                pin = pin + "0";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_1:
                pin = pin + "1";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_2:
                pin = pin + "2";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_3:
                pin = pin + "3";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_4:
                pin = pin + "4";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;
            case R.id.btn_5:
                pin = pin + "5";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_6:
                pin = pin + "6";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_7:
                pin = pin + "7";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_8:
                pin = pin + "8";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_9:
                pin = pin + "9";
                et_enterpin.setText(pin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_delete:
                if (pin.length() > 4) {
                    pin = pin.substring(0, 4);
                }

                if (pin.length() != 0) {
                    pin = pin.substring(0, pin.length() - 1);
                    et_enterpin.setText(pin);
                }
                break;

            case R.id.txt_cancel:
                finish();
                break;
            case R.id.btn_done:
                if (pin.length() > 4) {
                    pin = pin.substring(0, 4);
                }
                if (pin.length() == 4) {
                    MyPrecfence.getActiveInstance(this).setPin(pin);
                    oldpin = pin;

//                    String ee=MyPrecfence.getActiveInstance(this).getConfirmpin();
                    String ee=sp.getStringValue(Helper.CONFIRMPIN,"");


                    if (changedpin.equals("")) {
                        imageviewlayout.setVisibility(View.GONE);
                        imageviewlayout1.setVisibility(View.VISIBLE);
                        imageviewlayout2.setVisibility(View.GONE);

                    } else {
                        if (pin.equals(ee)){
                            imageviewlayout.setVisibility(View.GONE);
                            imageviewlayout1.setVisibility(View.GONE);
                            imageviewlayout2.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(this, getResources().getString(R.string.wrongpin), Toast.LENGTH_SHORT).show();

                        }


                    }

                } else {
                    Toast.makeText(this, getResources().getString(R.string.enterpin), Toast.LENGTH_SHORT).show();
                }

                break;


            //confirm pin

            case R.id.btn_0_confirm:
                confirmpin = confirmpin + "0";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_1_confirm:
                confirmpin = confirmpin + "1";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_2_confirm:
                confirmpin = confirmpin + "2";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_3_confirm:
                confirmpin = confirmpin + "3";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_4_confirm:
                confirmpin = confirmpin + "4";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;
            case R.id.btn_5_confirm:
                confirmpin = confirmpin + "5";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_6_confirm:
                confirmpin = confirmpin + "6";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_7_confirm:
                confirmpin = confirmpin + "7";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_8_confirm:
                confirmpin = confirmpin + "8";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_9_confirm:
                confirmpin = confirmpin + "9";
                et_enterpin_confirm.setText(confirmpin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_delete_confirm:
                if (confirmpin.length() > 4) {
                    confirmpin = pin.substring(0, 4);
                }

                if (confirmpin.length() != 0) {
                    confirmpin = confirmpin.substring(0, confirmpin.length() - 1);
                    et_enterpin_confirm.setText(confirmpin);
                }
                break;

            case R.id.txt_cancel_confirm:
                finish();
                break;
            case R.id.btn_done_confirm:
                if (confirmpin.length() > 4) {
                    confirmpin = confirmpin.substring(0, 4);
                }
                if (confirmpin.length() == 4) {
                } else {
                    Toast.makeText(this, getResources().getString(R.string.enterpin), Toast.LENGTH_SHORT).show();
                }


                if (oldpin.equals(confirmpin)) {
//                    MyPrecfence.getActiveInstance(this).setConfirmpin(confirmpin);

                    //store value in sharedpref
                    sp.setValue(Helper.CONFIRMPIN,confirmpin);

                    //store value in file
                    try {

                        String path = Environment.getExternalStorageDirectory() + "/" + Helper.SAVEPASS;

                        File f = new File(path);
                        if (!f.exists()) {
                            if (f.mkdir()) {
                                 cPath = path + "/" + Helper.SAVEFILE;
                                 data = confirmpin;
                                Log.i("savePIN1",cPath+"..."+data);

                                FileOutputStream out = new FileOutputStream(cPath);
                                out.write(data.getBytes());
                                out.close();
                            }else {
                                cPath = path + "/" + Helper.SAVEFILE;
                                data = confirmpin;
                                Log.i("savePIN2",cPath+"..."+data);

                                FileOutputStream out = new FileOutputStream(cPath);
                                out.write(data.getBytes());
                                out.close();

                            }
                        }
                        else
                            {
                                cPath = path + "/" + Helper.SAVEFILE;
                                data = confirmpin;
                                Log.i("savePIN3",cPath+"..."+data);

                                FileOutputStream out = new FileOutputStream(cPath);
                                out.write(data.getBytes());
                                out.close();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }




                   // Toast.makeText(this, "Set Pin is : " + sp.getStringValue(Helper.CONFIRMPIN,""), Toast.LENGTH_SHORT).show();
                    finish();

                } else {

                    Toast.makeText(this, getResources().getString(R.string.enterpin), Toast.LENGTH_SHORT).show();

                }

                break;

            //chnage pin
            case R.id.btn_0_change:
                chnagepin = chnagepin + "0";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_1_change:
                chnagepin = chnagepin + "1";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_2_change:
                chnagepin = chnagepin + "2";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_3_change:
                chnagepin = chnagepin + "3";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_4_change:
                chnagepin = chnagepin + "4";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;
            case R.id.btn_5_change:
                chnagepin = chnagepin + "5";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_6_change:
                chnagepin = chnagepin + "6";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_7_change:
              //  pin = pin + "7";  //bug was here
                chnagepin = chnagepin + "7";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_8_change:
                chnagepin = chnagepin + "8";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_9_change:
                chnagepin = chnagepin + "9";
                et_enterpin_change.setText(chnagepin);
                if (position < 4)
                    position++;
                break;

            case R.id.btn_delete_change:
                if (chnagepin.length() > 4) {
                    chnagepin = chnagepin.substring(0, 4);
                }

                if (chnagepin.length() != 0) {
                    chnagepin = chnagepin.substring(0, chnagepin.length() - 1);
                    et_enterpin_change.setText(chnagepin);
                }
                break;

            case R.id.txt_cancel_change:
                finish();
                break;
            case R.id.btn_done_change:
                if (chnagepin.length() > 4) {
                    chnagepin = chnagepin.substring(0, 4);
                }
                if (chnagepin.length() == 4) {
//                    MyPrecfence.getActiveInstance(this).setConfirmpin(chnagepin);
                    sp.setValue(Helper.CONFIRMPIN,chnagepin);
                    oldpin = chnagepin;
                    imageviewlayout1.setVisibility(View.VISIBLE);
                    imageviewlayout.setVisibility(View.GONE);
                    imageviewlayout2.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, getResources().getString(R.string.enterpin), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
package com.smartsoftwaresolutions.rosary2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

//import com.facebook.stetho.Stetho;
//import com.facebook.stetho.Stetho;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.Wave.WaveView;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;

import java.util.Locale;

public class Setting extends AppCompatActivity {
    SharedPreferences SpType ;
    public static final String PREFS_NAME = "Saved_Type";
    private AdView mAdView;
    Button btn_save_counter,btn_change_reminder;
    EditText et_set_counter;
String set_counter,show_help;
Switch switch1;
Boolean last_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SpType=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = SpType.edit();
        switch1=findViewById(R.id.switch1);
        show_help = SpType.getString("show_help", "1");
        btn_change_reminder=findViewById(R.id.btn_change_reminder);

        btn_change_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting.this,Notefication_Activity.class);
                startActivity(intent);
            }
        });
        if (show_help.equals("1")){
            switch1.setChecked(true);
            last_check=true;
        }else {
            switch1.setChecked(false);
            last_check=false;
        }
switch1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      //if (switch1.isChecked()) {
          if (last_check){
              switch1.setChecked(false);
              last_check=false;
             editor.putString("show_help","0");
             editor.apply();
          }else {
              switch1.setChecked(true);
              last_check=true;
              editor.putString("show_help","1");
              editor.apply();
          }
      }
  //  }
});


        et_set_counter=findViewById(R.id.et_set_counter);
        set_counter=String.valueOf(SpType.getInt("set_counter",33));
        btn_save_counter=findViewById(R.id.btn_save_counter);
et_set_counter.setText(set_counter);
        btn_save_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String C = String.valueOf(et_set_counter.getText());
                if (C==null){
// nothing to do
                }else {
                    editor.putInt("set_counter",Integer.parseInt(C));
                    editor.commit();
                    Toast.makeText(Setting.this,"Saved",Toast.LENGTH_LONG).show();
                }
            }
        });

        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
    //    if(BuildConfig.FLAVOR.equals("Free")){
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


            mAdView = findViewById(R.id.adView10);


            // AdRequest adRequest = new AdRequest.Builder().build();
            //  AdRequest adRequest = new AdRequest.Builder().addTestDevice("35663D4E3BC0B627A8E5C8571E17588F").build();
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);

            mAdView.setAdListener(new com.google.android.gms.ads.AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Log.e("onAdLoaded","AdLoaded");

                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    Log.e("onAdFailedToLoad",""+errorCode);

                }
            });
          //  Toast.makeText(Setting.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
    //    }
       // SpType=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    //    Stetho.initializeWithDefaults(this);

        editor.putString("with_native","1");
        editor.commit();
        ((RadioGroup) findViewById(R.id.native_Choice))
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        SharedPreferences.Editor editor = SpType.edit();
                        switch (i) {
                            case R.id.with_N:
                               // with native
                                editor.putString("with_native","1");
                                editor.commit();
                                break;
                            case R.id.without_N:
                                // without native
                                editor.putString("with_native","2");
                                editor.commit();
                                break;
                            default:
                                // native only
                                editor.putString("with_native","3");
                                editor.commit();
                                break;
                        }
                    }
                });
//        btn_go_pro=findViewById(R.id.btn_go_pro);
//        btn_go_pro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                sharingIntent.setType("text/plain");
//              //  String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.blackout.free";
//                String shareBodyText = "Check it out.\n\n"+"Sorry Not Available For Now";
//                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
//                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
//                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
//            }
//        });
    }
    @Override
    public boolean onNavigateUp(){

        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
              //  String shareBodyText = "Check it out.\n\n"+" https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free";

                shared_Menu sharedMenu=new shared_Menu();
                String flaver =BuildConfig.FLAVOR;
                String shareBodyText =sharedMenu.flavor(flaver);
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;
            case R.id.menu_back:

                finish();
                return true;
            case R.id.action_Arabic:
                setLocale("ar");

                Toast.makeText(this, R.string.la, Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_English:
                setLocale("en_US");

                Toast.makeText(this, R.string.le, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_setting:
                Intent intent=new Intent(Setting.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Setting.this, Help_Activity.class);
                startActivity(intent2);
                return true;
//            case R.id.action_reminder:
//                Intent intent3=new Intent(this, Notefication_Activity.class);
//                startActivity(intent3);
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        /// change the direction of the app from left to right to right to left
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(myLocale);
        }
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, MainActivity.class);
        startActivity(refresh);
        finish();
    }
}

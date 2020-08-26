package com.smartsoftwaresolutions.rosary2.Tasbeh_Mode_List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.Wave.Wave;
import com.smartsoftwaresolutions.rosary2.Clasic.Clasic;
import com.smartsoftwaresolutions.rosary2.Progress_Mode.ProgressBar_Mode;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Swip.Swipe_Mode;
import com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode.CustomRVItemTouchListener;
import com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode.RecyclerViewItemClickListener;
import com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode.T_explode;
import com.smartsoftwaresolutions.rosary2.Path.Path;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class List_Mode extends AppCompatActivity {
    SharedPreferences SpType ;
    MenuItem myItem;
    private AdView mAdView;
    public static final String PREFS_NAME = "Saved_Type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__mode);

        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
      //  if(BuildConfig.FLAVOR.equals("Free")){
            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


            mAdView = findViewById(R.id.adView7);


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
          //  Toast.makeText(List_Mode.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
       // }
        SpType=getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        List<Data> data = fill_with_data();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        List_Mode_Adapter adapter = new List_Mode_Adapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, recyclerView, new RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                // String p= String.valueOf(position);
                 if (position==5){
                   //  Toast.makeText(swipe_Mode.this,"selected position is "+ p,Toast.LENGTH_LONG).show();
                     SharedPreferences.Editor editor = SpType.edit();
                     editor.putString("start","5");
                     editor.commit();
                     Intent intent=new Intent(List_Mode.this,T_explode.class);
                     startActivity(intent);
                 }else if (position==2){
                     SharedPreferences.Editor editor = SpType.edit();
                     editor.putString("start","2");
                     editor.commit();
                     Intent intent=new Intent(List_Mode.this, Path.class);
                     startActivity(intent);
                 }else if (position==0){
                     SharedPreferences.Editor editor = SpType.edit();
                     editor.putString("start","0");
                     editor.commit();
                     Intent intent=new Intent(List_Mode.this, Swipe_Mode.class);
                     startActivity(intent);
                 }else if (position==1){
                     SharedPreferences.Editor editor = SpType.edit();
                     editor.putString("start","1");
                     editor.commit();
                     Intent intent=new Intent(List_Mode.this, ProgressBar_Mode.class);
                     startActivity(intent);
                 }else if (position==3){
                     SharedPreferences.Editor editor = SpType.edit();
                     editor.putString("start","3");
                     editor.commit();
                     Intent intent=new Intent(List_Mode.this, Wave.class);
                     startActivity(intent);
                 }else if (position==4){
                     SharedPreferences.Editor editor = SpType.edit();
                     editor.putString("start","4");
                     editor.commit();
                     Intent intent=new Intent(List_Mode.this, Clasic.class);
                     startActivity(intent);}


               // view.setBackgroundColor(Color.GREEN);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }




    public List<Data> fill_with_data() {

        List<Data> data = new ArrayList<>();

// position 0
        data.add(new Data(getString(R.string.swip_motion),
                getString(R.string.swip_discription),
                R.drawable.rosary_white));
        // position 1
        data.add(new Data(getString(R.string.progress_ring),
                getString(R.string.progress_discription),
                R.drawable.one));

        // position 2
        data.add(new Data(getString(R.string.path_motion),
                getString(R.string.path_discription),
                R.drawable.two));
        // position 3
        data.add(new Data(getString(R.string.wave),
                getString(R.string.wave_discription),
                R.drawable.three));
        // position 4
        data.add(new Data(getString(R.string.clasic),
                getString(R.string.clasic_description),
                R.drawable.four));
        // position 5
        data.add(new Data(getString(R.string.list_in_motion),
                getString(R.string.list_in_motion_discription),
                R.drawable.five));

        return data;
    }
    //menu
    @Override
    public boolean onNavigateUp(){
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        myItem = menu.findItem(R.id.menu_counter);

        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        // first to show zero
      //  myItem.setTitle("count  "+t_counter);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
              //  String shareBodyText = "Check it out.\n\n"+"https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free  ";

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
                Intent intent=new Intent(List_Mode.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(List_Mode.this, Help_Activity.class);
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

package com.smartsoftwaresolutions.rosary2.Path;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.ArcMotion;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.Congratulation.Congratulation;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;

import java.util.Locale;
//import com.transitionseverywhere.ArcMotion;
//import com.transitionseverywhere.ChangeBounds;
//import com.transitionseverywhere.TransitionManager;

public class Path extends AppCompatActivity {
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    int t_counter=0;
    int Grand_Total=0;
    String z="";
    String Zeker_counter_SQL;
    String value,value_ID,with_native,value_native;
    int set_counter;
    MenuItem myItem;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    public static final String PREFS_NAME = "Saved_Type";
    TextView tv_path_text;
    Button tv_counter_3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);

        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
       // if(BuildConfig.FLAVOR.equals("Free")){
            /**           Admob inti                               **/
            mInterstitialAd = new InterstitialAd(this);
            // real id is :ca-app-pub-8537373371656761/2013021686
            mInterstitialAd.setAdUnitId("ca-app-pub-8537373371656761/2013021686");
            mInterstitialAd.loadAd(new AdRequest.Builder().build());


            MobileAds.initialize(this, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                }
            });


            mAdView = findViewById(R.id.adView8);


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
           // Toast.makeText(Path.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
      //  }

        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();
        tv_path_text=findViewById(R.id.tv_path_text);
        tv_counter_3=findViewById(R.id.tv_counter_3);

        // data to populate the RecyclerView with
        SharedPreferences SpType =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        // Reading from SharedPreferences
        value = SpType.getString("Type", "");
        value_ID = SpType.getString("Type_ID", "");
        value_native = SpType.getString("Type_native", "");
        with_native = SpType.getString("with_native", "1");
        set_counter=SpType.getInt("set_counter",33);
tv_counter_3.setText("0");
       Get_Counter get_counter=new Get_Counter();
        get_counter.execute("");
/*********************************Create the Path*************************************/
        final ViewGroup transitionsContainer = findViewById(R.id.transitions_container_path);
        final Button btn_path= transitionsContainer.findViewById(R.id.button);

      //  btn_path.setText(value);
        if (with_native.equals("1")){
            tv_path_text.setText(value+"\n\n"+value_native);
        }else if (with_native.equals("2")){
            tv_path_text.setText(value);
        }else {
            tv_path_text.setText(value_native);
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
         //   btn_path.setBackgroundResource(R.drawable.rounded_button);
        }

        btn_path.setOnClickListener(new View.OnClickListener() {
            boolean mToRightAnimation;
            @Override
            public void onClick(View view) {

                final ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setPathMotion(new ArcMotion());
                changeBounds.setDuration(500);
                TransitionManager.beginDelayedTransition(transitionsContainer, changeBounds);

                mToRightAnimation = !mToRightAnimation;
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) btn_path.getLayoutParams();
                params.gravity = mToRightAnimation ? (Gravity.END | Gravity.BOTTOM) :
                        (Gravity.START | Gravity.TOP);
                btn_path.setLayoutParams(params);
                t_counter++;
              //  myItem.setTitle(getString(R.string.count)+"  "+t_counter);
                tv_counter_3.setText(String.valueOf(t_counter));
                if (t_counter==set_counter){
                    Update_Counter update_counter=new Update_Counter();
                    update_counter.execute("");
                    tv_counter_3.setText("0");
                   Intent intent=new Intent(Path.this, Congratulation.class);
                    startActivity(intent);
                }



            }
        });
    }
    public class Get_Counter extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {

        //    Toast.makeText(Path.this,""+ z,Toast.LENGTH_LONG).show();
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            /*********************************************read couter from sql**/
            String query_SQL = "select Zeker_count from Zeker_Tbl where Zeker_Id="+value_ID;
            // we run this select statment by creating a cursor and execute it
            Cursor cursor=AndPOS_db.rawQuery(query_SQL,null);
            /***find data**/
            if(cursor.moveToFirst()){
                do{
                    Zeker_counter_SQL= cursor.getString(cursor.getColumnIndex("Zeker_count"));
                }while (cursor.moveToNext());
                cursor.close();
                z = "Success";
            }else{
                z = "Error retrieving data from table";
            }


            /***********************explode *******************************/





            return null;
        }
    }

    public class Update_Counter extends AsyncTask<String,String ,String>{
        @Override
        protected void onPreExecute() {
       //   myItem.setTitle(getString(R.string.count)+"  "+t_counter);
            tv_counter_3.setText(String.valueOf(t_counter));
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    myItem.setTitle("count  "+t_counter);
//                }
//            }, 5000);
            Get_Counter get_counter=new Get_Counter();
            get_counter.execute("");
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            t_counter=0;
           // myItem.setTitle(getString(R.string.count)+t_counter);
            tv_counter_3.setText(String.valueOf(t_counter));
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Grand_Total= Integer.parseInt(Zeker_counter_SQL)+t_counter;
            /***************update the counter****************/
            // Create a new map of values, where column names are the keys
            ContentValues values1 = new ContentValues();
            // the customer Id is auto increament

            values1.put("Zeker_count", Grand_Total);
            //the insert() will give -1 if the statment failed and id number if successfully inserted
            // it took table name and the value we make a content value to put all the column names in it
            try {
                //the update commend will take (1,2,3,4) 1 database name, 2 values , 3 condition ===> where statmente
                long rowUpdate = AndPOS_db.update("Zeker_Tbl",values1,"Zeker_Id="+value_ID,null);
                if (rowUpdate != -1) {
                    //Toast.makeText(AddCustomer.this, "New row added, row id: " + rowInserted, Toast.LENGTH_SHORT).show();
                    z = "Updated Successfully";
                } else {
                    //  Toast.makeText(AddCustomer.this, "Something wrong", Toast.LENGTH_SHORT).show();
                    z = "Something wrong";
                }
            } catch (SQLiteConstraintException e1) {
                //Toast.makeText(AddCustomer.this,  e1.toString(),Toast.LENGTH_LONG).show();
                // z = e1.toString();
                z = "Counter not updated ";
            }
            /****************************************/
            return null;
        }
    }

    @Override
    protected void onStop() {
        Update_Counter update_counter=new Update_Counter();
        update_counter.execute("");
     //   if(BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
       // }
        super.onStop();
    }

    //menu
    @Override
    public boolean onNavigateUp(){
     //   if(BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
    //    }

        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
     //   myItem = menu.findItem(R.id.menu_counter);

        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        // first to show zero
       // myItem.setTitle(getString(R.string.count)+"  "+t_counter);
        tv_counter_3.setText(String.valueOf(t_counter));
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
                Intent intent=new Intent(Path.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Path.this, Help_Activity.class);
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

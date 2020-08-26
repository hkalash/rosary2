package com.smartsoftwaresolutions.rosary2.Clasic;

import androidx.appcompat.app.AppCompatActivity;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.smartsoftwaresolutions.rosary2.BuildConfig;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.Spinner.spinnerAdapter;
import com.smartsoftwaresolutions.rosary2.Spinner.spinner_info;
import com.smartsoftwaresolutions.rosary2.Tasbeh_CRUD.Tasbeh_info;
import com.smartsoftwaresolutions.rosary2.Wave.Wave;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Clasic extends AppCompatActivity {
    public static final String PREFS_NAME = "Saved_Type";
    // TextView tv;
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    int t_counter=0;
    int Grand_Total=0;
    String Zeker_counter_SQL;
    String value,value_ID,value_native,with_native;
   public Integer set_counter;
    String z="";
    MenuItem myItem;
   // Round_Button_View round_button_view;
    String X="";
    TextView x_counter;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    Button btn_c_counter;
    SharedPreferences SpType;
    Spinner sp_tasheh;
    List<spinner_info> LabelList;
    spinnerAdapter SADA;
    String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasic);

        btn_c_counter=findViewById(R.id.btn_c_counter);
        btn_c_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Clasic.this, Setting.class);
                startActivity(intent);
            }
        });


sp_tasheh=findViewById(R.id.sp_taspeh);






        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
    //    if(BuildConfig.FLAVOR.equals("Free")){
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


            mAdView = findViewById(R.id.adView2);


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
       //     Toast.makeText(Clasic.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
       // }



   //     round_button_view=new Round_Button_View(getApplicationContext());
        // data to populate the RecyclerView with
     SpType =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Reading from SharedPreferences
        value = SpType.getString("Type", "");
        value_native = SpType.getString("Type_native", "");
        value_ID = SpType.getString("Type_ID", "");
        with_native = SpType.getString("with_native", "1");
        set_counter=SpType.getInt("set_counter",33);
        // tv = (TextView) findViewById(R.id.tv_progress);
        lang=SpType.getString("lang", "en_US");
        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();
        LoadSpinner loadSpinner=new LoadSpinner();
        loadSpinner.execute("");
        Get_Counter get_counter=new Get_Counter();
        get_counter.execute("");
        TextView clasic_text=findViewById(R.id.btn_clasic);

        if (with_native.equals("1")){
            clasic_text.setText(value+"\n"+value_native);
        }else if (with_native.equals("2")){
            clasic_text.setText(value);
        }else {
            clasic_text.setText(value_native);
        }

         x_counter=findViewById(R.id.tv_clasic_counter);
        x_counter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                X= String.valueOf(x_counter.getText());
                if (X.equals(getString(R.string.god_bless_you))){
                    // this mean that the user fish the spicified counter and need to up date
                    Update_Counter_finsh update_counter_finsh=new Update_Counter_finsh();
                    update_counter_finsh.execute("");
                  //  X="0";

                }
            }
        });




    }


    public class LoadSpinner extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SADA = new spinnerAdapter(LabelList, Clasic.this);

            sp_tasheh.setAdapter(SADA);

            sp_tasheh.setSelection(0);
            SADA.notifyDataSetChanged();

        }

        @Override
        protected String doInBackground(String... strings) {
            LabelList = new ArrayList<spinner_info>();
            if (lang.equals( "en_US")){
                spinner_info first_select=new spinner_info("0","Select Sentence");
                LabelList.add(first_select);
            }else {
                spinner_info first_select=new spinner_info("0","حدد الجملة");
                LabelList.add(first_select);
            }


            // Select Quary for a spicific category_spinner example bedroom the cat id is from put extra
            String selectQuery = "select * from Zeker_Tbl  ";
            //sqlite will returen a cursor a pointer to the first data
            Cursor cursor = AndPOS_db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list the result is kids clasic and modern
            if (cursor.moveToFirst()) {
                do {
                    // we create a hash map with string for the key and string for the data
                    HashMap<String, String> map = new HashMap<String, String>();
                    String Zeker_Id, Zeker_Name_Arabic;
                    String Zeker_native;

                    // we use put method to save the data in that map and we use the column name as the key
                    Zeker_Id = cursor.getString( cursor.getColumnIndex( "Zeker_Id" ) );
                    if(lang.equals( "en_US")){
                        Zeker_Name_Arabic = cursor.getString( cursor.getColumnIndex( "Zeker_Name_native" ) );
                    }else{
                        Zeker_Name_Arabic = cursor.getString(cursor.getColumnIndex("Zeker_Name_Arabic"));
                    }
//                    Zeker_Name_Arabic = cursor.getString( cursor.getColumnIndex( "Zeker_Name_Arabic" ) );
//                    Zeker_native=cursor.getString(cursor.getColumnIndex("Zeker_Name_native"));



                    spinner_info country_Info = new spinner_info(Zeker_Id, Zeker_Name_Arabic);


                    // we add the saved map to the list one by one
                    LabelList.add(country_Info);

                    //  LabelList.add(cursor2.getString(cursor2.getColumnIndex("User_Type_Name")));
                } while (cursor.moveToNext());
            }

            cursor.close();


            //Sp_UserType.setAdapter(dataAdapter);
            return null;
        }
    }
    @Override
    protected void onPause() {
      //  X= (String) round_button_view.tv_clasic_counter.getText();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Update_Counter update_counter = new Update_Counter();
        //  X= (String) round_button_view.tv_clasic_counter.getText();
        // String X= String.valueOf(round_button_view.counter_x) ;
        X = String.valueOf(x_counter.getText());
        // if (X.equalvas("Finsh")){
        if (X.equals(getString(R.string.god_bless_you))) {
            // this mean that the user finsh the spicified counter and need to up date
            //   Update_Counter_finsh update_counter_finsh=new Update_Counter_finsh();
            //  update_counter_finsh.execute("");
            // x_counter.setText("0");
            //   X="0";

        } else {
            t_counter = Integer.parseInt(X);
            update_counter.execute("");
        }


      //  if (BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }

     //   }
        super.onStop();
    }

    //menu
    @Override
    public boolean onNavigateUp(){

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
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
        myItem.setVisible(false);
        // first to show zero
       //myItem.setTitle("count  "+t_counter);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

               // String shareBodyText = "Please Check it out.\n\n"+"https://play.google.com/store/apps/details?id=com.smartsoftwaresolutions.rosary2.free  ";

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
                Intent intent=new Intent(Clasic.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Clasic.this, Help_Activity.class);
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
    public class Get_Counter extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

          //  Toast.makeText(Clasic.this,""+ z,Toast.LENGTH_LONG).show();
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

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            t_counter=0;
           // X="0";
//            myItem.setTitle("count  "+t_counter);
//                progress=3*t_counter;

            //  mProgress.setProgress(progress);
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
                    Zeker_counter_SQL= String.valueOf(Grand_Total);
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
    public  class Update_Counter_finsh extends AsyncTask<String,String ,String>{


        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        //    t_counter=0;
           // X="0";

        }

        @Override
        protected String doInBackground(String... strings) {
        //    Grand_Total= Integer.parseInt(Zeker_counter_SQL)+t_counter;
            Grand_Total= Integer.parseInt(Zeker_counter_SQL)+SpType.getInt("set_counter",33);

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
                    Zeker_counter_SQL= String.valueOf(Grand_Total);
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
}

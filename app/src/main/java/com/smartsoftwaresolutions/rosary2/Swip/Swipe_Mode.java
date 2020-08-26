package com.smartsoftwaresolutions.rosary2.Swip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.smartsoftwaresolutions.rosary2.Clasic.Clasic;
import com.smartsoftwaresolutions.rosary2.Congratulation.Congratulation;
import com.smartsoftwaresolutions.rosary2.DataBase.DataHelper;
import com.smartsoftwaresolutions.rosary2.Help.Help_Activity;
import com.smartsoftwaresolutions.rosary2.MainActivity;
import com.smartsoftwaresolutions.rosary2.R;
import com.smartsoftwaresolutions.rosary2.Setting;
import com.smartsoftwaresolutions.rosary2.Spinner.spinnerAdapter;
import com.smartsoftwaresolutions.rosary2.Spinner.spinner_info;
import com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode.T_explode;
import com.smartsoftwaresolutions.rosary2.Tasbeh_Mode.explode.explode_Adapter;
import com.smartsoftwaresolutions.rosary2.notification.Notefication_Activity;
import com.smartsoftwaresolutions.rosary2.shared_Menu;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Swipe_Mode extends AppCompatActivity {
    public static final String PREFS_NAME = "Saved_Type";
    DataHelper AndPOS_dbh;
    SQLiteDatabase AndPOS_db;
    int t_counter=0;
    int Grand_Total=0;
    String Zeker_counter_SQL;
    String value,value_ID,value_native,with_native;
    int set_counter;
    String z="";
    List<Swipe_Data> data;
    swipe_Mode_Adapter adapter;
    MenuItem myItem;
    Button swip_reset;
    RecyclerView recyclerView;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    Button tv_counter_1;
    spinnerAdapter SP_ADA;
    String lang;
    List<spinner_info> LabelList;
    Spinner sp1;
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.RIGHT |ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//            if (viewHolder!=null){
//                viewHolder.itemView.setBackgroundColor(Color.RED);
//            }
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

data.remove(viewHolder.getAdapterPosition());
//t_counter++;
//            myItem.setTitle("count  "+t_counter);
adapter.notifyDataSetChanged();


           // if (t_counter<=32) {
            if (t_counter<=set_counter) {
                //  pStatus += 1;
                t_counter++;
                tv_counter_1.setText(String.valueOf(t_counter));
                myItem.setTitle(getString(R.string.count)+"  "+ t_counter);
              //  if( t_counter==33){
                if( t_counter==set_counter){
// the user reach the limt in count and need tosave it in the Grand total
                    Update_Counter update_counter=new Update_Counter();
                    update_counter.execute("");
//                        waterlevel =0.1f;
//                        waveView.setWaterLevelRatio(waterlevel);
                    Intent intent=new Intent(Swipe_Mode.this, Congratulation.class);
                    startActivity(intent);
                }
            }
        }

//        @Override
//        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
//            super.onSelectedChanged(viewHolder, actionState);
//            if (viewHolder!=null){
//
//              if (actionState==8){
//                  viewHolder.itemView.setBackgroundColor(0);
//              }
//            }
//        }

//        @Override
//        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
//                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//            viewHolder.itemView.setBackgroundColor(Color.RED);
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe__mode);

        /*******************************************Admob*******************/
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
     //   if(BuildConfig.FLAVOR.equals("Free")){

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


            mAdView = findViewById(R.id.adView5);
sp1=findViewById(R.id.sp1);

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
  //          Toast.makeText(Swipe_Mode.this,"this is a free version with ads",Toast.LENGTH_LONG).show();
    //    }

        tv_counter_1=findViewById(R.id.tv_counter_1);
        // on the bigining weput the t_counter with the value zero in it
        tv_counter_1.setText(String.valueOf(t_counter));
        swip_reset=findViewById(R.id.swip_reset);
        recyclerView = findViewById(R.id.swipe_recyclerview);


        swip_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    swip_reset.setText(R.string.reset_counter);

             Update_Counter update_counter=new Update_Counter();
                update_counter.execute("");

                fill_Adapter();
                tv_counter_1.setText("0");
            }
        });

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_info SelectedType;
                // On selecting a spinner item

                SelectedType=SP_ADA.getItem(position);

                String s = SelectedType.getZeker_Id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // data to populate the RecyclerView with
        SharedPreferences SpType =getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        // Reading from SharedPreferences
        value = SpType.getString("Type", "");
        value_native = SpType.getString("Type_native", "");
        value_ID = SpType.getString("Type_ID", "");
        with_native = SpType.getString("with_native", "1");
        set_counter=SpType.getInt("set_counter",33);
        lang=SpType.getString("lang","en_US");
      data = fill_with_data();

        // we create an instance of data base helper  database this will create the database
        AndPOS_dbh = new DataHelper(getApplicationContext());
        // and we connect the data to the data base helper and specify its use as a writable
        AndPOS_db = AndPOS_dbh.getWritableDatabase();
// read the grand total of the counter
       Get_Counter get_counter=new Get_Counter();
        get_counter.execute("");
        LoadSpinner loadSpinner=new LoadSpinner();
        loadSpinner.execute();


       // adapter = new swipe_Mode_Adapter(data, getApplication());
      //  recyclerView.setAdapter(adapter);
        fill_Adapter();
      //  recyclerView.setLayoutManager(new LinearLayoutManager(this));
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
//        recyclerView.addOnItemTouchListener(new CustomRVItemTouchListener(this, recyclerView, new RecyclerViewItemClickListener() {
//            @Override
//            public void onClick(View view, int position) {
////                // String p= String.valueOf(position);
////                 if (position==5){
////                   //  Toast.makeText(swipe_Mode.this,"selected position is "+ p,Toast.LENGTH_LONG).show();
////                     Intent intent=new Intent(swipe_Mode.this,T_explode.class);
////                     startActivity(intent);
////                 }else if (position==2){
////                     Intent intent=new Intent(swipe_Mode.this, Path.class);
////                     startActivity(intent);
////                 }
//
//
//                // view.setBackgroundColor(Color.GREEN);
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

    }
    public class LoadSpinner extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SP_ADA = new spinnerAdapter(LabelList, Swipe_Mode.this);

            sp1.setAdapter(SP_ADA);

            sp1.setSelection(0);
            SP_ADA.notifyDataSetChanged();

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
    private void fill_Adapter() {
        data = fill_with_data();
        adapter = new swipe_Mode_Adapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public List<Swipe_Data> fill_with_data() {
String value1="",value_native1="";
        List<Swipe_Data> data = new ArrayList<>();
        if (with_native.equals("1")){
           value1=value;
            value_native1=value_native;
        }else if (with_native.equals("2")){
            value_native1="";
            value1=value;
        }else {
           value1="";
            value_native1=value_native;
        }
// thess than 33 it begin from zero
for (int i=0;i<set_counter;i++){
    data.add(new Swipe_Data(value1,value_native1));

}
        return data;
    }

    @Override
    protected void onStop() {
        // on stop will update the counter on the tasbeh
        Update_Counter update_counter=new Update_Counter();
        update_counter.execute("");
       // if(BuildConfig.FLAVOR.equals("free")){
     //   if(BuildConfig.FLAVOR.equals("free")) {
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
      //  }
       // }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter.getItemCount()==0){
            swip_reset.setText(R.string.another_one);
        }
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
myItem.setVisible(false);
        MenuItem english=menu.findItem(R.id.action_English);
        MenuItem arabic=menu.findItem(R.id.action_Arabic);
        english.setVisible(false);
        arabic.setVisible(false);
        // first to show zero
        myItem.setTitle(getString(R.string.count)+"  "+t_counter);
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
                Intent intent=new Intent(Swipe_Mode.this, Setting.class);
                startActivity(intent);

                return true;
            case R.id.action_Help:
                Intent intent2=new Intent(Swipe_Mode.this, Help_Activity.class);
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

    public class Get_Counter extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {

          //  Toast.makeText(Swipe_Mode.this,""+ z,Toast.LENGTH_LONG).show();
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
        protected void onPostExecute(String s) {
            t_counter=0;
            Get_Counter get_counter=new Get_Counter();
            get_counter.execute("");
            myItem.setTitle(getString(R.string.count)+"  "+t_counter);
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


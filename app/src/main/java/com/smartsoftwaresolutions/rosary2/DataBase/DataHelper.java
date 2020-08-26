package com.smartsoftwaresolutions.rosary2.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase db = null;
    private static final String LOG = "DatabaseHelper";
    private static DataHelper mInstance = null;


    // Database Version
    private static final int DATABASE_VERSION =2;

    // Database Name in sqlite
    private static final String DATABASE_NAME = "Tasbeh";
// table name
    private static final String CREATE_Zeker_Tbl="CREATE TABLE Zeker_Tbl (\n" +
            "    Zeker_Id   INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
            "                         NOT NULL,\n" +
            "    Zeker_Name_Arabic CHAR (250) NOT NULL,\n" +
            "    Zeker_Name_native CHAR (250) NOT NULL,\n" +
            "    Zeker_count       Integer DEFAULT 0\n" +
            "                         COLLATE NOCASE\n" +
            ");";

    private static final String CREATE_Duaa_Tbl="CREATE TABLE Duaa_Tbl ( Duaa_Id   INTEGER   PRIMARY KEY AUTOINCREMENT\n" +
            "                                     NOT NULL,\n" +
            "               Duaa_Name CHAR (250) NOT NULL,\n" +
            "                Duaa_Dis CHAR (2000) NOT NULL\n" +
            "                                     COLLATE NOCASE\n" +
            "           );";
    private static final String Create_Vedio_Family_Tbl="CREATE TABLE VFamily_Tbl (\n" +
            "    VF_ID          INTEGER       PRIMARY KEY,\n" +
            "    VF_Discription VARCHAR (250) \n" +
            ");\n";
    private static final String Create_Vedio_Item_Tbl="CREATE TABLE Vedio_Item_Tbl (\n" +
            "    VI_ID          INTEGER       PRIMARY KEY,\n" +
            "    Vi_VF_ID       INTEGER,\n" +
            "    VI_Description VARCHAR (250),\n" +
            "    VI_Path        VARCHAR (500) \n" +
            ");";

    private static final String Drop_Zeker_Tbl="DROP TABLE IF EXISTS Zeker_Tbl";
    private static final String Drop_Duaa_Tbl="DROP TABLE IF EXISTS Duaa_Tbl";
    private static final String Drop_Vedio_Family_Tbl="DROP TABLE IF EXISTS VFamily_Tbl";
    private static final String Drop_Vedio_Item_Tbl="DROP TABLE IF EXISTS Vedio_Item_Tbl";


    public DataHelper(Context context) {
        /**
         * context to use to open or create the database;
         name of the database file; commments.db is our case.
         factory to use for creating cursor objects, or null for the default; null in our case.
         version number of the database (starting at 1); if the database is older,
         onUpgrade(SQLiteDatabase, int, int) will be used to upgrade the database;
         if the database is newer,
         * */
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Zeker_Tbl);
        db.execSQL(CREATE_Duaa_Tbl);
        db.execSQL(Create_Vedio_Family_Tbl);
        db.execSQL(Create_Vedio_Item_Tbl);
    }
// on update of sql version
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Drop_Zeker_Tbl);
        db.execSQL(Drop_Duaa_Tbl);
        db.execSQL(Drop_Vedio_Family_Tbl);
        db.execSQL(Drop_Vedio_Item_Tbl);
        // create new tables
        onCreate(db);
    }


}

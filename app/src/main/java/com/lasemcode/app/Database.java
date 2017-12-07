package com.lasemcode.app;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Z & N on 04/12/2017.
 */

public class Database extends SQLiteOpenHelper {
    public static String DB_NAME = "dbpengaduan";
    public static int DB_VERSION = 1;
    public Database(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTablePengaduan = "CREATE TABLE pengaduan(" + "idPengaduan INTEGER PRIMARY KEY, " +
                "idPengguna INTEGER, " +
                "fotoKerusakan TEXT, " +
                "latitude INTEGER, " +
                "longtitude INTEGER, " +
                "tglPengaduan DATE," +
                "keterangan TEXT);";
        String createTablePengguna = "CREATE TABLE pengguna(" + "idPengguna INTEGER PRIMARY KEY, " +
                "nmPengguna TEXT, " +
                "noTelepon TEXT, " +
                "email TEXT);";
        sqLiteDatabase.execSQL(createTablePengaduan);
        sqLiteDatabase.execSQL(createTablePengguna);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

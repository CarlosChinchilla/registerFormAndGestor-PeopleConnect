package com.carlos.peopleconnect;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.RadioButton;

import androidx.appcompat.app.AlertDialog;

class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String nombre, CursorFactory factory, int
            version) {
        super(context, nombre, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //aquí creamos la tabla de miembro
        db.execSQL("create table usuario(telefono integer primary key, nombre text, apellidos text, fechaNac text, estCiv text, pais text, provincia text, codPos integer, email text, pass text, avatar text)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int version1, int version2) {
        db.execSQL("drop table if exists usuario");
        db.execSQL("create table usuario(telefono integer primary key, nombre text, apellidos text, fechaNac text, estCiv text, pais text, provincia text, codPos integer, email text, pass text, avatar text)");
    }
}
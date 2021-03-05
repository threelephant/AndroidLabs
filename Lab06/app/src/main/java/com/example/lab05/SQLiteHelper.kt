package com.example.lab05

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, "items.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "kind TEXT, " +
                "title TEXT, " +
                "price REAL, " +
                "weight REAL, " +
                "yearCreation INTEGER, " +
                "photo TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { }
}
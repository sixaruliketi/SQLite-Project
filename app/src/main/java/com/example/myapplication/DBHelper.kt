package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION ) {

    companion object {
        val DATABASE_NAME = "MY_DATABASE"

        val DATABASE_VERSION = 1

        val TABLE_NAME = "my_table_name"

        val COL_ID = "id"

        val COL_NAME = "name"

        val COL_AGE = "age"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY, $COL_NAME TEXT, $COL_AGE TEXT)")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addName(name: String, age: String){

        val db = this.writableDatabase

        val values = ContentValues()

        values.put(COL_NAME, name)
        values.put(COL_AGE, age)

        db.insert(TABLE_NAME,null,values)

        db.close()
    }

    fun readName() : Cursor?{
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }
}
package com.example.pam

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, dbName, factory, version) {

    companion object {
        internal val dbName = "PAM_App"
        internal val factory = null
        internal val version = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table user(id integer primary key autoincrement,login varchar(30),password varchar(30))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertUserData(login: String, password: String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("login", login)
        values.put("password", password)

        db.insert("user", null, values)
        db.close()
        return true
    }

    fun getUser(login: String, password: String): Boolean {
        val db = writableDatabase
        val query = "select * from user where login = '$login' and password = '$password'"
        val cursor = db.rawQuery(query,null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }
}
package com.example.sns_app

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


@Suppress("DEPRECATION")
class DBHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "CREATE TABLE USER(NAME TEST," +
                        "EMAIL TEXT, PASSWORD TEXT, PASSWORD_OK TEXT);"
            )

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insert(
        name: String, email: String, password: String, password_ok: String
    ) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL(
            "INSERT INTO USER VALUES('" + name + "'" + ", '" + email + "'" + ", '" + password + "'" + ", '" + password_ok +
                    "'" +   "');"

        )
        db.close()
    }

    fun delete(name: String) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL("DELETE FROM USER WHERE NAME = '" + name + "';")
        db.close()
    }

    fun update(
        name: String, password: String, password_ok: String, email: String
    ) {
        var db: SQLiteDatabase = writableDatabase

        db.execSQL(
            "UPDATE USER SET PASSWORD = " + "'" + password + "'" + ", PASSWORD_OK = '" + password_ok + "'" + ", EMAIL = '" + email + "'"  +
                    "WHERE NAME = '" + name + "';"
        )

        db.close()
    }

    fun getResult(): String {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT * FROM USER", null)
        while (cursor.moveToNext()) {
            result += (cursor.getString(0)
                    + " : "
                    + cursor.getString(1)
                    + " : "
                    + cursor.getString(2)
                    + " : "
                    + cursor.getString(3)
                    + " : "
                    + cursor.getString(4)
                    + "\n")

        }

        return result
    }

    fun getResult1(EMAIL: String, PASSWORD: String): Boolean {
        var db: SQLiteDatabase = readableDatabase
        var result: String = ""

        var cursor: Cursor = db.rawQuery("SELECT EMAIL, PASSWORD FROM USER", null)
        while (cursor.moveToNext()) {
            result = (cursor.getString(0))
            if (result.equals(EMAIL)) {
                if (cursor.getString(1).equals(PASSWORD)) {
                    return true
                    break
                } else {
                    return false
                }
            }else {

            }
        }

        return false
    }

}
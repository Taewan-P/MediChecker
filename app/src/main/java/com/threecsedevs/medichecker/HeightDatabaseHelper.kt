package com.threecsedevs.medichecker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HeightDatabaseHelper (context: Context)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "ProfileDB"
        private val DB_VERSION = 4
        private val TABLE_NAME = "height"
        private val ID = "id"
        private val HEIGHT = "Height"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME" +
                    "($ID INTEGER PRIMARY KEY," +
                    "$HEIGHT TEXT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun addHeight(height : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(HEIGHT, height)

        val _success = db.insert(TABLE_NAME, null, value)
        db.close()

        return (Integer.parseInt("$_success") != -1)
    }

    fun updateHeight(height : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(HEIGHT, height)
        return db.update(TABLE_NAME, value, "$ID IN(SELECT $ID FROM $TABLE_NAME LIMIT 1 OFFSET 0)", null) > 0
    }

    fun getHeight() : String {
        var result : String = ""
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        var height : String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                height = cursor.getString(cursor.getColumnIndex(HEIGHT))
                result = height
            }
        }
        cursor.close()
        db.close()

        return result
    }

    fun onDowngrade(old: Int) {
        this.writableDatabase.version = old
    }

    fun getVersion(): Int {
        return DB_VERSION
    }

}
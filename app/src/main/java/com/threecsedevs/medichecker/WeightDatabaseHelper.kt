package com.threecsedevs.medichecker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class WeightDatabaseHelper (context: Context)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private val DB_NAME = "UserDB"
        private val DB_VERSION = 1
        private val TABLE_NAME = "weight"
        private val ID = "id"
        private val WEIGHT = "Weight"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME" +
                    "($ID INTEGER PRIMARY KEY," +
                    "$WEIGHT TEXT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun addWeight(weight : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(WEIGHT, weight)

        val _success = db.insert(TABLE_NAME, null, value)
        db.close()

        return (Integer.parseInt("$_success") != -1)
    }

    fun updateWeight(weight : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(WEIGHT, weight)
        return db.update(TABLE_NAME, value, "$ID IN(SELECT $ID FROM $TABLE_NAME LIMIT 1 OFFSET 0)", null) > 0
    }

    fun getWeight() : String {
        var result : String = ""
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        var weight : String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                weight = cursor.getString(cursor.getColumnIndex(WEIGHT))
                result = weight
            }
        }
        cursor.close()
        db.close()

        return result
    }

}
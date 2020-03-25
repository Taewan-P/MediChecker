package com.threecsedevs.medichecker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AgeDatabaseHelper (context: Context)
    : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {

    companion object {
        private val DB_NAME = "ProfileDB"
        private val DB_VERSION = 3
        private val TABLE_NAME = "age"
        private val ID = "id"
        private val AGE = "Age"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        onDowngrade(db, 2,
            DB_VERSION
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        onDowngrade(db, 2,
            DB_VERSION
        )
        val createTable =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME" +
                    "($ID INTEGER PRIMARY KEY," +
                    "$AGE TEXT)"

        db?.execSQL(createTable)
        onDowngrade(db, 2,
            DB_VERSION
        )
    }

    fun addAge(age : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(AGE, age)

        val _success = db.insert(TABLE_NAME, null, value)
        db.close()

        return (Integer.parseInt("$_success") != -1)
    }

    fun updateAge(age : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(AGE, age)

        val result = db.update(TABLE_NAME, value, "$ID IN(SELECT $ID FROM $TABLE_NAME LIMIT 1 OFFSET 0)", null) > 0
        db.close()

        return result
    }

    fun getAge() : String {
        var result : String = ""
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        var age : String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                age = cursor.getString(cursor.getColumnIndex(AGE))
                result = age
            }
        }
        cursor.close()
        db.close()

        return result
    }

    override fun onDowngrade(db: SQLiteDatabase?, old: Int, newVersion: Int) {
        db!!.version = old
    }
}
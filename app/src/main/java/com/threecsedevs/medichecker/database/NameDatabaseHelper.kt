package com.threecsedevs.medichecker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NameDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {

    companion object {
        private val DB_NAME = "ProfileDB"
        private val DB_VERSION = 2
        private val TABLE_NAME = "name"
        private val ID = "id"
        private val NAME = "Name"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME" +
                    "($ID INTEGER PRIMARY KEY," +
                    "$NAME TEXT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        onDowngrade(db, 2,
            DB_VERSION
        )
    }

    fun addName(name : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(NAME, name)

        val _success = db.insert(TABLE_NAME, null, value)
        db.close()

        return (Integer.parseInt("$_success") != -1)
    }

    fun updateName(name : String) : Boolean {
        val db = this.writableDatabase
        val value = ContentValues()

        value.put(NAME, name)

        val result = db.update(TABLE_NAME, value, "$ID IN(SELECT $ID FROM $TABLE_NAME LIMIT 1 OFFSET 0)", null) > 0
        db.close()

        return result
    }

    fun getName() : String {
        var result : String = ""
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        var name : String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                name = cursor.getString(cursor.getColumnIndex(NAME))
                result = name
            }
        }
        cursor.close()
        db.close()

        return result
    }

    override fun onDowngrade(db: SQLiteDatabase?, old: Int, newVersion: Int) {
        db!!.version = old
    }

    fun getVersion(): Int {
        return DB_VERSION
    }
}
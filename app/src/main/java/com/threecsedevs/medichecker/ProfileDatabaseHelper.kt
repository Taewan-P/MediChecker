package com.threecsedevs.medichecker

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProfileDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private val DB_NAME = "UserDB"
        private val DB_VERSION = 1
        private val TABLE_NAME = "profile"
        private val ID = "id"
        private val NAME = "Name"
        private val AGE = "Age"
        private val HEIGHT = "Height"
        private val WEIGHT = "Weight"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME" +
                    "($ID INTEGER PRIMARY KEY," +
                    "$NAME TEXT," +
                    "$AGE TEXT," +
                    "$HEIGHT TEXT," +
                    "$WEIGHT TEXT)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    // Exists' Methods
    fun nameExists() : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun ageExists() : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun heightExists() : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun weightExists() : Boolean {
        TODO("NOT IMPLEMENTED")
    }


    // Adding Methods
    fun addName(name: String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun addAge(age: String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun addHeight(height: String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun addWeight(weight: String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }


    // Updating Methods
    fun updateName(name : String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun updateAge(age: String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun updateHeight(height: String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    fun updateWeight(weight: String) : Boolean {
        TODO("NOT IMPLEMENTED")
    }

    // Get the whole profile
    fun getProfile() : MutableList<String> {
        var profile = mutableListOf<String>()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        var name : String
        var age : String
        var height : String
        var weight : String

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(NAME))
                    age = cursor.getString(cursor.getColumnIndex(AGE))
                    height = cursor.getString(cursor.getColumnIndex(HEIGHT))
                    weight = cursor.getString(cursor.getColumnIndexOrThrow(WEIGHT))

                    profile = mutableListOf(name, age, height, weight)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()

        return profile
    }
}
package com.threecsedevs.medichecker.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.threecsedevs.medichecker.medicine.Medicine

class MyMedicineDatabaseHelper(context: Context)
    : SQLiteOpenHelper(context,
    DB_NAME, null,
    DB_VERSION
) {

    companion object {
        private val DB_NAME = "UserDB"
        private val DB_VERSION = 1
        private val TABLE_NAME = "medicinelist"
        private val ID = "id"
        private val MEDICINE_NAME = "MedicineName"
        private val MORNING = "Morning"
        private val LUNCH = "Lunch"
        private val DINNER = "Dinner"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
                    "CREATE TABLE $TABLE_NAME" +
                    "($ID INTEGER PRIMARY KEY," +
                    "$MEDICINE_NAME TEXT," +
                    "$MORNING INTEGER DEFAULT 0," +
                    "$LUNCH INTEGER DEFAULT 0," +
                    "$DINNER INTEGER DEFAULT 0)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun addMedicine(medicine: Medicine) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(MEDICINE_NAME, medicine.name)
        values.put(MORNING, booleanToInteger(medicine.morning))
        values.put(LUNCH, booleanToInteger(medicine.lunch))
        values.put(DINNER, booleanToInteger(medicine.dinner))

        val _success = db.insert(TABLE_NAME, null, values)
        db.close()

        return (Integer.parseInt("$_success") != -1)
    }

    fun delMedicine(position: Int) : Boolean {
        val db = this.writableDatabase
        val query = db.rawQuery("DELETE FROM $TABLE_NAME WHERE $ID IN(SELECT $ID FROM $TABLE_NAME LIMIT 1 OFFSET $position)", null)
        val num = query.count
        query.close()
        db.close()
        return num == 0
    }

    fun updateMedicine(medicine: Medicine, position: Int) : Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(MEDICINE_NAME, medicine.name)
        values.put(MORNING, booleanToInteger(medicine.morning))
        values.put(LUNCH, booleanToInteger(medicine.lunch))
        values.put(DINNER, booleanToInteger(medicine.dinner))

        val result = db.update(TABLE_NAME, values, "$ID IN(SELECT $ID FROM $TABLE_NAME LIMIT 1 OFFSET $position)", null) > 0
        db.close()

        return result
    }

    fun getAllMedicine() : MutableList<Medicine> {
        var allMedicine = mutableListOf<Medicine>()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery, null)
        var name : String
        var morning : Boolean
        var lunch : Boolean
        var dinner : Boolean

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    name = cursor.getString(cursor.getColumnIndex(MEDICINE_NAME))
                    morning = integerToBoolean(cursor.getInt(cursor.getColumnIndex(
                        MORNING
                    )))
                    lunch = integerToBoolean(cursor.getInt(cursor.getColumnIndex(LUNCH)))
                    dinner = integerToBoolean(cursor.getInt(cursor.getColumnIndex(DINNER)))

                    allMedicine.add(
                        Medicine(
                            name,
                            morning,
                            lunch,
                            dinner
                        )
                    )
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()

        return allMedicine
    }

    private fun booleanToInteger(bool:Boolean) : Int {
        return if (bool) {
            // True
            1
        } else {
            // False
            0
        }
    }

    private fun integerToBoolean(int: Int) : Boolean {
        return int == 1
    }
}
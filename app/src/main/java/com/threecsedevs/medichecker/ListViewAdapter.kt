package com.threecsedevs.medichecker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ListViewAdapter(context: Context, var resource: Int ,var items: List<Medicine> ) : ArrayAdapter<Medicine>(context, resource, items){

    override fun getView(position: Int, convertView: View?, p2: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(resource , null )
        val medicineName = view.findViewById(R.id.medicineName) as TextView
        val morning = view.findViewById(R.id.morningToggle) as Switch
        val lunch = view.findViewById(R.id.lunchToggle) as Switch
        val dinner = view.findViewById(R.id.dinnerToggle) as Switch

        var medicine = items[position]

        medicineName.text = medicine.name
        morning.isChecked = medicine.morning
        lunch.isChecked = medicine.lunch
        dinner.isChecked = medicine.dinner

        return view
    }
}
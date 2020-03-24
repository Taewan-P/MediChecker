package com.threecsedevs.medichecker.input_medicine

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.threecsedevs.medichecker.R
import com.threecsedevs.medichecker.activities.SearchAllActivity

class InputMedicineListViewAdapter(context: Context, var resource: Int, var items: MutableList<String>) : ArrayAdapter<String>(context, resource, items){
    var selectedIdx : Int = -1

    override fun getView(position: Int, convertView: View?, p2: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(resource , null )
        val inputBtn = view.findViewById(R.id.inputMedicine_tmp) as Button
        val deleteBtn = view.findViewById(R.id.deleteBtn_tmp) as Button

        var drugName = items[position]
        if (drugName == "") {
            inputBtn.text = "Input Medicine"
        }
        else {
            inputBtn.text = drugName
        }

        inputBtn.setOnClickListener {
            selectedIdx = position
            val intent = Intent(context, SearchAllActivity::class.java)
            (context as Activity).startActivityForResult(intent, 100)
        }

        deleteBtn.setOnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }

    fun setResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    items[selectedIdx] = data?.getStringExtra("medicine").toString()
                    notifyDataSetChanged()
                }
                else -> {
                    // Error
                    Toast.makeText(context, "Error Receiving Medicine Name", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
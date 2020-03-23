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
import androidx.core.app.ActivityCompat.startActivityForResult
import com.threecsedevs.medichecker.R
import com.threecsedevs.medichecker.activities.SearchActivity
import com.threecsedevs.medichecker.activities.SearchAllActivity

class InputMedicineListViewAdapter(context: Context, var resource: Int, var items: MutableList<String>) : ArrayAdapter<String>(context, resource, items){

    override fun getView(position: Int, convertView: View?, p2: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(resource , null )
        val inputBtn = view.findViewById(R.id.inputMedicine_tmp) as Button
        val deleteBtn = view.findViewById(R.id.deleteBtn_tmp) as Button

        inputBtn.setOnClickListener {
            val intent = Intent(context, SearchAllActivity::class.java)
            (context as Activity).startActivityForResult(intent, 100)
        }

        deleteBtn.setOnClickListener {
            items.removeAt(position)
            notifyDataSetChanged()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    val inputBtn = (context as Activity).findViewById(R.id.inputMedicine_tmp) as Button
                    inputBtn.text = data!!.getStringExtra("medicine").toString()
                }
                else -> {
                    // Error
                    Toast.makeText(context, "Error Receiving Medicine Name", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
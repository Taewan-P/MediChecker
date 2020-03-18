package com.threecsedevs.medichecker

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MedicineListViewAdapter(context: Context, var resource: Int, var items: MutableList<Medicine> ) : ArrayAdapter<Medicine>(context, resource, items){
    private lateinit var db:DatabaseHelper

    override fun getView(position: Int, convertView: View?, p2: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(resource , null )
        val medicineName = view.findViewById(R.id.medicineName) as TextView
        val morning = view.findViewById(R.id.morningToggle) as CheckBox
        val lunch = view.findViewById(R.id.lunchToggle) as CheckBox
        val dinner = view.findViewById(R.id.dinnerToggle) as CheckBox
        val edit = view.findViewById(R.id.editBtn) as Button
        val delete = view.findViewById(R.id.delBtn) as Button
        db = DatabaseHelper(this.context)

        var medicine = items[position]

        medicineName.text = medicine.name
        morning.isChecked = medicine.morning
        lunch.isChecked = medicine.lunch
        dinner.isChecked = medicine.dinner

        edit.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            val dialogView = layoutInflater.inflate(R.layout.add_medicine_dialog, null)
            val medicineNameToAdd = dialogView.findViewById<EditText>(R.id.medicineNameToAdd)
            val takeMorning = dialogView.findViewById<CheckBox>(R.id.morningCheck)
            val takeLunch = dialogView.findViewById<CheckBox>(R.id.lunchCheck)
            val takeDinner = dialogView.findViewById<CheckBox>(R.id.dinnerCheck)

            // Insert Medicine Value to Dialog
            medicineNameToAdd.setText(medicine.name)
            takeMorning.isChecked = medicine.morning
            takeLunch.isChecked = medicine.lunch
            takeDinner.isChecked = medicine.dinner


            builder.setView(dialogView)
                .setPositiveButton("Edit") { dialogInterface, i ->
                    // Add Item to CustomListView
                    // Update Medicine Value(List) based on Dialog Value
                    val tmp = Medicine(
                        medicineNameToAdd.text.toString(),
                        takeMorning.isChecked,
                        takeLunch.isChecked,
                        takeDinner.isChecked)
                    val result = db.updateMedicine(tmp, position)

                    if (result) {
                        medicine.name = medicineNameToAdd.text.toString()
                        medicine.morning = takeMorning.isChecked
                        medicine.lunch = takeLunch.isChecked
                        medicine.dinner = takeDinner.isChecked
                        notifyDataSetChanged()
                    }
                    else {
                        Toast.makeText(this.context, "Edit Failed. Please Try Again.", Toast.LENGTH_SHORT).show()
                        notifyDataSetChanged()
                    }
                }
                .setNegativeButton("Cancel") {dialogInterface, i ->
                    // Cancel Btn. Do nothing.
                }
                .show()

        }

        delete.setOnClickListener {
            val result = db.delMedicine(position)
            if (result) {
                items.removeAt(position)
                notifyDataSetChanged()
            }
            else {
                Toast.makeText(this.context, "Delete Failed. Please Try Again.", Toast.LENGTH_SHORT).show()
                notifyDataSetChanged()
            }
        }



        return view
    }
}
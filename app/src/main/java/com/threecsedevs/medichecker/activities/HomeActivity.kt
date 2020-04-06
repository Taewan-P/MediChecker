package com.threecsedevs.medichecker.activities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.threecsedevs.medichecker.R
import com.threecsedevs.medichecker.database.MyMedicineDatabaseHelper
import com.threecsedevs.medichecker.medicine.Medicine
import com.threecsedevs.medichecker.medicine.MedicineListViewAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : Fragment() {
    var dbHandler : MyMedicineDatabaseHelper? = null
    fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.activity_home)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Change StatusBar Color
        this.activity?.window?.statusBarColor = ContextCompat.getColor(context!!,
            R.color.colorWhite
        )
        var view = this.activity?.window?.decorView
        view!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        dbHandler =
            MyMedicineDatabaseHelper(this.context!!)
        var mlist: MutableList<Medicine> = dbHandler!!.getAllMedicine()

        medicineList.adapter =
            MedicineListViewAdapter(
                this.context!!,
                R.layout.medicine_item,
                mlist
            )
        medicineList.emptyView = helpText

        add_button.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            val dialogView = layoutInflater.inflate(R.layout.add_medicine_dialog, null)
            val medicineNameToAdd = dialogView.findViewById<EditText>(R.id.medicineNameToAdd)
            val takeMorning = dialogView.findViewById<CheckBox>(R.id.morningCheck)
            val takeLunch = dialogView.findViewById<CheckBox>(R.id.lunchCheck)
            val takeDinner = dialogView.findViewById<CheckBox>(R.id.dinnerCheck)
            val imm = context!!.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            medicineNameToAdd.requestFocus()
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            val addbtn = builder.setView(dialogView)
                .setPositiveButton("Add") { _, _ ->
                    // Add Item to CustomListView
                    if (!TextUtils.isEmpty(medicineNameToAdd.text.trim())) {
                        val med = Medicine(
                            medicineNameToAdd.text.toString(),
                            takeMorning.isChecked,
                            takeLunch.isChecked,
                            takeDinner.isChecked
                        )
                        dbHandler!!.addMedicine(med)
                        (medicineList.adapter as MedicineListViewAdapter).add(med)
                        (medicineList.adapter as MedicineListViewAdapter).notifyDataSetChanged()
                        imm.hideSoftInputFromWindow(medicineNameToAdd.windowToken, 0)
                    }
                    else {
                        Toast.makeText(this.context!!,
                        "Fill in the Medicine Name!", Toast.LENGTH_SHORT).show()
                    }


            }
                .setNegativeButton("Cancel") {dialogInterface, i ->
                    // Cancel Btn. Do nothing.
                    imm.hideSoftInputFromWindow(medicineNameToAdd.windowToken, 0)
                }
                .show()
                .getButton(DialogInterface.BUTTON_POSITIVE)

            addbtn.isEnabled = false

            medicineNameToAdd.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    if (!TextUtils.isEmpty(p0.toString().trim())) {
                        addbtn.isEnabled = true
                    }
                    else {
                        medicineNameToAdd.error = "Medicine Name is required."
                        addbtn.isEnabled = false
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            })
        }
    }
}

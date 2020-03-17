package com.threecsedevs.medichecker

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : Fragment() {

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
        var mlist = mutableListOf<Medicine>()

        // Example Lists
//        mlist.add(Medicine("Tyrenol", morning = true, lunch = true, dinner = true))
//        mlist.add(Medicine("Tamiflu", morning = true, lunch = false, dinner = true))
//        mlist.add(Medicine("Ibuprophen", morning = false, lunch = false, dinner = false))

        medicineList.adapter = MedicineListViewAdapter(this.context!!, R.layout.medicine_item, mlist)
        medicineList.emptyView = helpText

        add_button.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            val dialogView = layoutInflater.inflate(R.layout.add_medicine_dialog, null)
            val medicineNameToAdd = dialogView.findViewById<EditText>(R.id.medicineNameToAdd)
            val takeMorning = dialogView.findViewById<CheckBox>(R.id.morningCheck)
            val takeLunch = dialogView.findViewById<CheckBox>(R.id.lunchCheck)
            val takeDinner = dialogView.findViewById<CheckBox>(R.id.dinnerCheck)

            builder.setView(dialogView)
                .setPositiveButton("Add") { dialogInterface, i ->
                    // Add Item to CustomListView
                    (medicineList.adapter as MedicineListViewAdapter).add(Medicine(medicineNameToAdd.text.toString(),takeMorning.isChecked, takeLunch.isChecked, takeDinner.isChecked))
                    (medicineList.adapter as MedicineListViewAdapter).notifyDataSetChanged()

            }
                .setNegativeButton("Cancel") {dialogInterface, i ->
                    // Cancel Btn. Do nothing.
                }
                .show()


        }

    }
}

package com.threecsedevs.medichecker

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : Fragment() {
    var dbHandler : ProfileDatabaseHelper? = null
    fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.activity_profile)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Change StatusBar Color
        this.activity?.window?.statusBarColor = ContextCompat.getColor(context!!, R.color.colorAccent)
        var view = this.activity?.window?.decorView
        view!!.systemUiVisibility = view.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()

        dbHandler = ProfileDatabaseHelper(this.context!!)
        val age = mutableListOf<String>("Select Age")
        val height = mutableListOf<String>("Select Height")
        val weight = mutableListOf<String>("Select Weight")
        for (i in 15..100) age.add("$i")
        for (i in 100..250) height.add("$i cm")
        for (i in 30..200) weight.add("$i kg")

        val ageAdapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, age)
        val heightAdapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, height)
        val weightAdapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, weight)

        val profile = dbHandler!!.getProfile()

        ageAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        weightAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        ageSpinner.adapter = ageAdapter
        heightSpinner.adapter = heightAdapter
        weightSpinner.adapter = weightAdapter

        if (profile.size != 0) {
            // Bug will occur. Still need fix.
            // What if the user just entered his/her name and left?
            // -> the array size will be 1, causing IndexOutOfRangeError.
            ageSpinner.setSelection(ageAdapter.getPosition(profile[1]))
            heightSpinner.setSelection(heightAdapter.getPosition(profile[2]))
            weightSpinner.setSelection(weightAdapter.getPosition(profile[3]))
        }

        profileName.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            val dialogView = layoutInflater.inflate(R.layout.activity_name_dialog, null)
            val nameToAdd = dialogView.findViewById<EditText>(R.id.nameToAdd)

            if (dbHandler!!.nameExists()) {
                nameToAdd.setText(profile[0])
            }
            val addbtn = builder.setView(dialogView)
                .setPositiveButton("Set") { dialogInterface, i ->
                    if (dbHandler!!.nameExists()) {
                        // Update Name
                        val result = dbHandler!!.updateName(nameToAdd.text.toString())
                        if (result) {
                            profileName.text = nameToAdd.text
                        }
                        else Toast.makeText(this.context, "Name Update Failed.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        // Add Name
                        var result = dbHandler!!.addName(nameToAdd.text.toString())
                        if (result) {
                            profileName.text = nameToAdd.text
                        }
                        else Toast.makeText(this.context, "Failed to add name.", Toast.LENGTH_SHORT).show()

                    }
                }
                .setNegativeButton("Cancel") { dialogInterface, i ->
                    // Do Nothing
                }
                .show()
                .getButton(DialogInterface.BUTTON_POSITIVE)

            if (TextUtils.isEmpty(nameToAdd.text)) {
                addbtn.isEnabled = false
            }
            nameToAdd.addTextChangedListener(object: TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    if (!TextUtils.isEmpty(p0.toString().trim())) {
                        addbtn.isEnabled = true
                    }
                    else {
                        nameToAdd.error = "Name is required."
                        addbtn.isEnabled = false
                    }
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }
            })

        }

        ageSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                if (position != 0) {
//                    Toast.makeText(view.context, "Spinner selected : ${parent.getItemAtPosition(position)}", Toast.LENGTH_SHORT).show()
                    // 1. Find if data exists
                    // 2. If exists, update.
                    // 3. If it doesn't exist, add.

                     if (dbHandler!!.ageExists()) {
                         dbHandler!!.updateAge(parent.getItemAtPosition(position).toString())
                     }
                    else {
                         dbHandler!!.addAge(parent.getItemAtPosition(position).toString())
                     }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

        heightSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                if (position != 0) {
//                    Toast.makeText(view.context, "Spinner selected : ${parent.getItemAtPosition(position)}", Toast.LENGTH_SHORT).show()

                    if (dbHandler!!.heightExists()) {
                        dbHandler!!.updateHeight(parent.getItemAtPosition(position).toString())
                    }
                    else {
                        dbHandler!!.addHeight(parent.getItemAtPosition(position).toString())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

        weightSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                if (position != 0) {
//                    Toast.makeText(view.context, "Spinner selected : ${parent.getItemAtPosition(position)}", Toast.LENGTH_SHORT).show()

                    if (dbHandler!!.weightExists()) {
                        dbHandler!!.updateWeight(parent.getItemAtPosition(position).toString())
                    }
                    else {
                        dbHandler!!.addWeight(parent.getItemAtPosition(position).toString())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

    }
}
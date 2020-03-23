package com.threecsedevs.medichecker.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.threecsedevs.medichecker.R
import com.threecsedevs.medichecker.database.AgeDatabaseHelper
import com.threecsedevs.medichecker.database.HeightDatabaseHelper
import com.threecsedevs.medichecker.database.NameDatabaseHelper
import com.threecsedevs.medichecker.database.WeightDatabaseHelper
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : Fragment() {
    var nameDBHandler : NameDatabaseHelper? = null
    var ageDBHandler : AgeDatabaseHelper? = null
    var heightDBHandler : HeightDatabaseHelper? = null
    var weightDBHandler : WeightDatabaseHelper? = null
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
        this.activity?.window?.statusBarColor = ContextCompat.getColor(context!!,
            R.color.colorAccent
        )
        var view = this.activity?.window?.decorView
        view!!.systemUiVisibility = view.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()


        val age = mutableListOf<String>("Select Age")
        val height = mutableListOf<String>("Select Height")
        val weight = mutableListOf<String>("Select Weight")
        for (i in 15..100) age.add("$i")
        for (i in 130..200 step 5) height.add("$i cm")
        for (i in 30..150 step 2) weight.add("$i kg")

        val ageAdapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, age)
        val heightAdapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, height)
        val weightAdapter = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, weight)

        nameDBHandler =
            NameDatabaseHelper(this.context!!)
        ageDBHandler =
            AgeDatabaseHelper(this.context!!)
        heightDBHandler =
            HeightDatabaseHelper(this.context!!)
        weightDBHandler =
            WeightDatabaseHelper(this.context!!)

        val nameFromDB = nameDBHandler!!.getName()
        val ageFromDB = ageDBHandler!!.getAge()
        val heightFromDB = heightDBHandler!!.getHeight()
        val weightFromDB = weightDBHandler!!.getWeight()

        ageAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        heightAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        weightAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        ageSpinner.adapter = ageAdapter
        heightSpinner.adapter = heightAdapter
        weightSpinner.adapter = weightAdapter

        if (nameFromDB != "") {
            // Name Exists, Set Name to nameFromDB
            profileName.text = nameFromDB

        }

        if (ageFromDB != "") {
            // Age Exists, Set Age to ageFromDB
            ageSpinner.setSelection(ageAdapter.getPosition(ageFromDB))
        }

        if (heightFromDB != "") {
            // Height Exists, Set Height to heightFromDB
            heightSpinner.setSelection(heightAdapter.getPosition(heightFromDB))
        }

        if (weightFromDB != "") {
            // Weight Exists, Set Weight to weightFromDB
            weightSpinner.setSelection(weightAdapter.getPosition(weightFromDB))
        }

        dotMenu.setOnClickListener {
            val popupMenu : PopupMenu = PopupMenu(this.context,dotMenu)
            popupMenu.menuInflater.inflate(R.menu.profile_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.opensource_menu -> {
                        // Basic Code
                        // Put actions here
                        val opensource = Intent(context, OssLicensesMenuActivity::class.java)
                        startActivity(opensource)
                    }
                }
                true
            }
            popupMenu.show()
        }

        profileName.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            val dialogView = layoutInflater.inflate(R.layout.activity_name_dialog, null)
            val nameToAdd = dialogView.findViewById<EditText>(R.id.nameToAdd)

            if (nameFromDB != "") {
                nameToAdd.setText(nameFromDB)
                if (nameFromDB != profileName.text) {
                    nameToAdd.setText(profileName.text)
                }
            }
            val addbtn = builder.setView(dialogView)
                .setPositiveButton("Set") { dialogInterface, i ->
                    if (nameFromDB != "") {
                        // Update Name
                        val result = nameDBHandler!!.updateName(nameToAdd.text.toString())
                        if (result) {
                            profileName.text = nameToAdd.text
                        }
                        else Toast.makeText(this.context, "Name Update Failed.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        // Add Name
                        var result = nameDBHandler!!.addName(nameToAdd.text.toString())
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

        nameEditBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this.context)
            val dialogView = layoutInflater.inflate(R.layout.activity_name_dialog, null)
            val nameToAdd = dialogView.findViewById<EditText>(R.id.nameToAdd)

            if (nameFromDB != "") {
                nameToAdd.setText(nameFromDB)
                if (nameFromDB != profileName.text) {
                    nameToAdd.setText(profileName.text)
                }
            }
            val addbtn = builder.setView(dialogView)
                .setPositiveButton("Set") { dialogInterface, i ->
                    if (nameFromDB != "") {
                        // Update Name
                        val result = nameDBHandler!!.updateName(nameToAdd.text.toString())
                        if (result) {
                            profileName.text = nameToAdd.text
                        }
                        else Toast.makeText(this.context, "Name Update Failed.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        // Add Name
                        var result = nameDBHandler!!.addName(nameToAdd.text.toString())
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
                    // 1. Find if data exists
                    // 2. If exists, update.
                    // 3. If it doesn't exist, add.
                     if (ageFromDB != "") {
                         ageDBHandler!!.updateAge(parent.getItemAtPosition(position).toString())
                     }
                    else {
                         ageDBHandler!!.addAge(parent.getItemAtPosition(position).toString())
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
                    if (heightFromDB != "") {
                        heightDBHandler!!.updateHeight(parent.getItemAtPosition(position).toString())
                    }
                    else {
                        heightDBHandler!!.addHeight(parent.getItemAtPosition(position).toString())
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
                    if (weightFromDB != "") {
                        weightDBHandler!!.updateWeight(parent.getItemAtPosition(position).toString())
                    }
                    else {
                        weightDBHandler!!.addWeight(parent.getItemAtPosition(position).toString())
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

    }


}
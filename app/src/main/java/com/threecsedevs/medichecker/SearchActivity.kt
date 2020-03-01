package com.threecsedevs.medichecker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : Fragment() {

    fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val drugList = arrayOf(
            "Acetaminophen", "Acetadryl", "Acetazolamide",
            "Acetaminophen-codeine", "INFANTS' Acetaminophen")

        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_dropdown_item_1line, drugList)

        drugInput.setAdapter(adapter)
        drugInput.threshold = 3

        // When Suggestion is clicked
        drugInput.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            Toast.makeText(this,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }

        return container?.inflate(R.layout.activity_search)
    }

    // After the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}

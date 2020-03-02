package com.threecsedevs.medichecker

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_search.*

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class SearchActivity : Fragment() {

    fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.activity_search)
    }

    // After the view is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var word = "Tami"
        testButton.setOnClickListener {
            var queue = Volley.newRequestQueue(this.context!!)
            val url = "https://rxnav.nlm.nih.gov/REST/spellingsuggestions.json?name="

            val name = "Tami"
            val stringrequest = object :
                StringRequest(Request.Method.GET, url + name, Response.Listener { response ->
                    println("Receiving Response : $response")
                    testText.text = response

                }, Response.ErrorListener { error ->
                    Log.d("ERROR", "Response Unsuccessful : $error")
                }) {
                    override fun getBodyContentType(): String {
                        return "application/json; charset=utf-8"
                    }
//                    override fun getBody(): ByteArray {
////                        return requestBody.toByteArray()
////                    }

            }
            Volley.newRequestQueue(context).add(stringrequest)

        }

//            val suggestions = SpellingCheck.getSpellingSuggestions(this.context!!, word) { testSuccess ->
//                if (testSuccess) {
//                    Toast.makeText(this.context!!, "Success!!!", Toast.LENGTH_SHORT).show()
//                }
//                else {
//                    Toast.makeText(this.context!!, "Failed...", Toast.LENGTH_SHORT).show()
//                }
//
//            }


        val drugList = arrayOf(
            "Acetaminophen", "Acetadryl", "Acetazolamide",
            "Acetaminophen-codeine", "INFANTS' Acetaminophen")

        val adapter = ArrayAdapter<String>(
            this.context!!, android.R.layout.simple_dropdown_item_1line, drugList)

        drugInput.setAdapter(adapter)
        drugInput.threshold = 3

        // When Suggestion is clicked
        drugInput.onItemClickListener = AdapterView.OnItemClickListener{
                parent, view, position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            Toast.makeText(activity?.applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }

    }

}

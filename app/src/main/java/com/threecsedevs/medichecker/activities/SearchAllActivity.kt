package com.threecsedevs.medichecker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher

import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search_popup.*
import java.util.*

import com.threecsedevs.medichecker.R
import org.json.JSONObject
import kotlinx.coroutines.*

import com.github.kittinunf.fuel.*
import com.github.kittinunf.result.Result
import org.json.JSONArray

class SearchAllActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_popup)

        var wordlist = mutableListOf<String>()

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordlist)

        resultListView.adapter = adapter


        drugInput.addTextChangedListener(object: TextWatcher {
            fun getSuggestions(s: String) : MutableList<String> {
                var suggestion_result = mutableListOf<String>()
                val a : JSONArray
                val url = "https://rxnav.nlm.nih.gov/REST/spellingsuggestions.json?name="

                val (request, response, result) = (url + s)
                    .httpGet()
                    .responseString()

                when (result) {
                    is Result.Failure -> {
                        val ex = result.getException()
                        println(ex)
                    }
                    is Result.Success -> {
                        val responseTest = JSONObject(result.get())
                        val b = responseTest.getJSONObject("suggestionGroup")
                        println(b)
                        a = if (b.get("suggestionList").toString() == "null") {
                            JSONArray()
                        } else {
                            b.getJSONObject("suggestionList").getJSONArray("suggestion")
                        }

                        val len = a.length()
                        for (i in 0 until len) {
                            print(" " + a[i].toString() + " ")
                            suggestion_result.add(a[i].toString())
                        }
                    }
                }

                return suggestion_result
            }
            override fun afterTextChanged(p0: Editable?) {
                val timer = Timer()
                timer.schedule(object: TimerTask() {
                    override fun run() {
                        GlobalScope.launch {
                            var suggestions : MutableList<String>
                            runBlocking {
                                if(p0 != null && !p0.toString().equals("")){
                                    var job = async {

                                        suggestions = getSuggestions(p0.toString())
//                                        delay(1200L)
                                        suggestions
                                    }
                                    var dataFromServer = job.await()
                                    runOnUiThread {
                                        adapter.clear() // Clear ListView
                                        for (i in dataFromServer) { adapter.add(i) }
                                        adapter = ArrayAdapter<String>(this@SearchAllActivity, android.R.layout.simple_list_item_1, dataFromServer)
                                        resultListView.adapter = adapter
                                        adapter.notifyDataSetChanged() // Update ListView to new List.
                                    }
                                }
                            }
                        }
                    }
                }, 500)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        drugInput.setOnEditorActionListener { v, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_DONE) {
               adapter.notifyDataSetChanged()
               true
           }
            else { false }
        }

        resultListView.setOnItemClickListener {parent, view, position, id ->
            var element = adapter.getItem(position) as String
            val intent = Intent()
            intent.putExtra("medicine", element)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }
}
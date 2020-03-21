package com.threecsedevs.medichecker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_search_popup.*
import java.util.*

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.threecsedevs.medichecker.R
import org.json.JSONObject
import kotlinx.coroutines.*

class SearchAllActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_popup)

        var wordlist = mutableListOf<String>()

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, wordlist)

        resultListView.adapter = adapter
//        drugInput.setAdapter(adapter)
//        drugInput.threshold = 1



        drugInput.addTextChangedListener(object: TextWatcher {
            fun getSuggestions(s: String) : MutableList<String> {
                var result = mutableListOf<String>()
                var queue = Volley.newRequestQueue(this@SearchAllActivity)
                val url = "https://rxnav.nlm.nih.gov/REST/spellingsuggestions.json?name="
                val stringRequest = object : StringRequest(Request.Method.GET, url + s, Response.Listener { response ->
                    val responseTest = JSONObject(response)
                    val a = responseTest.getJSONObject("suggestionGroup").getJSONObject("suggestionList").getJSONArray("suggestion")
                    if (a != null) {
                        val len = a.length()
                        println("len : $len")
                        for (i in 0 until len) {
                            print(" " + a[i].toString() + " ")
                            result.add(a[i].toString())
                        }
                    }
                }, Response.ErrorListener { error -> Log.d("ERROR", "Response Unsuccessful : $error") }){}

                queue.add(stringRequest)
                println("result : $result")
                return result
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
                                        delay(1200L)
                                        println("p0 : " + p0.toString())
                                        suggestions
                                    }
                                    var dataFromServer = job.await()
                                    runOnUiThread {
                                        adapter.clear() // Clear ListView
                                        println("suggestions : $dataFromServer")
                                        for (i in dataFromServer) { adapter.add(i) }
                                        adapter = ArrayAdapter<String>(this@SearchAllActivity, android.R.layout.simple_list_item_1, dataFromServer)
                                        // Adapter with empty string, but ListView works,(Do not know reason why)
                                        resultListView.adapter = adapter
                                        adapter.notifyDataSetChanged() // Update ListView to new List.
                                    }
                                }
                            }
                        }
                    }
                }, 1000)
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
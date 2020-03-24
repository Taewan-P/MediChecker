package com.threecsedevs.medichecker.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.threecsedevs.medichecker.R
import kotlinx.android.synthetic.main.activity_interaction_result.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class InteractionResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interaction_result)
        loadingCircle.visibility = View.VISIBLE
        safe.visibility = View.INVISIBLE
        warning.visibility = View.INVISIBLE
        var count = 0
        var result = mutableListOf<String>()
        var queue = Volley.newRequestQueue(this)
        val rxcuis = mutableListOf<String>()
        var drug = intent.getStringArrayListExtra("drugName")

        GlobalScope.launch {
            var job = async {
                for (name in drug){
                    var drugId = getrxcuis(name)
                    delay(2000L)
                    rxcuis.add(drugId)

                    count ++

                }
                rxcuis
            }
            var dataFromServer = job.await()


            var interURL = makeInteractionURL(dataFromServer)

            count ++

            val getInteractionRequest = object :
                StringRequest(Method.GET, interURL, Response.Listener { response ->
                    val responseTest = JSONObject(response)
                    if ( responseTest.length() > 2 ) {//상호작용 결과가 있을때는 배열 길이가 3임.
                        val a: Any? = responseTest.getJSONArray("fullInteractionTypeGroup").get(0) //GET source from drugbank only
                        val b: Any? = JSONObject(a.toString()).get("fullInteractionType")
                        val inter_len: Int = JSONArray(b.toString()).length() //상호작용 Description 을담고있는 배열의 길이. For 문 반복횟수.
                        for ( i in 0 until inter_len ) {
                            result.add(JSONObject(JSONObject(JSONArray(JSONObject(a.toString()).get("fullInteractionType").toString()).get(i).toString()).getJSONArray("interactionPair").get(0).toString()).get("description").toString())
                        }

                    } else {
                        result.add("Interaction between drugs not found")
                    }
                    count ++
                }, Response.ErrorListener { error ->
                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            queue.add(getInteractionRequest)
            delay(3000L)
            runOnUiThread {
                changeResultText(result)
                count ++
            }
        }
        count ++
    }
    fun changeResultText(result: MutableList<String>) {
        loadingCircle.visibility = View.GONE
        var text = ""
        if(result.contains("Interaction between drugs not found")){
            safe.visibility = View.VISIBLE
            resultTest.text = "Interaction between drugs not found"
        }
        else{
            warning.visibility = View.VISIBLE
            for (i in result){
                text += "\u2022 " + i
                text += "\n"
            }
            resultTest.text = text
        }
    }

    fun makeInteractionURL(rxcuis: MutableList<String>):String {
        var url = "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis="
        for (i in rxcuis){
            url += i
            url += "+"
        }
        url = url.dropLast(1)
        return url
    }

    fun makeRxcuiURL(name: String):String {
        var url = "https://rxnav.nlm.nih.gov/REST/rxcui.json?name="
        url+=name
        url+="&search=1"

        return url
    }

    fun getrxcuis(s: String): String{
        var queue = Volley.newRequestQueue(this)
        val url = makeRxcuiURL(s)
        var result: String
        result = ""
        val getRXCUIRequest = object : StringRequest(Request.Method.GET, url, Response.Listener { response ->
            runBlocking {
                var job = launch {
                    val responseTest = JSONObject(response)
                    responseTest.getJSONObject("idGroup").getJSONArray("rxnormId").get(0)
                            .apply {
                                result = toString()
                            }
                }
                job.join()
            }

        }, Response.ErrorListener { error ->
            println("Response Unsuccessful : $error") //This line should not be delete for debugging error
            result = "Server Response error. Please try later."
        }) {
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        queue.add(getRXCUIRequest)
        runBlocking {
            delay(3000L)
        }
        return result
    }
}
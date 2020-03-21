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
        val timer = Timer("schedule", true);
        setContentView(R.layout.activity_interaction_result)
        loadingCircle.visibility = View.VISIBLE
        var count = 0
        var result = ""
        var queue = Volley.newRequestQueue(this)
        val rxcuis = mutableListOf<String>()
        var drug = intent.getStringArrayListExtra("drugName")
//        var drugName = mutableListOf<String>(drug.toString())

        // TODO(): This for loop should by async
//        val deferred = GlobalScope.async {
//            async() {
//
//            }
//        }
        GlobalScope.launch {
            var job = async {
                for (name in drug){
                    var drugId = getrxcuis(name)
                    delay(2000L)
                    rxcuis.add(drugId)
                    println("Order : $count")
                    count ++
                    println("rxcui-for-loop : $rxcuis")
                }
                rxcuis
            }
            var dataFromServer = job.await()

            println("rxcuis.tostring = $dataFromServer")
            var interURL = makeInteractionURL(dataFromServer)
            println("Order : $count")
            count ++

            val getInteractionRequest = object :
                StringRequest(Method.GET, interURL, Response.Listener { response ->
                    println("Receiving Response : $response")
                    val responseTest = JSONObject(response)

                    if(responseTest.length() > 2){//상호작용 결과가 있을때는 배열 길이가 3임.
                        val a: Any? = responseTest.getJSONArray("fullInteractionTypeGroup").get(0)
                        val b: Any? =JSONObject(a.toString()).get("fullInteractionType")
                        val c: Any? = JSONArray(b.toString()).get(0) //fullInteractionTypeGroup - Array
                        val inter_len: Int? = JSONArray(b.toString()).length()
                        println("length of interaction : ${ inter_len.toString()}")
                        val drug1: Any? = JSONObject(c.toString()).getJSONArray("minConcept").get(0) //상호작용 약의 기본정보(이름)을 가져옴.
                        val drug1_name: Any? = JSONObject(drug1.toString()).get("name")
                        val drug2: Any? = JSONObject(c.toString()).getJSONArray("minConcept").get(1) //상호작용 약의 기본정보(이름)을 가져옴.
                        val drug2_name: Any? = JSONObject(drug2.toString()).get("name")
                        val e: Any? = JSONObject(c.toString()).getJSONArray("interactionPair").get(0)
                        val severity: Any? = JSONObject(e.toString()).get("severity")
                        val description: Any? = JSONObject(e.toString()).get("description")
                        val test: Any? = JSONObject((JSONObject(responseTest.getJSONArray("fullInteractionTypeGroup").get(0).toString()).getJSONArray("fullInteractionType").get(0)).toString()).get("comment")

                        println(drug1_name)
                        println(drug2_name)
                        println(e)


                        result = description.toString()
                        println("severity : ${severity.toString()}")
                        println("result : $result")
                    } else {
                        result = "Interaction between drugs not found"
                        println("result : $result")
                    }

//                    val drug1 = JSONArray(c.toString()).get(0)
//                    val d = JSONObject(b.toString()).get("interactionPair") // get serverity and description
                    println("Order : $count")
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
                println("Order : $count")
                count ++
            }
        }
        println("Order : $count")
        count ++

        println("result at InteractionResultActivity : $result")


    }
    fun changeResultText(result: String?) {
        loadingCircle.visibility = View.GONE
        resultTest.text = result
    }

    fun makeInteractionURL(rxcuis: MutableList<String>):String {
        var url = "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis="
        for (i in rxcuis){
            println(i)
            url += i
            url += "+"
        }
        url = url.dropLast(1)
        url+="&sources=ONCHigh"
        println(url)

        return url
    }

    fun makeRxcuiURL(name: String):String {
        var url = "https://rxnav.nlm.nih.gov/REST/rxcui.json?name="
        url+=name
        url+="&search=1"

        println(url)

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
                    val rxcui =
                        responseTest.getJSONObject("idGroup").getJSONArray("rxnormId").get(0)
                            .apply {
                                result = toString()
                            }
                    println("#1")
                }
                job.join()
            }

        }, Response.ErrorListener { error -> println("Response Unsuccessful : $error") }){
            override fun getBodyContentType(): String {
                return "application/json; charset=utf-8"
            }
        }

        queue.add(getRXCUIRequest)
        runBlocking {
            println("Wait for 3S")
            delay(3000L)
        }
        println("#2:  $result")
        return result
    }
}
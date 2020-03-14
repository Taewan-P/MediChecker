package com.threecsedevs.medichecker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search_popup.*
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
        var counter: Int = 1;
        addBtn.setOnClickListener {
            val newBtn : Button = Button(this.context)
            val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            newBtn.id = counter
            newBtn.layoutParams = params
            newBtn.text = "Input Medicine"
            newBtn.setOnClickListener {
                Toast.makeText(this.context, newBtn.id.toString(), Toast.LENGTH_SHORT).show()
                val intent = Intent(context, SearchAllActivity::class.java)
                startActivityForResult(intent, newBtn.id+100)
            }
            parentlayout.addView(newBtn)
            counter ++

        }
        deleteBtn.setOnClickListener {
            if(parentlayout.childCount > 1){
                parentlayout.removeView(parentlayout.getChildAt(parentlayout.childCount-1))
                counter --
            }

        }

        inputMedicine.setOnClickListener{
            val intent = Intent(context, SearchAllActivity::class.java)
            startActivityForResult(intent,100)
        }
        searchInterBtn.setOnClickListener{
//            val rxcuis = mutableListOf<String>("207106", "152923", "656659") //TESTING RXCUIS
            val drugName = mutableListOf<String>()
            val btn = Button(this.context)
            println("Counter starts from $counter")

            while (counter > 0){
                var target = parentlayout.getChildAt(counter - 1) as Button
                println(target.text)
                drugName.add(target.text.toString())
                counter--
            }
            println("drugName : " + drugName.toString())


            var queue = Volley.newRequestQueue(this.context!!)


            fun getrxcuis(s: String): String{ //s : 전달할 약의 이름, result : 함수 실행 결과를 담을 변수
                val url = makeRxcuiURL(s)
                var result: String = ""
                val getRXCUIRequest = object : StringRequest(Request.Method.GET, url, Response.Listener { response ->
                    val responseTest = JSONObject(response)
                    val rxcui = responseTest.getJSONObject("idGroup").getJSONArray("rxnormId").get(0)
                    result = rxcui.toString()
                }, Response.ErrorListener { error -> println("Response Unsuccessful : $error") }){
                    override fun getBodyContentType(): String {
                        return "application/json; charset=utf-8"
                    }
                }
                queue.add(getRXCUIRequest)
                return result
            }

            val rxcuis = mutableListOf<String>()

            for (name in drugName){
                rxcuis.add(getrxcuis(name))
            }

            println(rxcuis.toString())
            //@TODO : Coroutine 을 이용해 비동기처리 필요

            var interURL = makeInteractionURL(rxcuis)
            val getInteractionRequest = object :
                StringRequest(Request.Method.GET, interURL, Response.Listener { response ->
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


                        interactionResult.text = drug1_name.toString() + " / " + drug2_name.toString()
                        severity_grade.text = severity.toString()
                        desc.text = description.toString()
                    } else {
                        desc.text = "Interaction between drugs not found"
                    }


//                    val drug1 = JSONArray(c.toString()).get(0)
//                    val d = JSONObject(b.toString()).get("interactionPair") // get serverity and description


                }, Response.ErrorListener { error ->
                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            queue.add(getInteractionRequest)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    inputMedicine.text = data!!.getStringExtra("medicine").toString()
                }
                else -> {
                    var btn = parentlayout.getChildAt(requestCode - 100) as Button
                    btn.text = data!!.getStringExtra("medicine").toString()
                }
            }
        }
    }

    fun makeInteractionURL(rxcuis: MutableList<String>):String {
        var url = "https://rxnav.nlm.nih.gov/REST/interaction/list.json?rxcuis="
        for (i in rxcuis){
            println(i)
            url += i
            url+="+"
        }
        url.substring(0,url.length-1)
        url+="&sources=ONCHigh"
        println(url)

        return url
    }

    fun makeRxcuiURL(name: String):String {
        var url = "https://rxnav.nlm.nih.gov/REST/rxcui.json?name="
        url+=name
        url+="&search=1\""

        println(url)

        return url
    }

}

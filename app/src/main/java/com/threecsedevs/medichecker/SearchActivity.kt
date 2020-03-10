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
            val rxcuis = mutableListOf<String>("207106", "152923", "656659") //TESTING RXCUIS
            var url = makeURL(rxcuis)
            var queue = Volley.newRequestQueue(this.context!!)

            val stringrequest = object :
                StringRequest(Request.Method.GET, url, Response.Listener { response ->
                    println("Receiving Response : $response")
                    val responseTest = JSONObject(response)
                    val a = responseTest.getJSONArray("fullInteractionTypeGroup").get(0)
                    val b =JSONObject(a.toString()).get("fullInteractionType")
                    val c = JSONArray(b.toString()).get(0)
                    val drug1 = JSONObject(c.toString()).getJSONArray("minConcept").get(0) //상호작용 약의 기본정보(이름)을 가져옴.
                    val drug1_name = JSONObject(drug1.toString()).get("name")
                    val drug2 = JSONObject(c.toString()).getJSONArray("minConcept").get(1) //상호작용 약의 기본정보(이름)을 가져옴.
                    val drug2_name = JSONObject(drug2.toString()).get("name")
                    val e = JSONObject(c.toString()).getJSONArray("interactionPair").get(0)
                    val severity = JSONObject(e.toString()).get("severity")
                    val description = JSONObject(e.toString()).get("description")

//                    val drug1 = JSONArray(c.toString()).get(0)
//                    val d = JSONObject(b.toString()).get("interactionPair") // get serverity and description
                    println(drug1_name)
                    println(drug2_name)
                    println(e)


                    interactionResult.text = drug1_name.toString() + " / " + drug2_name.toString()
                    severity_grade.text = severity.toString()
                    desc.text = description.toString()

                }, Response.ErrorListener { error ->
                }) {
                override fun getBodyContentType(): String {
                    return "application/json; charset=utf-8"
                }
            }
            Volley.newRequestQueue(context).add(stringrequest)
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


//    //TEST CODE
//    fun searchInteraction(){
//        val rxcuis = mutableListOf<String>("207106", "152923", "656659")
//        getInteraction(rxcuis)
//    }
//
//    fun getInteraction(rxcuis: MutableList<String>) {
//        val url = makeURL(rxcuis)
//
//        val result =
//            URL(url).readText()
//        println(result)
//
//        val jsonres = JSONObject(result)
//        println(jsonres)
//        val response = jsonres.getJSONArray("fullInteractionTypeGroup")
//
//        println(response)
//
//    }

    fun makeURL(rxcuis: MutableList<String>):String {
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


}

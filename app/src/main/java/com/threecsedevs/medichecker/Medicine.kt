package com.threecsedevs.medichecker

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

//
//
// 코드 테스트용 파일!!!
//


fun main() {
   getDrugName("Tamiflu")
}

fun getDrugName(input: String) {
    val result =
        URL("https://rxnav.nlm.nih.gov/REST/spellingsuggestions.json?name=" + input).readText()
    println(result)

    val jsonres = JSONObject(result)
    println(jsonres)
    val response = jsonres.getJSONObject("suggestionGroup").getJSONObject("suggestionList").getJSONArray("suggestion")

    println(response)


}

//fun getName() {
//    val queue = Volley.newRequestQueue(this)
//    val url = "https://rxnav.nlm.nih.gov/REST/spellingsuggestions.json?name=Tamiflu"
////        val url = "http://my-json-feed"
//
//    val stringReq = StringRequest(Request.Method.GET, url,
//        Response.Listener<String> { response ->
//
//            var strResp = response.toString()
//            val jsonObj: JSONObject = JSONObject(strResp)
//            val jsonArray: JSONArray = jsonObj.getJSONArray("items")
//            var str_user: String = ""
//            for (i in 0 until jsonArray.length()) {
//                var jsonInner: JSONObject = jsonArray.getJSONObject(i)
//                str_user = str_user + "\n" + jsonInner.get("login")
//            }
////                textView!!.text = "response : $str_user "
//        },
//        Response.ErrorListener {
//            //                textView!!.text = "That didn't work!"
//            println("ERROR!")
//        })
//}
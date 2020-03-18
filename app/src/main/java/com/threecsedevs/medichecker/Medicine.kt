package com.threecsedevs.medichecker

import org.json.JSONObject
import java.net.URL

//
//
// 코드 테스트용 파일!!!
//


fun main() {
    val rxcuis = mutableListOf<String>("207106", "152923", "656659")
    getInteraction(rxcuis)
}

fun getInteraction(rxcuis: MutableList<String>) {
    val url = makeURL(rxcuis)

    val result =
        URL(url).readText()
    println(result)

    val jsonres = JSONObject(result)
    println(jsonres)
    val response = jsonres.getJSONArray("fullInteractionTypeGroup")

    println(response)

}

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

package com.threecsedevs.medichecker

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_interaction_result.*
import java.util.*

class InteractionResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val timer = Timer("schedule", true);
        setContentView(R.layout.activity_interaction_result)
        loadingCircle.visibility = View.VISIBLE
        var result = intent.getStringExtra("result")
        Handler().postDelayed({
            changeResultText(result)
        },1200)
        println("result at InteractionResultActivity : $result")
    }
    fun changeResultText(result: String?) {
        loadingCircle.visibility = View.GONE
        resultTest.text = result
    }
}
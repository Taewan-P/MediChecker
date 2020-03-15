package com.threecsedevs.medichecker

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_interaction_result.*

class InteractionResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interaction_result)
        loadingCircle.visibility = View.VISIBLE
        var result = intent.getStringExtra("result")
        println("result at InteractionResultActivity : $result")
        loadingCircle.visibility = View.GONE
        resultTest.text = result
    }
}
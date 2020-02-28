package com.threecsedevs.medichecker

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragment = SearchActivity()
        supportFragmentManager.beginTransaction().replace(R.id.HomePage, fragment, fragment.javaClass.simpleName).commit()
        bottomBar.setOnItemSelectedListener(object: OnItemSelectedListener {
            override fun onItemSelect(pos: Int) {
                when(pos) {
                    0 -> {
                        val fragment = HomeActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.HomePage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    1 -> {
                        val fragment = SearchActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.HomePage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    2 -> {
                        val fragment = ProfileActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.HomePage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    else -> {
                        val fragment = SearchActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.HomePage, fragment, fragment.javaClass.simpleName).commit()
                    }

                }

            }
        })
    }


}

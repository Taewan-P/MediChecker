package com.threecsedevs.medichecker.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.threecsedevs.medichecker.R
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fragment: Fragment =
            SearchActivity()
        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
        bottomBar.setOnItemSelectedListener(object: OnItemSelectedListener {
            override fun onItemSelect(pos: Int) {
                when(pos) {
                    0 -> {
                        val fragment =
                            HomeActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    1 -> {
                        val fragment =
                            SearchActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    2 -> {
                        val fragment =
                            ProfileActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    else -> {
                        val fragment =
                            SearchActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }

                }

            }
        })
    }


}

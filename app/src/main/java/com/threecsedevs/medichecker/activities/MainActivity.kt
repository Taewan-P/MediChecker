package com.threecsedevs.medichecker.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.threecsedevs.medichecker.R
import kotlinx.android.synthetic.main.activity_main.*
import me.ibrahimsn.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {
    var fragment : Fragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DisclaimerDialogFragment().show(supportFragmentManager,"disclaimer")
        var fragment: Fragment =
            SearchActivity()
        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
        bottomBar.setOnItemSelectedListener(object: OnItemSelectedListener {
            override fun onItemSelect(pos: Int) {
                when(pos) {
                    0 -> {
                        fragment = HomeActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    1 -> {
                        fragment = SearchActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    2 -> {
                        fragment = ProfileActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }
                    else -> {
                        fragment = SearchActivity()
                        supportFragmentManager.beginTransaction().replace(R.id.mainPage, fragment, fragment.javaClass.simpleName).commit()
                    }

                }

            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment = supportFragmentManager.findFragmentById(R.id.mainPage)
        if (fragment is SearchActivity){
            (fragment as SearchActivity).getResult(requestCode, resultCode, data)
        }
    }
}

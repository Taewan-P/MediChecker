package com.threecsedevs.medichecker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.threecsedevs.medichecker.R
import kotlinx.android.synthetic.main.activity_search.*


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
        // Change StatusBar Color
        this.activity?.window?.statusBarColor = ContextCompat.getColor(context!!,
            R.color.colorWhite
        )
        var view = this.activity?.window?.decorView
        view!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        var counter: Int = 1;
        addBtn.setOnClickListener {
            val newBtn : Button = Button(this.context)
            val params : LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { gravity = Gravity.CENTER_HORIZONTAL }
            newBtn.id = counter
            newBtn.layoutParams = params
            newBtn.text = "Input Medicine"
            newBtn.setBackgroundResource(R.drawable.darkblue_button_design)
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

            for (i in counter downTo 1) {
                var target = parentlayout.getChildAt(i - 1) as Button
                println(target.text)
                drugName.add(target.text.toString())
            }

            while ( "Input Medicine" in drugName){
                var idx = drugName.indexOf("Input Medicine")
                drugName.removeAt(idx)
                println("removed drugname : $drugName")
            }

            println("drugName : " + drugName.toString())

            val intent = Intent(context, InteractionResultActivity::class.java)
            intent.putStringArrayListExtra("drugName", ArrayList(drugName));
            startActivity(intent)

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



}
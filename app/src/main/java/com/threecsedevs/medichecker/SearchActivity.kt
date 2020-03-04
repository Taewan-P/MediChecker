package com.threecsedevs.medichecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
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
//                Toast.makeText(this.context, newBtn.text,Toast.LENGTH_SHORT).show()
                Toast.makeText(this.context, newBtn.id.toString(), Toast.LENGTH_SHORT).show()
                println(newBtn.id)
            }
            parentlayout.addView(newBtn)
            counter ++

        }
        deleteBtn.setOnClickListener {
//            val tmpbtn = counter as Button
            parentlayout.removeView(parentlayout.getChildAt(parentlayout.childCount-1))
            counter --
        }



    }

}

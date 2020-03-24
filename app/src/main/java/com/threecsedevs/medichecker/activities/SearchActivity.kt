package com.threecsedevs.medichecker.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.threecsedevs.medichecker.R
import com.threecsedevs.medichecker.input_medicine.InputMedicineListViewAdapter
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : Fragment() {
    var adapter : InputMedicineListViewAdapter? = null
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
        var iList = mutableListOf<String>()
        adapter = InputMedicineListViewAdapter(this.context!!, R.layout.input_medicine_item, iList)
        inputList.adapter = adapter


        addBtn.setOnClickListener {
            if (iList.size < 4) {
                (inputList.adapter as InputMedicineListViewAdapter).add("Input Medicine")
                (inputList.adapter as InputMedicineListViewAdapter).notifyDataSetChanged()
            }
            else {
                Toast.makeText(this.context, "You can add up to 4 medicines.", Toast.LENGTH_SHORT).show()
            }
        }

        searchInterBtn.setOnClickListener{
//            val rxcuis = mutableListOf<String>("207106", "152923", "656659") //TESTING RXCUIS
            val drugName = mutableListOf<String>()

            for (i in iList.size downTo 1) {
                var target = inputList.adapter.getItem(i - 1)
                drugName.add(target.toString())
            }

            while ( "Input Medicine" in drugName){
                var idx = drugName.indexOf("Input Medicine")
                drugName.removeAt(idx)
            }

            if( drugName.size < 2 ){
                var toast = Toast.makeText(this.context, "Please input medicine name", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                val intent = Intent(context, InteractionResultActivity::class.java)
                intent.putStringArrayListExtra("drugName", ArrayList(drugName));
                startActivity(intent)
            }

        }

    }

    fun getResult(requestCode: Int, resultCode: Int, data: Intent?) {
        adapter!!.setResult(requestCode, resultCode, data)
    }

}
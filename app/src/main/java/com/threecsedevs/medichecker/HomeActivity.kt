package com.threecsedevs.medichecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : Fragment() {

    fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.activity_home)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var mlist = mutableListOf<Medicine>()

        mlist.add(Medicine("Tyrenol", morning = true, lunch = true, dinner = true))
        mlist.add(Medicine("Tamiflu", morning = true, lunch = false, dinner = true))
        mlist.add(Medicine("Ibuprophen", morning = false, lunch = false, dinner = false))

        medicineList.adapter = ListViewAdapter(this.context!!, R.layout.medicine_item, mlist)

    }
}

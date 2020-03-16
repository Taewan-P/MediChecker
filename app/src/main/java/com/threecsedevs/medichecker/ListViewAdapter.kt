package com.threecsedevs.medichecker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ListViewAdapter(context: Context, var resource: Int ,var items: List<Medicine> ) : ArrayAdapter<Medicine>(context, resource, items){
    override fun getView(p0: Int, convertView: View?, p2: ViewGroup): View {
        TODO("Not yet implemented")
    }


    override fun getItem(p0: Int): Medicine? {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        TODO("Not yet implemented")
    }
}
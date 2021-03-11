package com.example.lab07

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class LocationListAdapter(val context: Context, val locations: List<Location>) : BaseAdapter()  {

    override fun getCount(): Int = locations.count()

    override fun getItem(position: Int): Any = locations[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null)
            view = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.location_list_item, parent, false)

        view!!.let {
            val location = locations[position]
            it.findViewById<TextView>(R.id.latitude).text = "широта = " +
                    location.latitude.toString()
            it.findViewById<TextView>(R.id.longitude).text = "долгота = " +
                    location.longitude.toString()
        }

        return view
    }
}
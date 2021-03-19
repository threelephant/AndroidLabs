package com.example.lab08

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AnswerAdapter(private val answersList: List<Int>, val action: (Int) -> Unit)
    : RecyclerView.Adapter<AnswerAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.my_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val titleView = holder.view.findViewById<TextView>(R.id.text)
        titleView.text = answersList[position].toString()
        titleView.setOnClickListener {
            action(position)
        }
    }

    override fun getItemCount(): Int {
        return answersList.size
    }
}
package com.example.lab04

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class ExpressionAdapter(private val expressionList: IntArray,
                        private val action: (Int) -> Unit) : RecyclerView.Adapter<ExpressionAdapter.ViewHolder>() {

    class ViewHolder( val view: View, val action: (Int) -> Unit) : RecyclerView.ViewHolder(view), View.OnClickListener{
        init {
            val listItem = view.findViewById<ConstraintLayout>(R.id.answerItem)
            listItem.setOnClickListener(this)
        }

        var index: Int = -1

        override fun onClick(v: View?) {
            action(index)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.answer_item_layout, parent, false)
        return ViewHolder(view, action)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val answerTextView = holder.view.findViewById<TextView>(R.id.answerTextView)
        answerTextView.text = expressionList[position].toString()
        holder.index = position
    }

    override fun getItemCount(): Int = expressionList.size
}
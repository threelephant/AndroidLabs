package com.example.lab08

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView

class LeftFragment : Fragment() {

    private lateinit var mainContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_left, container, false)

        val listOptions = view.findViewById<ListView>(R.id.operation_list)
        listOptions.adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1,
                arrayOf("Сложение", "Вычитание", "Умножение"))
        listOptions.setOnItemClickListener { parent, view, operation, id ->
            (mainContext as OnDataListener).onData(operation)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }
}
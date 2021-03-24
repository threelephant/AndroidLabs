package com.example.lab09

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.fragment.app.Fragment

class ProjectListFragment(
        private val projectList: MutableList<Map<String, String>> = arrayListOf()
) : Fragment() {

    private lateinit var projectInfoPresenter: OnProjectListener

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_projects_list, container, false)
        val listOptions = view.findViewById<ListView>(R.id.project_list)
        listOptions.adapter = SimpleAdapter(
                view.context,
                projectList,
                R.layout.projects_list_item,
                arrayOf("title", "description"),
                intArrayOf(R.id.projectTitle, R.id.projectDescription)
        )
        listOptions.setOnItemClickListener { parent, view, position, id ->
            projectInfoPresenter.onProjectData(position)
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        projectInfoPresenter = context as OnProjectListener
    }
}
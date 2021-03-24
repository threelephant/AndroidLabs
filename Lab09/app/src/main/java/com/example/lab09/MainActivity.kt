package com.example.lab09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.FragmentTransaction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(), OnProjectListener {

    private val projectsList: MutableList<Project> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialize()
    }

    private fun initialize() {
        val searchTextView: EditText = findViewById(R.id.searchTextView)
        val searchButton: ImageButton = findViewById(R.id.searchButton)

        searchButton.setOnClickListener {
            val searchQuery = searchTextView.text.toString() + " in:readme"
            GlobalScope.launch {
                val json = getJsonObject(searchQuery)
                val projects = getProjectList(json)

                MainScope().launch {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frame_projects_list, ProjectListFragment(projects))
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.frame_projects_list, ProjectListFragment())
            .add(R.id.frame_project_info, ProjectInfoFragment())
            .commit()
    }

    private fun getProjectList(json: JSONObject): MutableList<Map<String, String>> {
        val projects = mutableListOf<Map<String, String>>()
        json.getJSONArray("items").let { array ->
            projectsList.clear()
            for (i in 0 until array.length()) {
                array.getJSONObject(i).let { item ->
                    projects.add(
                        hashMapOf(
                            "title" to item.getString("full_name"),
                            "description" to item.getString("description"),
                        )
                    )
                    projectsList.add(Project(item))
                }
            }
        }
        return projects
    }

    private fun getJsonObject(searchQuery: String): JSONObject {
        val t =
            URL("https://api.github.com/search/repositories?q=$searchQuery&per_page=10")
                    .readText()
        return JSONObject(t)
    }

    override fun onProjectData(index: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_project_info, ProjectInfoFragment(projectsList[index]))
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit()
    }
}
package com.example.lab09

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.net.URL

class ProjectInfoFragment(
        private val project: Project = Project()
) : Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project_info, container, false)

        if (project.avatarURL != "") {
            GlobalScope.launch {
                val buf = URL(project.avatarURL).readBytes()
                val bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.size);
                MainScope().launch{
                    view.findViewById<ImageView>(R.id.imageView).setImageBitmap(bitmap)
                }
            }
        }

        view.findViewById<TextView>(R.id.name).text = "Name: ${project.name}"
        view.findViewById<TextView>(R.id.description).text = "Description: ${project.description}"
        view.findViewById<TextView>(R.id.url).text = project.URL
        view.findViewById<TextView>(R.id.watchersCount).text = "Watches: ${project.watchersCount}"
        view.findViewById<TextView>(R.id.size).text = "Size: ${project.size}"
        view.findViewById<TextView>(R.id.createdAt).text = "Created at: ${project.createdAt}"
        view.findViewById<TextView>(R.id.updatedAt).text = "Last update at: ${project.updatedAt}"
        view.findViewById<TextView>(R.id.license).text = "License: ${project.license}"

        return view
    }
}
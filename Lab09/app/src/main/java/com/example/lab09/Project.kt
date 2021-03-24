package com.example.lab09

import org.json.JSONObject

class Project(
        var name: String = "",
        var avatarURL: String = "",
        var description: String = "",
        var URL: String = "",
        var watchersCount: Int = 0,
        var size: Int = 0,
        var language: String = "",
        var createdAt: String = "",
        var updatedAt: String = "",
        var license: String = ""
) {
        constructor(json: JSONObject) : this() {
                name = json.getString("full_name")
                avatarURL = json.getJSONObject("owner").getString("avatar_url")
                description = json.getString("description")
                URL = json.getString("html_url")
                watchersCount = json.getInt("watchers")
                size = json.getInt("size")
                language = json.getString("language")
                createdAt = json.getString("created_at")
                updatedAt = json.getString("updated_at")
                updatedAt = json.getString("updated_at")
                json.optJSONObject("license")?.let {
                        license = it.getString("name")
                }
        }
}
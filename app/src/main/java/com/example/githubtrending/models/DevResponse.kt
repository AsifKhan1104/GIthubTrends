package com.example.githubtrending.models

data class DevResponse(
    val avatar: String,
    val name: String,
    val repo: Repo,
    val sponsorUrl: String,
    val url: String,
    val username: String
)

data class Repo(
    val description: String,
    val name: String,
    val url: String
)
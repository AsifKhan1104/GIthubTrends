package com.example.githubtrending.networking

import com.example.githubtrending.models.DevResponse
import com.example.githubtrending.models.RepoResponse
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @get:GET("developers")
    val trendingDevList: Call<List<DevResponse?>?>?

    @get:GET("repositories")
    val trendingRepoList: Call<List<RepoResponse?>?>?
}
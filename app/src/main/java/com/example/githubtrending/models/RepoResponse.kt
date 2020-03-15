package com.example.githubtrending.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RepoResponse(
    val author: String,
    val avatar: String,
    val builtBy: List<BuiltBy>,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String?,
    val languageColor: String?,
    val name: String,
    val stars: Int,
    val url: String
) : Parcelable

@Parcelize
data class BuiltBy(
    val avatar: String,
    val href: String,
    val username: String
) : Parcelable
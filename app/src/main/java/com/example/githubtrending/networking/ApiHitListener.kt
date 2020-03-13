package com.example.githubtrending.networking

interface ApiHitListener {
    fun onError(str: String?, i: Int)
    fun onSuccessReponse(obj: Any?, i: Int)
}
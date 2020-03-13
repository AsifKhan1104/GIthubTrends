package com.example.githubtrending.networking

import androidx.lifecycle.MutableLiveData
import com.example.githubtrending.models.DevResponse
import com.example.githubtrending.models.RepoResponse
import com.example.githubtrending.utils.AppConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {
    private val api = RetrofitService.INSTANCE.createService(Api::class.java) as Api
    fun getTrendingRepos(apiHitListener: ApiHitListener): MutableLiveData<List<RepoResponse>>? {
        api.trendingRepoList!!
            .enqueue(object : Callback<List<RepoResponse?>?> {
                override fun onResponse(
                    call: Call<List<RepoResponse?>?>,
                    response: Response<List<RepoResponse?>?>
                ) {
                    apiHitListener.onSuccessReponse(
                        response.body(),
                        AppConstants.repoApiId
                    )
                }

                override fun onFailure(
                    call: Call<List<RepoResponse?>?>,
                    t: Throwable
                ) {
                    apiHitListener.onError(t.toString(), AppConstants.repoApiId)
                }
            })
        return null
    }

    fun getTrendingDevs(apiHitListener: ApiHitListener): MutableLiveData<List<DevResponse>>? {
        api.trendingDevList!!
            .enqueue(object : Callback<List<DevResponse?>?> {
                override fun onResponse(
                    call: Call<List<DevResponse?>?>,
                    response: Response<List<DevResponse?>?>
                ) {
                    apiHitListener.onSuccessReponse(
                        response.body(),
                        AppConstants.devApiId
                    )
                }

                override fun onFailure(
                    call: Call<List<DevResponse?>?>,
                    t: Throwable
                ) {
                    apiHitListener.onError(t.toString(), AppConstants.devApiId)
                }
            })
        return null
    }

    companion object {
        private var repository: Repository? = null
        @JvmStatic
        val instance: Repository?
            get() {
                if (repository == null) {
                    repository =
                        Repository()
                }
                return repository
            }
    }
}
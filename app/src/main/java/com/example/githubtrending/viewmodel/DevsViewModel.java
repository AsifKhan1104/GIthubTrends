package com.example.githubtrending.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.githubtrending.models.DevResponse;
import com.example.githubtrending.networking.ApiHitListener;
import com.example.githubtrending.networking.Repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class DevsViewModel extends ViewModel implements ApiHitListener {
    @NotNull
    public MutableLiveData<List<DevResponse>> devsLiveData = new MutableLiveData<>();
    @NotNull
    public Repository repo;

    public final void init() {
        repo = Repository.getInstance();
    }

    public final void apiCall() {
        repo.getTrendingDevs( this);
    }

    public void onSuccessReponse(@Nullable Object response, int apiId) {
        if (response != null) {
            this.devsLiveData.setValue((List) response);
            return;
        }
    }

    public void onError(@Nullable String networkError, int apiId) {
        this.devsLiveData.setValue(null);
    }
}

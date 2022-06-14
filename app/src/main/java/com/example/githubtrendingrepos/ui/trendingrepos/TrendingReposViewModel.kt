package com.example.githubtrendingrepos.ui.trendingrepos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubtrendingrepos.data.models.TrendingRepoModel
import com.example.githubtrendingrepos.data.services.BaseResponse
import com.example.githubtrendingrepos.data.services.RepositorySDK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingReposViewModel @Inject constructor(
    private val repositorySDK: RepositorySDK
) : ViewModel() {
    private val _trendingReposData = MutableLiveData<BaseResponse<ArrayList<TrendingRepoModel>>>()
    val trendingReposData : LiveData<BaseResponse<ArrayList<TrendingRepoModel>>>
    get() = _trendingReposData

    init {
        getTrendingReposData()
    }

    private fun getTrendingReposData(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher){
            _trendingReposData.postValue(BaseResponse.Loading())
            kotlin.runCatching {
                repositorySDK.getTrendingReposData()
            }.onSuccess {
                _trendingReposData.postValue(BaseResponse.Success(it))
            }.onFailure {
                _trendingReposData.postValue(BaseResponse.Error(it.message))
            }
        }
    }
}
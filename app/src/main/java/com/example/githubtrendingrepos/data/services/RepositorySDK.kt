package com.example.githubtrendingrepos.data.services

import com.example.githubtrendingrepos.data.models.TrendingRepoModel
import com.example.githubtrendingrepos.ui.utils.Constants
import io.ktor.client.request.*
import kotlin.jvm.Throws

class RepositorySDK(private val remoteService: RemoteService) {

    @Throws(Exception::class)
    suspend fun getTrendingReposData():ArrayList<TrendingRepoModel>{
        return remoteService.httpClient.get {
            url(remoteService.getUrl(Constants.baseUrl))
            headers {
                remoteService.addHeaders(this)
            }
        }
    }
}
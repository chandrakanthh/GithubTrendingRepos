package com.example.githubtrendingrepos.data.services

sealed class BaseResponse<T>(
    val data : T? = null,
    val message : String? = null
){
    class Loading<T> : BaseResponse<T>()
    class Success<T>(data: T?) : BaseResponse<T>(data)
    class Error<T>(message: String?, data: T? = null) : BaseResponse<T>(data,message)
}

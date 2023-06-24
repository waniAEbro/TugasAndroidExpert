package com.waniaebro.core.data.source.remote.retrofit

sealed class ResultResponse<out R> {
    data class Success<out T>(val data: T) : ResultResponse<T>()
    data class Error(val message: String) : ResultResponse<Nothing>()
    object Loading : ResultResponse<Nothing>()
    object Empty : ResultResponse<Nothing>()
}

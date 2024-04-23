package com.brandyodhiambo.poempulse.utils


sealed class NetworkResult<T>(
    val data: T? = null,
    val errorCode: Int? = null,
    val errorMessage: String? = null,
) {
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(errorCode: Int, errorMessage: String?, data: T? = null) :
        NetworkResult<T>(data = data, errorCode = errorCode, errorMessage = errorMessage)
}


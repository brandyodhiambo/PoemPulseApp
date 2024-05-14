/*
 * Copyright (C)2024 Brandy Odhiambo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

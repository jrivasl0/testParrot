package com.rivas.testparrot.repository

import android.content.Context
import com.rivas.testparrot.AndroidApp
import com.rivas.testparrot.R
import com.rivas.testparrot.api.ApiService
import com.rivas.testparrot.api.model.*
import com.rivas.testparrot.api.response.ApiErrorResponse
import com.rivas.testparrot.api.response.ApiSuccessResponse
import com.rivas.testparrot.utils.Preferences
import com.rivas.testparrot.utils.extensions.toApiException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

interface Repository {

    sealed class ApiResult<out T : Any> {
        data class Success<out T : Any>(val data: T) : ApiResult<T>()
        data class Error(val exception: Exception) : ApiResult<Nothing>()
    }

    suspend fun createToken(user: UserObj): ApiResult<TokenResponse>
    suspend fun refreshToken(token: TokenResponse): ApiResult<TokenResponse>
    suspend fun validateToken(): ApiResult<StringResponse>
    suspend fun getStores(): ApiResult<StoreResponse>
    suspend fun getProducts(): ApiResult<ProductResponse>
    suspend fun updateProduct(id: String, availability: Availability): ApiResult<ProductResponse>

    class Network @Inject constructor(private val apiService: ApiService) : Repository {

        @Inject
        lateinit var context: Context

        override suspend fun createToken(user: UserObj) = withContext(Dispatchers.Default) {
            when (val apiResult = apiService.createTokenAsync(user).await()) {
                is ApiSuccessResponse ->
                    ApiResult.Success(apiResult.data)
                is ApiErrorResponse ->
                    ApiResult.Error(apiResult.errorMessage.toApiException())
                else ->
                    ApiResult.Error(context.getString(R.string.error_conectivity_message).toApiException())
            }
        }

        override suspend fun refreshToken(token: TokenResponse) = withContext(Dispatchers.Default) {
            when (val apiResult = apiService.refreshTokenAsync(token).await()) {
                is ApiSuccessResponse ->
                    ApiResult.Success(apiResult.data)
                is ApiErrorResponse ->
                    ApiResult.Error(apiResult.errorMessage.toApiException())
                else ->
                    ApiResult.Error(context.getString(R.string.error_conectivity_message).toApiException())
            }
        }

        override suspend fun validateToken() = withContext(Dispatchers.Default) {
            when (val apiResult = apiService.validateTokenAsync().await()) {
                is ApiSuccessResponse ->
                    ApiResult.Success(apiResult.data)
                is ApiErrorResponse ->
                    ApiResult.Error(apiResult.errorMessage.toApiException())
                else ->
                    ApiResult.Error(context.getString(R.string.error_conectivity_message).toApiException())
            }
        }

        override suspend fun getStores() = withContext(Dispatchers.Default) {
            when (val apiResult = apiService.getStoresAsync().await()) {
                is ApiSuccessResponse ->
                    ApiResult.Success(apiResult.data)
                is ApiErrorResponse ->
                    ApiResult.Error(apiResult.errorMessage.toApiException())
                else ->
                    ApiResult.Error(context.getString(R.string.error_conectivity_message).toApiException())
            }
        }

        override suspend fun getProducts() = withContext(Dispatchers.Default) {
            when (val apiResult = apiService.getProductsAsync(Preferences.getData(Preferences.STORE_ID,AndroidApp.appContext)!!).await()) {
                is ApiSuccessResponse ->
                    ApiResult.Success(apiResult.data)
                is ApiErrorResponse ->
                    ApiResult.Error(apiResult.errorMessage.toApiException())
                else ->
                    ApiResult.Error(context.getString(R.string.error_conectivity_message).toApiException())
            }
        }

        override suspend fun updateProduct(id: String, availability: Availability) = withContext(Dispatchers.Default) {
            when (val apiResult = apiService.updateProductAsync(id, availability).await()) {
                is ApiSuccessResponse ->
                    ApiResult.Success(apiResult.data)
                is ApiErrorResponse ->
                    ApiResult.Error(apiResult.errorMessage.toApiException())
                else ->
                    ApiResult.Error(context.getString(R.string.error_conectivity_message).toApiException())
            }
        }
    }
}
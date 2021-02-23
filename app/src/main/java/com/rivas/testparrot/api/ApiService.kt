package com.rivas.testparrot.api

import com.rivas.testparrot.api.model.*
import com.rivas.testparrot.api.response.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {
    @POST("auth/token")
    fun createTokenAsync(@Body userObj: UserObj): Deferred<ApiResponse<TokenResponse>>

    @GET("auth/token/test")
    fun validateTokenAsync(): Deferred<ApiResponse<StringResponse>>

    @POST("auth/token/refresh")
    fun refreshTokenAsync(@Body token: TokenResponse): Deferred<ApiResponse<TokenResponse>>

    @GET("v1/users/me")
    fun getStoresAsync(): Deferred<ApiResponse<StoreResponse>>

    @GET("v1/products")
    fun getProductsAsync(@Query("store", encoded = true) storeId: String): Deferred<ApiResponse<ProductResponse>>

    @PUT("v1/products/{id}/availability")
    fun updateProductAsync(@Path("id") id: String, @Body availability: Availability): Deferred<ApiResponse<ProductResponse>>
}
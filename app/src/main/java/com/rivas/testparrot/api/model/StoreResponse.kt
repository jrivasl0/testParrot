package com.rivas.testparrot.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StoreResponse(
    @SerializedName("status") @Expose val status: String,
    @SerializedName("result") @Expose val result: ResultData,
) : Parcelable

@Parcelize
data class ResultData(
    @SerializedName("uuid") @Expose val uuid: String,
    @SerializedName("email") @Expose val email: String,
    @SerializedName("username") @Expose val username: String,
    @SerializedName("stores") @Expose val stores: List<Store>,
) : Parcelable

@Parcelize
data class Store(
    @SerializedName("uuid") @Expose val uuid: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("availabilityState") @Expose val availabilityState: String,
) : Parcelable
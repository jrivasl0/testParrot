package com.rivas.testparrot.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TokenResponse(
    @SerializedName("refresh") @Expose val refresh: String,
    @SerializedName("access") @Expose val access: String,
) : Parcelable
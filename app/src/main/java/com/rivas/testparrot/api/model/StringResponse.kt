package com.rivas.testparrot.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StringResponse(
    @SerializedName("status") @Expose val status: String,
) : Parcelable
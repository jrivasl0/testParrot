package com.rivas.testparrot.api.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductResponse(
    @SerializedName("status") @Expose val status: String,
    @SerializedName("results") @Expose val result: List<Product>,
    @SerializedName("result") @Expose val resultSingle: Product,
) : Parcelable

@Parcelize
data class Product(
    @SerializedName("uuid") @Expose val uuid: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("description") @Expose val description: String,
    @SerializedName("imageUrl") @Expose val imageUrl: String,
    @SerializedName("legacyId") @Expose val legacyId: String,
    @SerializedName("price") @Expose val price: String,
    @SerializedName("alcoholCount") @Expose val alcoholCount: Int,
    @SerializedName("soldAlone") @Expose val soldAlone: Boolean,
    @SerializedName("availability") @Expose val availability: String,
    @SerializedName("category") @Expose val category: Category,
) : Parcelable

@Parcelize
data class Category(
    @SerializedName("uuid") @Expose val uuid: String,
    @SerializedName("name") @Expose val name: String,
    @SerializedName("sortPosition") @Expose val sortPosition: Int,
) : Parcelable
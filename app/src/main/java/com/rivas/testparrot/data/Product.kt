package com.rivas.testparrot.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String,
    @ColumnInfo(name = "legacyId") val legacyId: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "alcoholCount") val alcoholCount: Int,
    @ColumnInfo(name = "soldAlone") val soldAlone: Boolean,
    @ColumnInfo(name = "availability") var availability: String,
    @ColumnInfo(name = "categoryId") val categoryId: String,
)
package com.rivas.testparrot.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "sortPosition") val sortPosition: Int,
    @ColumnInfo(name = "items") val items: Int
)
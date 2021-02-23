package com.rivas.testparrot.repository.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rivas.testparrot.data.Category
import com.rivas.testparrot.data.CategoryDao
import com.rivas.testparrot.data.Product
import com.rivas.testparrot.data.ProductDao

@Database(entities = [Product::class, Category::class], version = TestDataBase.VERSION, exportSchema = false)
abstract class TestDataBase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val categoryDao: CategoryDao

    companion object {
        const val VERSION = 3
    }
}
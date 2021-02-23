package com.rivas.testparrot.di

import android.app.Application
import androidx.room.Room
import com.rivas.testparrot.AndroidApp
import com.rivas.testparrot.data.CategoryDao
import com.rivas.testparrot.data.ProductDao
import com.rivas.testparrot.repository.room.TestDataBase
import com.rivas.testparrot.repository.room.category.CategoryDataSource
import com.rivas.testparrot.repository.room.category.CategoryRepository
import com.rivas.testparrot.repository.room.product.ProductDataSource
import com.rivas.testparrot.repository.room.product.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    private val demoDatabase: TestDataBase =
        Room.databaseBuilder(AndroidApp.appContext, TestDataBase::class.java, "test-parrot.bd").build()

    @Singleton
    @Provides
    fun providesRoomDatabase(): TestDataBase {
        return demoDatabase
    }

    @Singleton
    @Provides
    fun providesProductDao(demoDatabase: TestDataBase): ProductDao {
        return demoDatabase.productDao
    }

    @Singleton
    @Provides
    fun productRepository(productDao: ProductDao): ProductRepository {
        return ProductDataSource(productDao)
    }

    @Singleton
    @Provides
    fun providesCategoryDao(demoDatabase: TestDataBase): CategoryDao {
        return demoDatabase.categoryDao
    }

    @Singleton
    @Provides
    fun categoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryDataSource(categoryDao)
    }

}
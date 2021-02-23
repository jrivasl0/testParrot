package com.rivas.testparrot.data

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface ProductDao {
    @Query("SELECT * FROM Product WHERE categoryId=:id")
    fun findByCategory(id: String): LiveData<List<Product>>

    @Query("SELECT * FROM Product")
    fun findAll(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Product?): Long

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product?): Int
}
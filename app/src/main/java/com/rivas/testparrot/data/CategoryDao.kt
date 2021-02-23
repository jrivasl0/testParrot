package com.rivas.testparrot.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun findAll(): LiveData<List<Category>>

    @Query("SELECT * FROM Category WHERE uuid =:id")
    fun findById(id: String): LiveData<Category?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Category?): Long

    @Delete
    fun delete(product: Category?): Int
}
package com.rivas.testparrot.repository.room.category

import androidx.lifecycle.LiveData
import com.rivas.testparrot.data.Category

interface CategoryRepository {
    fun findAll(): LiveData<List<Category>>
    fun findById(id: String): LiveData<Category?>?
    fun insert(category: Category?): Long
    fun delete(category: Category?): Int
}
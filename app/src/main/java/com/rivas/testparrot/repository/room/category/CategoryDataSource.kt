package com.rivas.testparrot.repository.room.category

import androidx.lifecycle.LiveData
import com.rivas.testparrot.data.Category
import com.rivas.testparrot.data.CategoryDao
import com.rivas.testparrot.data.Product
import javax.inject.Inject

class CategoryDataSource @Inject constructor(private var categoryDao: CategoryDao) : CategoryRepository{

    override fun findAll(): LiveData<List<Category>> {
        return categoryDao.findAll()
    }

    override fun findById(id: String): LiveData<Category?>? {
        return categoryDao.findById(id)
    }

    override fun insert(category: Category?): Long {
        return categoryDao.insert(category)
    }

    override fun delete(category: Category?): Int {
        return categoryDao.delete(category)
    }
}
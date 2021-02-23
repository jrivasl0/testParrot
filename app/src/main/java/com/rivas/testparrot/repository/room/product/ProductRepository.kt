package com.rivas.testparrot.repository.room.product

import androidx.lifecycle.LiveData
import com.rivas.testparrot.data.Product

interface ProductRepository {
    fun findByCategory(id: String): LiveData<List<Product>>
    fun findAll(): LiveData<List<Product>>
    fun insert(product: Product?): Long
    fun delete(product: Product?): Int
    fun update(product: Product)
}
package com.rivas.testparrot.repository.room.product

import androidx.lifecycle.LiveData
import com.rivas.testparrot.data.Product
import com.rivas.testparrot.data.ProductDao
import javax.inject.Inject


class ProductDataSource@Inject constructor(private var productDao: ProductDao) : ProductRepository{

    override fun findByCategory(id: String): LiveData<List<Product>> {
        return productDao.findByCategory(id)
    }

    override fun findAll(): LiveData<List<Product>> {
        return productDao.findAll()
    }

    override fun insert(product: Product?): Long {
        return productDao.insert(product)
    }

    override fun delete(product: Product?): Int {
        return productDao.delete(product)
    }

    override fun update(product: Product) {
        productDao.update(product)
    }
}
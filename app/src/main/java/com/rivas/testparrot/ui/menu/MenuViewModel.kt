package com.rivas.testparrot.ui.menu

import androidx.lifecycle.MutableLiveData
import com.rivas.testparrot.AndroidApp
import com.rivas.testparrot.api.model.Availability
import com.rivas.testparrot.api.model.ProductResponse
import com.rivas.testparrot.api.model.StoreResponse
import com.rivas.testparrot.core.CoroutinesViewModel
import com.rivas.testparrot.data.Category
import com.rivas.testparrot.data.Product
import com.rivas.testparrot.repository.Repository
import com.rivas.testparrot.repository.room.category.CategoryRepository
import com.rivas.testparrot.repository.room.product.ProductRepository
import com.rivas.testparrot.utils.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MenuViewModel(
    private val repository: Repository,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : CoroutinesViewModel() {

    val storeName = MutableLiveData<String>()

    private fun getStores() = uiScope.launch {
        _loading.value = true
        when (val store = repository.getStores()) {
            is Repository.ApiResult.Success<StoreResponse> -> {
                storeName.value = store.data.result.stores[0].name
                Preferences.putData(
                    Preferences.STORE_NAME,
                    store.data.result.stores[0].name,
                    AndroidApp.appContext
                )
                Preferences.putData(
                    Preferences.STORE_ID,
                    store.data.result.stores[0].uuid,
                    AndroidApp.appContext
                )
                getProducts()
            }
            is Repository.ApiResult.Error -> {
                _error.value = store.exception
            }
        }
        _loading.value = false
    }

    private fun getProducts() = uiScope.launch {
        _loading.value = true
        when (val products = repository.getProducts()) {
            is Repository.ApiResult.Success<ProductResponse> -> {
                processProducts(products.data)
            }
            is Repository.ApiResult.Error -> {
                _error.value = products.exception
            }
        }
        _loading.value = false
    }

    private fun processProducts(data: ProductResponse) {
        if (data.status == "ok") {
            data.result.forEach {
                val category = categoryRepository.findById(it.category.uuid)
                if (category?.value != null) {
                    createProduct(it)
                } else {
                    createCategory(
                        it,
                        data.result.filter { product -> product.category.uuid == it.category.uuid }.size
                    )
                    createProduct(it)
                }
            }
        }
    }

    private fun createCategory(product: com.rivas.testparrot.api.model.Product, size: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            categoryRepository.insert(
                Category(
                    product.category.uuid,
                    product.category.name,
                    product.category.sortPosition,
                    size
                )
            )
        }

    }

    private fun createProduct(it: com.rivas.testparrot.api.model.Product) {
        GlobalScope.launch(Dispatchers.IO) {
            productRepository.insert(
                Product(
                    it.uuid,
                    it.name,
                    it.description,
                    it.imageUrl,
                    it.legacyId,
                    it.price,
                    it.alcoholCount,
                    it.soldAlone,
                    it.availability,
                    it.category.uuid
                )
            )
        }
    }

    fun validateStoreData() {
        if (Preferences.getData(Preferences.STORE_ID, AndroidApp.appContext)
                .isNullOrEmpty()
        ) getStores()
        else loadLocalData()

    }

    private fun loadLocalData() {
        storeName.value = Preferences.getData(Preferences.STORE_NAME, AndroidApp.appContext)
        getStores()
    }

    fun changeStatus(item: Product, select: String)= uiScope.launch {
        val availability = Availability(select)
        _loading.value = true
        when (val products = repository.updateProduct(item.uuid, availability)) {
            is Repository.ApiResult.Success<ProductResponse> -> {
                item.availability=select
                GlobalScope.launch {
                    productRepository.update(item)
                }
            }
            is Repository.ApiResult.Error -> {
                _error.value = products.exception
            }
        }
        _loading.value = false
    }

}
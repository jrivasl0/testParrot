package com.rivas.testparrot.ui.menu

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivas.testparrot.AndroidApp
import com.rivas.testparrot.R
import com.rivas.testparrot.data.Category
import com.rivas.testparrot.data.Product
import com.rivas.testparrot.databinding.ActivityMenuBinding
import com.rivas.testparrot.lifecycleowner.TestParrotLifecycleOwner
import com.rivas.testparrot.repository.room.TestDataBase
import com.rivas.testparrot.repository.room.category.CategoryRepository
import com.rivas.testparrot.repository.room.product.ProductRepository
import com.rivas.testparrot.ui.login.LoginActivity
import com.rivas.testparrot.ui.menu.adapters.CategoryAdapter
import com.rivas.testparrot.utils.Preferences
import com.rivas.testparrot.utils.extensions.observer
import dagger.android.DaggerActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MenuActivity : DaggerActivity() {

    @Inject
    lateinit var menuViewModel: MenuViewModel

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var categoryRepository: CategoryRepository

    @Inject
    lateinit var database: TestDataBase

    private lateinit var parentAdapter: CategoryAdapter

    private val products = ArrayList<Product>()
    private val categories = ArrayList<Category>()

    private val testParrotLifeCycleOwner = TestParrotLifecycleOwner()
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testParrotLifeCycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_menu)
        configureBinding()
        menuViewModel.validateStoreData()
        initObservers()
    }

    private fun initObservers() {
        observerProducts()
        observerName()
        observerCategory()
        configureRecyclerView()
    }

    private fun configureRecyclerView() {
        val manager = LinearLayoutManager(this)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding.rv.layoutManager = manager
        binding.rv.setHasFixedSize(true)
    }

    private fun observerProducts() {
        productRepository.findAll().observer(testParrotLifeCycleOwner) {
            this.products.clear()
            this.products.addAll(it)
            createAdapter()
            productRepository.findAll().removeObservers(testParrotLifeCycleOwner)
        }
    }

    private fun createAdapter() {
        if (categories.isNotEmpty() && products.isNotEmpty() ) {
            if(binding.rv.adapter == null) {
                parentAdapter = CategoryAdapter(this, categories, products, menuViewModel)
                binding.rv.adapter = parentAdapter
            } else{
                parentAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun observerCategory() {
        categoryRepository.findAll().observer(testParrotLifeCycleOwner) {
            categories.clear()
            categories.addAll(it)
            createAdapter()
        }
    }

    private fun observerName() {
        menuViewModel.storeName.observer(testParrotLifeCycleOwner) {
            binding.title.text = it
        }
    }

    private fun configureBinding() {
        binding.closeSession.setOnClickListener {
            closeSession()
        }
    }

    override fun onResume() {
        super.onResume()
        testParrotLifeCycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    override fun onDestroy() {
        super.onDestroy()
        testParrotLifeCycleOwner.lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    private fun closeSession() {
        GlobalScope.launch {
            database.clearAllTables()
        }
        Preferences.putData(Preferences.TOKEN, "", AndroidApp.appContext)
        Preferences.putData(Preferences.REFRESH, "", AndroidApp.appContext)
        Preferences.putData(Preferences.STORE_ID, "", AndroidApp.appContext)
        Preferences.putData(Preferences.STORE_NAME, "", AndroidApp.appContext)
        startLogin()
    }

    private fun startLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
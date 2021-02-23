package com.rivas.testparrot.ui.menu.di

import com.rivas.testparrot.repository.Repository
import com.rivas.testparrot.repository.room.category.CategoryRepository
import com.rivas.testparrot.repository.room.product.ProductRepository
import com.rivas.testparrot.ui.menu.MenuViewModel
import dagger.Module
import dagger.Provides

@Module
class MenuModule {

    @Provides
    fun provideViewModel(repository: Repository, categoryRepository: CategoryRepository, productRepository: ProductRepository) = MenuViewModel(repository, categoryRepository, productRepository)
}
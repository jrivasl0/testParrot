package com.rivas.testparrot.ui.menu.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivas.testparrot.data.Category
import com.rivas.testparrot.data.Product
import com.rivas.testparrot.databinding.ItemCategoryBinding
import com.rivas.testparrot.ui.menu.MenuViewModel
import com.rivas.testparrot.utils.NestedRecyclerLinearLayoutManager


class CategoryAdapter(
    private var ctx: Context,
    private var data: ArrayList<Category>,
    private var dataProduct: ArrayList<Product>,
    private var menuViewModel: MenuViewModel
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCategoryBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            binding.category = item
            binding.executePendingBindings()
            initChildLayoutManager(binding,
                dataProduct.filter { it.categoryId == item.uuid } as ArrayList<Product>)
        }
    }

    private fun initChildLayoutManager(
        binding: ItemCategoryBinding,
        childData: ArrayList<Product>
    ) {
        val childAdapter = ProductAdapter(childData, menuViewModel)
        childAdapter.handler.post(childAdapter.collapseList);
        binding.itemParent.setOnClickListener {
            if (childAdapter.itemCount > 0) {
                Log.e("fasdf", "fasdf")
                childAdapter.handler.post(childAdapter.collapseList);
            } else {
                Log.e("fasdf", "fasdflkjanfijoasdnfkjds")
                childAdapter.handler.post(childAdapter.expandList);
            }
        }

        binding.rvChild.layoutManager = NestedRecyclerLinearLayoutManager(ctx)
        binding.rvChild.adapter = childAdapter
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

}
package com.rivas.testparrot.ui.menu.adapters

import android.annotation.SuppressLint
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rivas.testparrot.data.Product
import com.rivas.testparrot.databinding.ItemProductBinding
import com.rivas.testparrot.ui.menu.MenuViewModel
import com.rivas.testparrot.utils.extensions.loadImage


class ProductAdapter(childData: ArrayList<Product>, private val menuViewModel: MenuViewModel) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private val childData: ArrayList<Product> = childData
    private val childDataBk: ArrayList<Product> = ArrayList(childData)


    inner class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Product) {
            binding.product = item
            binding.image.loadImage(item.imageUrl)
            binding.price.text = "$${item.price}"
            binding.switchView.isChecked = item.availability=="AVAILABLE"
            binding.switchView.setOnClickListener {
                val status = if(binding.switchView.isChecked) "AVAILABLE" else "UNAVAILABLE"
                binding.switchView.isChecked = !binding.switchView.isChecked
                menuViewModel.changeStatus(item, status)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    val handler: Handler = Handler()
    var collapseList: Runnable = object : Runnable {
        override fun run() {
            if (itemCount > 0) {
                childData.removeAt(0)
                notifyDataSetChanged()
                handler.postDelayed(this, 50)
            }
        }
    }
    var expandList: Runnable = object : Runnable {
        override fun run() {
            val currSize: Int = childData.size
            if (currSize == childDataBk.size) return
            if (currSize == 0) {
                childData.add(childDataBk[currSize])
            } else {
                childData.add(childDataBk[currSize])
            }
            notifyDataSetChanged()
            handler.postDelayed(this, 50)
        }
    }


    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) =
        holder.bind(childData[position])

    override fun getItemCount(): Int {
        return childData.size
    }

}
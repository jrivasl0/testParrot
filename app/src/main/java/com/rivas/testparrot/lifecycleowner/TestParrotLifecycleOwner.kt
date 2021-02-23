package com.rivas.testparrot.lifecycleowner

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

class TestParrotLifecycleOwner: LifecycleOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)
    override fun getLifecycle() = lifecycleRegistry
}
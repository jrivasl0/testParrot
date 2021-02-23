package com.rivas.testparrot.utils.extensions

import android.view.View

internal fun View.visible(boolean: Boolean) {
    if(boolean)
        this.visibility = View.VISIBLE
    else
        this.visibility = View.GONE
}

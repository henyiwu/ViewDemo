package com.example.baselib.ext

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

// Activity
inline fun <reified VB : ViewBinding> Activity.binding() = lazy {
    inflateBinding<VB>(layoutInflater).apply {
        setContentView(root)
    }
}

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as VB
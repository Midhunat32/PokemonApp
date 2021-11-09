package com.globallogic.app

import android.text.TextUtils
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    Glide.with(view.context)
        .load(url ?: "")
        .into(view)
}



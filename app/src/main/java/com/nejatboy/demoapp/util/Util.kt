package com.nejatboy.demoapp.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.nejatboy.demoapp.R


//ImageView - Glide Extension
fun ImageView.dowloadFromUrl(url: String?) {
    val options = RequestOptions()
        .placeholder(R.drawable.ic_stock_photo)
        .error(R.drawable.ic_broken_photo)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}
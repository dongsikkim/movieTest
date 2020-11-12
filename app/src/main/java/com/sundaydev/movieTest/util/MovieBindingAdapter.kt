package com.sundaydev.movieTest.util

import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.Genres
import com.sundaydev.movieTest.network.URL_ORIGIN_IMAGE
import com.sundaydev.movieTest.network.URL_SUMMARY_IMAGE

@BindingAdapter("visibleGone")
fun View.setVisibleGone(boolean: Boolean) {
    visibility = when (boolean) {
        true -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("loadImage", "original", requireAll = false)
fun AppCompatImageView.setImage(url: String?, original: Boolean = false) {
    url?.let {
        Glide.with(context)
            .load((if (original) URL_ORIGIN_IMAGE else URL_SUMMARY_IMAGE) + it)
            .error(R.drawable.ic_logo)
            .into(this)
    }
}

@BindingAdapter("percentage")
fun AppCompatTextView.setPercentage(percentage: Int) {
    text = when (percentage) {
        in 1..100 -> {
            val spannableString = SpannableStringBuilder("$percentage%")
            spannableString.apply { setSpan(RelativeSizeSpan(1.2f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
        }
        else -> context.getString(R.string.note_rate)
    }
}

@BindingAdapter("refresh")
fun SwipeRefreshLayout.setRefresh(isRefresh: Boolean) {
    isRefreshing = isRefresh
}


@BindingAdapter("genres")
fun AppCompatTextView.setGenres(genreArray: List<Genres>?) {
    if (!genreArray.isNullOrEmpty()) {
        text = genreArray.joinToString { genres -> genres.name }
    }
}

@BindingAdapter("runtime")
fun AppCompatTextView.setRuntime(minute: Int) {
    val builder = StringBuilder()
    if (minute / 60 > 0) {
        builder.append(minute / 60).append("h ")
    }
    builder.append(minute % 60).append("m")
    text = builder.toString()
}

@BindingAdapter("otherName")
fun AppCompatTextView.setOtherName(nameList: List<String>?) {
    if (!nameList.isNullOrEmpty()) {
        text = nameList.joinToString()
    }
}

@BindingAdapter("defaultItemDecorator")
fun RecyclerView.setDefaultItemDecorator(drawable: Drawable) {
    addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
        setDrawable(drawable)
    })
}

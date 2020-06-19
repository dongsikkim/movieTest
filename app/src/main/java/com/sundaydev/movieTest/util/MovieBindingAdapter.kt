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
import com.bumptech.glide.Glide
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.Genres
import com.sundaydev.movieTest.network.URL_ORIGIN_IMAGE
import com.sundaydev.movieTest.network.URL_SUMMARY_IMAGE

@BindingAdapter("visibleGone")
fun setVisibleGone(view: View, boolean: Boolean) {
    view.visibility = when (boolean) {
        true -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("loadImage", "original", requireAll = false)
fun setImage(view: AppCompatImageView, url: String?, original: Boolean = false) {
    url?.let {
        Glide.with(view.context)
            .load((if (original) URL_ORIGIN_IMAGE else URL_SUMMARY_IMAGE) + it)
            .error(R.drawable.ic_logo)
            .into(view)
    }
}

@BindingAdapter("percentage")
fun setPercentage(view: AppCompatTextView, percentage: Int) {
    view.text = when (percentage) {
        in 1..100 -> {
            val spannableString = SpannableStringBuilder("$percentage%")
            spannableString.apply { setSpan(RelativeSizeSpan(1.2f), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
        }
        else -> view.context.getString(R.string.note_rate)
    }
}

@BindingAdapter("refresh")
fun setRefresh(layout: androidx.swiperefreshlayout.widget.SwipeRefreshLayout, isRefresh: Boolean) {
    layout.isRefreshing = isRefresh
}


@BindingAdapter("genres")
fun setGenres(view: AppCompatTextView, genreArray: List<Genres>?) {
    if (!genreArray.isNullOrEmpty()) {
        view.text = genreArray.joinToString { genres -> genres.name }
    }
}

@BindingAdapter("runtime")
fun setRuntime(view: AppCompatTextView, minute: Int) {
    val builder = StringBuilder()
    if (minute / 60 > 0) {
        builder.append(minute / 60).append("h ")
    }
    builder.append(minute % 60).append("m")
    view.text = builder.toString()
}

@BindingAdapter("otherName")
fun setOtherName(view: AppCompatTextView, nameList: List<String>?) {
    if (!nameList.isNullOrEmpty()) {
        view.text = nameList.joinToString()
    }
}

@BindingAdapter("defaultItemDecorator")
fun setDefaultItemDecorator(recyclerView: RecyclerView, drawable: Drawable) {
    recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL).apply {
        setDrawable(drawable)
    })
}

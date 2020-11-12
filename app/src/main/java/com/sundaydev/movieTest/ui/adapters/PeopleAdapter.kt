package com.sundaydev.movieTest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import com.sundaydev.movieTest.BR
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.People
import com.sundaydev.movieTest.util.BindingViewHolder

class PeopleAdapter(private val onClick: ((Pair<AppCompatImageView, People>) -> Unit)?) :
    PagedListAdapter<People, BindingViewHolder>(People.diffPeopleUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder =
        BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_people, parent, false))

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.setVariable(BR.item, getItem(position))
        holder.binding.root.bindingView(position)
    }

    private fun View.bindingView(position: Int) {
        setOnClickListener {
            getItem(position)?.let {
                val imageView = findViewById<AppCompatImageView>(R.id.profile_image)
                onClick?.invoke(Pair(imageView, it))
            }
        }
    }
}
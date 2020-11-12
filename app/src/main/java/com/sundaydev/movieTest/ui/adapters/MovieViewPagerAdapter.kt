package com.sundaydev.movieTest.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sundaydev.movieTest.ui.frament.MovieTabInfo
import com.sundaydev.movieTest.ui.frament.createMovieContentsFragment

class MovieViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): Fragment = createMovieContentsFragment(MovieTabInfo.values()[position])
}
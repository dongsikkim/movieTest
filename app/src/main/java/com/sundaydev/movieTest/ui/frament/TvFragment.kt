package com.sundaydev.movieTest.ui.frament

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.databinding.FragmentTvBinding
import com.sundaydev.movieTest.viewmodel.TvViewModel
import kotlinx.android.synthetic.main.fragment_tv.*
import org.koin.androidx.viewmodel.ext.android.viewModel

enum class TvTabInfo(@IdRes val resourceId: Int) {
    TV_POPULAR(R.string.popular),
    TV_TODAY(R.string.today_playing),
    TV_NOW_PLAYING(R.string.tv_now_playing),
    TV_TOP_RATE(R.string.top_rate)
}

class TvFragment : Fragment() {
    private val viewPagerAdapter: TvViewPagerAdapter by lazy { TvViewPagerAdapter(this) }
    private val viewModel: TvViewModel by viewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentTvBinding = FragmentTvBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_view_pager.adapter = viewPagerAdapter
        TabLayoutMediator(tab_layout, tv_view_pager) { tab, position ->
            tab.text = getString(TvTabInfo.values()[position].resourceId)
        }.attach()
    }
}

class TvViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): Fragment = createTvContentsFragment(TvTabInfo.values()[position])
}
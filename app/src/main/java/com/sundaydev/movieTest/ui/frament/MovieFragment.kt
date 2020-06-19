package com.sundaydev.movieTest.ui.frament

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.annotation.IdRes
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.sundaydev.movieTest.Key
import com.sundaydev.movieTest.PREF_NAME
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.databinding.FragmentMovieBinding
import com.sundaydev.movieTest.util.DayNight
import com.sundaydev.movieTest.util.ThemeSelector
import com.sundaydev.movieTest.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.androidx.viewmodel.ext.android.viewModel


enum class MovieTabInfo(@IdRes val resourceId: Int) {
    MOVIE_POPULAR(R.string.popular),
    MOVIE_NOW_PLAYING(R.string.now_playing),
    MOVIE_UPCOMING(R.string.upcoming),
    MOVIE_TOP_RATE(R.string.top_rate),
}

class MovieFragment : Fragment() {
    private lateinit var viewPagerAdapter: MovieViewPagerAdapter
    private val viewModel: MovieViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentMovieBinding = FragmentMovieBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPagerAdapter = MovieViewPagerAdapter(this@MovieFragment)
        move_view_pager.adapter = viewPagerAdapter
        TabLayoutMediator(tab_layout, move_view_pager) { tab, position ->
            tab.text = getString(MovieTabInfo.values()[position].resourceId)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.theme_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val preference = context?.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val postMode = when (item.itemId) {
            R.id.light_mode -> DayNight.LIGHT
            R.id.dark_mode -> DayNight.DARK
            R.id.system_flow_mode -> DayNight.DEFAULT
            else -> DayNight.DEFAULT
        }
        preference?.edit { putString(Key.DARK_MODE.name, postMode.name) }
        Handler().postDelayed({ ThemeSelector.applyTheme(postMode) }, 450)
        return true
    }
}

class MovieViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 4
    override fun createFragment(position: Int): Fragment = createMovieContentsFragment(MovieTabInfo.values()[position])
}
package com.sundaydev.movieTest.util

import android.os.Build
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sundaydev.movieTest.R

open class BindingViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

enum class DayNight(@IdRes val ids: Int) { LIGHT(R.id.light_mode), DARK(R.id.dark_mode), DEFAULT(R.id.system_mode) }

object ThemeSelector {
    fun applyTheme(dayNight: DayNight) {
        AppCompatDelegate.setDefaultNightMode(
            when (dayNight) {
                DayNight.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                DayNight.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                DayNight.DEFAULT -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM else AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
            }
        )
    }
}

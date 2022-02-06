package com.lab.tinkoff.gifapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.lab.tinkoff.gifapp.ui.PlaceHolderFragment

class PageAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabTitles = arrayOf("Последние", "Лучшее", "Горячие")
    override fun getCount() = 3

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> PlaceHolderFragment.newInstance("latest")
            1 -> PlaceHolderFragment.newInstance("hot")
            2 -> PlaceHolderFragment.newInstance("top")
            // Заглушка
            else -> PlaceHolderFragment.newInstance("latest")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }

}
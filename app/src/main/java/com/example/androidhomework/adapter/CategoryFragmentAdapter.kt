package com.example.androidhomework.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CategoryFragmentAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    private val pages = mutableListOf<Fragment>()

    fun setPages(fragments: List<Fragment>) {
        pages.clear()
        pages.addAll(fragments)
    }

    override fun getItemCount(): Int = pages.size

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }
}
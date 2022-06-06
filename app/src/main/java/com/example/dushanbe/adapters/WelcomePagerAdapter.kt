package com.example.dushanbe.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dushanbe.screens.welcome.ContentFragment
import com.example.dushanbe.screens.welcome.ContentFragment.Companion.slideNumber

class WelcomePagerAdapter(
    fragment: Fragment,
    private val mPageNumbers: Int
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = mPageNumbers

    override fun createFragment(position: Int): Fragment {
        val fragment = ContentFragment()
        when (position) {
            0 ->
                fragment.arguments = Bundle().apply {
                    putInt(slideNumber, 0)
                }
            1 ->
                fragment.arguments = Bundle().apply {
                    putInt(slideNumber, 1)
                }
            2 ->
                fragment.arguments = Bundle().apply {
                    putInt(slideNumber, 2)
                }
        }
        return fragment
    }

}
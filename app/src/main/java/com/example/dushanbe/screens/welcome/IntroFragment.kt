package com.example.dushanbe.screens.welcome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.example.dushanbe.R
import com.example.dushanbe.adapters.WelcomePagerAdapter
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.ui.invisible
import com.example.dushanbe.utils.ui.visible

class IntroFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun openMainActivity(){
        val homeIntent = Intent(requireContext(), MainActivity::class.java)
        requireContext().startActivity(homeIntent)
        requireActivity().finish()
    }
}
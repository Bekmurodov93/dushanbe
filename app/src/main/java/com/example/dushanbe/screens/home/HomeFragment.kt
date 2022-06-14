package com.example.dushanbe.screens.home


import androidx.core.os.bundleOf
import com.example.dushanbe.R
import com.example.dushanbe.databinding.HomeFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.screens.projects.PDF_NAME
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override fun getViewBinding() = HomeFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun observeData() {
        super.observeData()
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }
        binding.videos.setOnClickListener {
            it.navigate(R.id.action_homeToVideos)
        }
        binding.projects.setOnClickListener {
            it.navigate(R.id.action_homeToProjects)
        }
        binding.presents.setOnClickListener {
            it.navigate(R.id.action_homeToPresentaion)
        }
        binding.kodeksi.setOnClickListener {
            it.navigate(R.id.action_homeToKodeks)
        }
        binding.dushanbe.setOnClickListener {
                val bundle = bundleOf()
                bundle.putString(PDF_NAME, "dushanbe.pdf")
                it.navigate(R.id.action_homeToPdfFragment, bundle)
        }
        binding.tajikistan.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(PDF_NAME, "tajikistan.pdf")
            it.navigate(R.id.action_homeToPdfFragment, bundle)
        }
    }


}
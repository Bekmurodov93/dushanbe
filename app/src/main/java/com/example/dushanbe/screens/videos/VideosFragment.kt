package com.example.dushanbe.screens.videos


import com.example.dushanbe.databinding.VideosLayoutBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment

class VideosFragment : BaseFragment<VideosLayoutBinding,VideosViewModel>() {

    override fun getViewModelClass()=VideosViewModel::class.java

    override fun getViewBinding()=VideosLayoutBinding.inflate(layoutInflater)
    override fun observeData() {
        super.observeData()


    }

    override fun setUpViews() {
        super.setUpViews()
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }

    }
}
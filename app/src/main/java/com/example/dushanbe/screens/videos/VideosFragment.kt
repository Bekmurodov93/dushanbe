package com.example.dushanbe.screens.videos


import android.R
import android.graphics.PixelFormat
import android.net.Uri
import android.util.Log
import android.widget.MediaController
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
        val rawFile=requireContext().resources.getIdentifier("baby_shark","raw",requireContext().packageName)
        val localUri =Uri.parse("android.resource://" + requireContext().packageName + "/" + rawFile)
        Log.v("tag","$localUri")
        binding.one.setVideoURI(localUri)
        val controllerOne=MediaController(requireActivity())
        binding.one.setMediaController(controllerOne)
        controllerOne.setAnchorView(binding.one)

    }
}
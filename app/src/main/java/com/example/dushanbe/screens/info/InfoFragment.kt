package com.example.dushanbe.screens.info


import com.example.dushanbe.databinding.InfoFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment

class InfoFragment :  BaseFragment<InfoFragmentBinding, InfoViewModel>() {

    override fun getViewBinding() = InfoFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = InfoViewModel::class.java
    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity).setSupportActionBar(binding.toolbar)

    }

}
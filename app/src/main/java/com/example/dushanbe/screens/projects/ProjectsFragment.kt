package com.example.dushanbe.screens.projects


import com.example.dushanbe.databinding.ProjectLayoutBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment

class ProjectsFragment : BaseFragment<ProjectLayoutBinding,ProjectsViewModel>() {
    override fun getViewModelClass()=ProjectsViewModel::class.java

    override fun getViewBinding()=ProjectLayoutBinding.inflate(layoutInflater)

    override fun observeData() {
        super.observeData()


    }

    override fun setUpViews() {
        super.setUpViews()
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }

    }
}
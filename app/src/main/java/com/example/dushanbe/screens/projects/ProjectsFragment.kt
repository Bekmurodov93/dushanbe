package com.example.dushanbe.screens.projects


import androidx.core.os.bundleOf
import com.example.dushanbe.R
import com.example.dushanbe.databinding.ProjectLayoutBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.navigate

class ProjectsFragment : BaseFragment<ProjectLayoutBinding, ProjectsViewModel>() {
    override fun getViewModelClass() = ProjectsViewModel::class.java

    override fun getViewBinding() = ProjectLayoutBinding.inflate(layoutInflater)

    override fun observeData() {
        super.observeData()


    }

    override fun setUpViews() {
        super.setUpViews()
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }
        binding.oneWrapper.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(PDF_NAME, "lorem_ipsum.pdf")
            it.navigate(R.id.action_projectsToPdfFragment, bundle)
        }

    }
}
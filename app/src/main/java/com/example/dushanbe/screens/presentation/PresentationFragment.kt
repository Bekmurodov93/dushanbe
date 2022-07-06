package com.example.dushanbe.screens.presentation


import androidx.core.os.bundleOf
import com.example.dushanbe.R
import com.example.dushanbe.databinding.PresentationLayoutBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.screens.projects.PDF_NAME
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.navigate


class PresentationFragment : BaseFragment<PresentationLayoutBinding,PresentationViewModel>() {

    override fun getViewModelClass()=PresentationViewModel::class.java

    override fun getViewBinding()= PresentationLayoutBinding.inflate(layoutInflater)

    override fun observeData() {
        super.observeData()


    }

    override fun setUpViews() {
        super.setUpViews()
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }

        binding.oneWrapper.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(PDF_NAME, "presentation.pdf")
            it.navigate(R.id.action_presentationToPdfFragment, bundle)
        }

        binding.twoWrapper.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(PDF_NAME, "project_two.pdf")
            it.navigate(R.id.action_projectsToPdfFragment, bundle)
        }
    }
}
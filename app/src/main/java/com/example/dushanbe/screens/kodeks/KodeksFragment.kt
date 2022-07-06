package com.example.dushanbe.screens.kodeks


import androidx.core.os.bundleOf
import com.example.dushanbe.R
import com.example.dushanbe.databinding.KodeksLayoutBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.screens.projects.PDF_NAME
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.navigate


class KodeksFragment : BaseFragment<KodeksLayoutBinding,KodeksViewModel>() {
    override fun getViewModelClass()=KodeksViewModel::class.java

    override fun getViewBinding()= KodeksLayoutBinding.inflate(layoutInflater)
    override fun observeData() {
        super.observeData()


    }

    override fun setUpViews() {
        super.setUpViews()
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }

        binding.oneWrapper.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(PDF_NAME, "kodeks_one.pdf")
            it.navigate(R.id.action_kodeksToPdfFragment, bundle)
        }

        binding.twoWrapper.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(PDF_NAME, "kodeks_two.pdf")
            it.navigate(R.id.action_kodeksToPdfFragment, bundle)
        }
    }
}
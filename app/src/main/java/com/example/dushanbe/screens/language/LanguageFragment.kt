package com.example.dushanbe.screens.language


import android.app.Activity
import android.content.Intent
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.LanguageFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.Constants
import com.example.dushanbe.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class LanguageFragment : BaseFragment<LanguageFragmentBinding, LoginViewModel>() {

    override fun getViewBinding() = LanguageFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = LoginViewModel::class.java

    override fun observeData() {
        super.observeData()
    }

    override fun setUpViews() {
        super.setUpViews()
        binding.ruWrapper.setOnClickListener {
            viewModel.persistLanguage("ru")
            (activity as MainActivity).setLocale("ru")
            Navigation.findNavController(it).navigate(R.id.action_loginToHome)
        }

        binding.engWrapper.setOnClickListener {
            viewModel.persistLanguage("tg")
            (activity as MainActivity).setLocale("tg")
            Navigation.findNavController(it).navigate(R.id.action_loginToHome)
        }
    }
}
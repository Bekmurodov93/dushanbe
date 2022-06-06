package com.example.dushanbe.screens.language


import com.example.dushanbe.databinding.LanguageFragmentBinding
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



    }
}
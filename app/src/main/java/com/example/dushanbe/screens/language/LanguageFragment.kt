package com.example.dushanbe.screens.language



import com.example.dushanbe.R
import com.example.dushanbe.databinding.LanguageFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LanguageFragment : BaseFragment<LanguageFragmentBinding,LanguageViewModel>() {
    override fun getViewModelClass()=LanguageViewModel::class.java

    override fun getViewBinding()=LanguageFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title=""
        if (viewModel.lang=="ru"){
            binding.russianChecker.setBackgroundResource(R.drawable.ic_vector_check)
            binding.tajikChecker.setBackgroundResource(R.color.transparent)
        }else {
            binding.russianChecker.setBackgroundResource(R.color.transparent)
            binding.tajikChecker.setBackgroundResource(R.drawable.ic_vector_check)
        }
        binding.russian.setOnClickListener {
            changeLanguage("ru")
        }
        binding.tajik.setOnClickListener {
            changeLanguage("tg")
        }
    }
    private fun changeLanguage(lang:String){
        (activity as MainActivity).setLocale(lang)
    }
}
package com.example.dushanbe.screens.home



import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.HomeFragmentBinding
import com.example.dushanbe.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override fun getViewBinding() = HomeFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun observeData() {
        super.observeData()


    }



    override fun setUpViews() {
        super.setUpViews()

    }

}
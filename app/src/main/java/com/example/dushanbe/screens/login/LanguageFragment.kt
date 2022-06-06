package com.example.dushanbe.screens.login


import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.LanguageFragmentBinding
import com.example.dushanbe.databinding.LoginFragmentBinding
import com.example.dushanbe.repositories.Resource
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.enums.InputErrorType
import com.example.dushanbe.utils.enums.InputType
import com.example.dushanbe.utils.ui.InfoAlert
import com.example.dushanbe.utils.ui.textChanges
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.lang.StringBuilder

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
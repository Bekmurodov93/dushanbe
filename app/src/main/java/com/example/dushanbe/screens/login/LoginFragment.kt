package com.example.dushanbe.screens.login


import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.dushanbe.R
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
class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    override fun getViewBinding() = LoginFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = LoginViewModel::class.java

    override fun observeData() {
        super.observeData()

        viewModel.fieldError.observe(viewLifecycleOwner) {
            if (it.first == InputType.EMAIL) {

                when (it.second) {
                    InputErrorType.EMPTY -> {}
                    InputErrorType.MISMATCH -> {
                        binding.emailAddress.setBackgroundResource(R.drawable.input_disabled)
//                        binding.emailAddress.error = getString(R.string.error_fill_correctly)
                    }
                    InputErrorType.VALID -> {
                        binding.emailAddress.setBackgroundResource(R.drawable.input)

                    }
                    else -> {}
                }
            } else {
                when (it.second) {
                    InputErrorType.EMPTY -> {}
                    InputErrorType.MISMATCH -> {}
                    InputErrorType.INVALID -> {
                        binding.password.setBackgroundResource(R.drawable.input_disabled)
                    }
                    InputErrorType.VALID -> {
                        binding.password.setBackgroundResource(R.drawable.input)
                    }
                    else -> {}
                }
            }
        }

        binding.emailField.textChanges().debounce(500).onEach {
            it?.let {
                viewModel.validateLoginFields(InputType.EMAIL)
            }
        }.launchIn(lifecycleScope)

        binding.passwordField.textChanges().debounce(500).onEach {
            it?.let {
                viewModel.validateLoginFields(InputType.PASSWORD)
            }
        }.launchIn(lifecycleScope)

        viewModel.email.observe(viewLifecycleOwner) {
            binding.emailAddress.setBackgroundResource(R.drawable.input)
        }

        viewModel.password.observe(viewLifecycleOwner) {
            binding.password.setBackgroundResource(R.drawable.input)
        }

        viewModel.isLogin.observe(viewLifecycleOwner) {
            binding.login.isEnabled = true
        }

    }

    override fun setUpViews() {
        super.setUpViews()
        binding.loginModel = viewModel
        viewModel.logout()
        binding.login.setOnClickListener {
            viewModel.login().observe(viewLifecycleOwner) {
                if (it is Resource.Success)
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginToHome)
                else {
                    InfoAlert.showWarningAlert(requireContext(), "Error", it.message ?: "") {}
                }
            }
        }

        binding.forgotPassword.setOnClickListener {
            viewModel.forgot().observe(viewLifecycleOwner) {
                if (it.code == 200) {

                    val txt = StringBuilder()

                    for (item in it.user) {
                        txt.appendLine(item.fullname + ": " + item.phone+", ")
                    }

                    InfoAlert.showConfirmAlert(
                        requireContext(), "Центр поддержки", txt.toString(),
                        positiveBtnText = getString(R.string.ok), negativeBtnText = ""
                    ) {}

                }

            }
        }
    }
}
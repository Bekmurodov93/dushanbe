package com.example.dushanbe.screens.reverse

import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.ReverseRegisterFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.toDate
import com.example.dushanbe.utils.ui.validate
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class ReverseRegisterFragment :
    BaseFragment<ReverseRegisterFragmentBinding, ReverseRegisterViewModel>() {
    override fun getViewModelClass() = ReverseRegisterViewModel::class.java

    override fun getViewBinding() = ReverseRegisterFragmentBinding.inflate(layoutInflater)
    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title = ""
        binding.viewModel = viewModel
        val code = arguments?.getString("code", "") ?: ""

        binding.next.setOnClickListener { view ->
            if (viewModel.buttonEnabled.value!!) {
                viewModel.updateRequest(code).observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    Navigation.findNavController(view).navigateUp()
                }
            } else viewModel.validateDate()
        }
        binding.dateField.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.validateDate()
            }
        }

        binding.checkbox1.setOnCheckedChangeListener { _, b ->
            viewModel.firstReverse.postValue(b)
        }

        binding.checkbox2.setOnCheckedChangeListener { _, b ->
            viewModel.firstDiagnoseDate.postValue(b)
        }
        binding.date.setEndIconOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder
                    .datePicker()
                    .setTitleText(getString(R.string.date_of_pastonavka))
                    .build()
            datePicker.show(parentFragmentManager, "date")
            datePicker.addOnPositiveButtonClickListener {
                binding.dateField.setText(it.toDate())
                viewModel.validateDate()
            }
        }
    }

    override fun observeData() {
        super.observeData()
        lifecycleScope.launch {
            viewModel.fieldError.collect {
                when (it.first) {
                    "date" -> {
                        binding.dateField.validate(requireContext(), it.second)
                    }
                    else -> {}
                }
            }
        }
        viewModel.date.observe(viewLifecycleOwner) {
            binding.dateField.setBackgroundResource(R.drawable.input)
        }

    }
}
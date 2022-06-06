package com.example.dushanbe.screens.beforebirth


import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.BeforeBirthRegisterFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.*
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class BeforeBirthRegisterFragment :
    BaseFragment<BeforeBirthRegisterFragmentBinding, BeforeBirthRegisterViewModel>() {
    override fun getViewModelClass() = BeforeBirthRegisterViewModel::class.java
    override fun getViewBinding() = BeforeBirthRegisterFragmentBinding.inflate(layoutInflater)
    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title = ""
        binding.viewModel = viewModel
        viewModel.firstAnalysis.postValue(true)
        viewModel.addField()
        lifecycleScope.launch {
            viewModel.fieldError.collect {
                when (it.first) {
                    "date" -> {
                        binding.dateField.validate(requireContext(), it.second)
                    }
                    "2date" -> {
                        binding.secondDateField.validate(requireContext(), it.second)
                    }
                    else -> {}
                }

            }
        }

        val code = arguments?.getString("code", "") ?: ""

        binding.next.setOnClickListener {
            if (viewModel.buttonEnabled.value!!) {
                viewModel.updateRequest(code).observe(viewLifecycleOwner) {
                    if (it.code == 200 || it.code == 201)
                        Navigation.findNavController(requireView()).navigateUp()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            } else {
                viewModel.validateDate()
                viewModel.validateSecondDate()
            }
        }

        binding.dateField.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.validateDate()
            }
        }


        binding.secondDateField.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.validateSecondDate()
            }
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
        binding.dateSecond.setEndIconOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder
                    .datePicker()
                    .setTitleText(getString(R.string.date_of_pastonavka))
                    .build()
            datePicker.show(parentFragmentManager, "2date")
            datePicker.addOnPositiveButtonClickListener {
                binding.secondDateField.setText(it.toDate())
                viewModel.validateSecondDate()
            }
        }

        viewModel.firstAnalysis.postValue(binding.checkbox.isChecked)
        binding.checkbox.setOnCheckedChangeListener { _, b ->
            viewModel.firstAnalysis.postValue(b)
        }

        binding.checkbox1.setOnCheckedChangeListener { _, b ->
            viewModel.secondAnalysis.postValue(b)
            if (b) {
                viewModel.addField()
                binding.labelDate2.visible()
                binding.dateSecond.visible()
            } else {
                viewModel.removeField()
                binding.labelDate2.invisible()
                binding.dateSecond.invisible()
            }
        }

    }

    override fun observeData() {
        super.observeData()
        viewModel.date.observe(viewLifecycleOwner) {
            binding.date.reset()
        }
        viewModel.secondDate.observe(viewLifecycleOwner) {
            binding.dateSecond.reset()
        }
    }
}
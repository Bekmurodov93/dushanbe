package com.example.patient.screens.register


import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.patient.R
import com.example.patient.databinding.RegisterThirdFragmentBinding
import com.example.patient.screens.MainActivity
import com.example.patient.utils.base.BaseFragment
import com.example.patient.utils.ui.toDate
import com.example.patient.utils.ui.validate
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class RegisterThirdFragment  :  BaseFragment<RegisterThirdFragmentBinding, RegisterThirdViewModel>() {

    override fun getViewBinding() = RegisterThirdFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = RegisterThirdViewModel::class.java

    override fun setUpViews() {
        super.setUpViews()
        binding.registerViewModel=viewModel
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        binding.next.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_toBeforeBirthRegisterFragment)
        }
        lifecycleScope.launch {
            viewModel.fieldError.collect {
                Log.v("tag","$it")
                when (it.first) {
                    "dateOfMenstruation" -> {
                        binding.firstDateField.validate(requireContext(), it.second, null)
                    }
                    "parity" -> {
                        binding.parityField.validate(requireContext(), it.second, null)

                    }
                    else -> {

                    }
                }
            }
        }

        binding.firstDate.setEndIconOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder
                    .datePicker()
                    .setTitleText(getString(R.string.date_of_pastonavka))
                    .build()
            datePicker.show(parentFragmentManager, "dateOfMenstruation")
            datePicker.addOnPositiveButtonClickListener {
                binding.firstDateField.setText(it.toDate())
                val estimatedTime=it+ TimeUnit.DAYS.toMillis(280)
                binding.secondDateField.setText(estimatedTime.toDate())
            }
        }

    }

    override fun observeData() {
        super.observeData()
        viewModel.lastMenstruationDate.observe(viewLifecycleOwner) {
            binding.firstDateField.setBackgroundResource(R.drawable.input)
        }
        viewModel.parity.observe(viewLifecycleOwner) {
            binding.parityField.setBackgroundResource(R.drawable.input)
        }

    }

    override fun observeView() {
        super.observeView()
        binding.firstDateField.setOnFocusChangeListener { _, b ->
            if (b) viewModel.validateMenstruation()
        }
        binding.parityField.setOnFocusChangeListener { _, b ->
            if (b) viewModel.validateParity()
        }
    }
}
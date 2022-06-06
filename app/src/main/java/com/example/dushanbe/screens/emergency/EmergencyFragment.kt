package com.example.dushanbe.screens.emergency

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.EmergencyFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.reset
import com.example.dushanbe.utils.ui.toDate
import com.example.dushanbe.utils.ui.validate
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class EmergencyFragment : BaseFragment<EmergencyFragmentBinding, EmergencyViewModel>() {
    override fun getViewModelClass() = EmergencyViewModel::class.java
    override fun getViewBinding() = EmergencyFragmentBinding.inflate(layoutInflater)
    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title = ""
        binding.viewModel = viewModel
        val code = arguments?.getString("code", "") ?: ""

        binding.next.setOnClickListener {
            if (viewModel.buttonEnabled.value!!) {
                viewModel.updateRequest(code).observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    Navigation.findNavController(requireView()).navigateUp()
                }
            } else viewModel.validateAll()
        }
        binding.typeField.setBackgroundResource(R.drawable.input)
        viewModel.getHospitals().observe(viewLifecycleOwner) { list ->
            binding.typeField.setOnFocusChangeListener { _, b ->
                if (b) {
                    val adapter = ArrayAdapter(requireContext(), R.layout.list_item,
                        list.map { pair -> pair.title })
                    binding.typeField.setAdapter(adapter)
                    viewModel.validateType()
                }
            }
            binding.typeField.setOnItemClickListener { _, _, i, _ ->
                val itemId = list[i]
                viewModel.type.postValue(itemId.id)
                binding.typeField.setText(itemId.title)
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



        binding.nurseField.setOnFocusChangeListener { _, b ->
            if (b) viewModel.validateNurse()
        }
        binding.processField.setOnFocusChangeListener { _, b ->
            if (b) viewModel.validateProcess()
        }
        binding.reasonField.setOnFocusChangeListener { _, b ->
            if (b) viewModel.validateDiagnose()
        }
        binding.dateField.setOnFocusChangeListener { _, b ->
            if (b) viewModel.validateDate()
        }
        binding.checkbox1.setOnCheckedChangeListener { _, b ->
            viewModel.beenInformed.postValue(b)
        }
        binding.checkbox2.setOnCheckedChangeListener { _, b ->
            viewModel.hasBeenDirected.postValue(b)
        }
        binding.checkbox3.setOnCheckedChangeListener { _, b ->
            viewModel.hasHelperNurse.postValue(b)
        }
        binding.checkbox4.setOnCheckedChangeListener { _, b ->
            viewModel.transportSupported.postValue(b)
        }
    }

    override fun observeData() {
        super.observeData()

        viewModel.type.observe(viewLifecycleOwner) {
            binding.typeLayout.reset()
        }

        viewModel.date.observe(viewLifecycleOwner) {
            binding.dateField.reset()
        }

        viewModel.diagnose.observe(viewLifecycleOwner) {
            binding.reasonField.reset()
        }

        viewModel.process.observe(viewLifecycleOwner) {
            binding.processField.reset()
        }

        viewModel.nurse.observe(viewLifecycleOwner) {
            binding.nurseField.reset()
        }

        lifecycleScope.launch {
            viewModel.fieldError.collect {
                when (it.first) {
                    "date" -> binding.dateField.validate(requireContext(), it.second)
                    "nurse" -> binding.nurseField.validate(requireContext(), it.second)
                    "type" -> binding.typeField.validate(requireContext(), it.second)
                    "diagnose" -> binding.reasonField.validate(requireContext(), it.second)
                    "process" -> binding.processField.validate(requireContext(), it.second)
                    else -> {}
                }
            }
        }
    }

}
package com.example.dushanbe.screens.register


import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.RegisterFragmentBinding
import com.example.dushanbe.repositories.register.Register
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
class RegisterFragment : BaseFragment<RegisterFragmentBinding, RegisterViewModel>() {

    override fun getViewBinding() = RegisterFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = RegisterViewModel::class.java

    override fun setUpViews() {
        super.setUpViews()
        binding.registerViewModel = viewModel
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        val code = arguments?.getString("code", "") ?: ""
        val bundle = bundleOf()
        bundle.putString("code", code)
        if (code.isNotEmpty()) {
            val arg = arguments?.get("reg")
            arg?.let {
                val register = it as Register
                bundle.putParcelable("reg",register)
                viewModel.initValues(register)
            }
        }
        binding.next.setOnClickListener {
            if (viewModel.buttonEnabled.value!!) {
                mainViewModel.register.type = viewModel.type.value ?: -1
                mainViewModel.register.publishDate = viewModel.date.value ?: ""
                Navigation.findNavController(it)
                    .navigate(R.id.action_toRegisterSecondFragment, bundle)
            } else {
                viewModel.validateFields()
            }

        }
        binding.dateField.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.validateDate()
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

        viewModel.getHospitals().observe(viewLifecycleOwner) { list ->
            //for saving
            if (code.isNotEmpty()) {
                val arg = arguments?.get("reg")
                arg?.let {
                    val register = it as Register
                    val text = list.findLast { p -> p.id == register.type }
                    viewModel.type.postValue(text?.id)
                    binding.typeField.setText(text?.title)
                    viewModel.validateFields()
                }
            }

            // default behavior
            binding.typeField.setOnFocusChangeListener { _, b ->
                if (b) {
                    val adapter = ArrayAdapter(requireContext(), R.layout.list_item,
                        list.map { pair -> pair.title })
                    binding.typeField.setAdapter(adapter)
                    viewModel.validateType()
                }
            }
            binding.typeField.setOnItemClickListener { _, _, i, _ ->
                val itemId = list[i].id
                viewModel.type.postValue(itemId)
            }
        }



        binding.typeField.setBackgroundResource(R.drawable.input)

    }

    override fun observeData() {
        super.observeData()
        lifecycleScope.launch {
            viewModel.fieldError.collect {
                when (it.first) {
                    "date" -> {
                        binding.dateField.validate(requireContext(), it.second)
                    }
                    else -> {
                        binding.typeField.validate(requireContext(), it.second)
                    }
                }
            }
        }
        viewModel.date.observe(viewLifecycleOwner) {
            binding.dateField.setBackgroundResource(R.drawable.input)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            it?.let { user ->
                binding.region.text = user.region
                binding.street.text = user.subregion
                binding.filial.text = user.hospital
            }
        }


    }

}
package com.example.dushanbe.screens.death

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.RegisterDeathFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.reset
import com.example.dushanbe.utils.ui.validate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@AndroidEntryPoint
class RegisterDeathFragment : BaseFragment<RegisterDeathFragmentBinding, RegisterDeathViewModel>() {

    override fun getViewModelClass() = RegisterDeathViewModel::class.java

    override fun getViewBinding() = RegisterDeathFragmentBinding.inflate(layoutInflater)
    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.title = ""
        binding.viewModel = viewModel
        binding.deathRegionField.setBackgroundResource(R.drawable.input)
        binding.deathRegion2.setBackgroundResource(R.drawable.input)
        val code = arguments?.getString("code", "") ?: ""

        binding.next.setOnClickListener { view ->
            if (viewModel.buttonEnabled.value!!) {
                viewModel.updateRequest(code).observe(viewLifecycleOwner) {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                    Navigation.findNavController(view).navigateUp()
                }
            } else viewModel.validateFields()
        }

        binding.checkbox1.setOnCheckedChangeListener { _, b ->
            if (b) viewModel.maternalDeath.postValue(1)
            else viewModel.maternalDeath.postValue(0)
        }
        binding.checkbox2.setOnCheckedChangeListener { _, b ->
            if (b) viewModel.childDeath.postValue(1)
            else viewModel.childDeath.postValue(0)

        }

        binding.deathReason.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.validateDeathReasonOne()
            }
        }
        binding.deathReason2.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.validateDeathReasonTwo()
            }
        }
        binding.deathHours.setOnFocusChangeListener { _, b ->
            if (b) {
                viewModel.validateDeathHours()
            }
        }
        viewModel.getHospitals().observe(viewLifecycleOwner) { list ->
            //for saving
//            if (code.isNotEmpty()) {
//                val arg = arguments?.get("reg")
//                arg?.let {
//                    val register = it as Register
//                    val text = list.findLast { p -> p.id == register.type }
//                    viewModel.type.postValue(text?.id)
//                    binding.typeField.setText(text?.title)
//                    viewModel.validateFields()
//                }
//            }

            // default behavior
            binding.deathRegionField.setOnFocusChangeListener { _, b ->
                if (b) {
                    val adapter = ArrayAdapter(requireContext(), R.layout.list_item,
                        list.map { pair -> pair.title })
                    binding.deathRegionField.setAdapter(adapter)
                    viewModel.validateDeathRegionOne()
                }
            }
            binding.deathRegionField.setOnItemClickListener { _, _, i, _ ->
                val itemId = list[i].id
                viewModel.deathRegionOne.postValue(itemId)
            }
            // default behavior
            binding.deathRegion2.setOnFocusChangeListener { _, b ->
                if (b) {
                    val adapter = ArrayAdapter(requireContext(), R.layout.list_item,
                        list.map { pair -> pair.title })
                    binding.deathRegion2.setAdapter(adapter)
                    viewModel.validateDeathRegionTwo()
                }
            }
            binding.deathRegion2.setOnItemClickListener { _, _, i, _ ->
                val itemId = list[i].id
                viewModel.deathRegionTwo.postValue(itemId)
            }
        }
    }

    override fun observeData() {
        super.observeData()
        viewModel.deathHours.observe(viewLifecycleOwner) {
            binding.deathHours.reset()
        }
        viewModel.deathReasonOne.observe(viewLifecycleOwner) {
            binding.deathReason.reset()
        }
        viewModel.deathReasonTwo.observe(viewLifecycleOwner) {
            binding.deathReason2.reset()
        }
        viewModel.deathRegionOne.observe(viewLifecycleOwner) {
            binding.deathRegionField.reset()
        }
        viewModel.deathRegionTwo.observe(viewLifecycleOwner) {
            binding.deathRegion2.reset()
        }
        lifecycleScope.launch {
            viewModel.fieldError.collect {
                when (it.first) {
                    "deathHours" -> {
                        binding.deathHours.validate(requireContext(), it.second)
                    }
                    "region1" -> {
                        binding.deathRegionField.validate(requireContext(), it.second)
                    }
                    "reason1" -> {
                        binding.deathReason.validate(requireContext(), it.second)
                    }
                    "region2" -> {
                        binding.deathRegion2.validate(requireContext(), it.second)
                    }
                    "reason2" -> {
                        binding.deathReason2.validate(requireContext(), it.second)
                    }
                }
            }
        }

    }

}
package com.example.patient.screens.search

import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.patient.R
import com.example.patient.adapters.FilterAdapter
import com.example.patient.adapters.PaginationScrollListener
import com.example.patient.databinding.SearchFragmentBinding
import com.example.patient.repositories.register.Register
import com.example.patient.repositories.register.RegisterModel
import com.example.patient.screens.MainActivity
import com.example.patient.utils.base.BaseFragment
import com.example.patient.utils.ui.invisible
import com.example.patient.utils.ui.toDate
import com.example.patient.utils.ui.validate
import com.example.patient.utils.ui.visible
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<SearchFragmentBinding, SearchViewModel>() {

    override fun getViewBinding() = SearchFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = SearchViewModel::class.java

    private val adapter = FilterAdapter(clicked = this::clicked)
    private var loading = false
    private var lastPage = false
    private var isFilter = true
    private var offset = 0
    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity).setSupportActionBar(binding.toolbar)
        binding.viewModel = viewModel
        binding.recycleView.adapter = adapter
        val layout = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
        binding.recycleView.layoutManager = layout

        binding.recycleView.addOnScrollListener(object : PaginationScrollListener(layout) {
            override fun loadMoreItems() {
                if (!isFilter ) return
                loading = true
                offset += 10
                adapter.addLoadingFooter()
                loadNextPage()
            }

            override val isLastPage: Boolean
                get() = lastPage
            override val isLoading: Boolean
                get() = loading
        })
        if (!isFilter) return
        loadFirstPage()

        binding.filter.setOnClickListener {
            if (binding.searchFilterWrapper.isVisible) closeFilter()
            else openFilter()
        }
        binding.accept.setOnClickListener {
            loadFirstPage()
            closeFilter()
            isFilter = true
        }

        binding.searchInputLayout.setEndIconOnClickListener {
            isFilter = false
            viewModel.getByIdPhone().observe(viewLifecycleOwner) {
                adapter.resetAll(it.payload)
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
        viewModel.birthday.observe(viewLifecycleOwner) {
            binding.dateField.setBackgroundResource(R.drawable.input)
        }

    }

    private fun clicked(model: RegisterModel) {
        val details = Register()
        details.infoBirthPermit = model.info_birthpermit
        details.infoParity = model.info_parity ?: -1
        details.infoEstimatedDate = model.info_estimated_date ?: ""
        details.infoMenstruation = model.info_menstruation ?: ""
        details.birthday = model.birthdate
        details.phoneEx = model.phone_ex ?: ""
        details.address = model.address
        details.passport = model.passport ?: ""
        details.publishDate = model.publish_date ?: ""
        details.fio = model.fio
        details.type = model.hospital_type
        val bundle = bundleOf()
        bundle.putParcelable("reg", details)
        bundle.putString("code", model.code)
        Navigation.findNavController(requireView())
            .navigate(R.id.action_searchToDetailsFragment, bundle)
    }

    private fun loadNextPage() {

        viewModel.getPatients(offset, 10).observe(viewLifecycleOwner) {
            adapter.removeLoadingFooter()
            loading = false
            if (it.code == 200 || it.code == 201) {
                adapter.addAll(it.payload)
            }
            lastPage = it.payload.isEmpty()

        }
    }

    private fun loadFirstPage() {
        viewModel.getPatientsInit(offset, 10).observe(viewLifecycleOwner) {
            if (it.code == 200 || it.code == 201) {
                adapter.addAll(it.payload)
            }
            lastPage = it.payload.isEmpty()
        }
    }

    private fun openFilter() {
        binding.searchFilterWrapper.visible()
        binding.filterIcon.setBackgroundResource(R.drawable.ic_filter)
        binding.filterEndIcon.setBackgroundResource(R.drawable.ic_chevron_up)
        binding.filterText.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
    }

    private fun closeFilter() {
        binding.searchFilterWrapper.invisible()
        binding.filterIcon.setBackgroundResource(R.drawable.ic_filter_blured)
        binding.filterEndIcon.setBackgroundResource(R.drawable.ic_chevron_down)
        binding.filterText.setTextColor(ContextCompat.getColor(requireContext(), R.color.darkGrey))
    }

}
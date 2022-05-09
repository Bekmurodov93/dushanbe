package com.example.patient.screens.localPatients

import android.annotation.SuppressLint
import android.widget.Toast
import com.example.patient.R
import com.example.patient.databinding.LocalPatientsFragmentBinding
import com.example.patient.repositories.register.Register
import com.example.patient.screens.MainActivity
import com.example.patient.utils.base.BaseFragment
import com.example.patient.utils.ui.invisible
import com.example.patient.utils.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocalPatientsFragment : BaseFragment<LocalPatientsFragmentBinding, LocalPatientsViewModel>() {

    override fun getViewBinding() = LocalPatientsFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = LocalPatientsViewModel::class.java
    override fun setUpViews() {
        super.setUpViews()
        binding.viewModel = viewModel
        (activity as MainActivity).setSupportActionBar(binding.toolbar)

        viewModel.getLocalPatients().observe(viewLifecycleOwner) { list ->
            setInfo(list)
        }

        binding.syncBtn.setOnClickListener {
            viewModel.syncLocalPatients()
        }

        viewModel.netWorkConnect.observe(viewLifecycleOwner) {
            it?.let { b ->
                if (!b){
                    noInternetConnection()
                }
            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun setInfo(list: List<Register>?) {
        list?.let {
            if (it.isNotEmpty()) {
                binding.syncBtn.visible()
                binding.syncIcon.setBackgroundResource(R.drawable.ic_refresh_cw_2)
                binding.syncInfo.visible()
                binding.syncNumber.visible()
                binding.syncNumber.text = "${it.size} ${getString(R.string.patient)}"
            } else {
                noItemsToSync()
            }
        } ?: run {
            noItemsToSync()
        }
    }

    private fun noItemsToSync() {
        binding.syncBtn.invisible()
        binding.syncIcon.setBackgroundResource(R.drawable.ic_check_circle)
        binding.syncInfo.visible()
        binding.syncNumber.invisible()
        binding.syncInfo.text = getString(R.string.sync_succeeded)
    }

    private fun noInternetConnection(){
        binding.syncBtn.invisible()
        binding.syncIcon.setBackgroundResource(R.drawable.ic_wifi_off)
        binding.syncInfo.visible()
        binding.syncNumber.invisible()
        binding.syncInfo.text = getString(R.string.no_connection)
    }

}
package com.example.patient.utils.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewbinding.ViewBinding
import com.example.patient.R
import com.example.patient.databinding.LoginFragmentBinding
import com.example.patient.screens.MainActivity
import com.example.patient.screens.MainViewModel
import com.example.patient.utils.ui.InfoAlert
import com.example.patient.utils.ui.applyKeyboardInset
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment<VBinding : ViewBinding, VM : BaseViewModel> : Fragment() {
    open var useSharedViewModel: Boolean = false

    protected lateinit var viewModel: VM
    protected abstract fun getViewModelClass(): Class<VM>

    protected lateinit var binding: VBinding
    protected abstract fun getViewBinding(): VBinding
    protected val mainViewModel: MainViewModel by activityViewModels()
    private fun init() {
        binding = getViewBinding()
        viewModel = if (useSharedViewModel) {
            ViewModelProvider(requireActivity())[getViewModelClass()]
        } else {
            ViewModelProvider(this)[getViewModelClass()]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeData()
        observeView()
        val root= (activity as MainActivity).findViewById<View>(R.id.activityView)
        if (binding !is LoginFragmentBinding) {
            binding.root.applyKeyboardInset()
            root.applyKeyboardInset(true)
        }else{
            root.setOnApplyWindowInsetsListener(null)
        }

    }

    open fun setUpViews() {}

    open fun observeView() {}

    open fun observeData() {

        InfoAlert.hideProgressDialog()
        viewModel.mutableErrorType.observe(viewLifecycleOwner) {
            InfoAlert.hideProgressDialog()
            Log.v("tag","$it")
            when (it) {
                ErrorType.UNKNOWN, ErrorType.NETWORK,
                ErrorType.TIMEOUT, ErrorType.HOST_EXCEPTION -> {
                    Snackbar.make(requireContext(), requireView(), it.name, 2000).show()
                }

                ErrorType.SESSION_EXPIRED->{

                    Navigation.findNavController(binding.root).navigate(R.id.action_global_toLogin)
                }

                ErrorType.BAD_REQUEST -> {
                    Snackbar.make(
                        requireContext(), requireView(),
                        viewModel.mutableErrorMessage.value ?: "", 2000
                    ).show()
                }
                else -> {

                }
            }
        }

        viewModel.mutableErrorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(requireContext(), requireView(), it, 2000).show()
        }

        viewModel.mutableScreenState.observe(viewLifecycleOwner) {
            if (it == ScreenState.LOADING) InfoAlert.showProgressDialog(requireContext())
            else InfoAlert.hideProgressDialog()
        }

    }
}
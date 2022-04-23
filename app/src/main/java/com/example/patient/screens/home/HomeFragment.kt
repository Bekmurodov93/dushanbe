package com.example.patient.screens.home



import androidx.navigation.Navigation
import com.example.patient.R
import com.example.patient.databinding.HomeFragmentBinding
import com.example.patient.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override fun getViewBinding() = HomeFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun observeData() {
        super.observeData()
        viewModel.user.observe(viewLifecycleOwner){
            it?.let { user ->
                binding.subText.text=user.hospital
            }
        }

    }



    override fun setUpViews() {
        super.setUpViews()
        binding.homeModel=viewModel
        binding.first.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeToRegister)
        }
        binding.third.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeToLocalPatientsFragment)
        }
        binding.fouth.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeToLocalInfoFragment)
        }
        binding.second.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeToSearchFragment)

        }
        binding.fifth.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_homeToLanguageFragment)
        }
    }

}
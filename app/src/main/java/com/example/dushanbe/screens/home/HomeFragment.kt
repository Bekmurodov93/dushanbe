package com.example.dushanbe.screens.home


import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.dushanbe.R
import com.example.dushanbe.databinding.HomeFragmentBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.screens.projects.PDF_NAME
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.invisible
import com.example.dushanbe.utils.ui.navigate
import com.example.dushanbe.utils.ui.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override fun getViewBinding() = HomeFragmentBinding.inflate(layoutInflater)
    override fun getViewModelClass() = HomeViewModel::class.java
    private var isVisable=false

    override fun observeData() {
        super.observeData()
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                Log.v("tag","hel")
//
//            }
//        })
//    }

    override fun setUpViews() {
        super.setUpViews()
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isVisable){
                    binding.videos.invisible()
                    binding.projects.invisible()
                    binding.kodeksi.visible()
                    binding.presents.visible()
                    binding.tajikistan.visible()
                    binding.dushanbe.visible()
                    isVisable=false
                }else{
                    Navigation.findNavController(requireView()).navigateUp()
                }
            }
        })
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }
        binding.videos.setOnClickListener {
            it.navigate(R.id.action_homeToVideos)
        }
        binding.projects.setOnClickListener {
            it.navigate(R.id.action_homeToProjects)
        }
        binding.presents.setOnClickListener {
            it.navigate(R.id.action_homeToPresentaion)
        }
        binding.kodeksi.setOnClickListener {
            it.navigate(R.id.action_homeToKodeks)
        }
        binding.dushanbe.setOnClickListener {
            isVisable=true
            binding.videos.visible()
            binding.projects.visible()
            binding.kodeksi.invisible()
            binding.presents.invisible()
            binding.tajikistan.invisible()
            binding.dushanbe.invisible()
//                val bundle = bundleOf()
//                bundle.putString(PDF_NAME, "dushanbe.pdf")
//                it.navigate(R.id.action_homeToPdfFragment, bundle)
        }
        binding.tajikistan.setOnClickListener {
            val bundle = bundleOf()
            bundle.putString(PDF_NAME, "tajikistan.pdf")
            it.navigate(R.id.action_homeToPdfFragment, bundle)
        }
    }


}
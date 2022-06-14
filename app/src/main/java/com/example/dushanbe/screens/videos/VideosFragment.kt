package com.example.dushanbe.screens.videos



import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.TimeUtils
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.dushanbe.R
import com.example.dushanbe.databinding.VideosLayoutBinding
import com.example.dushanbe.screens.MainActivity
import com.example.dushanbe.utils.base.BaseFragment
import com.example.dushanbe.utils.ui.navigate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class VideosFragment : BaseFragment<VideosLayoutBinding,VideosViewModel>() {

    override fun getViewModelClass()=VideosViewModel::class.java

    override fun getViewBinding()=VideosLayoutBinding.inflate(layoutInflater)

    override fun observeData() {
        super.observeData()


    }

    override fun setUpViews() {
        super.setUpViews()
        binding.engWrapper.setOnClickListener { (activity as MainActivity).setLocale("tg") }
        binding.ruWrapper.setOnClickListener { (activity as MainActivity).setLocale("ru") }

           val oneRaw=requireContext().resources.getIdentifier("touristic_place","raw",requireContext().packageName)
           val localUri = Uri.parse("android.resource://" + requireContext().packageName + "/" + oneRaw)
           binding.one.setVideoURI(localUri)



            val twoRaw=requireContext().resources.getIdentifier("opportunity_dushanbe","raw",requireContext().packageName)
            val localUriTwo = Uri.parse("android.resource://" + requireContext().packageName + "/" + twoRaw)
            binding.two.setVideoURI(localUriTwo)


            val threeRaw=requireContext().resources.getIdentifier("dushanbe","raw",requireContext().packageName)
            val localUriThree = Uri.parse("android.resource://" + requireContext().packageName + "/" + threeRaw)
            binding.three.setVideoURI(localUriThree)



//        CoroutineScope(lifecycleScope.coroutineContext).launch {
//            val twoRaw=requireContext().resources.getIdentifier("opportunity_dushanbe","raw",requireContext().packageName)
//            val localUriTwo = Uri.parse("android.resource://" + requireContext().packageName + "/" + twoRaw)
//            binding.two.setVideoURI(localUriTwo)
//            binding.two.seekTo(15)
//        }
//        CoroutineScope(lifecycleScope.coroutineContext).launch {
//            val threeRaw=requireContext().resources.getIdentifier("dushanbe","raw",requireContext().packageName)
//            val localUriThree = Uri.parse("android.resource://" + requireContext().packageName + "/" + threeRaw)
//            binding.three.setVideoURI(localUriThree)
//            binding.three.seekTo(15)
//        }



        binding.onePlay.setOnClickListener {
            it.navigate(R.id.action_videosToVideoFragment, bundleOf(Pair(VIDEO_NAME,"touristic_place")))
        }

        binding.twoPlay.setOnClickListener {
            it.navigate(R.id.action_videosToVideoFragment, bundleOf(Pair(VIDEO_NAME,"opportunity_dushanbe")))
        }

        binding.threePlay.setOnClickListener {
            it.navigate(R.id.action_videosToVideoFragment, bundleOf(Pair(VIDEO_NAME,"dushanbe")))
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.one.seekTo(300)
//        binding.two.seekTo(10)
//        binding.three.seekTo(30)
    }


}
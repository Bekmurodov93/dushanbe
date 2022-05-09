package com.example.patient.screens.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.patient.R

class ContentFragment : Fragment() {
    private lateinit var img:ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_content, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         img=view.findViewById(R.id.img)
        arguments?.takeIf { it.containsKey(slideNumber) }?.apply {
            changeContent(this.getInt(slideNumber))
        }

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeContent(page: Int) {
        when (page) {
            1 -> {
                img.background=resources.getDrawable(R.drawable.one)
            }
            2 -> {
                img.background=resources.getDrawable(R.drawable.three)
            }
            else -> {
                img.background=resources.getDrawable(R.drawable.one)
            }
        }
    }

    companion object {
        const val slideNumber = "number"
    }
}
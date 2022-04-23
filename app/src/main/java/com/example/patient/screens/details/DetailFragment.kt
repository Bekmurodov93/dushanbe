package com.example.patient.screens.details


import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.patient.R
import com.example.patient.databinding.DetailFragmentBinding
import com.example.patient.repositories.register.Form2
import com.example.patient.repositories.register.Form4
import com.example.patient.repositories.register.Register
import com.example.patient.repositories.register.RegisterModel
import com.example.patient.screens.MainActivity
import com.example.patient.utils.base.BaseFragment
import com.example.patient.utils.ui.deNormalize
import com.example.patient.utils.ui.invisible

class DetailFragment : BaseFragment<DetailFragmentBinding, DetailViewModel>() {
    override fun getViewModelClass() = DetailViewModel::class.java

    override fun getViewBinding() = DetailFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {
        super.setUpViews()
        binding.toolbar.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_global_toHome)
        }
        binding.toolbar.title = ""
        val arg = arguments?.get("reg")
        val code = arguments?.getString("code","")?:""
//        val model = arguments?.getParcelable<RegisterModel?>("model")
        arg?.let {
            val register = arg as Register
            binding.addressText.text = register.address
            binding.passportText.text = register.passport
            binding.fioText.text = register.fio
            binding.phoneText.text = register.phone
            binding.dateText.text = register.birthday.deNormalize()
            if (code.isEmpty()){
                binding.idText.invisible()
                binding.idLabel.invisible()
            }
            binding.idText.text = code
            val bundle = bundleOf()
            bundle.putString("code", code)
            bundle.putParcelable("reg",register)
            binding.registeredPLace.setOnClickListener {
                navigate(R.id.action_toRegisterFragment, bundle)
            }
            binding.emergencyPLace.setOnClickListener {
                navigate(R.id.action_toEmergencyFragment, bundle)
            }

            binding.reversePLace.setOnClickListener {
                val form= Form4()
//                model?.let {
//                    form.ch_rtn_accept_newborn_1=it.rtn
//                }
                navigate(R.id.action_toReverseRegisterFragment, bundle)
            }

            binding.registeredBeforeBirthPLace.setOnClickListener {
                val form = Form2()
//                model?.let {
//                    form.ch_visit_date_1=
//                }
                bundle.putParcelable("form", form)
                navigate(R.id.action_toBeforeBirthRegisterFragment, bundle)
            }

            binding.deathPLace.setOnClickListener {
                navigate(R.id.action_toRegisterDeathFragment, bundle)
            }

        }


    }

    private fun navigate(id: Int, bundle: Bundle = bundleOf()) {
        Navigation.findNavController(requireView()).navigate(id, bundle)
    }

}
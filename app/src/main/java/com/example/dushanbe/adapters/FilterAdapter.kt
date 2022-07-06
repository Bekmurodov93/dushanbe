package com.example.dushanbe.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dushanbe.databinding.PatientEmptyBinding
import com.example.dushanbe.databinding.PatientListItemBinding
import com.example.dushanbe.databinding.PatientLoadingItemBinding
import com.example.dushanbe.repositories.register.RegisterModel


class FilterAdapter(private var list: MutableList<RegisterModel?> = arrayListOf(),
   private val clicked:(model: RegisterModel)->Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        var LOADING = 0
        var ITEM = 1
        var isLoadingAdded = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM -> {
                val view = PatientListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PatientViewHolder(view)
            }
            LOADING -> {
                val view = PatientLoadingItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                PatientLoadingViewHolder(view)
            }
            else -> {
                val empty =
                    PatientEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                PatientEmptyViewHolder(empty)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(getItemViewType(position)){
            ITEM-> {
                val model=list[position]
                val view=holder as PatientViewHolder
                model?.let {
                    view.setBinding(it)
                }
            }
        }

    }

    override fun getItemCount(): Int = if (list.isEmpty()) 0 else list.size

    override fun getItemViewType(position: Int): Int {
         return if (position == list.size - 1 && isLoadingAdded)  LOADING else ITEM
    }

    fun getItem(position: Int): RegisterModel? = list[position]


    fun addLoadingFooter() {
        isLoadingAdded = true
        list.add(null)
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = list.size - 1
        val result: RegisterModel? = getItem(position)
        if (result != null) {
            list.remove(result)
            notifyItemRemoved(position)
        }
    }

    fun add(movie: RegisterModel?) {
        list.add(movie)
        notifyItemInserted(list.size - 1)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun resetAll(addList: List<RegisterModel>){
        list= arrayListOf()
        list.addAll(addList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(addList: List<RegisterModel?>) {
        list.addAll(addList)
        notifyDataSetChanged()
    }


    inner class PatientViewHolder(private val patientListItemBinding: PatientListItemBinding) :
        RecyclerView.ViewHolder(patientListItemBinding.root) {
        fun setBinding( model: RegisterModel) {
            patientListItemBinding.mainText.text = model.fio
            patientListItemBinding.subText.text =model.code
            patientListItemBinding.root.setOnClickListener {
                clicked(model)
            }
        }
    }

    inner class PatientEmptyViewHolder(private val empty: PatientEmptyBinding) :
        RecyclerView.ViewHolder(empty.root) {
        fun setBinding() {

        }
    }

    inner class PatientLoadingViewHolder(private val empty: PatientLoadingItemBinding) :
        RecyclerView.ViewHolder(empty.root) {}
}
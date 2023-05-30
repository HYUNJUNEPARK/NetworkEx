package com.example.networkex.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.example.networkex.R
import com.example.networkex.view.MainActivity.Companion.TAG
import com.example.networkex.databinding.FragmentB1Binding
import com.example.networkex.model.DataSample

//FragmentB1, FragmentB2 : Fragment 간 데이터 전달
class FragmentB1 : Fragment() {
    private var _binding: FragmentB1Binding? = null
    private val binding get() = _binding!!

    val dataSample1: String = "FragmentB1 String dataSample"
    val dataSample2: DataSample = DataSample(
        data1 = "FragmentB1 String Data",
        data2 = 200
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_b1, container, false)
        binding.fragmentB1 = this@FragmentB1
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView FragmentB1")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy FragmentB1")
    }

    fun onB1ButtonClicked() {
        //Fragment 간 데이터(String, Int, Long, Boolean) 전달할 때 Bundle 에 넣어 전달한다.
        setFragmentResult(
            "REQ_KEY",
            bundleOf("BUNDLE_KEY" to dataSample1)
        )
        findNavController().navigate(R.id.action_fragmentB1_to_fragmentB2) //FragmentB1 onDestroyView
    }
}
package com.example.networkex.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.networkex.R
import com.example.networkex.databinding.FragmentB2Binding
import com.example.networkex.model.DataSample
import com.example.networkex.view.MainActivity.Companion.TAG

class FragmentB2 : Fragment() {
    private var _binding: FragmentB2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_b2, container, false)
        binding.fragmentB2 = this@FragmentB2
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setFragmentResultListener("REQ_KEY") { /*requestKey*/_, bundle ->
            binding.fragmentB2Tv1.text = "RECEIVED : " + bundle.getString("BUNDLE_KEY1", "-")
        }

        binding.fragmentB2Tv2.text = "RECEIVED : " + arguments?.getString("BUNDLE_KEY2")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView FragmentB2")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy FragmentB2")
    }

    fun onB2ButtonClicked() {
        findNavController().popBackStack() //FragmentB2 onDestroy
    }
}
package com.example.networkex

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.networkex.MainActivity.Companion.TAG
import com.example.networkex.databinding.FragmentB1Binding

class FragmentB1 : Fragment() {
    private var _binding: FragmentB1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_b1, container, false)
        binding.fragmentB1 = this@FragmentB1
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView FragmentB1: ")
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy FragmentB1: ")
    }

    fun onB1ButtonClicked() {
        findNavController().navigate(R.id.action_fragmentB1_to_fragmentB2)
    }
}
package com.example.networkex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
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
        _binding = null
    }

    fun onB1ButtonClicked() {
        Toast.makeText(requireContext(), "B1", Toast.LENGTH_SHORT).show()
    }
}
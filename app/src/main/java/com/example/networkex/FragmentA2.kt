package com.example.networkex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.networkex.databinding.FragmentA2Binding

class FragmentA2 : Fragment() {
    private var _binding: FragmentA2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a2, container, false)
        binding.fragmentA2 = this@FragmentA2
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun onA2ButtonClicked() {
        Toast.makeText(requireContext(), "A2", Toast.LENGTH_SHORT).show()
    }
}
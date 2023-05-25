package com.example.networkex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.networkex.databinding.FragmentA1Binding


class FragmentA1 : Fragment() {
    private var _binding : FragmentA1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a1, container, false)
        binding.fragmentA1 = this@FragmentA1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onA1ButtonClicked() {
        Toast.makeText(requireContext(), "FragmentA1", Toast.LENGTH_SHORT).show()
    }
}
package com.example.networkex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.networkex.databinding.FragmentB2Binding


class FragmentB2 : Fragment() {
    private var _binding: FragmentB2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_b2, container, false)
        binding.fragmentB2 = this@FragmentB2
        return binding.root
    }

    fun onB2ButtonClicked() {
        findNavController().popBackStack()
    }
}
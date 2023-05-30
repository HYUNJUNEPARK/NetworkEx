package com.example.networkex.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.networkex.R
import com.example.networkex.databinding.FragmentA2Binding
import com.example.networkex.view.vm.FragmentViewModel
import com.example.networkex.view.vm.SharedViewModel

class FragmentA2 : Fragment() {
    private var _binding: FragmentA2Binding? = null
    private val binding get() = _binding!!
    private val fragmentViewModel: FragmentViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels ()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a2, container, false)
        binding.fragmentA2 = this@FragmentA2
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.sampleData.observe(viewLifecycleOwner) {
            binding.sampleData = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onA2ButtonClicked() {
        Toast.makeText(requireContext(), "FragmentA VM LiveDate : ${fragmentViewModel.membershipIdAndGrade.value?.userId}", Toast.LENGTH_SHORT).show()
    }
}
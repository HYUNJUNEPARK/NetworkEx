package com.example.networkex.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.networkex.R
import com.example.networkex.view.MainActivity.Companion.TEST_USER_ID
import com.example.networkex.databinding.FragmentA1Binding
import com.example.networkex.view.MainActivity.Companion.TAG
import com.example.networkex.view.vm.FragmentViewModel
import com.example.networkex.view.vm.SharedViewModel

//FragmentA1, FragmentA2 : SharedViewModel, FragmentViewModel 비교
class FragmentA1 : Fragment() {
    private var _binding : FragmentA1Binding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels ()
    private val fragmentViewModel: FragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a1, container, false)
        binding.fragmentA1 = this@FragmentA1
        binding.sharedViewModel = sharedViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.membershipIdAndGrade.observe(viewLifecycleOwner) {
            val result = "${it.userId}"
            binding.liveDataFVM = result
        }

        //네트워크 예외
        fragmentViewModel.responseState.observe(viewLifecycleOwner) { responseState ->
            Toast.makeText(requireContext(), "${responseState.name}", Toast.LENGTH_SHORT).show()
        }

        sharedViewModel.sampleData.observe(viewLifecycleOwner) { sampleData ->
            binding.sampleData = sampleData.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun onSharedVMButtonClicked() {
        val liveData = sharedViewModel.paymentMethod.value
        binding.liveDataSVM = liveData
    }

    fun onFragmentVMButtonClicked() {
        fragmentViewModel.requestMembershipIdAndGrade(TEST_USER_ID)
    }
}
package com.example.networkex

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.networkex.MainActivity.Companion.TEST_USER_ID
import com.example.networkex.databinding.FragmentA1Binding
import com.example.networkex.vm.FragmentAViewModel
import com.example.networkex.vm.MainViewModel


class FragmentA1 : Fragment() {
    private var _binding : FragmentA1Binding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: MainViewModel by activityViewModels ()
    private val fragmentViewModel: FragmentAViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_a1, container, false)
        binding.fragmentA1 = this@FragmentA1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentViewModel.membershipIdAndGrade.observe(viewLifecycleOwner){
            val result = "${it.userId}"
            binding.liveDataFVM = result
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
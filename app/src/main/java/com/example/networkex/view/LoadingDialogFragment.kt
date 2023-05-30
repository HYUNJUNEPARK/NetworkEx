package com.example.networkex.view

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.networkex.R
import com.example.networkex.databinding.FragmentLoadingDialogBinding
import kotlinx.coroutines.*

class LoadingDialogFragment : DialogFragment() {
    private var _binding : FragmentLoadingDialogBinding? = null
    private val binding get() = _binding!!
    private val changeImageTerm: Long = 300
    private lateinit var imageList: List<Drawable>
    private lateinit var swappingImage: Job
    private var imgIndexValue = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_loading_dialog, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageList = getLoadingImgList()
        swappingImage = changeLoadingImgJob()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        swappingImage.cancel()
        _binding = null
    }

    private fun changeLoadingImgJob() = CoroutineScope(Dispatchers.Main).launch {
        while (true) {
            delay(changeImageTerm)
            binding.ivLoading.setImageDrawable(imageList[imgIndexValue++ % imageList.size])
        }
    }

    private fun getLoadingImgList(): List<Drawable> {
        return listOf(
            ContextCompat.getDrawable(requireActivity(), R.drawable.loading_img_black)!!,
            ContextCompat.getDrawable(requireActivity(), R.drawable.loading_img_blue)!!,
            ContextCompat.getDrawable(requireActivity(), R.drawable.loading_img_green)!!,
            ContextCompat.getDrawable(requireActivity(), R.drawable.loading_img_red)!!
        )
    }
}
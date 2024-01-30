package com.example.networkex.loading

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.networkex.R
import com.example.networkex.databinding.DialogFragmentLoadingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingDialogFragment : DialogFragment() {
    private var _binding : DialogFragmentLoadingBinding? = null
    private val binding get() = _binding!!
    private lateinit var imgList: List<Drawable>
    private lateinit var imgChangeJob: Job
    private var imgIndexValue = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_loading, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgList = getLoadingImgList()
        imgChangeJob = changeImgJob()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        imgChangeJob.cancel()
        _binding = null
    }

    private fun changeImgJob() = CoroutineScope(Dispatchers.Main).launch {
        while (true) {
            delay(300)
            binding.ivLoading.setImageDrawable(imgList[imgIndexValue++ % imgList.size])
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

    companion object {
        @JvmStatic
        fun newInstance(): LoadingDialogFragment {
            return LoadingDialogFragment().apply {
                isCancelable = false //시스템 백버튼으로 종료 block
                setStyle(STYLE_NO_TITLE, R.style.DialogTheme)
            }
        }
    }
}
package com.example.networkex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.networkex.R
import com.example.networkex.databinding.ActivityMainBinding
import com.example.networkex.util.AppUtil.makeBase64
import com.example.networkex.util.AppUtil.makeSHA256AndBase64
import com.example.networkex.view.vm.SharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels ()
    private val loadingDialog by lazy {
        LoadingDialogFragment().apply { setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme) }
    }

    companion object {
        const val TAG = "testLog"
        const val TEST_USER_ID = "japark7@konai.com"
        const val TEST_USER_PW = "kona!234"
        const val TEST_MDM = "01056142379"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this@MainActivity

        if (sharedViewModel.accessToken.value.isNullOrEmpty()) binding.isToken = "X"

        sharedViewModel.isLoading.observe(this@MainActivity) { isLoading ->
            if (isLoading) {
                if (!loadingDialog.isAdded) {
                    loadingDialog.show(supportFragmentManager, "Loading")
                }
            } else {
                loadingDialog.dismiss()
            }
        }

        sharedViewModel.accessToken.observe(this@MainActivity) { accessToken ->
            if (accessToken.isNotEmpty()) binding.isToken = "O"
        }

        sharedViewModel.membershipIdAndGrade.observe(this@MainActivity) { membershipData ->
            val result = "${membershipData.userId}, ${membershipData.grade}, ${membershipData.loginId}"
            binding.membershipIdAndGrade = result
        }
        
        sharedViewModel.responseState.observe(this@MainActivity) { responseState ->
            Toast.makeText(this, responseState.name, Toast.LENGTH_SHORT).show()
        }

        sharedViewModel.membershipPoint.observe(this@MainActivity) {
            binding.membershipPoint = it
        }

        sharedViewModel.paymentMethod.observe(this@MainActivity) {
            binding.paymentMethod = it
        }

        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container_a1, FragmentA1())
            add(R.id.fragment_container_a2, FragmentA2())
            commit()
        }
    }

    fun onRequestTokenButtonClicked() {
        val userId = if (binding.activityMainEtId.text.isEmpty()) {
            makeBase64(TEST_USER_ID)
        } else {
            binding.activityMainEtId.text.toString()
        }
        val pw = if (binding.activityMainEtPw.text.isEmpty()) {
            makeSHA256AndBase64(TEST_USER_PW)
        } else {
            binding.activityMainEtPw.text.toString()
        }

        sharedViewModel.requestAccessToken(
            userId = userId,
            pwd = pw,
            "deviceId1234", //getDeviceId(this), (deviceId1234 중복 로그인 푸시 확인 가능)
            "pushToken1234"
        )
    }

    fun onRequestMembershipIdButtonClicked() {
        sharedViewModel.requestMembershipIdAndGrade(TEST_USER_ID)
    }

    fun onRequestMembershipPointButtonClicked() {
        sharedViewModel.requestCardPointEx2(TEST_USER_ID)
    }

    fun onRequestSelf04ButtonClicked() {
        sharedViewModel.requestSelf04(TEST_MDM)
    }
}
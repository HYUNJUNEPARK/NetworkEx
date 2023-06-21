package com.example.networkex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.networkex.R
import com.example.networkex.databinding.ActivityMainBinding
import com.example.networkex.util.AppUtil.makeBase64
import com.example.networkex.util.AppUtil.makeSHA256AndBase64
import com.example.networkex.view.vm.SharedViewModelGson
import com.example.networkex.view.vm.SharedViewModelKotlinx

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedViewModelGson: SharedViewModelGson by viewModels ()
    private val sharedViewModelKotlinx: SharedViewModelKotlinx by viewModels ()
    val loadingDialog by lazy {
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


        if (sharedViewModelGson.accessToken.value.isNullOrEmpty()) binding.isToken = "X"

        sharedViewModelGson.isLoading.observe(this@MainActivity) { isLoading ->
            if (isLoading) {
                if (!loadingDialog.isAdded) {
                    loadingDialog.show(supportFragmentManager, "Loading")
                }
            } else {
                loadingDialog.dismiss()
            }
        }

        sharedViewModelGson.accessToken.observe(this@MainActivity) { accessToken ->
            if (accessToken.isNotEmpty()) binding.isToken = "O"
        }

        sharedViewModelGson.membershipIdAndGrade.observe(this@MainActivity) { membershipData ->
            val result = "${membershipData.userId}, ${membershipData.grade}, ${membershipData.loginId}"
            binding.membershipIdAndGrade = result
        }
        
        sharedViewModelGson.responseState.observe(this@MainActivity) { responseState ->
            Toast.makeText(this, responseState.name, Toast.LENGTH_SHORT).show()
        }

        sharedViewModelGson.membershipPoint.observe(this@MainActivity) {
            binding.membershipPoint = it
        }

        sharedViewModelGson.paymentMethod.observe(this@MainActivity) {
            binding.paymentMethod = it
        }

        sharedViewModelKotlinx.responseState.observe(this@MainActivity) { responseState ->
            Toast.makeText(this, responseState.name, Toast.LENGTH_SHORT).show()
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
            makeBase64(binding.activityMainEtId.text.toString())
        }
        val pw = if (binding.activityMainEtPw.text.isEmpty()) {
            makeSHA256AndBase64(TEST_USER_PW)
        } else {
            makeSHA256AndBase64(binding.activityMainEtPw.text.toString())
        }

        sharedViewModelGson.requestAccessToken(
            userId = userId,
            pwd = pw,
            "deviceId1234", //getDeviceId(this), (deviceId1234 중복 로그인 푸시 확인 가능)
            "pushToken1234"
        )
    }

    fun onRequestMembershipIdButtonClicked() {
        sharedViewModelGson.requestMembershipIdAndGrade(TEST_USER_ID)
    }

    fun onRequestMembershipPointButtonClicked() {
        //sharedViewModelGson.requestCardPointEx2(TEST_USER_ID)
        //sharedViewModelGson.requestCardPointEx4(TEST_USER_ID)
        //sharedViewModelGson.requestCardPointEx5(TEST_USER_ID)
        sharedViewModelGson.requestCardPointEx6(TEST_USER_ID)
    }

    fun onRequestSelf04ButtonClicked() {
        sharedViewModelGson.requestSelf04(TEST_MDM)
    }

    fun onRequestKotlinxEx1() {
        //sharedViewModelKotlinx.requestMembershipPoint("50001027812")
        sharedViewModelKotlinx.requestTest("ln-12")
    }

    fun onRequestKotlinxEx2() {
        Toast.makeText(this, "Ex2", Toast.LENGTH_SHORT).show()
    }
}

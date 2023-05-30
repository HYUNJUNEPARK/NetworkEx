package com.example.networkex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.networkex.R
import com.example.networkex.databinding.ActivityMainBinding
import com.example.networkex.view.vm.SharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: SharedViewModel by viewModels ()

    companion object {
        const val TAG = "testLog"
        const val TEST_USER_ID = "japark7@konai.com"
        const val TEST_MDM = "01056142379"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this@MainActivity

        if (sharedViewModel.accessToken.value.isNullOrEmpty()) binding.isToken = "X"

        sharedViewModel.isLoading.observe(this@MainActivity) { isLoading ->
            binding.isLoading = isLoading
        }

        sharedViewModel.accessToken.observe(this@MainActivity) { accessToken ->
            if (accessToken.isNotEmpty()) binding.isToken = "O"
        }

        sharedViewModel.membershipIdAndGrade.observe(this@MainActivity) { it ->
            val result = "${it.userId}, ${it.grade}, ${it.loginId}"
            binding.membershipIdAndGrade = result
        }
        
        sharedViewModel.responseState.observe(this@MainActivity) { it ->
            Toast.makeText(this, "${it.name}", Toast.LENGTH_SHORT).show()
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
            "amFwYXJrN0Brb25haS5jb20="
        } else {
            binding.activityMainEtId.text.toString()
        }
        val pw = if (binding.activityMainEtPw.text.isEmpty()) {
            "NjRjOWNmZTgzZWViZmFlZDUwNWE0ZjU4YmY2OWIyNTljNTExNDFkZDUxM2M3ODdjNzg2NjgwN2E4MDcyNzFmMA=="
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
package com.example.networkex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.networkex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val sharedViewModel: MainViewModel by viewModels() //TODO SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.mainActivity = this@MainActivity

        sharedViewModel.isLoading.observe(this@MainActivity) { isLoading ->
            binding.isLoading = isLoading
        }

        sharedViewModel.accessToken.observe(this@MainActivity) { accessToken ->
            if (accessToken.isNullOrEmpty()) {
                binding.isToken = "X"
            } else {
                binding.isToken = "O"
            }
        }

        sharedViewModel.membershipIdAndGrade.observe(this@MainActivity) { it ->
            val result = "${it.userId},${it.grade}.${it.loginId}"
            binding.testResult = result
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

    fun onButton2Clicked() {
        sharedViewModel.requestMembershipIdAndGrade("japark7@konai.com")
    }

    fun onButton3Clicked() {
        Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "testLog"
    }
}
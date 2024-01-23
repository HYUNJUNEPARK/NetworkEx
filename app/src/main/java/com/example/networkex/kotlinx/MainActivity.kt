package com.example.networkex.kotlinx

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.networkex.R
import com.example.networkex.databinding.ActivityMain2Binding
import com.example.networkex.kotlinx.network.NetworkManager
import com.example.networkex.legacy.network.model.kotlinx.Repo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    private val networkManager = NetworkManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2)
        binding.mainActivity = this@MainActivity
    }

    //enqueue
    fun onTest1ButtonClicked() = CoroutineScope(Dispatchers.IO).launch {
        networkManager.requestGithubInfo("ln-12").enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                when (response.code()) {
                    200 -> {
                        val responseBody = response.body() as List<Repo>
                        Timber.d("Response : $responseBody")
                    }
                    else -> {
                        Toast.makeText(this@MainActivity, response.code(), Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    //execute
    fun onTest2ButtonClicked() = CoroutineScope(Dispatchers.IO + exceptionHandler).launch  {
        val response = networkManager.requestGithubInfo("ln-12").execute()
        when(response.code()) {
            200 -> {
                val responseBody = response.body() as List<Repo>
                Timber.d("Response : $responseBody")
            }
            else -> {
                Toast.makeText(this@MainActivity, response.code(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e("${throwable.message}")
        throwable.printStackTrace()

        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(this@MainActivity, "${throwable.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
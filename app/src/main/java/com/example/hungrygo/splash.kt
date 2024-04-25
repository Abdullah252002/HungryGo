package com.example.hungrygo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.hungrygo.app.login.Login

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        Handler(Looper.getMainLooper()).postDelayed({gotologin()},3000)

    }

    private fun gotologin() {
        val intent=Intent(this,Login::class.java)
        startActivity(intent)
        finish()
    }
}
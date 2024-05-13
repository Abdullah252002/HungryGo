package com.example.hungrygo.app.model

import android.app.Application
import com.yariksoffice.lingver.Lingver
import java.util.Locale

class language_app: Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize Lingver with your desired default language
        Lingver.init(this, Locale("en")) // Default language is English ("en")
    }
}
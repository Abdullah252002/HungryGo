package com.example.hungrygo.app.model

import android.app.Application
import com.yariksoffice.lingver.Lingver
import java.util.Locale

class language_app: Application() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, Locale("en"))

    }
}
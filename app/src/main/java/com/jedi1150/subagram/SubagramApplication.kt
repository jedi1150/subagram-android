package com.jedi1150.subagram

import android.app.Application
import com.jedi1150.subagram.utils.GetResource
import com.jedi1150.subagram.utils.Shared.getResource
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SubagramApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        getResource = GetResource(applicationContext)
    }
}

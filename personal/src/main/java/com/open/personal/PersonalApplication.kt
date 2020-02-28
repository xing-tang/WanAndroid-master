package com.open.personal

import android.app.Application
import android.content.Context
import com.open.baselibrary.base.app.WanAndroidApplication

class PersonalApplication : WanAndroidApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}
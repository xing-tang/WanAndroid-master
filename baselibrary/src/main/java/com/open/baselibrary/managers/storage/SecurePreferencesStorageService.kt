package com.open.baselibrary.managers.storage

import android.content.SharedPreferences

class SecurePreferencesStorageService private constructor() :
    PreferencesStorageService("com.open.wanandroid.services.c.a") {

    override val preferences: SharedPreferences =
        ObscuredSharedPreferences(applicationContext, super.preferences)

    companion object {
        @JvmStatic
        val instance: IStorageService by lazy { SecurePreferencesStorageService() }
    }
}

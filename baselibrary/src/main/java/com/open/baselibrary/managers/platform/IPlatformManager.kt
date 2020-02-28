package com.truckerpath.managers.platform

import android.content.Context
import androidx.annotation.StringRes
import com.open.baselibrary.managers.IManager


interface IPlatformManager : IManager {
    val applicationContext: Context
    fun forceAppRestart()
    fun exitApp()
    fun getString(@StringRes id: Int): String
    fun getString(@StringRes id: Int, vararg args: Any): String
    fun runOnUiThread(action: () -> Unit)
    var isAfterCrash: Boolean
}
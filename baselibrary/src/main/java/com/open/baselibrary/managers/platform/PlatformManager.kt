package com.open.baselibrary.managers.platform

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import com.open.baselibrary.managers.BaseManager


open class PlatformManager : BaseManager(), IPlatformManager {

    private lateinit var preferences: SharedPreferences

    private lateinit var uiThread: Thread

    override var isAfterCrash: Boolean
        get() = preferences.getBoolean(KEY_IS_AFTER_CRASH, false)
        set(value) {
            preferences.edit().putBoolean(KEY_IS_AFTER_CRASH, value).commit()
        }

    override fun initialize() {
        super.initialize()
        uiThread = Thread.currentThread()
        preferences = context.getSharedPreferences("com.open.wanandroid.platform.PlatformManager", Context.MODE_PRIVATE)
    }

    override fun runOnUiThread(action: () -> Unit) {
        if (Thread.currentThread() != uiThread) {
            uiHandler.post(action)
        } else {
            action.invoke()
        }
    }

    override fun getString(id: Int): String = context.getString(id)

    override fun getString(id: Int, vararg args: Any): String = context.getString(id, *args)

    override fun forceAppRestart() {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        val pendingIntent = PendingIntent.getActivity(context, 123456, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).set(AlarmManager.RTC, System.currentTimeMillis(), pendingIntent)
        exitApp()
    }

    override fun exitApp() = System.exit(0)

    override val applicationContext: Context
        get() = context

    protected fun setInstance(instance: IPlatformManager) {
        _instance = instance
    }

    companion object {
        private const val KEY_IS_AFTER_CRASH = "KEY_IS_AFTER_CRASH"

        private val uiHandler = Handler(Looper.getMainLooper())


        @JvmStatic protected var _instance: IPlatformManager? = null

        @JvmStatic
        val instance: IPlatformManager
            get() {
                if (_instance == null) {
                    _instance = PlatformManager()
                }
                return _instance!!
            }
    }
}
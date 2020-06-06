package com.open.baselibrary.base.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.open.baselibrary.BuildConfig
import com.open.baselibrary.managers.config.Config
import com.open.baselibrary.managers.config.ConfigManager
import com.open.baselibrary.managers.platform.PlatformManager
import com.open.baselibrary.utils.HostUtils

open class WanAndroidApplication : Application() {

    private var instance: WanAndroidApplication? = null

    open fun getInstance(): WanAndroidApplication? {
        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appContext = applicationContext
        //        if (BuildConfig.DEBUG) {
        ARouter.openLog() // 打印日志
        ARouter.openDebug() // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        //        }
        ARouter.init(this) // 尽可能早，推荐在Application中初始化
        initCardan()
        initializeManagers()
    }

    open protected fun initializeManagers() {
        PlatformManager.instance.initialize()

    }

    private fun initCardan(){
        var builder = Config.Builder("AIzaSyCrDOcUT62EptCd2bMh6tAaH_QHT4bHSGQ"
            , BuildConfig.VERSION_NAME, HostUtils.instance.APP_DOMAIN)
        ConfigManager.instance.init(builder.build(), this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    /**
     * 程序终止的时候执行
     */
    override fun onTerminate() {
        Log.d("Application", "onTerminate")
        super.onTerminate()
    }


    /**
     * 低内存的时候执行
     */
    override fun onLowMemory() {
        Log.d("Application", "onLowMemory")
        super.onLowMemory()
    }


    /**
     * HOME键退出应用程序
     * 程序在内存清理的时候执行
     */
    override fun onTrimMemory(level: Int) {
        Log.d("Application", "onTrimMemory")
        super.onTrimMemory(level)
    }


    /**
     * onConfigurationChanged
     */
    override fun onConfigurationChanged(newConfig: Configuration?) {
        Log.d("Application", "onConfigurationChanged")
        super.onConfigurationChanged(newConfig)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        lateinit var appContext: Context
            private set
    }
}
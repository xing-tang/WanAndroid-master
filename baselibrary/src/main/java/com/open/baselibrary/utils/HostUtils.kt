package com.open.baselibrary.utils

class HostUtils {

    val APP_DOMAIN = "https://www.wanandroid.com"

    companion object {
        @JvmStatic
        val instance: HostUtils by lazy { HostUtils() }
    }
}
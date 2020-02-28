package com.open.baselibrary.base

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle

class PhoneDelegate(private val requestedOrientation: Int) : Delegate {

    private var previousOrientation: Int = 0
    private var activity: Activity? = null

    override fun onCreate(activity: Activity, savedInstanceState: Bundle?) {
        this.activity = activity

        previousOrientation = savedInstanceState?.getInt(PREV_SCREEN_ORIENTATION_KEY) ?: activity.requestedOrientation
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            this.activity!!.requestedOrientation = requestedOrientation
        }
    }

    override fun onSaveInstanceState(outState: Bundle) =
            outState.putInt(PREV_SCREEN_ORIENTATION_KEY, previousOrientation)

    override fun onResume() {
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            activity!!.requestedOrientation = requestedOrientation
        }
    }

    @SuppressLint("WrongConstant")
    override fun onDetach() {
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            activity!!.requestedOrientation = previousOrientation
        }
    }

    companion object {
        private const val PREV_SCREEN_ORIENTATION_KEY = "PREV_SCREEN_ORIENTATION_KEY"
    }
}

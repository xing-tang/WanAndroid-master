package com.open.baselibrary.base

import android.app.Activity
import android.os.Bundle

interface Delegate {
    fun onCreate(activity: Activity, savedInstanceState: Bundle?)
    fun onSaveInstanceState(outState: Bundle)
    fun onResume()
    fun onDetach()
}

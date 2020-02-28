package com.open.baselibrary.base

import android.app.Activity
import android.os.Bundle

class TabletDelegate : Delegate {
    override fun onCreate(activity: Activity, savedInstanceState: Bundle?) = Unit
    override fun onSaveInstanceState(outState: Bundle) = Unit
    override fun onResume() = Unit
    override fun onDetach() = Unit
}

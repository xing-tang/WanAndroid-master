package com.open.baselibrary.base

import android.content.Intent

interface AnimatedEnterExitAvailable {
    fun startAnimated(intent: Intent)
    fun finishAnimated()
}

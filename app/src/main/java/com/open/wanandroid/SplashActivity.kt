package com.open.wanandroid

import android.os.Bundle
import com.open.baselibrary.base.BaseActivity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splsh)
        addOnCreateSubscription(Observable.just(true)
            .delay(1000, TimeUnit.MILLISECONDS)
            .subscribe {
                startAnimated(MainActivity.getIntent(this))
            }
        )
    }
}
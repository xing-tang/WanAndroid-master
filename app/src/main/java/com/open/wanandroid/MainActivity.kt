package com.open.wanandroid

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.open.baselibrary.base.BaseActivity
import com.open.baselibrary.base.BaseFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_fl_layout, MainFragmengt.newInstance())
            .commit()
    }

    companion object {

        @JvmStatic
        fun getIntent(activity: Activity) =
            Intent(activity, MainActivity::class.java).apply {

            }
    }
}

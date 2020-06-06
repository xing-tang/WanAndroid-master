package com.open.personal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PersonalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.personal_fl_layout, PersonalFragment.newInstance())
            .commit()
    }

    companion object {

        @JvmStatic
        fun getIntent(activity: Activity) =
            Intent(activity, PersonalActivity::class.java).apply {

            }
    }
}

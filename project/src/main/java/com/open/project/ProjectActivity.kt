package com.open.project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.project_fl_layout, ProjectFragment.newInstance())
            .commit()
    }

    companion object {

        @JvmStatic
        fun getIntent(activity: Activity) =
            Intent(activity, ProjectActivity::class.java).apply {

            }
    }
}

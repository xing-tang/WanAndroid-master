package com.open.article

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ArticleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.article_fl_layout, ArticleFragment.newInstance())
            .commit()
    }

    companion object {

        @JvmStatic
        fun getIntent(activity: Activity) =
            Intent(activity, ArticleActivity::class.java).apply {

            }
    }
}

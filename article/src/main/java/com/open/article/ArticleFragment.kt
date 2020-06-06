package com.open.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.baselibrary.base.BaseFragment

@Route(path = "/article/ArticleFragment")
class ArticleFragment : BaseFragment() {

    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_article, container, false)
        }
        return rootView
    }

    companion object {

        fun newInstance() = ArticleFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}
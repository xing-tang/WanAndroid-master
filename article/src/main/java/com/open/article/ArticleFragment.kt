package com.open.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.baselibrary.base.BaseFragment

@Route(path = "/article/ArticleFragment")
class ArticleFragment : BaseFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, null);
    }

    companion object {

        fun newInstance() = ArticleFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}
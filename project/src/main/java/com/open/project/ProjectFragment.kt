package com.open.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.baselibrary.base.BaseFragment

@Route(path = "/project/ProjectFragment")
class ProjectFragment : BaseFragment() {

    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_project, container, false)
        }
        return rootView
    }

    companion object {

        fun newInstance() = ProjectFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}

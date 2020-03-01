package com.open.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.baselibrary.base.BaseFragment

@Route(path = "/project/ProjectFragment")
class ProjectFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project, null);
    }

    companion object {

        fun newInstance() = ProjectFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}

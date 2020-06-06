package com.open.personal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.baselibrary.base.BaseFragment

@Route(path = "/personal/PersonalFragment")
class PersonalFragment : BaseFragment() {

    var rootView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_personal, container, false)
        }
        return rootView
    }

    companion object {

        fun newInstance() = PersonalFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}

package com.open.personal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Route
import com.open.baselibrary.base.BaseFragment
import com.open.home.R

@Route(path = "/personal/PersonalFragment")
class PersonalFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_personal, null);
    }

    companion object {

        fun newInstance() = PersonalFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}

package com.open.baselibrary.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.open.baselibrary.utils.ViewUtils.isTablet

open class BaseDialogFragment : AppCompatDialogFragment() {

    private val delegate: Delegate by lazy {

        if (requireActivity().isTablet()) {
            TabletDelegate()
        } else {
            PhoneDelegate(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        delegate.onCreate(requireActivity(), savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        delegate.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        delegate.onDetach()
    }

    override fun onStart() {
        try {
            super.onStart()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected open val requestedOrientationForPhone: Int
        get() = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager.isStateSaved == false) {
            super.show(manager, tag)
        }

    }

}

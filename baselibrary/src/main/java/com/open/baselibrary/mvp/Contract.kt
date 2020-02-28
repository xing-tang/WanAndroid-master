package com.open.baselibrary.mvp

import android.content.Context
import androidx.annotation.UiThread

class Contract {

    interface ViewWithModel<in M> : View {
        fun showContent(model: M)
    }

    interface ViewWithPersistentModel<in M> : ViewWithModel<M> {
        fun updateModelFromView(model: M)
    }

    interface View {
        fun getContext(): Context?

        fun showLoading()

        fun hideLoading()

        fun close()
    }

    interface Presenter<in T : View> {
        val id: String

        fun init(id: String, isRestoring: Boolean)

        //@UiThread通过注解实现指定在Ui线程中被调用
        @UiThread
        fun attachView(view: T)

        @UiThread
        fun detachView()

        @UiThread
        fun onDestroy()
    }
}

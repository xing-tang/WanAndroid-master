package com.open.wanandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.open.baselibrary.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragmengt : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, null);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_vp_layout?.setScroll(false)
        main_vp_layout?.adapter = FragmentsPagerAdapter(childFragmentManager)
        main_vp_layout?.offscreenPageLimit = 3
        main_vp_layout?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) = Unit
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) = Unit
            override fun onPageSelected(position: Int) {

            }
        })
        main_tb_layout?.setupWithViewPager(main_vp_layout)

        main_tb_layout?.removeAllTabs()
        val homeTab: TabLayout.Tab = main_tb_layout.newTab()
        homeTab.text = "首页"
        main_tb_layout.addTab(homeTab)
        val prejectTab: TabLayout.Tab = main_tb_layout.newTab()
        prejectTab.text = "项目"
        main_tb_layout.addTab(prejectTab)
        val articleTab: TabLayout.Tab = main_tb_layout.newTab()
        articleTab.text = "公众号"
        main_tb_layout.addTab(articleTab)
        val personal: TabLayout.Tab = main_tb_layout.newTab()
        personal.text = "我的"
        main_tb_layout.addTab(personal)



    }

    internal class FragmentsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): BaseFragment {
            return when (position) {
                0 -> ARouter.getInstance().build("/home/HomeFragment").navigation() as BaseFragment
                1 -> ARouter.getInstance().build("/project/ProjectFragment").navigation() as BaseFragment
                2 -> ARouter.getInstance().build("/article/ArticleFragment").navigation() as BaseFragment
                3 -> ARouter.getInstance().build("/personal/PersonalFragment").navigation() as BaseFragment
                else -> ARouter.getInstance().build("/home/HomeFragment").navigation() as BaseFragment
            }
        }

        override fun getCount(): Int = 4


    }

    companion object {

        fun newInstance() = MainFragmengt().apply {
            arguments = Bundle().apply {

            }
        }
    }
}

package com.open.home.mvp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.open.baselibrary.base.BaseActionPresenterFragment
import com.open.baselibrary.mvp.IPresenterFactory
import com.open.home.R
import com.open.home.entity.AriticleResponse
import com.open.home.entity.BannerResponse
import com.open.home.entity.PagerResponse
import com.open.home.mvp.contract.Contract
import com.open.home.mvp.model.Model
import com.open.home.mvp.presenter.HomePresenter
import com.open.home.mvp.ui.adapter.AriticleAdapter
import com.open.home.mvp.ui.adapter.ImageTitleNumAdapter
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils
import com.youth.banner.util.LogUtils
import kotlinx.android.synthetic.main.fragment_home.*

@Route(path = "/home/HomeFragment")
class HomeFragment : BaseActionPresenterFragment<Model, Contract.View, HomePresenter>()
    , Contract.View {

    private val TAG = "HomeFragment"
    private var rootView: View? = null
    private var bannerView: View? = null
    private var mArrayList: ArrayList<AriticleResponse> = arrayListOf()
    private var mAdapter: AriticleAdapter = AriticleAdapter(mArrayList)

    override val presenterFactory: IPresenterFactory<HomePresenter> =
        IPresenterFactory { HomePresenter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_home, container, false)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        // 进入页面，刷新数据
        swiperefresh_layout.isRefreshing = true
        onRefresh()
    }

    private fun initView() {
        home_fl_recycler.apply {
            layoutManager = LinearLayoutManager(activity!!)
            adapter = mAdapter
        }
        swiperefresh_layout.setColorSchemeColors(Color.rgb(47, 223, 189))
        swiperefresh_layout.setOnRefreshListener(OnRefreshListener { onRefresh() })
        mAdapter.loadMoreModule.setOnLoadMoreListener(OnLoadMoreListener { onLoadMore() })
        mAdapter.loadMoreModule.isAutoLoadMore = true
        // 当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        // Banner View
        bannerView = layoutInflater.inflate(
            R.layout.include_banner,
            home_fl_recycler.parent as ViewGroup,
            false
        )
        bannerView?.run { mAdapter.addHeaderView(this) }
    }

    /**
     * 刷新
     */
    private fun onRefresh() {
        // 这里的作用是防止下拉刷新的时候还可以上拉加载
        mAdapter.loadMoreModule.isEnableLoadMore = false
        // 下拉刷新，需要重置页数
        presenter.resetPage()
        presenter.getBannerList()
        presenter.getArticleList()
    }

    /**
     * 加载更多
     */
    private fun onLoadMore() {
        presenter.getBannerList()
        presenter.getArticleList()
    }

    override fun requestBannerSucces(banners: ArrayList<BannerResponse>) {
        var imageAdapter = ImageTitleNumAdapter(banners);
        var banner =
            bannerView?.findViewById<Banner<BannerResponse, ImageTitleNumAdapter>>(R.id.banner)
        banner?.setAdapter(imageAdapter)
            ?.setOnBannerListener { data: Any, position: Int ->
                LogUtils.d("position：$position")
            }
    }

    override fun requestAritilSucces(ariticles: PagerResponse<ArrayList<AriticleResponse>>) {
        swiperefresh_layout.isRefreshing = false
        mAdapter.loadMoreModule.isEnableLoadMore = true
        if (ariticles.isFirstPage()) {// 如果是加载的第一页数据，用setData()
            mAdapter.setList(ariticles.datas)
        } else {// 不是第一页，则用addData()
            ariticles.datas?.run { mAdapter.addData(this) }
        }
        if (ariticles.isLastPage()) { // 如果是最后一页数组,显示没有更多数据布局
            mAdapter.loadMoreModule.loadMoreEnd()
        } else {
            mAdapter.loadMoreModule.loadMoreComplete()
        }
    }

    override fun requestAritilFaild(errorMsg: String) {
        swiperefresh_layout.isRefreshing = false
        mAdapter.loadMoreModule.isEnableLoadMore = true
        mAdapter.loadMoreModule.loadMoreFail()
    }

    companion object {
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}
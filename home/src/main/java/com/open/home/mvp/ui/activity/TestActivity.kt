package com.open.home.mvp.ui.activity

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.open.baselibrary.base.BaseActionPresenterActivity
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
import kotlinx.android.synthetic.main.fragment_home.*

class TestActivity : BaseActionPresenterActivity<Model, Contract.View, HomePresenter>()
    , Contract.View {

    private var mArrayList: ArrayList<AriticleResponse> = arrayListOf()
    private var mAdapter: AriticleAdapter = AriticleAdapter(mArrayList)

    override val presenterFactory: IPresenterFactory<HomePresenter> =
        IPresenterFactory { HomePresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        initRecyclerView()
        initRefreshLayout()
        initLoadMore()
        // 进入页面，刷新数据
        swiperefresh_layout.isRefreshing = true
        refresh()
    }

    private fun initRecyclerView() {
        home_fl_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun initRefreshLayout() {
        swiperefresh_layout.setColorSchemeColors(Color.rgb(47, 223, 189))
        swiperefresh_layout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { refresh() })
    }

    /**
     * 初始化加载更多
     */
    private fun initLoadMore() {
        mAdapter.loadMoreModule.setOnLoadMoreListener(OnLoadMoreListener { loadMore() })
        mAdapter.loadMoreModule.isAutoLoadMore = true
        //当自动加载开启，同时数据不满一屏时，是否继续执行自动加载更多(默认为true)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
    }

    /**
     * 刷新
     */
    private fun refresh() {
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
    private fun loadMore() {
        presenter.getBannerList()
        presenter.getArticleList()
    }

    override fun requestBannerSucces(banners: ArrayList<BannerResponse>) {

    }

    override fun requestAritilSucces(ariticles: PagerResponse<ArrayList<AriticleResponse>>) {
//        swiperefresh_layout.isRefreshing = false
        mAdapter.loadMoreModule.isEnableLoadMore = true

//        if (ariticles.datas != null && ariticles.datas.size > 0) {
        mAdapter.setList(ariticles.datas)
//        }


        mAdapter.loadMoreModule.loadMoreComplete()
    }

    override fun requestAritilFaild(errorMsg: String) {
//        swiperefresh_layout.isRefreshing = false
        mAdapter.loadMoreModule.isEnableLoadMore = true
        mAdapter.loadMoreModule.loadMoreFail()
    }

}

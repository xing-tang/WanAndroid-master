package com.open.home.mvp.ui.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.open.home.R
import com.open.home.entity.AriticleResponse

class AriticleAdapter(data: ArrayList<AriticleResponse>?) :
    BaseQuickAdapter<AriticleResponse, BaseViewHolder>(R.layout.item_ariticle, data),
    LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: AriticleResponse) {
        // 文章布局的赋值
        item?.run {
            helper.setText(
                R.id.item_home_author,
                if (author?.isNotEmpty() == true) author else shareUser
            )
            helper.setText(R.id.item_home_content, Html.fromHtml(title))
            helper.setText(R.id.item_home_type2, Html.fromHtml("$superChapterName·$chapterName"))
            helper.setText(R.id.item_home_date, niceDate)
//            helper.getView<CollectView>(R.id.item_home_collect).isChecked = collect
//            if (showTag) {
            //展示标签
            helper.setGone(R.id.item_home_new, fresh == true)
            helper.setGone(R.id.item_home_top, type == 1)
            if (tags?.isNotEmpty() == true) {
                helper.setGone(R.id.item_home_type1, true)
                helper.setText(R.id.item_home_type1, tags[0].name)
            } else {
                helper.setGone(R.id.item_home_type1, false)
            }
//            } else {
//                //隐藏所有标签
//                helper.setGone(R.id.item_home_top, false)
//                helper.setGone(R.id.item_home_type1, false)
//                helper.setGone(R.id.item_home_new, false)
//            }
        }
    }
}
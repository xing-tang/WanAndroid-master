package com.open.home.mvp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.open.baselibrary.utils.ImageLoadUtils
import com.open.home.R
import com.open.home.entity.BannerResponse
import com.youth.banner.adapter.BannerAdapter
import java.util.*

/**
 * 自定义布局，图片+标题+数字指示器
 */
class ImageTitleNumAdapter(mDatas: ArrayList<BannerResponse>) :
    BannerAdapter<BannerResponse, ImageTitleNumAdapter.BannerViewHolder>(mDatas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.banner_image_title_num, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindView(holder: BannerViewHolder, data: BannerResponse,
        position: Int, size: Int) {
        ImageLoadUtils.displayImage(holder.imageView, data.imagePath, holder.imageView);
        holder.title.text = data.title
        holder.numIndicator.text = (position + 1).toString() + "/" + size
    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.image)
        var title: TextView = view.findViewById(R.id.bannerTitle)
        var numIndicator: TextView = view.findViewById(R.id.numIndicator)
    }
}

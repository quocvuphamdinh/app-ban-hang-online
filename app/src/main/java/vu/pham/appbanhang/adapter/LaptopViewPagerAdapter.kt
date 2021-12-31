package vu.pham.appbanhang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.QuangCao

class LaptopViewPagerAdapter : PagerAdapter {

    private var context: Context? = null
    private var layout :Int=0
    private var photoLists:ArrayList<QuangCao> = ArrayList()
    private var clickItem:CLickItem

    constructor(clickItem: CLickItem) : super() {
        this.clickItem = clickItem
    }


    interface CLickItem{
        fun showInfoItem(photo:QuangCao)
    }
    fun setData(lists:ArrayList<QuangCao>, lay:Int, context: Context?){
        this.photoLists=lists
        this.layout=lay
        this.context = context
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(layout, container, false)
        val imageView:ImageView=view.findViewById(R.id.imageViewPagerItemLaptop)
        val quangCao = photoLists[position]
        Picasso.get().load(quangCao.getHinhAnh()).into(imageView)
        view.setOnClickListener {
            clickItem.showInfoItem(quangCao)
        }
        container.addView(view)
        return view
    }
    override fun getCount(): Int {
        return photoLists.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }
}
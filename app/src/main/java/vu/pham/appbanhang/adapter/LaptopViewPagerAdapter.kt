package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.SanPham

class LaptopViewPagerAdapter : PagerAdapter() {

    private var layout :Int=0
    private var photoLists:ArrayList<SanPham> = ArrayList()
    fun setData(lists:ArrayList<SanPham>, lay:Int){
        this.photoLists=lists
        this.layout=lay
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(container.context).inflate(layout, container, false)
        val imageView:ImageView=view.findViewById(R.id.imageViewPagerItemLaptop)
        val sanPham = photoLists[position]
        Picasso.get().load(sanPham.getHinhAnh()).into(imageView)
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
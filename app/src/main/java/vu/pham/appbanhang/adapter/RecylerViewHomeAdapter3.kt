package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.QuangCao
import vu.pham.appbanhang.model.SanPham

class RecylerViewHomeAdapter3 : RecyclerView.Adapter<RecylerViewHomeAdapter3.HomeView3Holder> {
    private var sanPhamBanChayList:ArrayList<SanPham> = ArrayList()
    private var clickItem:CLickItem

    constructor(clickItem: CLickItem) : super() {
        this.clickItem = clickItem
    }


    interface CLickItem{
        fun showInfoItem(sanPham: SanPham)
    }
    fun setData(lists:ArrayList<SanPham>){
        this.sanPhamBanChayList=lists
        notifyDataSetChanged()
    }
    class HomeView3Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewHinh3:ImageView
        var txtTen3:TextView
        init {
            imgViewHinh3 = itemView.findViewById(R.id.imageViewPhuKienBanChayItem)
            txtTen3=itemView.findViewById(R.id.textViewPhuKienBanChayItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeView3Holder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.home3_item_row, parent, false)
        return HomeView3Holder(view)
    }

    override fun onBindViewHolder(holder: HomeView3Holder, position: Int) {
        val sanPham = sanPhamBanChayList[position]
        Picasso.get().load(sanPham.getHinhAnh()).into(holder.imgViewHinh3)
        holder.txtTen3.text = sanPham.getTenSanPham()

        holder.imgViewHinh3.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
        holder.txtTen3.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
    }

    override fun getItemCount(): Int {
       return sanPhamBanChayList.size
    }
}
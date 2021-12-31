package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class RecyclerViewLaptopAdapter : RecyclerView.Adapter<RecyclerViewLaptopAdapter.LaptopViewHolder> {
    private var laptopList:ArrayList<SanPham> = ArrayList()
    private var layout : Int =0
    private var clickItem:ClickItem

    constructor(clickItem: ClickItem) : super() {
        this.clickItem = clickItem
    }

    interface ClickItem{
        fun showInfoItem(sanPham: SanPham)
    }
    fun setData(lists:ArrayList<SanPham>, lay:Int){
        this.laptopList=lists
        this.layout=lay
    }
    class LaptopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgHinh:ImageView
        var txtTen:TextView
        var txtGia:TextView

        init {
            imgHinh=itemView.findViewById(R.id.imageViewHinhLaptopItem)
            txtTen=itemView.findViewById(R.id.textViewTenLaptopItem)
            txtGia=itemView.findViewById(R.id.textViewGiaLaptopItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaptopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return LaptopViewHolder(view)
    }

    override fun onBindViewHolder(holder: LaptopViewHolder, position: Int) {
        val sanPham = laptopList[position]
        Picasso.get().load(sanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTen.text = sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text = "Giá: ${decimalFormat.format(sanPham.getGiaSanPham())} Đ"

        holder.imgHinh.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
        holder.txtTen.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
        holder.txtGia.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
    }

    override fun getItemCount(): Int {
        return laptopList.size
    }
}
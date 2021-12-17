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

class RecyclerViewHomeAdapter : RecyclerView.Adapter<RecyclerViewHomeAdapter.HomeViewHolder>() {
    private var sanPhamList:ArrayList<SanPham> = ArrayList()
    fun setData(lists:ArrayList<SanPham>){
        this.sanPhamList=lists
        notifyDataSetChanged()
    }
    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewItemHome : ImageView
        var txtTenItemHome : TextView
        var txtGiaItemHome : TextView

        init {
            imgViewItemHome=itemView.findViewById(R.id.imageViewItemHome)
            txtTenItemHome=itemView.findViewById(R.id.textviewTenItemHome)
            txtGiaItemHome=itemView.findViewById(R.id.textviewGiaItemHome)
        }
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeViewHolder {
        val view =LayoutInflater.from(p0.context).inflate(R.layout.home_item_row, p0, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(p0: HomeViewHolder, p1: Int) {
        val sanPham= sanPhamList[p1]
        Picasso.get().load(sanPham.getHinhAnh()).into(p0.imgViewItemHome)
        p0.txtTenItemHome.text=sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        p0.txtGiaItemHome.text="Giá: ${decimalFormat.format(sanPham.getGiaSanPham())} Đ"
    }

    override fun getItemCount(): Int {
        return sanPhamList.size
    }
}
package vu.pham.appbanhang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class ListSanPhamAdapter : BaseAdapter {
    private var context:Context
    private var layout:Int
    private var listSanPham:List<SanPham>

    constructor(context: Context, layout: Int, listSanPham: List<SanPham>) : super() {
        this.context = context
        this.layout = layout
        this.listSanPham = listSanPham
    }

    override fun getCount(): Int {
       return listSanPham.size
    }

    override fun getItem(position: Int): Any {
        return listSanPham[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private  class ViewHolder(view: View){
        val imgHinh:ImageView
        val txtTen:TextView
        val txtGia:TextView
        val txtMoTa:TextView
        init {
            imgHinh = view.findViewById(R.id.imageViewItemGameFragment)
            txtTen = view.findViewById(R.id.textViewNameItemGameFragment)
            txtGia = view.findViewById(R.id.textViewGiaItemGameFragment)
            txtMoTa = view.findViewById(R.id.textViewMoTaItemGameFragment)
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val holder:ViewHolder
        if(convertView==null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(layout, null)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val sanPham = listSanPham[position]
        Picasso.get().load(sanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTen.text = sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text="Giá: ${decimalFormat.format(sanPham.getGiaSanPham())} Đ"
        holder.txtMoTa.text = sanPham.getMoTa().subSequence(0, 70).toString()+"..."
        return view
    }
}
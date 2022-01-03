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
import vu.pham.appbanhang.model.CartSanPham
import java.text.DecimalFormat

class ListCartThanhToanAdapter: BaseAdapter {
    private var context: Context
    private var layout:Int
    private var cartList:ArrayList<CartSanPham>

    constructor(context: Context, layout: Int, cartList: ArrayList<CartSanPham>) : super() {
        this.context = context
        this.layout = layout
        this.cartList = cartList
    }


    override fun getCount(): Int {
        return cartList.size
    }

    override fun getItem(position: Int): Any {
        return cartList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    private class ViewHolder(view: View){
        var imgHinh:ImageView
        var txtTen:TextView
        var txtGia:TextView
        var txtSoLuong:TextView
        init {
            imgHinh = view.findViewById(R.id.imageViewHinhCartThanhToan)
            txtTen = view.findViewById(R.id.textViewTenCartThanhToan)
            txtGia =view.findViewById(R.id.textViewGiaCartThanhToan)
            txtSoLuong = view.findViewById(R.id.textViewSoLuongCartThanhToan)
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder
        var view:View
        if (convertView==null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(layout, null)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val cartSanPham = cartList[position]
        Picasso.get().load(cartSanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTen.text = cartSanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text="${decimalFormat.format(cartSanPham.getGiaSanPham())} Đ"
        holder.txtSoLuong.text="x${cartSanPham.getSoLuong()}"

        return view
    }
}
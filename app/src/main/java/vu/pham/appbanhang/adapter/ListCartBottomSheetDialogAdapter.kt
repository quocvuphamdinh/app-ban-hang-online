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

class ListCartBottomSheetDialogAdapter : BaseAdapter {
    private var context:Context
    private var layout:Int
    private var lists:ArrayList<CartSanPham>

    constructor(context: Context, layout: Int, lists: ArrayList<CartSanPham>) : super() {
        this.context = context
        this.layout = layout
        this.lists = lists
    }


    override fun getCount(): Int {
        return lists.size
    }

    override fun getItem(position: Int): Any {
        return lists[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class CartBottomHolder(view: View){
        var imgHinh:ImageView
        var txtTen:TextView
        var txtGia:TextView
        var txtSoLuong:TextView

        init {
            imgHinh = view.findViewById(R.id.imageViewCartBottomSheetDialog)
            txtTen = view.findViewById(R.id.textViewTenCartBottomSheetDialog)
            txtGia = view.findViewById(R.id.textViewGiaCartBottomSheetDialog)
            txtSoLuong = view.findViewById(R.id.textViewSoLuongCartItemBottomSheet)
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder:CartBottomHolder
        val view:View
        if(convertView==null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(layout, null)
            holder = CartBottomHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as CartBottomHolder
        }
        val cartSanPham = lists[position]
        Picasso.get().load(cartSanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTen.text = cartSanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text="${decimalFormat.format(cartSanPham.getGiaSanPham() * cartSanPham.getSoLuong())} Đ"
        holder.txtSoLuong.text ="Số lượng: ${cartSanPham.getSoLuong()}"
        return view
    }
}
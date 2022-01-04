package vu.pham.appbanhang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class ListKeyBoardAdapter : BaseAdapter {
    private var context:Context
    private var layout:Int
    private var lists:ArrayList<SanPham>

    constructor(context: Context, layout: Int, lists: ArrayList<SanPham>) : super() {
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

    class ViewHolder(view: View){
        var imgHinh:ImageView
        var txtTen:TextView
        var txtGia:TextView
        var txtMoTa:TextView
        var imgViewSoldOut:ImageView
        init {
            imgHinh=view.findViewById(R.id.imageViewKeyBoardItem)
            txtTen = view.findViewById(R.id.textViewTenKeyBoardItem)
            txtGia = view.findViewById(R.id.textViewGiaKeyBoardItem)
            txtMoTa = view.findViewById(R.id.textViewMoTaKeyBoardItem)
            imgViewSoldOut = view.findViewById(R.id.imageViewSoldOutKeyBoardItem)
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder :ViewHolder
        val view:View
        if(convertView==null){
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(layout, null)
            holder = ViewHolder(view)
            view.tag = holder
        }else{
            view = convertView
            holder = convertView.tag as ViewHolder
        }
        val sanPham =lists[position]
        Picasso.get().load(sanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTen.text = sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text="${decimalFormat.format(sanPham.getGiaSanPham())} Đ"
        holder.txtMoTa.text = sanPham.getMoTa().subSequence(0, 60).toString()+"..."
        if (sanPham.getSoLuongSanPham()<=0){
            holder.imgViewSoldOut.visibility = View.VISIBLE
        }else{
            holder.imgViewSoldOut.visibility = View.INVISIBLE
        }
        return view
    }
}
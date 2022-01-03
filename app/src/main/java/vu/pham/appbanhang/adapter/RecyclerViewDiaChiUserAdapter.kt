package vu.pham.appbanhang.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.DiaChi
import java.text.DecimalFormat

class RecyclerViewDiaChiUserAdapter : RecyclerView.Adapter<RecyclerViewDiaChiUserAdapter.DiaCHiUserHolder>{

    private var diaChiList:ArrayList<DiaChi> = ArrayList()
    private var clickItem:ClickItem
    private var viewBinderHelper = ViewBinderHelper()

    constructor(clickItem: ClickItem) : super() {
        this.clickItem = clickItem
    }


    interface ClickItem{
        fun click(diaChi: DiaChi)
        fun delete(diaChi: DiaChi)
        fun edit(diaChi: DiaChi)
    }

    fun setData(lists:ArrayList<DiaChi>){
        this.diaChiList = lists
        notifyDataSetChanged()
    }
    class DiaCHiUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTen: TextView
        var txtPhone: TextView
        var txtAddress: TextView
        var imgTickDefault:ImageView
        var layoutDiaChi:RelativeLayout
        var swipeRevealLayout : SwipeRevealLayout
        var txtDelete: TextView
        var txtEdit:TextView
        init {
            txtTen = itemView.findViewById(R.id.textViewHoTenDiaChiItem)
            txtPhone =itemView.findViewById(R.id.textViewPhoneDiaChiItem)
            txtAddress = itemView.findViewById(R.id.textViewAddressDiaChiItem)
            imgTickDefault = itemView.findViewById(R.id.imageViewTickDefault)
            layoutDiaChi = itemView.findViewById(R.id.layout_diachi_item)
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayoutDiaChiItem)
            txtDelete = itemView.findViewById(R.id.layout_delete_diachi_item)
            txtEdit = itemView.findViewById(R.id.layout_edit_diachi_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaCHiUserHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.diachi_item_row, parent, false)
        return DiaCHiUserHolder(view)
    }

    override fun onBindViewHolder(holder: DiaCHiUserHolder, position: Int) {
        val diaChi = diaChiList[position]
        viewBinderHelper.bind(holder.swipeRevealLayout, diaChi.getId().toString())
        holder.txtTen.text = diaChi.getName()
        holder.txtPhone.text = diaChi.getPhone()
        holder.txtAddress.text = diaChi.getAddress()
        if (diaChi.getDefaultAddress()==1){
            holder.imgTickDefault.visibility = View.VISIBLE
        }else{
            holder.imgTickDefault.visibility = View.GONE
        }
        holder.layoutDiaChi.setOnClickListener {
            clickItem.click(diaChi)
        }
        holder.txtDelete.setOnClickListener {
            val diaChiDelete = diaChiList[holder.adapterPosition]
            diaChiList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            clickItem.delete(diaChiDelete)
        }
        holder.txtEdit.setOnClickListener {
            val diaChiEdit = diaChiList[holder.adapterPosition]
            clickItem.edit(diaChiEdit)
        }
    }

    override fun getItemCount(): Int {
        return diaChiList.size
    }
}
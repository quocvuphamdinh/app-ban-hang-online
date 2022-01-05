package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.DonHang
import java.text.DecimalFormat

class RecyclerViewDonHangAdapter : RecyclerView.Adapter<RecyclerViewDonHangAdapter.DonHangHolder>{
    private var donHangList:ArrayList<DonHang> = ArrayList()
    private var cartList:ArrayList<CartSanPham> = ArrayList()
    private var clickItem:ClickItem

    constructor(clickItem: ClickItem) : super() {
        this.clickItem = clickItem
    }


    interface ClickItem{
        fun click(donHang: DonHang)
    }
    fun setData(lists1:ArrayList<DonHang>, lists2:ArrayList<CartSanPham>){
        this.donHangList = lists1
        this.cartList = lists2
        notifyDataSetChanged()
    }

    class DonHangHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgHinh:ImageView
        var txtTenSanPham:TextView
        var txtSoLuongSanPham:TextView
        var txtTongTien:TextView
        var txtTrangThai:TextView
        var txtDiaChi:TextView
        var txtTenDonHang:TextView

        init {
            imgHinh = itemView.findViewById(R.id.imageViewHinhDonHangItem)
            txtTenSanPham = itemView.findViewById(R.id.textViewTenSanPhamDonHangItem)
            txtSoLuongSanPham =itemView.findViewById(R.id.textViewSoLuongSanPhamDonHangItem)
            txtTongTien = itemView.findViewById(R.id.textViewTongTienDonHangItem)
            txtTrangThai = itemView.findViewById(R.id.textViewTrangThaiDonHangItem)
            txtDiaChi = itemView.findViewById(R.id.textViewDiaChiDonHangItem)
            txtTenDonHang = itemView.findViewById(R.id.textViewTenDonHangItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonHangHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.donhang_item_row, parent, false)
        return DonHangHolder(view)
    }

    override fun onBindViewHolder(holder: DonHangHolder, position: Int) {
        val donHang = donHangList[position]
        var cartSanPham = CartSanPham()
        for (i in 0 until cartList.size){
            if (cartList[i].getId() == donHang.getGioHangId()){
                cartSanPham = cartList[i]
                break
            }
        }
        Picasso.get().load(cartSanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTenSanPham.text = cartSanPham.getTenSanPham()
        holder.txtSoLuongSanPham.text = "x${cartSanPham.getSoLuong()}"
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtTongTien.text = "${decimalFormat.format(donHang.getTongTien())} Đ"
        holder.txtTrangThai.text = "Trạng thái đơn hàng: ${donHang.getTrangThaiName()}"
        holder.txtDiaChi.text = "Gửi tới: ${donHang.getAddressUser()}"
        holder.txtTenDonHang.text = "Đơn hàng: ${donHang.getTenDonHang()}"

        holder.imgHinh.setOnClickListener {
            clickItem.click(donHang)
        }
    }

    override fun getItemCount(): Int {
       return donHangList.size
    }
}
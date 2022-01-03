package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.Cart
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class RecyclerViewCartAdapter : RecyclerView.Adapter<RecyclerViewCartAdapter.CartHolder>{
    private var cartSanPhamList:ArrayList<CartSanPham> = ArrayList()
    private var checkBoxChecked:EventItemCart
    private var viewBinderHelper = ViewBinderHelper()

    constructor(checkBoxChecked: EventItemCart) : super() {
        this.checkBoxChecked = checkBoxChecked
    }

    interface EventItemCart{
        fun checkedItem(cartSanPham: CartSanPham, state:Boolean)
        fun checkedItem2(cartSanPham: CartSanPham, state: Boolean)
        fun tangVaGiamSoLuongChange(cartSanPham: CartSanPham)
        fun deleteItem(cartSanPham: CartSanPham)
    }
    fun setData(lists:ArrayList<CartSanPham>){
        this.cartSanPhamList = lists
        notifyDataSetChanged()
    }
    class CartHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imgHinh:ImageView
        var txtTen:TextView
        var txtGia:TextView
        var buttonGiam:Button
        var buttonTang:Button
        var txtSoLuong:TextView
        var txtCreateAt:TextView
        var checkBox:CheckBox
        var swipeRevealLayout : SwipeRevealLayout
        var layoutDelete:LinearLayout

        init {
            imgHinh = itemView.findViewById(R.id.imageViewCartItem)
            txtTen = itemView.findViewById(R.id.textViewTenCartItem)
            txtGia = itemView.findViewById(R.id.textViewGiaCartItem)
            txtSoLuong = itemView.findViewById(R.id.textViewSoLuongCartItem)
            buttonGiam = itemView.findViewById(R.id.buttonGiamCartItem)
            buttonTang = itemView.findViewById(R.id.buttonTangCartItem)
            txtCreateAt = itemView.findViewById(R.id.textViewCreatAtCartItem)
            checkBox = itemView.findViewById(R.id.checkBoxCartItem)
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayoutCartItem)
            layoutDelete = itemView.findViewById(R.id.layout_delete_cart_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_row, parent, false)
        return CartHolder(view)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val cartSanPham = cartSanPhamList[position]
        viewBinderHelper.bind(holder.swipeRevealLayout, cartSanPham.getId().toString())
        Picasso.get().load(cartSanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTen.text = cartSanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text = "${decimalFormat.format(cartSanPham.getGiaSanPham())} Đ"
        holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
        holder.txtCreateAt.text = "${cartSanPham.getCreateAt()?.date}/${cartSanPham.getCreateAt()?.month?.plus(1)}/${cartSanPham.getCreateAt()?.year?.plus(1900)}"
        if(cartSanPham.getSelected()==1){
            holder.checkBox.isChecked = true
            checkBoxChecked.checkedItem2(cartSanPham, true)
        }else if (cartSanPham.getSelected()==0){
            holder.checkBox.isChecked = false
        }
        if (cartSanPham.getSoLuong()>=cartSanPham.getSoLuongSanPham()){
            holder.buttonTang.visibility = View.INVISIBLE
            holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
        }
        if(cartSanPham.getSoLuong()<=1){
            holder.buttonGiam.visibility = View.INVISIBLE
            holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
        }

        holder.buttonTang.setOnClickListener {
            cartSanPham.setSoLuong(cartSanPham.getSoLuong()+1)
            cartSanPham.setTongTien(cartSanPham.getGiaSanPham() * cartSanPham.getSoLuong())
            holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            if(cartSanPham.getSoLuong()>=cartSanPham.getSoLuongSanPham()){
                holder.buttonTang.visibility = View.INVISIBLE
                cartSanPham.setSoLuong(cartSanPham.getSoLuongSanPham())
                holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            }
            if (cartSanPham.getSoLuong()>1){
                holder.buttonGiam.visibility = View.VISIBLE
            }
            checkBoxChecked.tangVaGiamSoLuongChange(cartSanPham)
        }
        holder.buttonGiam.setOnClickListener {
            cartSanPham.setSoLuong(cartSanPham.getSoLuong()-1)
            cartSanPham.setTongTien(cartSanPham.getGiaSanPham() * cartSanPham.getSoLuong())
            holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            if(cartSanPham.getSoLuong()<=1){
                holder.buttonGiam.visibility = View.INVISIBLE
                cartSanPham.setSoLuong(1)
                holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            }
            if(cartSanPham.getSoLuong()<cartSanPham.getSoLuongSanPham()){
                holder.buttonTang.visibility = View.VISIBLE
            }
            checkBoxChecked.tangVaGiamSoLuongChange(cartSanPham)
        }
        holder.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    cartSanPham.setSelected(1)
                    checkBoxChecked.checkedItem(cartSanPham, true)
                }else{
                    cartSanPham.setSelected(0)
                    checkBoxChecked.checkedItem(cartSanPham, false)
                }
            }
        })
        holder.layoutDelete.setOnClickListener {
            val sanpham = cartSanPhamList[holder.adapterPosition]
            cartSanPhamList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
            checkBoxChecked.deleteItem(sanpham)
        }
    }
    override fun getItemCount(): Int {
       return cartSanPhamList.size
    }
}
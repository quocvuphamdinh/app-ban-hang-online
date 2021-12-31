package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.Cart
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class RecyclerViewCartAdapter : RecyclerView.Adapter<RecyclerViewCartAdapter.CartHolder>{
   private var cartSanPhamList:ArrayList<CartSanPham> = ArrayList()
    private var checkBoxChecked:CheckBoxItem
    private var isSelectedAll=false

    constructor(checkBoxChecked: CheckBoxItem) : super() {
        this.checkBoxChecked = checkBoxChecked
    }

    interface CheckBoxItem{
        fun checkedItem(cartSanPham: CartSanPham, state:Boolean)
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

        init {
            imgHinh = itemView.findViewById(R.id.imageViewCartItem)
            txtTen = itemView.findViewById(R.id.textViewTenCartItem)
            txtGia = itemView.findViewById(R.id.textViewGiaCartItem)
            txtSoLuong = itemView.findViewById(R.id.textViewSoLuongCartItem)
            buttonGiam = itemView.findViewById(R.id.buttonGiamCartItem)
            buttonTang = itemView.findViewById(R.id.buttonTangCartItem)
            txtCreateAt = itemView.findViewById(R.id.textViewCreatAtCartItem)
            checkBox = itemView.findViewById(R.id.checkBoxCartItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_row, parent, false)
        return CartHolder(view)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val cartSanPham = cartSanPhamList[position]
        Picasso.get().load(cartSanPham.getHinhAnh()).into(holder.imgHinh)
        holder.txtTen.text = cartSanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text = "${decimalFormat.format(cartSanPham.getGiaSanPham())} Đ"
        holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
        holder.txtCreateAt.text = "${cartSanPham.getCreateAt()?.date}/${cartSanPham.getCreateAt()?.month?.plus(1)}/${cartSanPham.getCreateAt()?.year?.plus(1900)}"

        if(cartSanPham.getSoLuong()==10){
            holder.buttonTang.visibility = View.INVISIBLE
        }
        if(cartSanPham.getSoLuong()==1){
            holder.buttonGiam.visibility = View.INVISIBLE
        }

        if(!isSelectedAll){
            holder.checkBox.isChecked = false
        }
        if(isSelectedAll){
            holder.checkBox.isChecked = true
        }

        holder.buttonTang.setOnClickListener {
            cartSanPham.setSoLuong(cartSanPham.getSoLuong()+1)
            holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            if(cartSanPham.getSoLuong()>=10){
                holder.buttonTang.visibility = View.INVISIBLE
                cartSanPham.setSoLuong(10)
                holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            }
            if (cartSanPham.getSoLuong()>1){
                holder.buttonGiam.visibility = View.VISIBLE
            }
        }
        holder.buttonGiam.setOnClickListener {
            cartSanPham.setSoLuong(cartSanPham.getSoLuong()-1)
            holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            if(cartSanPham.getSoLuong()<=1){
                holder.buttonGiam.visibility = View.INVISIBLE
                cartSanPham.setSoLuong(1)
                holder.txtSoLuong.text = cartSanPham.getSoLuong().toString()
            }
            if(cartSanPham.getSoLuong()<10){
                holder.buttonTang.visibility = View.VISIBLE
            }
        }
        holder.checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    checkBoxChecked.checkedItem(cartSanPham, true)
                }else{
                    checkBoxChecked.checkedItem(cartSanPham, false)
                }
            }
        })
    }
    fun selectAll(){
        isSelectedAll = true
        notifyDataSetChanged()
    }
    fun unselectAll(){
        isSelectedAll = false
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
       return cartSanPhamList.size
    }
}
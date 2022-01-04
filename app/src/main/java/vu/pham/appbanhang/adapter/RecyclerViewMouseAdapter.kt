package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class RecyclerViewMouseAdapter : RecyclerView.Adapter<RecyclerViewMouseAdapter.ViewHolderMouse>, Filterable {

    private var mouseList:ArrayList<SanPham> = ArrayList()
    private var mouseListFiltered:ArrayList<SanPham> = ArrayList()
    private var clickItem:ClickItem

    constructor(clickItem: ClickItem) : super() {
        this.clickItem = clickItem
    }


    interface ClickItem{
        fun showItemInfo(sanPham: SanPham)
    }
    fun setData(lists:ArrayList<SanPham>){
        this.mouseList = lists
        this.mouseListFiltered = mouseList
        notifyDataSetChanged()
    }
    class ViewHolderMouse(itemView: View) :RecyclerView.ViewHolder(itemView){
        var imageView:ImageView
        var txtTen:TextView
        var txtGia:TextView
        var txtMoTa:TextView
        var imgSoldOut:ImageView

        init {
            imageView = itemView.findViewById(R.id.imageViewMouseItem)
            txtTen = itemView.findViewById(R.id.textViewTenMouseItem)
            txtGia = itemView.findViewById(R.id.textViewGiaMouseItem)
            txtMoTa = itemView.findViewById(R.id.textViewMoTaMouseItem)
            imgSoldOut = itemView.findViewById(R.id.imageViewSoldOutMouseItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMouse {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mouse_item_row, parent, false)
        return ViewHolderMouse(view)
    }

    override fun onBindViewHolder(holder: ViewHolderMouse, position: Int) {
        val sanPham = mouseListFiltered[position]
        Picasso.get().load(sanPham.getHinhAnh()).into(holder.imageView)
        holder.txtTen.text = sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGia.text = "Giá: ${decimalFormat.format(sanPham.getGiaSanPham())} Đ"
        holder.txtMoTa.text = sanPham.getMoTa().subSequence(0, 60).toString()+"..."
        if (sanPham.getSoLuongSanPham()<=0){
            holder.imgSoldOut.visibility = View.VISIBLE
        }else{
            holder.imgSoldOut.visibility = View.INVISIBLE
        }

        holder.imageView.setOnClickListener {
            clickItem.showItemInfo(sanPham)
        }
        holder.txtTen.setOnClickListener {
            clickItem.showItemInfo(sanPham)
        }
        holder.txtGia.setOnClickListener {
            clickItem.showItemInfo(sanPham)
        }
        holder.txtMoTa.setOnClickListener {
            clickItem.showItemInfo(sanPham)
        }
    }

    override fun getItemCount(): Int {
        return mouseListFiltered.size
    }

    override fun getFilter(): Filter {
        val filter = object :Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResult = FilterResults()
                if(constraint==null || constraint.isEmpty()){
                    filterResult.count = mouseList.size
                    filterResult.values = mouseList
                }else{
                    val resultMouseList:ArrayList<SanPham> = ArrayList()
                    val searchString =constraint.toString().trim()
                    for (i in 0 until mouseList.size){
                        if (mouseList[i].getTenSanPham().contains(searchString)){
                            resultMouseList.add(mouseList[i])
                        }
                        filterResult.count = resultMouseList.size
                        filterResult.values = resultMouseList
                    }
                }
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                mouseListFiltered = results?.values as ArrayList<SanPham>
                notifyDataSetChanged()
            }
        }
        return filter
    }
}
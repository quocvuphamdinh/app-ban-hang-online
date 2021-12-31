package vu.pham.appbanhang.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class RecyclerViewGameAdapter : RecyclerView.Adapter<RecyclerViewGameAdapter.GameHolder> {
    private var gameList:ArrayList<SanPham> = ArrayList()
    private var clickItem:CLickItem

    constructor(clickItem: CLickItem) : super() {
        this.clickItem = clickItem
    }

    interface CLickItem{
        fun showInfoItem(sanPham: SanPham)
    }
    fun setData(lists : ArrayList<SanPham>){
        this.gameList=lists
        notifyDataSetChanged()
    }

    class GameHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgViewItemGame:ImageView
        var txtNameItemGame:TextView
        var txtGiaItemGame:TextView
        var txtMoTaItemGame:TextView

        init {
            imgViewItemGame=itemView.findViewById(R.id.imageViewItemGameFragment)
            txtNameItemGame=itemView.findViewById(R.id.textViewNameItemGameFragment)
            txtGiaItemGame=itemView.findViewById(R.id.textViewGiaItemGameFragment)
            txtMoTaItemGame=itemView.findViewById(R.id.textViewMoTaItemGameFragment)
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_item_row, parent, false)
        return GameHolder(view)
    }

    override fun getItemCount(): Int {
       return gameList.size
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val sanPham = gameList[position]
        Picasso.get().load(sanPham.getHinhAnh()).into(holder.imgViewItemGame)
        holder.txtNameItemGame.text = sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        holder.txtGiaItemGame.text = "Giá: ${decimalFormat.format(sanPham.getGiaSanPham())} Đ"
        holder.txtMoTaItemGame.text = sanPham.getMoTa().subSequence(0, 70).toString()+"..."

        holder.imgViewItemGame.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
        holder.txtNameItemGame.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
        holder.txtGiaItemGame.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
        holder.txtMoTaItemGame.setOnClickListener {
            clickItem.showInfoItem(sanPham)
        }
    }
}
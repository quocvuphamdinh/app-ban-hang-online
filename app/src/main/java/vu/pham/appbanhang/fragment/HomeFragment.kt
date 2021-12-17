package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.adapter.RecyclerViewHomeAdapter
import vu.pham.appbanhang.adapter.RecylerViewHomeAdapter3
import vu.pham.appbanhang.model.SanPham

class HomeFragment :Fragment() {
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var txtViewAll: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var sanPhamList: ArrayList<SanPham>
    private lateinit var adapterHome: RecyclerViewHomeAdapter
    private lateinit var recylerView2: RecyclerView
    private lateinit var sanPhamList2: ArrayList<SanPham>
    private lateinit var adapterHome2: RecyclerViewHomeAdapter
    private lateinit var sanPhamBanChay:ArrayList<SanPham>
    private lateinit var adapterHome3 : RecylerViewHomeAdapter3
    private lateinit var recyclerView3: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.home_fragment, container, false)
        anhXa(view)
        actionViewFlipper()
        khoiTaoDataRecylerView()
        khoiTaoDataRecylerView2()
        khoiTaoDataRecylerView3()
        return view
    }

    private fun khoiTaoDataRecylerView3() {
        sanPhamBanChay= ArrayList()
        sanPhamBanChay.add(SanPham("PlayStation 5", 10000000, "Máy PlayStation 5", "https://br.atsit.in/vi/wp-content/uploads/2021/12/sony-playstation-hoat-dong-tren-ps5-choi-game-dam-may-gpu-kep-kha-nang-tuong-thich-nguoc-tren-the.jpg"))
        sanPhamBanChay.add(SanPham("Laptop Gaming MSI", 9000000, "Laptop chơi game", "https://laptopcu.cdn.vccloud.vn/wp-content/uploads/2017/04/MSI-GT80-Titan-Gaming-Laptop.jpg"))
        sanPhamBanChay.add(SanPham("Bàn phím cơ Logitech Pro X", 2890000, "Bàn phím chơi game", "https://www.playzone.vn/image/catalog/san%20pham/Logitech/keyboard/g-pro-x/3.jpg"))
        adapterHome3 = RecylerViewHomeAdapter3()
        adapterHome3.setData(sanPhamBanChay)
        recyclerView3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView3.adapter = adapterHome3
        recyclerView3.setHasFixedSize(true)
    }

    private fun khoiTaoDataRecylerView2() {
        sanPhamList2 = ArrayList()
        sanPhamList2.add(SanPham("GTA V", 2000000, "Game GTA V", "https://st.quantrimang.com/photos/image/2020/05/14/gta-5-mien-phi-640.jpg"))
        sanPhamList2.add(SanPham("GTA San Andreas", 3000000, "Game GTA San Andreas", "https://s3.cloud.cmctelecom.vn/tinhte2/2019/10/4816527_Cover_GTA.jpg"))
        sanPhamList2.add(SanPham("It Takes Two", 3000000, "Game It Take Two", "https://file.hstatic.net/1000231532/file/game_choi_hai_nguoi_ps5_xbox_series_pc_it_takes_two_f0d2613e06ae412fa1a3a44654902230.jpg"))
        sanPhamList2.add(SanPham("Resident Evil 8", 2500000, "Game Resident Evil 8", "https://br.atsit.in/vi/wp-content/uploads/2021/08/resident-evil-village-gia-nhap-danh-hieu-bach-kim-cua-capcom-resident-evil-7-dat-98-trieu-ban-sao-duoc-ban.png"))
        adapterHome2= RecyclerViewHomeAdapter()
        adapterHome2.setData(sanPhamList2)
        recylerView2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recylerView2.adapter=adapterHome2
        recylerView2.setHasFixedSize(true)
    }

    private fun khoiTaoDataRecylerView() {
        sanPhamList= ArrayList()
        sanPhamList.add(SanPham("Cyberpunk 2077", 1500000, "Game Cyberpunk", "https://media-cdn.laodong.vn/Storage/NewsPortal/2020/12/31/867051/Cyberpunk.jpg"))
        sanPhamList.add(SanPham("Call of Duty: Vanguard", 900000, "Game Call of Duty", "https://phegame.com.vn/wp-content/uploads/2021/08/VGD_SEE_THEM_RISE.jpg"))
        sanPhamList.add(SanPham("Far Cry 6", 1000000, "Game Far Cry", "https://hadoantv.com/wp-content/uploads/2021/10/download-far-cry-6-hadoan-tv.jpg"))
        sanPhamList.add(SanPham("OutRiders", 2000000, "Game OutRiders", "https://cdn.tgdd.vn/GameApp/4/238612/Screentshots/outriders-06-05-2021-2.jpg"))
        adapterHome= RecyclerViewHomeAdapter()
        adapterHome.setData(sanPhamList)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter=adapterHome
        recyclerView.setHasFixedSize(true)
    }

    private fun actionViewFlipper() {
        var mangQuangCao= ArrayList<String>()
        mangQuangCao.add("https://media-cdn.laodong.vn/Storage/NewsPortal/2020/12/31/867051/Cyberpunk.jpg")
        mangQuangCao.add("https://gstatic.gvn360.com/2020/05/Grand-Theft-Auto-poster.jpg")
        mangQuangCao.add("https://gamek.mediacdn.vn/133514250583805952/2021/9/28/photo-1-16327649179271935023979.jpg")
        mangQuangCao.add("https://gamek.mediacdn.vn/133514250583805952/2021/11/27/photo-1-1637989455867604907749.jpg")
        for(i in 0 until mangQuangCao.size){
            val imageView= ImageView(context)
            Picasso.get().load(mangQuangCao[i]).into(imageView)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            viewFlipper.addView(imageView)
        }
        viewFlipper.flipInterval = 5000 //chạy khoảng 5s
        viewFlipper.isAutoStart = true // cho tự động chạy
        //animation
        val viewFlipperIn = AnimationUtils.loadAnimation(context, R.anim.view_flipper_in)
        val viewFlipperOut = AnimationUtils.loadAnimation(context, R.anim.view_flipper_out)
        viewFlipper.inAnimation=viewFlipperIn
        viewFlipper.outAnimation=viewFlipperOut
    }

    private fun anhXa(view: View) {
        viewFlipper = view.findViewById(R.id.viewFlipperHome)
        txtViewAll = view.findViewById(R.id.textviewViewAll)
        recyclerView = view.findViewById(R.id.recylerViewHome)
        recylerView2=view.findViewById(R.id.recylerViewHome2)
        recyclerView3=view.findViewById(R.id.recylerViewHome3)
    }
}
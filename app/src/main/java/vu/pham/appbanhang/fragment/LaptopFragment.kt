package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TabHost
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.adapter.RecyclerViewHomeAdapter
import vu.pham.appbanhang.model.SanPham
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator
import vu.pham.appbanhang.adapter.LaptopViewPagerAdapter
import vu.pham.appbanhang.adapter.RecyclerViewLaptop


class LaptopFragment : Fragment() {

    private lateinit var tabHost:TabHost
    private lateinit var recylerView1:RecyclerView
    private lateinit var recylerView2:RecyclerView
    private lateinit var recylerView3:RecyclerView
    private lateinit var imgViewLaptop1:ImageView
    private lateinit var imgViewLaptop2:ImageView
    private lateinit var laptopTatCa:ArrayList<SanPham>
    private lateinit var laptopGaming:ArrayList<SanPham>
    private lateinit var laptopVanPhong:ArrayList<SanPham>
    private lateinit var adapterTatCa: RecyclerViewHomeAdapter
    private lateinit var adapterGaming: RecyclerViewLaptop
    private lateinit var adapterVanPhong: RecyclerViewLaptop
    private lateinit var viewPager:ViewPager
    private lateinit var circleIndicator:CircleIndicator
    private lateinit var viewPagerLists:ArrayList<SanPham>
    private val handler: Handler = Handler()
    private val runnable = Runnable { kotlin.run {
        if(viewPager.currentItem == viewPagerLists.size-1){
            viewPager.currentItem=0
        }else{
            viewPager.currentItem = viewPager.currentItem+1
        }
    }}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.laptop_fragment, container, false)
        anhXa(view)
        khoiTaoDataLaptopTatCa()
        khoiTaoDataLapTopGaming()
        khoiTaoDataLapTopVanPhong()
        khoitaoDataViewPager()
        return view
    }

    private fun khoitaoDataViewPager() {
        val laptopViewPagerAdapter = LaptopViewPagerAdapter()
        viewPagerLists = ArrayList()
        viewPagerLists.add(SanPham("Laptop Gaming MSI", 9000000, "Laptop chơi game", "https://laptopcu.cdn.vccloud.vn/wp-content/uploads/2017/04/MSI-GT80-Titan-Gaming-Laptop.jpg"))
        viewPagerLists.add(SanPham("Laptop Acer Nitro 5", 22390000, "Laptop chơi game", "https://img.websosanh.vn/v10/users/review/images/5pytfe7wy6qfi/131png.jpg?compress=85"))
        viewPagerLists.add(SanPham("Laptop ASUS Gaming FX506LH-HN002T", 20890000, "Laptop Gaming", "https://fptshop.com.vn/Uploads/images/2015/Tin-Tuc/QuanLNH2/asus-tuf-fx-506-19.jpg"))
        viewPagerLists.add(SanPham("Laptop Dell Gaming G15 Ryzen Edition", 28390000, "Laptop Gaming", "https://lumen.thinkpro.vn//backend/uploads/baiviet/2021/7/6/Dell%20Gaming%20G15%205515%20Ryzen%20Edition%20khuyen%20mai%20-5-2.jpg"))
        laptopViewPagerAdapter.setData(viewPagerLists, R.layout.view_pager_laptop_item)
        viewPager.adapter = laptopViewPagerAdapter
        //liên kết indicator với viewpager
        circleIndicator.setViewPager(viewPager)
        /////
        handler.postDelayed(runnable, 2000)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }


    private fun khoiTaoDataLapTopVanPhong() {
        Picasso.get().load("https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/macbook-pro-13-og-202011?wid=600&hei=315&fmt=jpeg&qlt=95&.v=1604347427000")
            .into(imgViewLaptop1)
        Picasso.get().load("https://www.anphatpc.com.vn/media/news/1601_top1.jpg").into(imgViewLaptop2)
        laptopVanPhong = ArrayList()
        laptopVanPhong.add(SanPham("MacBook Air M1", 29900000, "MacBook M1", "https://mac24h.vn/images/thumbnails/350/268/detailed/48/macbook-air_m1-2021-_MAC24H.png?t=1629457233"))
        laptopVanPhong.add(SanPham("Laptop Asus VivoBook", 18990000, "Laptop văn phòng", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/1000x/040ec09b1e35df139433887a97daa66f/_/0/_0004_asus-vivobook-a515ea-bq498t_65db.jpg"))
        adapterVanPhong = RecyclerViewLaptop()
        adapterVanPhong.setData(laptopVanPhong, R.layout.laptop_item_row)
        recylerView3.layoutManager = LinearLayoutManager(context)
        recylerView3.adapter = adapterVanPhong
        recylerView3.setHasFixedSize(true)
    }

    private fun khoiTaoDataLapTopGaming() {
        laptopGaming = ArrayList()
        laptopGaming.add(SanPham("Laptop Gaming MSI", 9000000, "Laptop chơi game", "https://tangiahuy.vn/wp-content/uploads/2021/11/MSI-Gaming-GF65-Thin-10UE-228VN.jpg"))
        laptopGaming.add(SanPham("Laptop Acer Nitro 5", 22390000, "Laptop chơi game", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/9df78eab33525d08d6e5fb8d27136e95/_/0/_0004_acer-nitro-5-an515-45-r86d-r7-58.jpg"))
        laptopGaming.add(SanPham("Laptop ASUS Gaming FX506LH-HN002T", 20890000, "Laptop Gaming", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/1000x/040ec09b1e35df139433887a97daa66f/l/a/laptop-asus-tuf-gaming-f15-fx506lh_4_.jpg"))
        laptopGaming.add(SanPham("Laptop Dell Gaming G15 Ryzen Edition", 28390000, "Laptop Gaming", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/9df78eab33525d08d6e5fb8d27136e95/_/0/_0003_dell-gaming-g15-5515-70266675_09_2.jpg"))
        adapterGaming = RecyclerViewLaptop()
        adapterGaming.setData(laptopGaming, R.layout.laptop_item_row)
        recylerView2.layoutManager = LinearLayoutManager(context)
        recylerView2.adapter = adapterGaming
        recylerView2.setHasFixedSize(true)
    }

    private fun khoiTaoDataLaptopTatCa() {
        laptopTatCa = ArrayList()
        laptopTatCa.add(SanPham("Laptop Gaming MSI", 9000000, "Laptop chơi game", "https://tangiahuy.vn/wp-content/uploads/2021/11/MSI-Gaming-GF65-Thin-10UE-228VN.jpg"))
        laptopTatCa.add(SanPham("Laptop Acer Nitro 5", 22390000, "Laptop chơi game", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/9df78eab33525d08d6e5fb8d27136e95/_/0/_0004_acer-nitro-5-an515-45-r86d-r7-58.jpg"))
        laptopTatCa.add(SanPham("Laptop Asus VivoBook", 18990000, "Laptop văn phòng", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/1000x/040ec09b1e35df139433887a97daa66f/_/0/_0004_asus-vivobook-a515ea-bq498t_65db.jpg"))
        laptopTatCa.add(SanPham("MacBook Air M1", 29900000, "MacBook M1", "https://mac24h.vn/images/thumbnails/350/268/detailed/48/macbook-air_m1-2021-_MAC24H.png?t=1629457233"))
        laptopTatCa.add(SanPham("Laptop ASUS Gaming FX506LH-HN002T", 20890000, "Laptop Gaming", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/1000x/040ec09b1e35df139433887a97daa66f/l/a/laptop-asus-tuf-gaming-f15-fx506lh_4_.jpg"))
        laptopTatCa.add(SanPham("Laptop Dell Gaming G15 Ryzen Edition", 28390000, "Laptop Gaming", "https://cdn.cellphones.com.vn/media/catalog/product/cache/7/image/9df78eab33525d08d6e5fb8d27136e95/_/0/_0003_dell-gaming-g15-5515-70266675_09_2.jpg"))
        adapterTatCa = RecyclerViewHomeAdapter()
        adapterTatCa.setData(laptopTatCa)
        recylerView1.layoutManager = GridLayoutManager(context, 2)
        recylerView1.adapter = adapterTatCa
        recylerView1.setHasFixedSize(true)
    }

    private fun anhXa(view: View) {
        recylerView1=view.findViewById(R.id.recylerViewLaptopFragment_1)
        recylerView2=view.findViewById(R.id.recylerViewLaptopFragment_2)
        recylerView3=view.findViewById(R.id.recylerViewLaptopFragment_3)
        imgViewLaptop1=view.findViewById(R.id.imageViewLaptop1)
        imgViewLaptop2=view.findViewById(R.id.imageViewLaptop2)
        viewPager=view.findViewById(R.id.viewPagerLaptop)
        circleIndicator=view.findViewById(R.id.circuleIndicatorLaptop)

        tabHost = view.findViewById(R.id.tabHostLaptopFragment)
        tabHost.setup()

        val tab1:TabHost.TabSpec=tabHost.newTabSpec("t1")
        tab1.setIndicator("Tất cả")
        tab1.setContent(R.id.tab1)
        tabHost.addTab(tab1)
        // thay đổi cỡ chữ trên tab
        val x = tabHost.tabWidget.getChildAt(0).findViewById<View>(android.R.id.title) as TextView
        x.textSize = 10f

        val tab2:TabHost.TabSpec=tabHost.newTabSpec("t2")
        tab2.setIndicator("Gaming")
        tab2.setContent(R.id.tab2)
        tabHost.addTab(tab2)
        val x2 = tabHost.tabWidget.getChildAt(1).findViewById<View>(android.R.id.title) as TextView
        x2.textSize = 10f

        val tab3:TabHost.TabSpec=tabHost.newTabSpec("t3")
        tab3.setIndicator("Văn phòng")
        tab3.setContent(R.id.tab3)
        tabHost.addTab(tab3)
        val x3 = tabHost.tabWidget.getChildAt(2).findViewById<View>(android.R.id.title) as TextView
        x3.textSize = 10f
    }
}
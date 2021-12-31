package vu.pham.appbanhang.fragment

import android.content.Intent
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
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.squareup.picasso.Picasso
import me.relex.circleindicator.CircleIndicator
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.LaptopViewPagerAdapter
import vu.pham.appbanhang.adapter.RecyclerViewLaptopAdapter
import vu.pham.appbanhang.loaddata.GetListLoai
import vu.pham.appbanhang.loaddata.GetListQuangCao
import vu.pham.appbanhang.loaddata.GetListSanPham
import vu.pham.appbanhang.loaddata.GetSanPham
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.model.QuangCao


class LaptopFragment : Fragment() {
    companion object{
        private var isShow=false
        private var LAPTOP_ID=7
    }
    private lateinit var getListQuangCao:LoaderManager.LoaderCallbacks<ArrayList<QuangCao>>
    private lateinit var getLaptopAllLoader:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private lateinit var getLaptopVanPhongPhoBien:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private lateinit var laptopLoader:LoaderManager.LoaderCallbacks<SanPham>
    private val LAPTOP_ALL_ID=3
    private val LAPTOP_VANPHONG_ID=5
    private val QUANGCAO_ID=6
    private lateinit var homeActivity: HomeActivity
    private lateinit var tabHost:TabHost
    private lateinit var recylerView1:RecyclerView
    private lateinit var recylerView2:RecyclerView
    private lateinit var recylerView3:RecyclerView
    private lateinit var imgViewLaptop1:ImageView
    private lateinit var imgViewLaptop2:ImageView
    private lateinit var laptopTatCa:ArrayList<SanPham>
    private lateinit var laptopGaming:ArrayList<SanPham>
    private lateinit var laptopVanPhong:ArrayList<SanPham>
    private lateinit var laptopVanPhongMacbook:ArrayList<SanPham>
    private lateinit var laptopVanPhongBinhThuong:ArrayList<SanPham>
    private lateinit var adapterTatCa: RecyclerViewHomeAdapter
    private lateinit var adapterGaming: RecyclerViewLaptopAdapter
    private lateinit var adapterVanPhong: RecyclerViewLaptopAdapter
    private lateinit var viewPager:ViewPager
    private lateinit var circleIndicator:CircleIndicator
    private lateinit var quangCaoList:ArrayList<QuangCao>
    private val sqlGetAllLaptop="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham INNER JOIN loaisanpham ON sanpham.loai_id=loaisanpham.id WHERE sanpham.loai_id=3 OR sanpham.loai_id=7 OR sanpham.loai_id=9 ORDER BY id DESC"
    private val sqlGetListLaptopVanPhongPhoBien="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham \n" +
            "INNER JOIN favorite_sanpham ON sanpham.id = favorite_sanpham.sanpham_id \n" +
            "INNER JOIN loaisanpham ON sanpham.loai_id=loaisanpham.id \n" +
            "WHERE (favorite_sanpham.favorite_id = 3 OR favorite_sanpham.favorite_id=2) AND (sanpham.loai_id =7 OR sanpham.loai_id=9)\n" +
            "GROUP BY sanpham.id ORDER BY sanpham.id DESC"
    private val sqlGetQuangCaoLaptopGaming="SELECT quangcao.* FROM quangcao\n" +
            "INNER JOIN sanpham ON sanpham.id = quangcao.sanpham_id\n" +
            "WHERE sanpham.loai_id = 3"
    private val handler: Handler = Handler()
    private val runnable = Runnable { kotlin.run {
        if(viewPager.currentItem == quangCaoList.size-1){
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
        initArrayList()
        getListQuangCao()
        khoiTaoDataLaptopTatCa()
        khoiTaoDataLapTopGaming()
        khoiTaoDataLapTopVanPhong()
        initLaptopALlLoader()
        initLaptopVanPhong()

        imgViewLaptop1.setOnClickListener {
            homeActivity.sendDataToListFragment(laptopVanPhongMacbook)
        }
        imgViewLaptop2.setOnClickListener {
            homeActivity.sendDataToListFragment(laptopVanPhongBinhThuong)
        }
        return view
    }

    private fun getListQuangCao(){
        getListQuangCao = object : LoaderManager.LoaderCallbacks<ArrayList<QuangCao>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<QuangCao>> {
                return context?.let { GetListQuangCao(it, sqlGetQuangCaoLaptopGaming) }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<QuangCao>>,
                data: ArrayList<QuangCao>?
            ) {
                if (data != null) {
                    quangCaoList = data
                    khoitaoDataViewPager()
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<QuangCao>>) {
                quangCaoList.clear()
            }
        }
        loaderManager.initLoader(QUANGCAO_ID, null, getListQuangCao)
    }
    private fun initLaptopVanPhong() {
       getLaptopVanPhongPhoBien = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
           override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
               return context?.let { GetListSanPham(it, sqlGetListLaptopVanPhongPhoBien) }!!
           }

           override fun onLoadFinished(
               loader: Loader<ArrayList<SanPham>>,
               data: ArrayList<SanPham>?
           ) {
               if (data != null) {
                   laptopVanPhong = data
                   adapterVanPhong.setData(laptopVanPhong, R.layout.laptop_item_row)
               }
           }

           override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
               laptopVanPhong.clear()
           }
       }
        loaderManager.initLoader(LAPTOP_VANPHONG_ID, null, getLaptopVanPhongPhoBien)
    }

    private fun initLaptopBinhThuong(){
        for(i in 0 until laptopTatCa.size){
            if (laptopTatCa[i].getLoai()==7L){
                laptopVanPhongBinhThuong.add(laptopTatCa[i])
            }
        }
    }
    private fun initLaptopMacBook(){
        for(i in 0 until laptopTatCa.size){
            if (laptopTatCa[i].getLoai()==9L){
                laptopVanPhongMacbook.add(laptopTatCa[i])
            }
        }
    }
    private fun initLaptopGaming(){
        for(i in 0 until laptopTatCa.size){
            if (laptopTatCa[i].getLoai()==3L){
                laptopGaming.add(laptopTatCa[i])
            }
        }
        adapterGaming.setData(laptopGaming, R.layout.laptop_item_row)
    }
    private fun initLaptopALlLoader(){
        getLaptopAllLoader = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
                return context?.let { GetListSanPham(it, sqlGetAllLaptop) }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<SanPham>>,
                data: ArrayList<SanPham>?
            ) {
                if (data != null) {
                    laptopTatCa = data
                    adapterTatCa.setData(laptopTatCa)
                    if(laptopGaming.size==0){
                        initLaptopGaming()
                    }
                    if(laptopVanPhongMacbook.size==0){
                        initLaptopMacBook()
                    }
                    if(laptopVanPhongBinhThuong.size==0){
                        initLaptopBinhThuong()
                    }
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
                laptopTatCa.clear()
            }

        }
        loaderManager.initLoader(LAPTOP_ALL_ID, null, getLaptopAllLoader)
    }
    private fun getLaptopById(idLaptop:Long){
        laptopLoader = object : LoaderManager.LoaderCallbacks<SanPham>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<SanPham> {
                return context?.let { GetSanPham(it, "SELECT sanpham.*, loaisanpham.tenloai FROM sanpham\n" +
                        "INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id\n" +
                        "WHERE sanpham.id = $idLaptop") }!!
            }

            override fun onLoadFinished(loader: Loader<SanPham>, data: SanPham?) {
                if(data!=null){
                    if(!isShow){
                        homeActivity.sendDataToDetailActivity(data)
                    }
                    LAPTOP_ID+=1
                    isShow = true
                }
            }

            override fun onLoaderReset(loader: Loader<SanPham>) {

            }
        }
        loaderManager.initLoader(LAPTOP_ID, null, laptopLoader)
    }
    private fun khoitaoDataViewPager() {
        val laptopViewPagerAdapter = LaptopViewPagerAdapter(object : LaptopViewPagerAdapter.CLickItem{
            override fun showInfoItem(photo: QuangCao) {
                isShow = false
                getLaptopById(photo.getSanPhamID())
            }
        })
        laptopViewPagerAdapter.setData(quangCaoList, R.layout.view_pager_laptop_item, context)
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
        adapterVanPhong = RecyclerViewLaptopAdapter(object : RecyclerViewLaptopAdapter.ClickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recylerView3.layoutManager = LinearLayoutManager(context)
        recylerView3.adapter = adapterVanPhong
        recylerView3.setHasFixedSize(true)
    }

    private fun khoiTaoDataLapTopGaming() {
        adapterGaming = RecyclerViewLaptopAdapter(object : RecyclerViewLaptopAdapter.ClickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recylerView2.layoutManager = LinearLayoutManager(context)
        recylerView2.adapter = adapterGaming
        recylerView2.setHasFixedSize(true)
    }

    private fun khoiTaoDataLaptopTatCa() {
        adapterTatCa = RecyclerViewHomeAdapter(object : RecyclerViewHomeAdapter.ClickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recylerView1.layoutManager = GridLayoutManager(context, 2)
        recylerView1.adapter = adapterTatCa
        recylerView1.setHasFixedSize(true)
    }
    private fun initArrayList(){
        quangCaoList = ArrayList()
        laptopTatCa = ArrayList()
        laptopGaming = ArrayList()
        laptopVanPhong = ArrayList()
        laptopVanPhongMacbook = ArrayList()
        laptopVanPhongBinhThuong = ArrayList()
    }

    private fun anhXa(view: View) {
        recylerView1=view.findViewById(R.id.recylerViewLaptopFragment_1)
        recylerView2=view.findViewById(R.id.recylerViewLaptopFragment_2)
        recylerView3=view.findViewById(R.id.recylerViewLaptopFragment_3)
        imgViewLaptop1=view.findViewById(R.id.imageViewLaptop1)
        imgViewLaptop2=view.findViewById(R.id.imageViewLaptop2)
        viewPager=view.findViewById(R.id.viewPagerLaptop)
        circleIndicator=view.findViewById(R.id.circuleIndicatorLaptop)
        homeActivity = activity as HomeActivity
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
package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.adapter.RecyclerViewDonHangAdapter
import vu.pham.appbanhang.loaddata.GetListCartSanPham
import vu.pham.appbanhang.loaddata.GetListDonHang
import vu.pham.appbanhang.model.Cart
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.DonHang
import vu.pham.appbanhang.model.User

class DonHangFragment : Fragment() {

    private lateinit var user: User
    private lateinit var recyclerViewDonHang:RecyclerView
    private lateinit var donHangList:ArrayList<DonHang>
    private lateinit var cartList:ArrayList<CartSanPham>
    private lateinit var adapterDonHang:RecyclerViewDonHangAdapter
    private lateinit var donHangLoader : LoaderManager.LoaderCallbacks<ArrayList<DonHang>>
    private lateinit var cartLoader:LoaderManager.LoaderCallbacks<ArrayList<CartSanPham>>
    private val DONHANG_ID=1
    private val CART_ID=2
    private var checkDonHang=false
    private var checkCart = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.donhang_fragment, container, false)

        anhXa(view)
        initAdapter()
        getUser()
        return view
    }

    private fun getUser() {
        user = arguments?.getParcelable("userProfile")!!
        getListDonHang()
        getListCart()
    }

    private fun getListCart(){
        cartLoader = object : LoaderManager.LoaderCallbacks<ArrayList<CartSanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<CartSanPham>> {
                return context?.let { GetListCartSanPham(it, "SELECT giohang.*, sanpham.tensanpham, sanpham.giasanpham, sanpham.hinhanh, sanpham.soluongsanpham FROM giohang \n" +
                        "INNER JOIN sanpham ON giohang.sanpham_id = sanpham.id\n" +
                        "WHERE giohang.user_id= ${user.getId()} AND giohang.deleted = 1") }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<CartSanPham>>,
                data: ArrayList<CartSanPham>?
            ) {
                if (data!=null){
                    cartList = data
                    checkCart = true
                    if (checkCart && checkDonHang){
                        adapterDonHang.setData(donHangList, cartList)
                    }
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<CartSanPham>>) {
                cartList.clear()
            }
        }
        loaderManager.initLoader(CART_ID, null, cartLoader)
    }
    private fun getListDonHang() {
        donHangLoader = object : LoaderManager.LoaderCallbacks<ArrayList<DonHang>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<DonHang>> {
                return context?.let { GetListDonHang(it, "SELECT donhang.*, diachi_user.address, giohang_status.name FROM donhang\n" +
                        "INNER JOIN diachi_user ON donhang.address_user = diachi_user.id\n" +
                        "INNER JOIN giohang_status ON donhang.trangthai = giohang_status.id\n" +
                        "WHERE donhang.user_id = ${user.getId()} ORDER BY donhang.time_dat_hang DESC") }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<DonHang>>,
                data: ArrayList<DonHang>?
            ) {
                if (data!=null){
                    donHangList = data
                    checkDonHang = true
                    if (checkCart && checkDonHang){
                        adapterDonHang.setData(donHangList, cartList)
                    }
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<DonHang>>) {
                donHangList.clear()
            }
        }
        loaderManager.initLoader(DONHANG_ID, null, donHangLoader)
    }

    private fun initAdapter(){
        adapterDonHang = RecyclerViewDonHangAdapter()
        recyclerViewDonHang.layoutManager = LinearLayoutManager(context)
        recyclerViewDonHang.adapter = adapterDonHang
        recyclerViewDonHang.setHasFixedSize(true)
    }
    private fun anhXa(view: View) {
        recyclerViewDonHang = view.findViewById(R.id.recyclerViewDonHang)
        donHangList= ArrayList()
        cartList = ArrayList()
    }
}
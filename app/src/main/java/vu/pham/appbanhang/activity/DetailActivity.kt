package vu.pham.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.loaddata.GetCart
import vu.pham.appbanhang.loaddata.Insert
import vu.pham.appbanhang.loaddata.Update
import vu.pham.appbanhang.model.Cart
import vu.pham.appbanhang.model.SanPham
import vu.pham.appbanhang.model.User
import java.sql.Timestamp
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var insertCartLoader:LoaderManager.LoaderCallbacks<Long>
    private lateinit var checkCartLoader:LoaderManager.LoaderCallbacks<Cart>
    private lateinit var updateCartLoader:LoaderManager.LoaderCallbacks<Boolean>
    private var UPDATE_CART_ID=2
    private var CHECK_CART_ID=3
    private val INSERT_CART_ID=1
    private lateinit var imgBack:ImageView
    private lateinit var imgHinh:ImageView
    private lateinit var txtTenSanPham:TextView
    private lateinit var txtGiaSanPham:TextView
    private lateinit var spinner: Spinner
    private lateinit var buttonAddToCart : Button
    private lateinit var txtMoTaChiTiet:TextView
    private lateinit var txtLoaiSanPham :TextView
    private lateinit var txtSoLuongHienCo:TextView
    private lateinit var sanPham: SanPham
    private lateinit var user: User
    private var listSoluong:ArrayList<Int> = ArrayList()
    private lateinit var adapterSpinner : ArrayAdapter<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        anhXa()
        getDataDetail()
        khoiTaoSpinner()
        buttonAddToCart.setOnClickListener {
            checkCart()
        }
        imgBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
    }

    private fun checkCart(){
        checkCartLoader = object : LoaderManager.LoaderCallbacks<Cart>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cart> {
                return GetCart(this@DetailActivity, "SELECT * FROM giohang WHERE user_id = ${user.getId()} AND sanpham_id = ${sanPham.getId()} AND deleted = 0")
            }

            override fun onLoadFinished(loader: Loader<Cart>, data: Cart?) {
                if (data!=null) {
                    if (data.getId() == 0L) {
                        themVaoGioHang()
                    } else {
                        updateGioHang(data.getSoLuong())
                    }
                }
                CHECK_CART_ID+=1
            }

            override fun onLoaderReset(loader: Loader<Cart>) {

            }
        }
        supportLoaderManager.initLoader(CHECK_CART_ID, null, checkCartLoader)
    }

    private fun updateGioHang(sl:Int) {
        val timeNow = Timestamp(System.currentTimeMillis())
        val soluong = spinner.selectedItem.toString()
        var soluongNewInt=soluong.toInt() + sl
        if (soluongNewInt >= sanPham.getSoLuongSanPham()){
            soluongNewInt = sanPham.getSoLuongSanPham()
        }
        val tongtien = sanPham.getGiaSanPham() * soluongNewInt
        updateCartLoader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return Update(this@DetailActivity, "UPDATE giohang SET soluong = $soluongNewInt, tongtien = $tongtien, update_at='$timeNow' WHERE user_id = ${user.getId()} AND sanpham_id = ${sanPham.getId()}")
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if (data!=null){
                    if (data){
                        Toast.makeText(this@DetailActivity, "Thêm giỏ hàng thành công", Toast.LENGTH_SHORT).show()
                        finish()
                        overridePendingTransition(0, 0)
                    }else{
                        Toast.makeText(this@DetailActivity, "Thêm giỏ hàng thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        supportLoaderManager.initLoader(UPDATE_CART_ID, null, updateCartLoader)
    }

    private fun themVaoGioHang() {
        val soluong = spinner.selectedItem.toString()
        val timeNow = Timestamp(System.currentTimeMillis())
        val soluongInt=soluong.toInt()
        val tongtien = sanPham.getGiaSanPham() * soluongInt
        insertCartLoader = object : LoaderManager.LoaderCallbacks<Long>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Long> {
                val sql="INSERT INTO giohang (user_id, sanpham_id, create_at, soluong, tongtien) VALUES (${user.getId()}, ${sanPham.getId()}, '${timeNow}', ${soluong}, ${tongtien})"
                return Insert(this@DetailActivity, sql)
            }

            override fun onLoadFinished(loader: Loader<Long>, data: Long?) {
                if (data!=null){
                    Toast.makeText(this@DetailActivity, "Thêm vào giỏ hàng thành công !", Toast.LENGTH_LONG).show()
                    finish()
                    overridePendingTransition(0, 0)
                }else{
                    Toast.makeText(this@DetailActivity, "Thêm vào giỏ hàng thất bại !", Toast.LENGTH_LONG).show()
                }
            }

            override fun onLoaderReset(loader: Loader<Long>) {

            }
        }
        supportLoaderManager.initLoader(INSERT_CART_ID, null, insertCartLoader)
    }


    private fun khoiTaoSpinner() {
        if(sanPham.getSoLuongSanPham()<10){
            for (i in 1 .. sanPham.getSoLuongSanPham()){
                listSoluong.add(i)
            }
        }else{
            for (i in 1 until 11){
                listSoluong.add(i)
            }
        }
        adapterSpinner = ArrayAdapter(this@DetailActivity, android.R.layout.simple_list_item_1, listSoluong)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner
    }

    private fun getDataDetail(){
        sanPham = intent.extras?.getParcelable("sanpham")!!
        user = intent.extras?.getParcelable("userCart")!!
        Picasso.get().load(sanPham.getHinhAnh()).into(imgHinh)
        txtTenSanPham.text = sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        txtGiaSanPham.text = "Giá: ${decimalFormat.format(sanPham.getGiaSanPham())} Đ"
        txtLoaiSanPham.text = "Loại sản phẩm: ${sanPham.getLoaiName()}"
        txtMoTaChiTiet.text = sanPham.getMoTa()
        txtSoLuongHienCo.text = "Số lượng hiện có: ${sanPham.getSoLuongSanPham()}"
    }

    private fun anhXa() {
        imgHinh = findViewById(R.id.imageViewDetail)
        txtTenSanPham = findViewById(R.id.textViewTenDetail)
        txtGiaSanPham = findViewById(R.id.textViewGiaDetail)
        spinner = findViewById(R.id.spinnerDetail)
        buttonAddToCart = findViewById(R.id.buttonThemGioHang)
        txtMoTaChiTiet = findViewById(R.id.textViewMoTaChiTietSanPham)
        txtLoaiSanPham = findViewById(R.id.textViewLoaiSanPhamDetail)
        imgBack = findViewById(R.id.imageViewToolBarDetailActivity)
        txtSoLuongHienCo = findViewById(R.id.textViewSoLuongSanPhamHienCo)
    }
}
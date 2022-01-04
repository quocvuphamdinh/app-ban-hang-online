package vu.pham.appbanhang.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import vu.pham.appbanhang.R
import vu.pham.appbanhang.adapter.ListCartThanhToanAdapter
import vu.pham.appbanhang.loaddata.GetDiaChiUser
import vu.pham.appbanhang.loaddata.Insert
import vu.pham.appbanhang.loaddata.Update
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.DiaChi
import vu.pham.appbanhang.model.DonHang
import vu.pham.appbanhang.model.User
import java.security.SecureRandom
import java.sql.Timestamp
import java.text.DecimalFormat

class ThanhToanActivity : AppCompatActivity() {
    private lateinit var cardViewDiaChi:CardView
    private lateinit var listViewCart:ListView
    private lateinit var txtHoTenVaSDT:TextView
    private lateinit var txtDiaChiNhanHang:TextView
    private lateinit var txtTongTien:TextView
    private lateinit var buttonDatHang:Button
    private lateinit var txtAddDiaChi:TextView
    private lateinit var toolBarThanhToan:Toolbar
    private lateinit var diaChiLoader:LoaderManager.LoaderCallbacks<DiaChi>
    private lateinit var datHangLoader:LoaderManager.LoaderCallbacks<Long>
    private lateinit var gioHangUpdateLoader:LoaderManager.LoaderCallbacks<Boolean>
    private lateinit var sanPhamUpdateLoader:LoaderManager.LoaderCallbacks<Boolean>
    private lateinit var user: User
    private lateinit var cartList:List<CartSanPham>
    private lateinit var adapterCart:ListCartThanhToanAdapter
    private lateinit var diaChiUser:DiaChi
    private var DIACHI_ID=1
    private var DONHANG_ID=100
    private var GIOHANG_UPDATE_ID=300
    private var size=0
    private var size2=0
    private var size3=0
    private var SANPHAM_UPDATE_ID=500
    private var random:SecureRandom = SecureRandom()
    private var check1=false
    private var check2=false
    private var check3=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thanh_toan)

        anhXa()
        initToolBar()
        getData()
        showCartInListView()
        tinhTongThanhToan()

        txtAddDiaChi.setOnClickListener {
            goToDiaChiPage()
        }
        cardViewDiaChi.setOnClickListener {
            goToDiaChiPage()
        }
        buttonDatHang.setOnClickListener {
            if (diaChiUser.getId()==0L){
                Toast.makeText(this@ThanhToanActivity, "Bạn chưa chọn địa chỉ đặt hàng", Toast.LENGTH_SHORT).show()
            }else{
                for (i in  0 until cartList.size){
                    updateSanPham(cartList[i])
                    updateGioHang(cartList[i].getId())
                    xacNhanDatHang(cartList[i])
                }
            }
        }
    }

    private fun getRandomName(lenght:Int) : String{
        val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
        val name = StringBuilder("")
        for (i in 0 until lenght){
            val index = random.nextInt(alphabet.length)
            name.append(alphabet.substring(index, index+1))
        }
        return name.toString()
    }

    private fun updateSanPham(cartSanPhamUpdate:CartSanPham){
        SANPHAM_UPDATE_ID+=1
        val soLuongNew = cartSanPhamUpdate.getSoLuongSanPham() - cartSanPhamUpdate.getSoLuong()
        sanPhamUpdateLoader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return Update(this@ThanhToanActivity, "UPDATE sanpham SET soluongsanpham = $soLuongNew WHERE id = ${cartSanPhamUpdate.getSanPhamId()}")
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if (data!=null){
                    if (data){
                        size2+=1
                        if (size2==cartList.size){
                            check1 = true
                            if (check1&&check2&&check3){
                                finish()
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
                            }
                        }
                    }else{
                        Toast.makeText(this@ThanhToanActivity, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ThanhToanActivity, "Cập nhật sản phẩm thất bại 2", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        supportLoaderManager.initLoader(SANPHAM_UPDATE_ID, null, sanPhamUpdateLoader)
    }
    private fun updateGioHang(idCart:Long) {
        GIOHANG_UPDATE_ID+=1
        gioHangUpdateLoader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return Update(this@ThanhToanActivity, "UPDATE giohang SET deleted= 1, selected = 0 WHERE id = $idCart")
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if (data!=null){
                    if (data){
                       size+=1
                        if (size==cartList.size){
                            check2 = true
                            if (check1&&check2&&check3){
                                finish()
                                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
                            }
                        }
                    }else{
                        Toast.makeText(this@ThanhToanActivity, "Lỗi giỏ hàng !", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ThanhToanActivity, "Cập nhật giỏ hàng thất bại", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        supportLoaderManager.initLoader(GIOHANG_UPDATE_ID, null, gioHangUpdateLoader)
    }

    private fun xacNhanDatHang(cart:CartSanPham) {
        val tongTien=cart.getTongTien()
        val timeNow = Timestamp(System.currentTimeMillis())
        val nameRandom = getRandomName(14)
        DONHANG_ID+=1
        datHangLoader = object : LoaderManager.LoaderCallbacks<Long>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Long> {
                return Insert(this@ThanhToanActivity, "INSERT INTO donhang (user_id, giohang_id, tendonhang, address_user, tongtien, time_dat_hang) " +
                        "VALUES (${user.getId()}, ${cart.getId()}, '$nameRandom', ${diaChiUser.getId()}, $tongTien, '$timeNow')")
            }

            override fun onLoadFinished(loader: Loader<Long>, data: Long?) {
                if (data!=null){
                    size3+=1
                    if (size3==cartList.size){
                        check3 = true
                        if (check1&&check2&&check3){
                            finish()
                            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
                        }
                    }
                    Toast.makeText(this@ThanhToanActivity, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@ThanhToanActivity, "Đặt hàng thất bại", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onLoaderReset(loader: Loader<Long>) {

            }
        }
        supportLoaderManager.initLoader(DONHANG_ID, null, datHangLoader)

    }

    override fun onResume() {
        super.onResume()
        getDiaChi()
    }

    private fun tinhTongThanhToan(){
        var tong=0
        for (element in cartList){
            tong+= element.getTongTien()
        }
        val decimalFormat = DecimalFormat("###,###,###")
        txtTongTien.text = "${decimalFormat.format(tong)} Đ"
    }
    private fun initToolBar() {
        setSupportActionBar(toolBarThanhToan)
        toolBarThanhToan.setNavigationIcon(R.drawable.ic_back)
        toolBarThanhToan.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }
    private fun showCartInListView(){
        adapterCart = ListCartThanhToanAdapter(this@ThanhToanActivity, R.layout.cart_item_thanhtoan_row,
            cartList as ArrayList<CartSanPham>
        )
        listViewCart.adapter = adapterCart
    }
    private fun getData(){
        user = intent.extras?.getParcelable("userThanhToan")!!
        cartList = intent.extras?.getParcelableArrayList("listCartThanhToan")!!
        getDiaChi()
    }

    private fun getDiaChi(){
        diaChiLoader = object : LoaderManager.LoaderCallbacks<DiaChi>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<DiaChi> {
                return GetDiaChiUser(this@ThanhToanActivity, "SELECT * FROM diachi_user WHERE user_id = ${user.getId()} AND default_address = 1")
            }

            override fun onLoadFinished(loader: Loader<DiaChi>, data: DiaChi?) {
                if (data!=null){
                    if (data.getId()==0L){
                        diaChiUser = data
                        txtAddDiaChi.visibility = View.VISIBLE
                        txtHoTenVaSDT.visibility = View.GONE
                        txtDiaChiNhanHang.visibility = View.GONE
                    }else{
                        diaChiUser = data
                        txtAddDiaChi.visibility = View.GONE
                        txtHoTenVaSDT.visibility = View.VISIBLE
                        txtDiaChiNhanHang.visibility = View.VISIBLE
                        txtHoTenVaSDT.text = "${data.getName()} | ${data.getPhone()}"
                        txtDiaChiNhanHang.text = "${data.getAddress()}"
                    }
                }
                DIACHI_ID+=1
            }
            override fun onLoaderReset(loader: Loader<DiaChi>) {

            }
        }
        supportLoaderManager.initLoader(DIACHI_ID, null, diaChiLoader)
    }

    private fun goToDiaChiPage() {
        val intent = Intent(this@ThanhToanActivity, DiaChiActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("userDiaChi", user)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
    }


    private fun anhXa() {
        cardViewDiaChi = findViewById(R.id.cardViewDiaChiNhanHangDefault)
        listViewCart = findViewById(R.id.listViewThanhToan)
        txtHoTenVaSDT = findViewById(R.id.textViewHoTenVaSDT)
        txtDiaChiNhanHang = findViewById(R.id.textViewDiaChiNhanHang)
        txtTongTien = findViewById(R.id.textViewTongTienThanhToan)
        buttonDatHang = findViewById(R.id.buttonDatHang)
        txtAddDiaChi = findViewById(R.id.textViewAddDiaChi)
        toolBarThanhToan = findViewById(R.id.toolBarThanhToan)

        cartList = ArrayList()
    }
}
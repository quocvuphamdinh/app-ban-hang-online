package vu.pham.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.loaddata.GetDiaChiUser
import vu.pham.appbanhang.loaddata.GetDonHang
import vu.pham.appbanhang.loaddata.GetListCartSanPham
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.DiaChi
import vu.pham.appbanhang.model.DonHang
import java.text.DecimalFormat

class DonHangDetailActivity : AppCompatActivity() {

    private lateinit var toolBar:Toolbar
    private lateinit var txtTrangThaiDonHang:TextView
    private lateinit var txtHoTenDiaChiDonHang:TextView
    private lateinit var txtPhoneDiaChiDonHang:TextView
    private lateinit var txtAddressDiaChiDonHang:TextView
    private lateinit var txtTenSanPhamDonHang:TextView
    private lateinit var txtSoLuongSanPhamDonHang:TextView
    private lateinit var txtTongTienSanPhamDonHang:TextView
    private lateinit var txtNgayDatHang:TextView
    private lateinit var txtNgayThanhToan:TextView
    private lateinit var txtMaTenDonHang:TextView
    private lateinit var imgHinhSanPham:ImageView
    private lateinit var donHangLoader:LoaderManager.LoaderCallbacks<DonHang>
    private lateinit var diaChiLoader:LoaderManager.LoaderCallbacks<DiaChi>
    private lateinit var gioHangLoader:LoaderManager.LoaderCallbacks<ArrayList<CartSanPham>>
    private val GIOHANG_ID=3
    private val DIACHI_ID=2
    private val DONHANG_ID=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_don_hang_detail)

        anhXa()
        initToolBar()
        getIdDonHang()
    }

    private fun getIdDonHang() {
        val idDonHang:Long = intent.extras?.getLong("idDonHang")!!
        val idDiaChi:Long = intent.extras?.getLong("idDonHangAddress")!!
        val idGioHang:Long = intent.extras?.getLong("idGioHang")!!
        getDonHang(idDonHang)
        getDiaChi(idDiaChi)
        getGioHang(idGioHang)
    }
    private fun getGioHang(idGioHang:Long){
        gioHangLoader = object : LoaderManager.LoaderCallbacks<ArrayList<CartSanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<CartSanPham>> {
                return GetListCartSanPham(this@DonHangDetailActivity, "SELECT giohang.*, sanpham.tensanpham, sanpham.giasanpham, sanpham.hinhanh, sanpham.soluongsanpham FROM giohang \n" +
                        "INNER JOIN sanpham ON giohang.sanpham_id = sanpham.id\n" +
                        "WHERE giohang.id = $idGioHang")
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<CartSanPham>>,
                data: ArrayList<CartSanPham>?
            ) {
                if (data!=null){
                    val gioHang = data[0]
                    Picasso.get().load(gioHang.getHinhAnh()).into(imgHinhSanPham)
                    txtTenSanPhamDonHang.text = gioHang.getTenSanPham()
                    txtSoLuongSanPhamDonHang.text = "x${gioHang.getSoLuong()}"
                    val decimalFormat = DecimalFormat("###,###,###")
                    txtTongTienSanPhamDonHang.text = "Tổng tiền phải thanh toán: ${decimalFormat.format(gioHang.getTongTien())} Đ"
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<CartSanPham>>) {

            }
        }
        supportLoaderManager.initLoader(GIOHANG_ID, null, gioHangLoader)
    }
    private fun getDiaChi(idDiaChi:Long){
        diaChiLoader = object : LoaderManager.LoaderCallbacks<DiaChi>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<DiaChi> {
                return GetDiaChiUser(this@DonHangDetailActivity, "SELECT * FROM diachi_user WHERE id = $idDiaChi")
            }

            override fun onLoadFinished(loader: Loader<DiaChi>, data: DiaChi?) {
                if (data!=null){
                    txtHoTenDiaChiDonHang.text = data.getName()
                    txtPhoneDiaChiDonHang.text = data.getPhone()
                    txtAddressDiaChiDonHang.text = data.getAddress()
                }
            }

            override fun onLoaderReset(loader: Loader<DiaChi>) {

            }
        }
        supportLoaderManager.initLoader(DIACHI_ID, null, diaChiLoader)
    }
    private fun getDonHang(idDonHang:Long){
        donHangLoader = object : LoaderManager.LoaderCallbacks<DonHang>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<DonHang> {
                return GetDonHang(this@DonHangDetailActivity, "SELECT donhang.*, diachi_user.address, giohang_status.name FROM donhang\n" +
                        "INNER JOIN diachi_user ON donhang.address_user = diachi_user.id\n" +
                        "INNER JOIN giohang_status ON donhang.trangthai = giohang_status.id\n" +
                        "WHERE donhang.id = $idDonHang")
            }

            override fun onLoadFinished(loader: Loader<DonHang>, data: DonHang?) {
                if (data!=null){
                    txtTrangThaiDonHang.text ="Trạng thái đơn hàng: ${data.getTrangThaiName()}"
                    txtNgayDatHang.text = "${data.getTimeDatHang()?.date}/${data.getTimeDatHang()?.month?.plus(1)}/${data.getTimeDatHang()?.year?.plus(1900)}"
                    if (data.getTimeThanhToan()==null){
                        txtNgayThanhToan.text = "Chưa giao"
                    }else{
                        txtNgayThanhToan.text = "${data.getTimeThanhToan()?.date}/${data.getTimeThanhToan()?.month?.plus(1)}/${data.getTimeThanhToan()?.year?.plus(1900)}"
                    }
                    txtMaTenDonHang.text = data.getTenDonHang()
                }
            }

            override fun onLoaderReset(loader: Loader<DonHang>) {

            }
        }
        supportLoaderManager.initLoader(DONHANG_ID, null, donHangLoader)
    }

    private fun initToolBar() {
        setSupportActionBar(toolBar)
        toolBar.setNavigationIcon(R.drawable.ic_back)
        toolBar.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    private fun anhXa() {
        toolBar = findViewById(R.id.toolBarDonHangDetail)
        txtTrangThaiDonHang = findViewById(R.id.textViewTrangThaiDonHangDetail)
        txtHoTenDiaChiDonHang = findViewById(R.id.textViewHoTenDiaChiDonHangDetail)
        txtPhoneDiaChiDonHang = findViewById(R.id.textViewPhoneDiaChiDonHangDetail)
        txtAddressDiaChiDonHang = findViewById(R.id.textViewAddressDiaChiDonHangDetail)
        txtTenSanPhamDonHang = findViewById(R.id.textViewTenSanPhamDonHangDetail)
        txtSoLuongSanPhamDonHang = findViewById(R.id.textViewSoLuongSanPhamDonHangDetail)
        txtTongTienSanPhamDonHang = findViewById(R.id.textViewTongTienDonHangDetail)
        txtNgayDatHang = findViewById(R.id.textViewNgayDatHangDetail)
        txtNgayThanhToan = findViewById(R.id.textViewNgayThanhToanDetail)
        txtMaTenDonHang = findViewById(R.id.textViewMaTenDonHangDetail)
        imgHinhSanPham = findViewById(R.id.imageViewHinhDonHangDetail)
    }
}
package vu.pham.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.SanPham
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var imgHinh:ImageView
    private lateinit var txtTenSanPham:TextView
    private lateinit var txtGiaSanPham:TextView
    private lateinit var spinner: Spinner
    private lateinit var buttonAddToCart : Button
    private lateinit var txtMoTaChiTiet:TextView
    private lateinit var sanPham: SanPham
    private var listSoluong:ArrayList<Int> = ArrayList()
    private lateinit var adapterSpinner : ArrayAdapter<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        anhXa()
        khoiTaoSpinner()
        hienthiSanPham()
        buttonAddToCart.setOnClickListener {
            Toast.makeText(this@DetailActivity, "Thêm vào giỏ hàng thành công !", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun hienthiSanPham() {
        sanPham = getDataDetail()
        Picasso.get().load(sanPham.getHinhAnh()).into(imgHinh)
        txtTenSanPham.text = sanPham.getTenSanPham()
        val decimalFormat = DecimalFormat("###,###,###")
        txtGiaSanPham.text = "Giá: ${decimalFormat.format(sanPham.getGiaSanPham())} Đ"
        txtMoTaChiTiet.text = sanPham.getMoTa()
    }


    private fun khoiTaoSpinner() {
        for (i in 0 until 11){
            listSoluong.add(i)
        }
        adapterSpinner = ArrayAdapter(this@DetailActivity, android.R.layout.simple_list_item_1, listSoluong)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner
    }

    private fun getDataDetail(): SanPham {
        val bundle = intent.extras
        return bundle?.getSerializable("sanpham") as SanPham
    }

    private fun anhXa() {
        imgHinh = findViewById(R.id.imageViewDetail)
        txtTenSanPham = findViewById(R.id.textViewTenDetail)
        txtGiaSanPham = findViewById(R.id.textViewGiaDetail)
        spinner = findViewById(R.id.spinnerDetail)
        buttonAddToCart = findViewById(R.id.buttonThemGioHang)
        txtMoTaChiTiet = findViewById(R.id.textViewMoTaChiTietSanPham)
    }
}
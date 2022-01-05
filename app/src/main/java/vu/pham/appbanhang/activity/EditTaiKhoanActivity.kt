package vu.pham.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import vu.pham.appbanhang.R

class EditTaiKhoanActivity : AppCompatActivity() {
    private lateinit var toolBar:Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_tai_khoan)

        anhXa()
        initToolBar()
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
        toolBar = findViewById(R.id.toolBarEditTaiKhoanActivity)
    }
}
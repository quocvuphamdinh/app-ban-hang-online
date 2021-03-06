package vu.pham.appbanhang.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.drawerlayout.widget.DrawerLayout
import vu.pham.appbanhang.R
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import com.google.android.material.navigation.NavigationView
import vu.pham.appbanhang.fragment.*
import vu.pham.appbanhang.loaddata.GetListLoai
import vu.pham.appbanhang.model.*
import vu.pham.appbanhang.utils.CheckConnection
import java.util.*
import kotlin.collections.ArrayList





class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var listSanPham:ArrayList<SanPham>
    private lateinit var user: User
    private lateinit var sanPhamDetail: SanPham
    private var onlineChange =true
    private lateinit var drawerLayout:DrawerLayout
    private lateinit var toolbarManHinhHome : Toolbar
    private lateinit var txtTenHeader : TextView
    private lateinit var txtOnlineHeader : TextView
    private lateinit var navigationView :NavigationView
    private val HOME_FRAGMENT=1
    private val GAME_FRAGMENT=2
    private val LAPTOP_FRAGMENT=3
    private val PROFILE_FRAGMENT=4
    private val PLAYSTATION_FRAGMENT=5
    private val PHONE_FRAGMENT=6
    private val MOUSE_FRAGMENT=7
    private val KEYBOARD_FRAGMENT=8
    private val CART_FRAGMENT=9
    private val DONHANG_FRAGMENT=10
    private var currentFragment=HOME_FRAGMENT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        anhXa()
        if(CheckConnection.haveNetworkConnection(this@HomeActivity)){
            eventNavigationDrawer()
            eventHeaderNavigationDrawer()
            getUser()
            replaceFragment(HomeFragment())
            navigationView.menu.findItem(R.id.menu_home).isChecked = true
        }else{
            CheckConnection.showToastShort(this@HomeActivity, "Kh??ng c?? Internet !")
            finish()
        }
    }

    private fun getUser() {
        val intent = intent
        val bundle = intent.extras
        user = bundle?.getParcelable("user")!!
        txtTenHeader.text = user.getFullName()
    }

    private fun anhXa() {
        drawerLayout=findViewById<DrawerLayout>(R.id.drawerLayout)
        toolbarManHinhHome=findViewById<Toolbar>(R.id.toolbarManHinhHome)
        navigationView = findViewById(R.id.navigation_view)
        val headerLayout: View = navigationView.inflateHeaderView(R.layout.header_nav_view)
        navigationView.itemIconTintList=null
       // navigationView.inflateMenu(R.menu.menu_nav_view)
        txtTenHeader = headerLayout.findViewById<TextView>(R.id.textviewHoTenHeader)
        txtOnlineHeader = headerLayout.findViewById<TextView>(R.id.textviewOnlineHeader)
    }

    private fun eventHeaderNavigationDrawer() {
        txtOnlineHeader.setOnClickListener {
            if(onlineChange){
                onlineChange=false
                txtOnlineHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_offline, 0)
                txtOnlineHeader.text=resources.getString(R.string.offline)
                user.setStatus(1)
            }else{
                onlineChange=true
                txtOnlineHeader.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_online, 0)
                txtOnlineHeader.text=resources.getString(R.string.online)
                user.setStatus(0)
            }
        }
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun eventNavigationDrawer() {
        setSupportActionBar(toolbarManHinhHome)
        val toggle = ActionBarDrawerToggle(this@HomeActivity, drawerLayout, toolbarManHinhHome,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toolbarManHinhHome.title =resources.getString(R.string.app_title)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_cart, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_cart_2 -> {
                if(currentFragment!=CART_FRAGMENT){
                    goToGioHangFragment()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun goToHomeFragment(){
        replaceFragment(HomeFragment())
        currentFragment=HOME_FRAGMENT
        navigationView.menu.findItem(R.id.menu_home).isChecked=true
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    private fun goToGameFragment(){
        replaceFragment(GameFragment())
        currentFragment=GAME_FRAGMENT
        navigationView.menu.findItem(R.id.menu_game).isChecked=true
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    private fun goToLaptopFragment(){
        replaceFragment(LaptopFragment())
        currentFragment = LAPTOP_FRAGMENT
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=true
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    private fun goToPlayStationFragment(){
        replaceFragment(PlayStationFragment())
        currentFragment = PLAYSTATION_FRAGMENT
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=true
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    private fun goToSmartPhoneFragment(){
        replaceFragment(PhoneFragment())
        currentFragment = PHONE_FRAGMENT
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=true
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    private fun goToMouseFragment(){
        replaceFragment(MouseFragment())
        currentFragment = MOUSE_FRAGMENT
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = true
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    private fun goToKeyBoardFragment(){
        replaceFragment(KeyBoardFragment())
        currentFragment = KEYBOARD_FRAGMENT
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=true
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    private fun goToProfileFragment(){
        replaceFragment(ProfileFragment())
        currentFragment = PROFILE_FRAGMENT
        navigationView.menu.findItem(R.id.menu_profile).isChecked=true
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    fun goToGioHangFragment(){
        replaceFragment(CartFragment())
        currentFragment = CART_FRAGMENT
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = true
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = false
    }
    fun goToDonHangFragment(){
        replaceFragment(DonHangFragment())
        currentFragment = DONHANG_FRAGMENT
        navigationView.menu.findItem(R.id.menu_donhang).isChecked = true
        navigationView.menu.findItem(R.id.menu_giohang).isChecked = false
        navigationView.menu.findItem(R.id.menu_profile).isChecked=false
        navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
        navigationView.menu.findItem(R.id.menu_home).isChecked=false
        navigationView.menu.findItem(R.id.menu_game).isChecked=false
        navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
        navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
        navigationView.menu.findItem(R.id.menu_keyboard).isChecked=false
        navigationView.menu.findItem(R.id.menu_mouse).isChecked = false
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id =item.itemId
        if(id==R.id.menu_home){
            if(currentFragment!=HOME_FRAGMENT){
               goToHomeFragment()
            }
        }else if (id==R.id.menu_game){
            if(currentFragment!=GAME_FRAGMENT){
               goToGameFragment()
            }
        }else if(id==R.id.menu_laptop){
            if(currentFragment!=LAPTOP_FRAGMENT){
                goToLaptopFragment()
            }
        }else if(id==R.id.menu_ps5){
            if(currentFragment!=PLAYSTATION_FRAGMENT){
                goToPlayStationFragment()
            }
        }else if(id==R.id.menu_smartphone){
            if(currentFragment!=PHONE_FRAGMENT){
                goToSmartPhoneFragment()
            }
        }else if(id==R.id.menu_mouse){
            if(currentFragment!=MOUSE_FRAGMENT){
                goToMouseFragment()
            }
        }else if(id==R.id.menu_keyboard){
            if(currentFragment!=KEYBOARD_FRAGMENT){
                goToKeyBoardFragment()
            }
        }else if(id==R.id.menu_profile){
            if(currentFragment!=PROFILE_FRAGMENT){
                goToProfileFragment()
            }
        }else if(id==R.id.menu_giohang){
            if(currentFragment!=CART_FRAGMENT){
                goToGioHangFragment()
            }
        }else if (id==R.id.menu_donhang){
            if (currentFragment!=DONHANG_FRAGMENT){
                goToDonHangFragment()
            }
        }else if(id == R.id.menu_logout){
           showDialogLogOut()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    private fun showDialogLogOut(){
        val dialog = Dialog(this@HomeActivity)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(R.layout.dialog_log_out)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val buttonConfirm = dialog.findViewById<Button>(R.id.buttonLogOut)
        val buttonHuy = dialog.findViewById<Button>(R.id.buttonHuyLogOut)
        buttonConfirm.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
        buttonHuy.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        if(fragment is ProfileFragment || fragment is CartFragment || fragment is DonHangFragment){
            val bundle = Bundle()
            bundle.putParcelable("userProfile", user)
            bundle.putSerializable("createAtUser", user.getCreateAt())
            fragment.arguments = bundle
        }
        if(fragment is ListFragment){
            val bundle = Bundle()
            bundle.putParcelableArrayList("listSanPham", listSanPham)
            fragment.arguments = bundle
            transaction.add(R.id.content_frame_main, fragment)
            transaction.commit()
        }
        if(fragment !is ListFragment){
            transaction.replace(R.id.content_frame_main, fragment)
            transaction.commit()
        }
    }
    fun sendDataToDetailActivity(sanPham: SanPham){
        if (sanPham.getSoLuongSanPham()<=0){
            Toast.makeText(this@HomeActivity, "S???n ph???m ???? h???t h??ng", Toast.LENGTH_LONG).show()
        }else{
            sanPhamDetail = sanPham
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("sanpham", sanPhamDetail)
            bundle.putParcelable("userCart", user)
            intent.putExtras(bundle)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }
    fun sendDataToListFragment(lists:ArrayList<SanPham>){
        listSanPham = lists
        replaceFragment(ListFragment())
    }
    fun sendDataToThanhToanActivity(cartList:ArrayList<CartSanPham>){
        val intent = Intent(this@HomeActivity, ThanhToanActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("userThanhToan", user)
        bundle.putParcelableArrayList("listCartThanhToan", cartList)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
    }

    fun sendDataToDonHangDetailActivity(donHang: DonHang){
        val intent = Intent(this@HomeActivity, DonHangDetailActivity::class.java)
        val bundle = Bundle()
        bundle.putLong("idDonHang", donHang.getId())
        bundle.putLong("idDonHangAddress", donHang.getAddressUserId())
        bundle.putLong("idGioHang", donHang.getGioHangId())
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
    }
    fun sendDataToEditTaiKhoanActivity(user: User){
        val intent = Intent(this@HomeActivity, EditTaiKhoanActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("userEdit", user)
        intent.putExtras(bundle)
        startActivity(intent)
        overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
    }
    override fun onBackPressed() {

    }
}
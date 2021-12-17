package vu.pham.appbanhang.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import vu.pham.appbanhang.R
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import vu.pham.appbanhang.fragment.*
import vu.pham.appbanhang.model.SanPham
import vu.pham.appbanhang.utils.CheckConnection
import java.util.*


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
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
    private var currentFragment=HOME_FRAGMENT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        anhXa()
        if(CheckConnection.haveNetworkConnection(this@HomeActivity)){
            eventNavigationDrawer()
            eventHeaderNavigationDrawer()
        }else{
            CheckConnection.showToastShort(this@HomeActivity, "Không có Internet !")
            finish()
        }
        replaceFragment(HomeFragment())
        navigationView.menu.findItem(R.id.menu_home).isChecked = true
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
            }else{
                onlineChange=true
                txtOnlineHeader.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_online, 0)
                txtOnlineHeader.text=resources.getString(R.string.online)
            }
        }
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun eventNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(this@HomeActivity, drawerLayout, toolbarManHinhHome,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        toolbarManHinhHome.title =resources.getString(R.string.app_title)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id =item.itemId
        if(id==R.id.menu_home){
            if(currentFragment!=HOME_FRAGMENT){
                replaceFragment(HomeFragment())
                currentFragment=HOME_FRAGMENT
                navigationView.menu.findItem(R.id.menu_home).isChecked=true
                navigationView.menu.findItem(R.id.menu_game).isChecked=false
                navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
                navigationView.menu.findItem(R.id.menu_profile).isChecked=false
                navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
                navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
            }
        }else if (id==R.id.menu_game){
            if(currentFragment!=GAME_FRAGMENT){
                replaceFragment(GameFragment())
                currentFragment=GAME_FRAGMENT
                navigationView.menu.findItem(R.id.menu_home).isChecked=false
                navigationView.menu.findItem(R.id.menu_game).isChecked=true
                navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
                navigationView.menu.findItem(R.id.menu_profile).isChecked=false
                navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
                navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
            }
        }else if(id==R.id.menu_laptop){
            if(currentFragment!=LAPTOP_FRAGMENT){
                replaceFragment(LaptopFragment())
                currentFragment = LAPTOP_FRAGMENT
                navigationView.menu.findItem(R.id.menu_laptop).isChecked=true
                navigationView.menu.findItem(R.id.menu_home).isChecked=false
                navigationView.menu.findItem(R.id.menu_game).isChecked=false
                navigationView.menu.findItem(R.id.menu_profile).isChecked=false
                navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
                navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
            }
        }else if(id==R.id.menu_ps5){
            if(currentFragment!=PLAYSTATION_FRAGMENT){
                replaceFragment(PlayStationFragment())
                currentFragment = PLAYSTATION_FRAGMENT
                navigationView.menu.findItem(R.id.menu_ps5).isChecked=true
                navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
                navigationView.menu.findItem(R.id.menu_home).isChecked=false
                navigationView.menu.findItem(R.id.menu_game).isChecked=false
                navigationView.menu.findItem(R.id.menu_profile).isChecked=false
                navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
            }
        }else if(id==R.id.menu_smartphone){
            if(currentFragment!=PHONE_FRAGMENT){
                replaceFragment(PhoneFragment())
                currentFragment = PHONE_FRAGMENT
                navigationView.menu.findItem(R.id.menu_smartphone).isChecked=true
                navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
                navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
                navigationView.menu.findItem(R.id.menu_home).isChecked=false
                navigationView.menu.findItem(R.id.menu_game).isChecked=false
                navigationView.menu.findItem(R.id.menu_profile).isChecked=false
            }
        }else if(id==R.id.menu_mouse){

        }else if(id==R.id.menu_keyboard){

        }else if(id==R.id.menu_profile){
            if(currentFragment!=PROFILE_FRAGMENT){
                replaceFragment(ProfileFragment())
                currentFragment = PROFILE_FRAGMENT
                navigationView.menu.findItem(R.id.menu_profile).isChecked=true
                navigationView.menu.findItem(R.id.menu_laptop).isChecked=false
                navigationView.menu.findItem(R.id.menu_home).isChecked=false
                navigationView.menu.findItem(R.id.menu_game).isChecked=false
                navigationView.menu.findItem(R.id.menu_ps5).isChecked=false
                navigationView.menu.findItem(R.id.menu_smartphone).isChecked=false
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content_frame_main, fragment)
        transaction.commit()
    }
    fun sendDataToDetailActivity(sanPham: SanPham){
        sanPhamDetail = SanPham()
        sanPhamDetail = sanPham
        val intent = Intent(this@HomeActivity, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable("sanpham", sanPham)
        intent.putExtras(bundle)
        startActivity(intent)
        //Toast.makeText(this@HomeActivity, "Sản phẩm: ${sanPham.getTenSanPham()}", Toast.LENGTH_SHORT).show()
    }
}
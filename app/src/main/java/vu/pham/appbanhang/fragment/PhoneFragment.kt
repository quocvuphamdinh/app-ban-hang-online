package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.RecyclerViewGameAdapter
import vu.pham.appbanhang.model.SanPham

class PhoneFragment : Fragment(){

    private lateinit var recyclerViewPhoneiOS : RecyclerView
    private lateinit var recyclerViewPhoneAndroid : RecyclerView
    private lateinit var txtViewAlliOS:TextView
    private lateinit var txtViewAllAndroid:TextView
    private lateinit var adapterPhoneiOS : RecyclerViewGameAdapter
    private lateinit var adapterPhoneAndroid : RecyclerViewGameAdapter
    private lateinit var phoneiOSList : ArrayList<SanPham>
    private lateinit var phoneAndroidList : ArrayList<SanPham>
    private lateinit var homeActivity: HomeActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.phone_fragment, container, false)
        anhXa(view)
        khoiTaoDataChoRecyclerViewPhoneiOS()
        khoiTaoDataChoRecyclerViewPhoneAndroid()

        return view
    }

    private fun khoiTaoDataChoRecyclerViewPhoneAndroid() {
        phoneAndroidList = ArrayList()
        phoneAndroidList = getListPhoneAndroid()
        adapterPhoneAndroid = RecyclerViewGameAdapter(object : RecyclerViewGameAdapter.CLickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        adapterPhoneAndroid.setData(phoneAndroidList)
        recyclerViewPhoneAndroid.layoutManager = LinearLayoutManager(context)
        recyclerViewPhoneAndroid.adapter = adapterPhoneAndroid
        recyclerViewPhoneAndroid.setHasFixedSize(true)
    }

    private fun khoiTaoDataChoRecyclerViewPhoneiOS(){
        phoneiOSList = ArrayList()
        phoneiOSList = getListPhoneiOS()
        adapterPhoneiOS = RecyclerViewGameAdapter(object : RecyclerViewGameAdapter.CLickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        adapterPhoneiOS.setData(phoneiOSList)
        recyclerViewPhoneiOS.layoutManager = LinearLayoutManager(context)
        recyclerViewPhoneiOS.adapter = adapterPhoneiOS
        recyclerViewPhoneiOS.setHasFixedSize(true)
    }
    private fun getListPhoneAndroid():ArrayList<SanPham>{
        val lists :ArrayList<SanPham> = ArrayList()
        lists.add(SanPham("Samsung Galaxy S10", 16989474, "Điện thoại Samsung", "https://cdn.tgdd.vn/Products/Images/42/179530/samsung-galaxy-s10-plus-white-600x600.jpg"))
        lists.add(SanPham("Xiaomi Mi 11", 9490000, "Điện thoại Xiaomi", "https://cdn.tgdd.vn/Products/Images/42/226264/xiaomi-mi-11-xanhduong-600x600-600x600.jpg"))
        lists.add(SanPham("Oppo Reno6 Z", 6389000, "Điện thoại Oppo", "https://cdn.tgdd.vn/Products/Images/42/239747/oppo-reno6-z-5g-aurora-1-600x600.jpg"))
        lists.add(SanPham("Huawei P30 Lite", 16989474, "Điện thoại Huawei", "https://cdn.tgdd.vn/Products/Images/42/198985/huawei-p30-lite-1-600x600.jpg"))
        return lists
    }

    private fun getListPhoneiOS():ArrayList<SanPham>{
        val lists :ArrayList<SanPham> = ArrayList()
        lists.add(SanPham("iPhone 13", 32000000, "Điện thoại iPhone 13", "https://cdn.hoanghamobile.com/i/productlist/dsp/Uploads/2021/09/15/image-removebg-preview-15.png"))
        lists.add(SanPham("iPhone 12", 24490000, "Điện thoại iPhone 12", "https://cdn.hoanghamobile.com/i/preview/Uploads/2020/11/06/apple-iphone-12-mini-5.png"))
        lists.add(SanPham("iPhone 11", 15900000, "Điện thoại iPhone 11", "https://cdn.tgdd.vn/Products/Images/42/210644/iphone-11-tim-200x200.jpg"))
        lists.add(SanPham("iPhone 8", 8000000, "Điện thoại iPhone 8", "https://cdn.tgdd.vn/Products/Images/42/114113/iphone-8-64gb-hh-600x600.jpg"))
        return lists
    }

    private fun anhXa(view: View) {
        recyclerViewPhoneiOS = view.findViewById(R.id.recylerViewPhoneiOSFragment)
        recyclerViewPhoneAndroid = view.findViewById(R.id.recylerViewPhoneAndroidFragment)
        txtViewAlliOS = view.findViewById(R.id.textviewViewAlliOS)
        txtViewAllAndroid = view.findViewById(R.id.textviewViewAlliOS)
        homeActivity = activity as HomeActivity
    }
}
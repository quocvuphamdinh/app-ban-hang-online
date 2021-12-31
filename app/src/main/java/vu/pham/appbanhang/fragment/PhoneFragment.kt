package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.RecyclerViewGameAdapter
import vu.pham.appbanhang.loaddata.GetListLoai
import vu.pham.appbanhang.loaddata.GetListSanPham
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.model.SanPham

class PhoneFragment : Fragment(){

    private val IOS_ID=8
    private val ANDROID_ID=9
    private lateinit var getListiOS:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private lateinit var getListAndroid:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private lateinit var recyclerViewPhoneiOS : RecyclerView
    private lateinit var recyclerViewPhoneAndroid : RecyclerView
    private lateinit var txtViewAlliOS:TextView
    private lateinit var txtViewAllAndroid:TextView
    private lateinit var adapterPhoneiOS : RecyclerViewGameAdapter
    private lateinit var adapterPhoneAndroid : RecyclerViewGameAdapter
    private lateinit var phoneiOSList : ArrayList<SanPham>
    private lateinit var phoneAndroidList : ArrayList<SanPham>
    private lateinit var phoneiOSList2 : ArrayList<SanPham>
    private lateinit var phoneAndroidList2 : ArrayList<SanPham>
    private lateinit var homeActivity: HomeActivity
    private val sqlGetAndroid="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id WHERE loai_id = 8 ORDER BY id DESC"
    private val sqlGetiOS="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id WHERE loai_id = 4 ORDER BY id DESC"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.phone_fragment, container, false)
        anhXa(view)
        khoiTaoDataChoRecyclerViewPhoneiOS()
        khoiTaoDataChoRecyclerViewPhoneAndroid()
        getListPhoneAndroid()
        getListPhoneiOS()

        txtViewAllAndroid.setOnClickListener {
            homeActivity.sendDataToListFragment(phoneAndroidList)
        }
        txtViewAlliOS.setOnClickListener {
            homeActivity.sendDataToListFragment(phoneiOSList)
        }
        return view
    }

    private fun khoiTaoDataChoRecyclerViewPhoneAndroid() {
        phoneAndroidList = ArrayList()
        adapterPhoneAndroid = RecyclerViewGameAdapter(object : RecyclerViewGameAdapter.CLickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recyclerViewPhoneAndroid.layoutManager = LinearLayoutManager(context)
        recyclerViewPhoneAndroid.adapter = adapterPhoneAndroid
        recyclerViewPhoneAndroid.setHasFixedSize(true)
    }

    private fun khoiTaoDataChoRecyclerViewPhoneiOS(){
        phoneiOSList = ArrayList()
        adapterPhoneiOS = RecyclerViewGameAdapter(object : RecyclerViewGameAdapter.CLickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recyclerViewPhoneiOS.layoutManager = LinearLayoutManager(context)
        recyclerViewPhoneiOS.adapter = adapterPhoneiOS
        recyclerViewPhoneiOS.setHasFixedSize(true)
    }
    private fun getListPhoneAndroid(){
        getListAndroid = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
                return context?.let { GetListSanPham(it, sqlGetAndroid) }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<SanPham>>,
                data: ArrayList<SanPham>?
            ) {
                if (data != null) {
                    phoneAndroidList = data
                    phoneAndroidList2= ArrayList()
                    for (i in 0 until (phoneAndroidList.size/2)){
                        phoneAndroidList2.add(phoneAndroidList[i])
                    }
                    adapterPhoneAndroid.setData(phoneAndroidList2)
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
                phoneAndroidList.clear()
                phoneAndroidList2.clear()
            }

        }
        loaderManager.initLoader(ANDROID_ID, null, getListAndroid)
    }

    private fun getListPhoneiOS(){
        getListiOS = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
                return context?.let { GetListSanPham(it, sqlGetiOS) }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<SanPham>>,
                data: ArrayList<SanPham>?
            ) {
                if (data != null) {
                    phoneiOSList = data
                    phoneiOSList2= ArrayList()
                    for (i in 0 until (phoneiOSList.size/2)){
                        phoneiOSList2.add(phoneiOSList[i])
                    }
                    adapterPhoneiOS.setData(phoneiOSList2)
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
                phoneiOSList.clear()
                phoneiOSList2.clear()
            }

        }
        loaderManager.initLoader(IOS_ID, null, getListiOS)
    }

    private fun anhXa(view: View) {
        recyclerViewPhoneiOS = view.findViewById(R.id.recylerViewPhoneiOSFragment)
        recyclerViewPhoneAndroid = view.findViewById(R.id.recylerViewPhoneAndroidFragment)
        txtViewAlliOS = view.findViewById(R.id.textviewViewAlliOS)
        txtViewAllAndroid = view.findViewById(R.id.textviewViewAllAndroid)
        homeActivity = activity as HomeActivity
    }
}
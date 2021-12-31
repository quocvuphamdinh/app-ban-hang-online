package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.RecyclerViewMouseAdapter
import vu.pham.appbanhang.loaddata.GetListLoai
import vu.pham.appbanhang.loaddata.GetListSanPham
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.model.SanPham
import android.view.inputmethod.EditorInfo

import android.widget.TextView
import android.widget.TextView.OnEditorActionListener


class MouseFragment:Fragment() {

    private lateinit var edtSearch:EditText
    private lateinit var recylerViewMouse:RecyclerView
    private lateinit var mouseList:ArrayList<SanPham>
    private lateinit var adapterMouse:RecyclerViewMouseAdapter
    private lateinit var homeActivity: HomeActivity
    private lateinit var getMouseList:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private val MOUSE_ID=11
    private val sqlGetMouseList="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id WHERE loai_id = 5 ORDER BY id DESC"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.mouse_fragment, container, false)

        anhXa(view)
        initMouseList()
        getListMouse()
        searchMouse()
        return view
    }

    private fun searchMouse(){
        edtSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapterMouse.filter.filter(s.toString().trim())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }
    private fun getListMouse(){
        getMouseList = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
               return context?.let { GetListSanPham(it, sqlGetMouseList) }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<SanPham>>,
                data: ArrayList<SanPham>?
            ) {
                if (data!=null){
                    mouseList=data
                    adapterMouse.setData(mouseList)
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
                mouseList.clear()
            }
        }
        loaderManager.initLoader(MOUSE_ID, null, getMouseList)
    }
    private fun initMouseList(){
        mouseList = ArrayList()
        adapterMouse = RecyclerViewMouseAdapter(object : RecyclerViewMouseAdapter.ClickItem{
            override fun showItemInfo(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recylerViewMouse.layoutManager = LinearLayoutManager(context)
        recylerViewMouse.adapter = adapterMouse
        recylerViewMouse.setHasFixedSize(true)
    }
    private fun anhXa(view: View) {
        edtSearch = view.findViewById(R.id.editTextSearchMouseFragment)
        recylerViewMouse = view.findViewById(R.id.recylerViewMouse)
        homeActivity = activity as HomeActivity
    }
}
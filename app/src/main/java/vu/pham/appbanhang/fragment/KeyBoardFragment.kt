package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.ListKeyBoardAdapter
import vu.pham.appbanhang.loaddata.GetListSanPham
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.model.SanPham

class KeyBoardFragment : Fragment(){

    private val KEY_BOARD_ID=1
    private lateinit var keyBoardLoader:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private lateinit var listView:ListView
    private lateinit var keyBoardList:ArrayList<SanPham>
    private lateinit var adapterKeyboard:ListKeyBoardAdapter
    private lateinit var homeActivity: HomeActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.keyboard_fragment, container, false)
        anhXa(view)
        initList()
        getListKeyBoard()
        clickItemKeyBoard()
        return view
    }

    private fun clickItemKeyBoard(){
        listView.setOnItemClickListener { parent, view, position, id ->
            homeActivity.sendDataToDetailActivity(keyBoardList[position])
        }
    }
    private fun getListKeyBoard(){
        keyBoardLoader = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
                return context?.let { GetListSanPham(it, "SELECT sanpham.*, loaisanpham.tenloai FROM sanpham INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id WHERE loai_id = 6 ORDER BY id DESC") }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<SanPham>>,
                data: ArrayList<SanPham>?
            ) {
                if (data != null) {
                    keyBoardList=data
                    adapterKeyboard = context?.let { ListKeyBoardAdapter(it, R.layout.keyboard_item_row, keyBoardList) }!!
                    listView.adapter = adapterKeyboard
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
                keyBoardList.clear()
            }
        }
        loaderManager.initLoader(KEY_BOARD_ID, null, keyBoardLoader)
    }
    private fun initList() {
        keyBoardList = ArrayList()
    }

    private fun anhXa(view: View) {
        listView = view.findViewById(R.id.listViewKeyBoard)
        homeActivity = activity as HomeActivity
    }
}
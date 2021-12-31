package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.RecyclerViewHomeAdapter
import vu.pham.appbanhang.loaddata.GetListLoai
import vu.pham.appbanhang.loaddata.GetListSanPham
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.model.SanPham

class PlayStationFragment : Fragment() {

    private lateinit var getListPlayStation:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private val PLAYSTATION_ID=6
    private lateinit var recylerViewPlaystation:RecyclerView
    private lateinit var homeActivity: HomeActivity
    private lateinit var adapterPlaystation : RecyclerViewHomeAdapter
    private lateinit var playStationList:ArrayList<SanPham>
    private val sqlGetListPlayStation="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id WHERE loai_id = 2 ORDER BY id DESC"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.playstation_fragment, container, false)
        anhXa(view)
        khoiTaoDataRecyclerViewPlaystation()
        getListPlaystation()

        return view
    }

    private fun khoiTaoDataRecyclerViewPlaystation() {
        playStationList = ArrayList()
        adapterPlaystation = RecyclerViewHomeAdapter(object : RecyclerViewHomeAdapter.ClickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recylerViewPlaystation.layoutManager = GridLayoutManager(context, 2)
        recylerViewPlaystation.adapter = adapterPlaystation
        recylerViewPlaystation.setHasFixedSize(true)
    }

    private fun getListPlaystation(){
        getListPlayStation = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
                return context?.let { GetListSanPham(it, sqlGetListPlayStation) }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<SanPham>>,
                data: ArrayList<SanPham>?
            ) {
                if (data != null) {
                    playStationList = data
                    adapterPlaystation.setData(playStationList)
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
                playStationList.clear()
            }
        }
        loaderManager.initLoader(PLAYSTATION_ID, null, getListPlayStation)
    }

    private fun anhXa(view: View) {
        recylerViewPlaystation=view.findViewById(R.id.recylerViewPlayStationFragment)
        homeActivity = activity as HomeActivity
    }
}
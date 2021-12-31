package vu.pham.appbanhang.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.RecyclerViewGameAdapter
import vu.pham.appbanhang.loaddata.GetListSanPham
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.model.SanPham

class GameFragment : Fragment() {
    private val GAME_DATA_ID=1
    private lateinit var getGameDataLoader:LoaderManager.LoaderCallbacks<ArrayList<SanPham>>
    private lateinit var recyclerViewGame: RecyclerView
    private lateinit var adapterGame: RecyclerViewGameAdapter
    private lateinit var gameList: ArrayList<SanPham>
    private lateinit var homeActivity: HomeActivity
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_fragment, container, false)
        anhXa(view)
        khoiTaoRecylerView()
        initGameLoader()
        loaderManager.initLoader(GAME_DATA_ID, null, getGameDataLoader)
        return view
    }
    private fun initGameLoader() {
        getGameDataLoader = object : LoaderManager.LoaderCallbacks<ArrayList<SanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<SanPham>> {
                return context?.let { GetListSanPham(it, "SELECT sanpham.*, loaisanpham.tenloai FROM sanpham INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id WHERE loai_id = 1 ORDER BY id DESC") }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<SanPham>>,
                data: ArrayList<SanPham>?
            ) {
                if (data != null) {
                    gameList = data
                    adapterGame.setData(gameList)
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<SanPham>>) {
                gameList.clear()
            }

        }
    }

    private fun anhXa(view: View){
        recyclerViewGame=view.findViewById(R.id.recylerViewGame)
        homeActivity = activity as HomeActivity
        linearLayoutManager = LinearLayoutManager(context)
    }
    private fun khoiTaoRecylerView(){
        gameList = ArrayList()
        adapterGame= RecyclerViewGameAdapter(object : RecyclerViewGameAdapter.CLickItem{
            override fun showInfoItem(sanPham: SanPham) {
                homeActivity.sendDataToDetailActivity(sanPham)
            }
        })
        recyclerViewGame.layoutManager= linearLayoutManager
        recyclerViewGame.adapter = adapterGame
        recyclerViewGame.setHasFixedSize(true)
    }
}
package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.adapter.RecyclerViewGameAdapter
import vu.pham.appbanhang.model.SanPham

class GameFragment : Fragment() {
    private lateinit var recyclerViewGame: RecyclerView
    private lateinit var adapterGame: RecyclerViewGameAdapter
    private lateinit var gameList: ArrayList<SanPham>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.game_fragment, container, false)
        recyclerViewGame=view.findViewById(R.id.recylerViewGame)
        khoiTaoRecylerView()
        return view
    }
    private fun khoiTaoRecylerView(){
        gameList = ArrayList()
        gameList.add(SanPham("Cyberpunk 2077", 1500000, "Game Cyberpunk", "https://media-cdn.laodong.vn/Storage/NewsPortal/2020/12/31/867051/Cyberpunk.jpg"))
        gameList.add(SanPham("Call of Duty: Vanguard", 900000, "Game Call of Duty", "https://phegame.com.vn/wp-content/uploads/2021/08/VGD_SEE_THEM_RISE.jpg"))
        gameList.add(SanPham("Far Cry 6", 1000000, "Game Far Cry", "https://hadoantv.com/wp-content/uploads/2021/10/download-far-cry-6-hadoan-tv.jpg"))
        gameList.add(SanPham("OutRiders", 2000000, "Game OutRiders", "https://cdn.tgdd.vn/GameApp/4/238612/Screentshots/outriders-06-05-2021-2.jpg"))
        gameList.add(SanPham("Lost Judgment", 3000000, "Game Lost Judgment", "https://gamek.mediacdn.vn/133514250583805952/2021/9/28/photo-1-16327649179271935023979.jpg"))
        gameList.add(SanPham("Dead by DayLight", 900000, "Game Dead by DayLight", "https://gamek.mediacdn.vn/133514250583805952/2021/11/27/photo-1-1637989455867604907749.jpg"))
        adapterGame= RecyclerViewGameAdapter(object : RecyclerViewGameAdapter.CLickItem{
            override fun showInfoItem(sanPham: SanPham) {

            }
        })
        adapterGame.setData(gameList)
        recyclerViewGame.layoutManager= LinearLayoutManager(context)
        recyclerViewGame.adapter = adapterGame
        recyclerViewGame.setHasFixedSize(true)
    }
}
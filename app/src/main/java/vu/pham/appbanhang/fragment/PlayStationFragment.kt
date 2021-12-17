package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import vu.pham.appbanhang.R
import vu.pham.appbanhang.adapter.RecyclerViewHomeAdapter
import vu.pham.appbanhang.model.SanPham

class PlayStationFragment : Fragment() {

    private lateinit var recylerViewPlaystation:RecyclerView
    private lateinit var adapterPlaystation : RecyclerViewHomeAdapter
    private lateinit var playStationList:ArrayList<SanPham>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.playstation_fragment, container, false)
        anhXa(view)
        khoiTaoDataRecyclerViewPlaystation()
        return view
    }

    private fun khoiTaoDataRecyclerViewPlaystation() {
        playStationList = ArrayList()
        playStationList = getListPlaystation()
        adapterPlaystation = RecyclerViewHomeAdapter()
        adapterPlaystation.setData(playStationList)
        recylerViewPlaystation.layoutManager = GridLayoutManager(context, 2)
        recylerViewPlaystation.adapter = adapterPlaystation
        recylerViewPlaystation.setHasFixedSize(true)
    }
    private fun getListPlaystation():ArrayList<SanPham>{
        val lists :ArrayList<SanPham> = ArrayList()
        lists.add(SanPham("PlayStation 5", 10000000, "Máy PlayStation 5", "https://cdn.vjshop.vn/hightech/may-choi-game/ps5/sony-ps-5-1.jpg"))
        lists.add(SanPham("PlayStation 4", 12390000, "Máy PlayStation 4", "https://vn-live-01.slatic.net/p/9139a532ac5a7966a7bbdb905771ffaa.png"))
        lists.add(SanPham("PlayStation 3", 3000000, "Máy PlayStation 3", "https://media.baodansinh.vn/baodansinh/222978561005920256/2021/6/4/photo-1-1622737051903418202718-1622771824799-16227718253081347654825.jpg"))
        return lists
    }

    private fun anhXa(view: View) {
        recylerViewPlaystation=view.findViewById(R.id.recylerViewPlayStationFragment)
    }
}
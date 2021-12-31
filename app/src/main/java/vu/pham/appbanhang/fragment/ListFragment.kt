package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.ListSanPhamAdapter
import vu.pham.appbanhang.model.SanPham

class ListFragment : Fragment(){
    private lateinit var buttonQuayLai:Button
    private lateinit var listView: ListView
    private lateinit var sanPhamList :List<SanPham>
    private lateinit var adapterListView:ListSanPhamAdapter
    private lateinit var homeActivity: HomeActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        anhXa(view)
        initList()
        buttonQuayLai.setOnClickListener {
            back()
        }
        listView.setOnItemClickListener { parent, view, position, id ->
            homeActivity.sendDataToDetailActivity(sanPhamList[position])
        }
        return view
    }

    private fun back() {
        fragmentManager?.beginTransaction()?.remove(this@ListFragment)?.commit()
    }

    private fun initList(){
        sanPhamList = ArrayList()
        val bundle = arguments
        sanPhamList = bundle?.getParcelableArrayList("listSanPham")!!
        adapterListView = context?.let { ListSanPhamAdapter(it, R.layout.game_item_row, sanPhamList) }!!
        listView.adapter = adapterListView
    }
    private fun anhXa(view: View){
        buttonQuayLai = view.findViewById(R.id.buttonQuayLai)
        listView = view.findViewById(R.id.listViewSanPham)
        homeActivity = activity as HomeActivity
    }
}
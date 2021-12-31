package vu.pham.appbanhang.fragment

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.RecyclerViewHomeAdapter
import vu.pham.appbanhang.adapter.RecylerViewHomeAdapter3
import vu.pham.appbanhang.loaddata.GetSanPham
import vu.pham.appbanhang.mapper.LoaiMapper
import vu.pham.appbanhang.mapper.QuangCaoMapper
import vu.pham.appbanhang.mapper.SanPhamMapper
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.model.QuangCao
import vu.pham.appbanhang.model.SanPham
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class HomeFragment :Fragment() {
  companion object{
    private var GAME_ID=1
    private var isShow=false
  }
  private lateinit var gameLoader:LoaderManager.LoaderCallbacks<SanPham>
  private lateinit var viewFlipper: ViewFlipper
  private lateinit var txtViewAll: TextView
  private lateinit var txtViewAll2: TextView
  private lateinit var recyclerView: RecyclerView
  private lateinit var sanPhamList: ArrayList<SanPham>
  private lateinit var adapterHome: RecyclerViewHomeAdapter
  private lateinit var recylerView2: RecyclerView
  private lateinit var sanPhamList2: ArrayList<SanPham>
  private lateinit var adapterHome2: RecyclerViewHomeAdapter
  private lateinit var sanPhamBanChay:ArrayList<SanPham>
  private lateinit var adapterHome3 : RecylerViewHomeAdapter3
  private lateinit var recyclerView3: RecyclerView
  private lateinit var quangCaos:ArrayList<QuangCao>
  private lateinit var homeActivity : HomeActivity
  private val sqlGetGameMoiNhat = "SELECT sanpham.*, loaisanpham.tenloai FROM sanpham \n" +
          "INNER JOIN favorite_sanpham ON sanpham.id = favorite_sanpham.sanpham_id\n" +
          "INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id\n" +
          "WHERE favorite_sanpham.favorite_id = 1 AND sanpham.loai_id = 1 ORDER BY sanpham.id DESC"
  private val sqlGetGameChoiNhieuNhat="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham\n" +
          "INNER JOIN favorite_sanpham ON sanpham.id = favorite_sanpham.sanpham_id\n" +
          "INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id\n" +
          "WHERE favorite_sanpham.favorite_id = 3 AND sanpham.loai_id = 1 ORDER BY sanpham.id DESC"
  private val sqlGetPhuKienBanChay ="SELECT sanpham.*, loaisanpham.tenloai FROM sanpham\n" +
          "INNER JOIN favorite_sanpham ON sanpham.id = favorite_sanpham.sanpham_id\n" +
          "INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id\n" +
          "WHERE favorite_sanpham.favorite_id = 3 AND sanpham.loai_id >1 ORDER BY sanpham.id DESC"
  private val sqlGetQuangCao ="SELECT quangcao.* FROM quangcao\n" +
          "INNER JOIN sanpham ON sanpham.id = quangcao.sanpham_id\n" +
          "WHERE sanpham.loai_id=1"
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view=inflater.inflate(R.layout.home_fragment, container, false)
    anhXa(view)
    quangCaos =  GetQuangCaoDataHome().execute(sqlGetQuangCao).get()
    actionViewFlipper(quangCaos)
    khoiTaoDataRecylerView()
    khoiTaoDataRecylerView2()
    khoiTaoDataRecylerView3()
    GetGameNewDataHome().execute(sqlGetGameMoiNhat)
    GetGameChoiNhieuDataHome().execute(sqlGetGameChoiNhieuNhat)
    GetPhuKienDataHome().execute(sqlGetPhuKienBanChay)
    return view
  }

  private inner class GetQuangCaoDataHome : AsyncTask<String, Void, ArrayList<QuangCao>>(){
    override fun doInBackground(vararg params: String?): ArrayList<QuangCao> {
      val lists = ArrayList<QuangCao>()
      try {
        Class.forName(Server.className)
        val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(params[0])
        while (resultSet.next()){
          val quangCaoMapper = QuangCaoMapper()
          val quangCao = quangCaoMapper.mapper(resultSet)
          lists.add(quangCao)
        }
        connection.close()
        statement.close()
        resultSet.close()
      }catch (e:SQLException){
        Log.d("hivu", e.toString())
      }
      return lists
    }

    override fun onPostExecute(result: ArrayList<QuangCao>) {
      super.onPostExecute(result)
    }
  }
  private inner class GetPhuKienDataHome : AsyncTask<String, Void, ArrayList<SanPham>>() {

    override fun doInBackground(vararg params: String?): ArrayList<SanPham> {
      val lists = ArrayList<SanPham>()
      try {
        Class.forName(Server.className)
        val connection = DriverManager.getConnection(Server.url, Server.username, Server.password);
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(params[0])
        while (resultSet.next()){
          val sanPhamMapper = SanPhamMapper()
          val sanPham = sanPhamMapper.mapper(resultSet)
          lists.add(sanPham)
        }
        connection.close()
        statement.close()
        resultSet.close()
      }catch (e: SQLException){
        Log.d("hivu", e.toString())
      }
      return lists
    }

    override fun onPostExecute(result: ArrayList<SanPham>) {
      super.onPostExecute(result)
      sanPhamBanChay = result
      adapterHome3.setData(sanPhamBanChay)
    }
  }
  private inner class GetGameNewDataHome : AsyncTask<String, Void, List<SanPham>>() {

    override fun doInBackground(vararg params: String?): List<SanPham> {
      val gameNews = ArrayList<SanPham>()
      try {
        Class.forName(Server.className)
        val connection = DriverManager.getConnection(Server.url, Server.username, Server.password);
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(params[0])
        while (resultSet.next()){
          val sanPhamMapper = SanPhamMapper()
          val sanPham = sanPhamMapper.mapper(resultSet)
          gameNews.add(sanPham)
        }
        connection.close()
        statement.close()
        resultSet.close()
      }catch (e: SQLException){
        Log.d("hivu", e.toString())
      }
      return gameNews
    }

    override fun onPostExecute(result: List<SanPham>?) {
      super.onPostExecute(result)
      sanPhamList = result as ArrayList<SanPham>
      val sanPhamListHalf = ArrayList<SanPham>()
      for(i in 0 .. 3){
        sanPhamListHalf.add(sanPhamList[i])
      }
      adapterHome.setData(sanPhamListHalf)
      txtViewAll.setOnClickListener {
        homeActivity.sendDataToListFragment(sanPhamList)
      }
    }
  }
  private inner class GetGameChoiNhieuDataHome : AsyncTask<String, Void, List<SanPham>>() {

    override fun doInBackground(vararg params: String?): List<SanPham> {
      val gameNews = ArrayList<SanPham>()
      try {
        Class.forName(Server.className)
        val connection = DriverManager.getConnection(Server.url, Server.username, Server.password);
        val statement = connection.createStatement()
        val resultSet = statement.executeQuery(params[0])
        while (resultSet.next()){
          val sanPhamMapper = SanPhamMapper()
          val sanPham = sanPhamMapper.mapper(resultSet)
          gameNews.add(sanPham)
        }
        connection.close()
        statement.close()
        resultSet.close()
      }catch (e: SQLException){
        Log.d("hivu", e.toString())
      }
      return gameNews
    }

    override fun onPostExecute(result: List<SanPham>?) {
      super.onPostExecute(result)
      sanPhamList2 = result as ArrayList<SanPham>
      val sanPhamListHalf = ArrayList<SanPham>()
      for(i in 0 .. 3){
        sanPhamListHalf.add(sanPhamList2[i])
      }
      adapterHome2.setData(sanPhamListHalf)
      txtViewAll2.setOnClickListener {
        homeActivity.sendDataToListFragment(sanPhamList2)
      }
    }
  }
  private fun khoiTaoDataRecylerView3() {
    sanPhamBanChay= ArrayList()
    quangCaos = ArrayList()
    adapterHome3 = RecylerViewHomeAdapter3(object : RecylerViewHomeAdapter3.CLickItem{
      override fun showInfoItem(sanPham: SanPham) {
        homeActivity.sendDataToDetailActivity(sanPham)
      }
    })
    recyclerView3.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    recyclerView3.adapter = adapterHome3
    recyclerView3.setHasFixedSize(true)
  }

  private fun khoiTaoDataRecylerView2() {
    sanPhamList2 = ArrayList()
    adapterHome2= RecyclerViewHomeAdapter(object : RecyclerViewHomeAdapter.ClickItem{
      override fun showInfoItem(sanPham: SanPham) {
        homeActivity.sendDataToDetailActivity(sanPham)
      }
    })
    recylerView2.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    recylerView2.adapter=adapterHome2
    recylerView2.setHasFixedSize(true)
  }

  private fun khoiTaoDataRecylerView() {
    sanPhamList= ArrayList()
    adapterHome= RecyclerViewHomeAdapter(object : RecyclerViewHomeAdapter.ClickItem{
      override fun showInfoItem(sanPham: SanPham) {
        homeActivity.sendDataToDetailActivity(sanPham)
      }
    })
    recyclerView.layoutManager = GridLayoutManager(context, 2)
    recyclerView.adapter=adapterHome
    recyclerView.setHasFixedSize(true)
  }

  private fun getGameById(idGame:Long){
    gameLoader = object : LoaderManager.LoaderCallbacks<SanPham>{
      override fun onCreateLoader(id: Int, args: Bundle?): Loader<SanPham> {
        return context?.let { GetSanPham(it, "SELECT sanpham.*, loaisanpham.tenloai FROM sanpham\n" +
                "INNER JOIN loaisanpham ON sanpham.loai_id = loaisanpham.id\n" +
                "WHERE sanpham.id = $idGame") }!!
      }

      override fun onLoadFinished(loader: Loader<SanPham>, data: SanPham?) {
        if(data!=null){
          if(!isShow){
            homeActivity.sendDataToDetailActivity(data)
          }
          GAME_ID+=1
          isShow = true
        }
      }

      override fun onLoaderReset(loader: Loader<SanPham>) {

      }
    }
    loaderManager.initLoader(GAME_ID, null, gameLoader)
  }

  private fun actionViewFlipper(mangQuangCao:ArrayList<QuangCao>) {
    for(i in 0 until mangQuangCao.size){
      val imageView= ImageView(context)
      Picasso.get().load(mangQuangCao[i].getHinhAnh()).into(imageView)
      imageView.scaleType = ImageView.ScaleType.FIT_XY
      viewFlipper.addView(imageView)
      imageView.setOnClickListener {
        isShow = false
        getGameById(mangQuangCao[i].getSanPhamID())
      }
    }
    viewFlipper.flipInterval = 5000 //chạy khoảng 5s
    viewFlipper.isAutoStart = true // cho tự động chạy
    //animation
    val viewFlipperIn = AnimationUtils.loadAnimation(context, R.anim.view_flipper_in)
    val viewFlipperOut = AnimationUtils.loadAnimation(context, R.anim.view_flipper_out)
    viewFlipper.inAnimation=viewFlipperIn
    viewFlipper.outAnimation=viewFlipperOut
  }

  private fun anhXa(view: View) {
    viewFlipper = view.findViewById(R.id.viewFlipperHome)
    txtViewAll = view.findViewById(R.id.textviewViewAll)
    txtViewAll2 = view.findViewById(R.id.textviewViewAll2)
    recyclerView = view.findViewById(R.id.recylerViewHome)
    recylerView2=view.findViewById(R.id.recylerViewHome2)
    recyclerView3=view.findViewById(R.id.recylerViewHome3)
    homeActivity = activity as HomeActivity
  }
}
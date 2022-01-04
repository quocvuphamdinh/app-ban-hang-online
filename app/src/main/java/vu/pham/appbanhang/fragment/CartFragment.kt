package vu.pham.appbanhang.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.adapter.ListCartBottomSheetDialogAdapter
import vu.pham.appbanhang.adapter.RecyclerViewCartAdapter
import vu.pham.appbanhang.loaddata.GetListCartSanPham
import vu.pham.appbanhang.loaddata.Update
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.model.User
import java.sql.Timestamp
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class CartFragment : Fragment() {

    private val CART_ID=1
    private var CART_CHECK_ID=2
    private var CART_DELETE_ID=100
    private lateinit var user: User
    private lateinit var cartDeleteLoader:LoaderManager.LoaderCallbacks<Boolean>
    private lateinit var cartLoader:LoaderManager.LoaderCallbacks<ArrayList<CartSanPham>>
    private lateinit var cartCheckLoader:LoaderManager.LoaderCallbacks<Boolean>
    private lateinit var cartList:ArrayList<CartSanPham>
    private lateinit var recyclerViewCart:RecyclerView
    private lateinit var adapterCart : RecyclerViewCartAdapter
    private lateinit var buttonXuLyCart:Button
    private lateinit var listsBottomSheet:ArrayList<CartSanPham>
    private lateinit var adapterBottomSheetDialog:ListCartBottomSheetDialogAdapter
    private lateinit var homeActivity: HomeActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.cart_fragment, container, false)
        anhXa(view)
        getUser()
        buttonXuLyCart.setOnClickListener {
            showBottomSheetDialog()
        }
        return view
    }

    private fun getUser() {
        val bundle = arguments
        user = bundle?.getParcelable("userProfile")!!
        getCart()
    }

    private fun getCart() {
        cartLoader = object : LoaderManager.LoaderCallbacks<ArrayList<CartSanPham>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<CartSanPham>> {
                return context?.let {
                    GetListCartSanPham(
                        it, "SELECT giohang.*, sanpham.tensanpham, sanpham.giasanpham, sanpham.hinhanh, sanpham.soluongsanpham FROM giohang \n" +
                                "INNER JOIN sanpham ON giohang.sanpham_id = sanpham.id\n" +
                                "WHERE giohang.user_id = ${user.getId()} AND giohang.deleted = 0 ORDER BY giohang.id DESC")
                }!!
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<CartSanPham>>,
                data: ArrayList<CartSanPham>?
            ) {
                if (data!=null){
                    cartList = data
                    showCart()
                }
            }

            override fun onLoaderReset(loader: Loader<ArrayList<CartSanPham>>) {
               cartList.clear()
            }
        }
        loaderManager.initLoader(CART_ID, null, cartLoader)
    }
    private fun updateCheckedCart(cartSanPhamCheck: CartSanPham){
        val timeNow = Timestamp(System.currentTimeMillis())
        cartCheckLoader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return context?.let { Update(it, "UPDATE giohang SET selected = ${cartSanPhamCheck.getSelected()}, soluong = ${cartSanPhamCheck.getSoLuong()}, " +
                        "tongtien = ${cartSanPhamCheck.getTongTien()}, update_at='${timeNow}' WHERE id = ${cartSanPhamCheck.getId()}") }!!
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if (data!=null){

                }
                CART_CHECK_ID+=1
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        loaderManager.initLoader(CART_CHECK_ID, null, cartCheckLoader)
    }
    private fun deleteCart(cartDelete:CartSanPham){
        cartDeleteLoader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return context?.let { Update(it, "DELETE FROM giohang WHERE id = ${cartDelete.getId()}") }!!
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if(data!=null){
                    if (data){
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
                CART_DELETE_ID+=1
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        loaderManager.initLoader(CART_DELETE_ID, null, cartDeleteLoader)
    }
    private fun showCart(){
        listsBottomSheet.clear()
        adapterCart = RecyclerViewCartAdapter(object : RecyclerViewCartAdapter.EventItemCart{
            override fun checkedItem(cartSanPham: CartSanPham, state: Boolean) {
               if(state){
                   if(!listsBottomSheet.contains(cartSanPham)){
                       listsBottomSheet.add(cartSanPham)
                   }
               }else{
                   listsBottomSheet.remove(cartSanPham)
               }
                updateCheckedCart(cartSanPham)
            }

            override fun checkedItem2(cartSanPham: CartSanPham, state: Boolean) {
                if(state) {
                    if(!listsBottomSheet.contains(cartSanPham)){
                        listsBottomSheet.add(cartSanPham)
                    }
                }
            }

            override fun tangVaGiamSoLuongChange(cartSanPham: CartSanPham) {
                updateCheckedCart(cartSanPham)
            }

            override fun deleteItem(cartSanPham: CartSanPham) {
                if (listsBottomSheet.contains(cartSanPham)){
                    listsBottomSheet.remove(cartSanPham)
                }
                cartList.remove(cartSanPham)
                deleteCart(cartSanPham)
            }
        })
        adapterCart.setData(cartList)
        recyclerViewCart.layoutManager = LinearLayoutManager(context)
        recyclerViewCart.adapter = adapterCart
        recyclerViewCart.setHasFixedSize(true)
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = context?.let { BottomSheetDialog(it) }
        bottomSheetDialog?.setContentView(R.layout.bottom_sheet_dialog_cart)
        val listView :ListView? = bottomSheetDialog?.findViewById(R.id.listViewBottomSheetDialog)
        val buttonThanhToan:Button? = bottomSheetDialog?.findViewById(R.id.buttonThanhToanBottomSheetDialog)
        val buttonHuyThanhToan:Button? = bottomSheetDialog?.findViewById(R.id.buttonDismissBottomSheetDialog)
        val txtTongTien:TextView? = bottomSheetDialog?.findViewById(R.id.textViewTongTien)
        val txtChuaChon:TextView? = bottomSheetDialog?.findViewById(R.id.textViewChuaChon)
        bottomSheetDialog?.show()

        if(listsBottomSheet.size>0){
            txtChuaChon?.visibility = View.GONE
            listView?.visibility = View.VISIBLE
            buttonHuyThanhToan?.visibility = View.VISIBLE
        }else{
            txtChuaChon?.visibility = View.VISIBLE
            listView?.visibility = View.GONE
            buttonThanhToan?.visibility = View.GONE
        }
        val tong = tinhTongTien(listsBottomSheet)
        val decimalFormat = DecimalFormat("###,###,###")
        txtTongTien?.text = "Tổng tiền: ${decimalFormat.format(tong)} Đ"
        listsBottomSheet.reverse()
        adapterBottomSheetDialog = context?.let { ListCartBottomSheetDialogAdapter(it, R.layout.cart_item_bottom_sheet_dialog, listsBottomSheet) }!!
        listView?.adapter = adapterBottomSheetDialog
        buttonThanhToan?.setOnClickListener {
            bottomSheetDialog.dismiss()
            homeActivity.sendDataToThanhToanActivity(listsBottomSheet)
        }
        buttonHuyThanhToan?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog?.setOnDismissListener {
            listsBottomSheet.reverse()
        }
    }
    private fun tinhTongTien(lists:ArrayList<CartSanPham>):Int{
        var tong =0
        for (i in 0 until lists.size){
            tong+=lists[i].getTongTien()
        }
        return tong
    }

    private fun anhXa(view: View) {
        recyclerViewCart = view.findViewById(R.id.recyclerViewCart)
        buttonXuLyCart = view.findViewById(R.id.buttonXuLyCart)
        homeActivity = activity as HomeActivity
        cartList = ArrayList()
        listsBottomSheet = ArrayList()
    }

}
package vu.pham.appbanhang.activity

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import vu.pham.appbanhang.R
import vu.pham.appbanhang.adapter.RecyclerViewDiaChiUserAdapter
import vu.pham.appbanhang.loaddata.GetListDiaChiUser
import vu.pham.appbanhang.loaddata.Insert
import vu.pham.appbanhang.loaddata.Update
import vu.pham.appbanhang.model.DiaChi
import vu.pham.appbanhang.model.User

class DiaChiActivity : AppCompatActivity() {
    private lateinit var toolBarDiaChi:Toolbar
    private lateinit var recyclerViewDiaChI:RecyclerView
    private lateinit var floatingButtonAdd:FloatingActionButton
    private lateinit var diaChiAddLoader:LoaderManager.LoaderCallbacks<Long>
    private lateinit var diaChiListLoader:LoaderManager.LoaderCallbacks<ArrayList<DiaChi>>
    private lateinit var diaChiUpdateLoader:LoaderManager.LoaderCallbacks<Boolean>
    private lateinit var diaChiDeleteLoader:LoaderManager.LoaderCallbacks<Boolean>
    private lateinit var diaChiUpdate2Loader:LoaderManager.LoaderCallbacks<Boolean>
    private lateinit var user: User
    private lateinit var diaChiList:ArrayList<DiaChi>
    private lateinit var adapterDiaChi:RecyclerViewDiaChiUserAdapter
    private lateinit var diaChiDefaultOne:DiaChi
    private var DIACHI_ADD_ID=2
    private var DIACHI_LIST_ID=300
    private var DIACHI_UPDATE_ID=100
    private var DIACHI_DELETE_ID=500
    private var DIACHI_UPDATE2_ID= 900
    private lateinit var diaChiDefault2:DiaChi
    private lateinit var diaChiAdd:DiaChi
    private var update=false
    private var add = false
    private var add2=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dia_chi)

        anhXa()
        initToolBar()
        getUser()
        floatingButtonAdd.setOnClickListener {
            initDialogAddDiaChi()
        }
    }

    private fun getDiaChiDefaultOne(lists:ArrayList<DiaChi>) : DiaChi{
        var diachiOne = DiaChi()
        for (i in 0 until lists.size){
            if (lists[i].getDefaultAddress()==1){
                diachiOne = lists[i]
                break
            }
        }
        return diachiOne
    }
    private fun showListDiaChi(){
        adapterDiaChi = RecyclerViewDiaChiUserAdapter(object : RecyclerViewDiaChiUserAdapter.ClickItem{
            override fun click(diaChi: DiaChi) {
                diaChiDefaultOne = getDiaChiDefaultOne(diaChiList)
                if (diaChiDefaultOne.getId()!=0L){
                    diaChiDefault2 = diaChi
                    update = true
                    updateDefaultAddress(diaChiDefaultOne.getId(), 0)
                }else{
                    updateDefaultAddress(diaChi.getId(), 1)
                }
            }

            override fun delete(diaChi: DiaChi) {
                deleteDiaChi(diaChi)
            }

            override fun edit(diaChi: DiaChi) {
                showDiaLogUpdateDiaChi(diaChi)
            }
        })
        adapterDiaChi.setData(diaChiList)
        recyclerViewDiaChI.layoutManager = LinearLayoutManager(this@DiaChiActivity)
        recyclerViewDiaChI.adapter = adapterDiaChi
        recyclerViewDiaChI.setHasFixedSize(true)
    }

    private fun showDiaLogUpdateDiaChi(diaChiDialogEdit:DiaChi){
        val dialog = Dialog(this@DiaChiActivity)
        dialog.setContentView(R.layout.dialog_edit_diachi)

        val edtTextHoTen = dialog.findViewById<EditText>(R.id.edtTextHoTenDiaChiEdit)
        val edtTextPhone = dialog.findViewById<EditText>(R.id.edtTextPhoneDiaChiEdit)
        val edtTextAddress = dialog.findViewById<EditText>(R.id.edtTextAddressDiaChiEdit)
        val btnUpdate = dialog.findViewById<Button>(R.id.buttonUpdateDiaChi)

        edtTextHoTen.setText(diaChiDialogEdit.getName().toString())
        edtTextPhone.setText(diaChiDialogEdit.getPhone())
        edtTextAddress.setText(diaChiDialogEdit.getAddress())

        btnUpdate.setOnClickListener {
            val hoTen = edtTextHoTen.text.toString().trim()
            val phone= edtTextPhone.text.toString().trim()
            val address = edtTextAddress.text.toString().trim()
            if (hoTen.isEmpty() || phone.isEmpty() || address.isEmpty()){
                Toast.makeText(this@DiaChiActivity, "Thông tin không được để trống", Toast.LENGTH_LONG).show()
            }else{
                diaChiDialogEdit.setName(hoTen)
                diaChiDialogEdit.setPhone(phone)
                diaChiDialogEdit.setAddress(address)
                updateDiaChi(diaChiDialogEdit)
            }
            dialog.dismiss()
        }
        dialog.show()
    }
    private fun updateDiaChi(diaChiEdit: DiaChi) {
        diaChiUpdate2Loader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return Update(this@DiaChiActivity, "UPDATE diachi_user SET name = '${diaChiEdit.getName()}', phone = '${diaChiEdit.getPhone()}', address = '${diaChiEdit.getAddress()}' " +
                        "WHERE id = ${diaChiEdit.getId()}")
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if (data!=null){
                    if (data){
                        Toast.makeText(this@DiaChiActivity, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                        getListDiaChi()
                    }else{
                        Toast.makeText(this@DiaChiActivity, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
                DIACHI_UPDATE2_ID+=1
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        supportLoaderManager.initLoader(DIACHI_UPDATE2_ID, null, diaChiUpdate2Loader)
    }

    private fun deleteDiaChi(diaChiDelete:DiaChi) {
        diaChiDeleteLoader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return Update(this@DiaChiActivity, "DELETE FROM diachi_user WHERE id = ${diaChiDelete.getId()}")
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if (data!=null){
                    if (data){
                        Toast.makeText(this@DiaChiActivity, "Xóa thành công", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this@DiaChiActivity, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                    }
                }
                DIACHI_DELETE_ID+=1
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        supportLoaderManager.initLoader(DIACHI_DELETE_ID, null, diaChiDeleteLoader)
    }

    private fun getUser() {
        user = intent.extras?.getParcelable("userDiaChi")!!
        getListDiaChi()
    }

    private fun initDialogAddDiaChi(){
        val dialog = Dialog(this@DiaChiActivity)
        dialog.setContentView(R.layout.dialog_add_diachi)

        val edtTextHoTen = dialog.findViewById<EditText>(R.id.edtTextHoTenDiaChi)
        val edtTextPhone = dialog.findViewById<EditText>(R.id.edtTextPhoneDiaChi)
        val edtTextAddress = dialog.findViewById<EditText>(R.id.edtTextAddressDiaChi)
        val btnHoanThanh = dialog.findViewById<Button>(R.id.buttonHoanThanhDiaChi)
        val switchDefaultAddress=dialog.findViewById<Switch>(R.id.switchDefaulfAddress)

        btnHoanThanh.setOnClickListener {
            val hoten=edtTextHoTen.text.toString().trim()
            val phone= edtTextPhone.text.toString().trim()
            val address = edtTextAddress.text.toString().trim()
            val isSwitched = switchDefaultAddress.isChecked
            if (hoten.isEmpty() || phone.isEmpty() || address.isEmpty()){
                Toast.makeText(this@DiaChiActivity, "Thông tin không được để trống", Toast.LENGTH_LONG).show()
            }else{
                val diaChi = DiaChi()
                diaChi.setName(hoten)
                diaChi.setPhone(phone)
                diaChi.setAddress(address)
                if (isSwitched){
                    diaChi.setDefaultAddress(1)
                }else{
                    diaChi.setDefaultAddress(0)
                }
                if (diaChi.getDefaultAddress()==1){
                    diaChiDefaultOne = getDiaChiDefaultOne(diaChiList)
                    if (diaChiDefaultOne.getId()!=0L){
                        add = true
                        add2+=1
                        diaChiAdd = diaChi
                        updateDefaultAddress(diaChiDefaultOne.getId(), 0)
                    }else{
                        addDiaChi(diaChi)
                    }
                }else{
                    addDiaChi(diaChi)
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun getListDiaChi(){
        diaChiListLoader = object : LoaderManager.LoaderCallbacks<ArrayList<DiaChi>>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<DiaChi>> {
                return GetListDiaChiUser(this@DiaChiActivity, "SELECT * FROM diachi_user WHERE user_id = ${user.getId()}")
            }

            override fun onLoadFinished(
                loader: Loader<ArrayList<DiaChi>>,
                data: ArrayList<DiaChi>?
            ) {
                if (data!=null){
                    diaChiList = data
                    showListDiaChi()
                }
                DIACHI_LIST_ID+=1
            }

            override fun onLoaderReset(loader: Loader<ArrayList<DiaChi>>) {

            }
        }
        supportLoaderManager.initLoader(DIACHI_LIST_ID, null, diaChiListLoader)
    }
    private fun updateDefaultAddress(idDiaChi:Long, default:Int){
        diaChiUpdateLoader = object : LoaderManager.LoaderCallbacks<Boolean>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Boolean> {
                return Update(this@DiaChiActivity, "UPDATE diachi_user SET default_address = $default WHERE id = $idDiaChi")
            }

            override fun onLoadFinished(loader: Loader<Boolean>, data: Boolean?) {
                if (data!=null){
                    if (data){
                        DIACHI_UPDATE_ID+=1
                        if (update){
                            update = false
                            updateDefaultAddress(diaChiDefault2.getId(), 1)
                        }
                        if (add){
                            add = false
                            addDiaChi(diaChiAdd)
                        }
                    }
                    getListDiaChi()
                    add2=0
                }
            }

            override fun onLoaderReset(loader: Loader<Boolean>) {

            }
        }
        supportLoaderManager.initLoader(DIACHI_UPDATE_ID, null, diaChiUpdateLoader)
    }
    private fun addDiaChi(diaChiNew:DiaChi) {
        diaChiAddLoader = object : LoaderManager.LoaderCallbacks<Long>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Long> {
                return Insert(this@DiaChiActivity, "INSERT INTO diachi_user (user_id, name, phone, address, default_address) " +
                        "VALUES (${user.getId()}, '${diaChiNew.getName()}', '${diaChiNew.getPhone()}', '${diaChiNew.getAddress()}', ${diaChiNew.getDefaultAddress()})")
            }

            override fun onLoadFinished(loader: Loader<Long>, data: Long?) {
                if (data!=null){
                    Toast.makeText(this@DiaChiActivity, "Thêm địa chỉ thành công", Toast.LENGTH_LONG).show()
                    if (add2==0){
                        getListDiaChi()
                    }
                }else{
                    Toast.makeText(this@DiaChiActivity, "Thêm địa chỉ thất bại", Toast.LENGTH_LONG).show()
                }
                DIACHI_ADD_ID+=1
            }

            override fun onLoaderReset(loader: Loader<Long>) {

            }
        }
        supportLoaderManager.initLoader(DIACHI_ADD_ID, null, diaChiAddLoader)
    }

    private fun initToolBar() {
        setSupportActionBar(toolBarDiaChi)
        toolBarDiaChi.setNavigationIcon(R.drawable.ic_back)
        toolBarDiaChi.setNavigationOnClickListener {
            finish()
            overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
        }
    }

    private fun anhXa() {
        toolBarDiaChi = findViewById(R.id.toolBarDiaChiActivity)
        recyclerViewDiaChI = findViewById(R.id.recyclerViewDiaChi)
        floatingButtonAdd = findViewById(R.id.floatingActionButtonAdd)

        diaChiList = ArrayList()
    }
}
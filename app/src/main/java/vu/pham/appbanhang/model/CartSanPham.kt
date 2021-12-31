package vu.pham.appbanhang.model

import java.sql.Timestamp

class CartSanPham {
    private var userId:Long=0
    private var sanPhamId:Long=0
    private var trangThai:Long=0
    private var trangThaiName:String=""
    private var soLuong:Int=0
    private var id:Long=0
    private var createAt: Timestamp?=null
    private var updateAt: Timestamp?=null
    private var deleted:Int=0
    private var deletedAt: Timestamp?=null
    private var tenSanPham:String=""
    private var giaSanPham:Int =0
    private var hinhAnh:String=""

    fun getSoLuong():Int{
        return soLuong
    }
    fun setSoLuong(sl:Int){
        soLuong = sl
    }
    fun getUserId():Long{
        return userId
    }
    fun setUserId(id:Long){
        userId = id
    }
    fun getSanPhamId():Long{
        return sanPhamId
    }
    fun setSanPhamId(id:Long){
        sanPhamId = id
    }
    fun getTrangThai():Long{
        return trangThai
    }
    fun setTrangThai(status:Long){
        trangThai = status
    }
    fun getTrangThaiName():String{
        return trangThaiName
    }
    fun setTrangThaiName(statusName:String){
        trangThaiName = statusName
    }
    fun getId():Long{
        return id
    }
    fun setId(id:Long){
        this.id=id
    }
    fun getCreateAt():Timestamp?{
        return createAt
    }
    fun setCreateAt(create_at:Timestamp?){
        createAt=create_at
    }
    fun getUpdateAt():Timestamp?{
        return updateAt
    }
    fun setUpdateAt(update_at:Timestamp?){
        updateAt=update_at
    }
    fun getDeletedAt():Timestamp?{
        return deletedAt
    }
    fun setDeletedAt(deleted_at:Timestamp?){
        deletedAt=deleted_at
    }
    fun getDeleted():Int{
        return deleted
    }
    fun setDeleted(delete:Int){
        deleted=delete
    }
    fun getTenSanPham():String{
        return tenSanPham
    }
    fun setTenSanPham(ten:String){
        tenSanPham=ten
    }
    fun getGiaSanPham():Int{
        return giaSanPham
    }
    fun setGiaSanPham(gia:Int){
        giaSanPham=gia
    }
    fun getHinhAnh():String{
        return hinhAnh
    }
    fun setHinhAnh(hinh:String){
        hinhAnh=hinh
    }
}
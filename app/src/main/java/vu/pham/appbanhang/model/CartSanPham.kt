package vu.pham.appbanhang.model

import android.os.Parcel
import android.os.Parcelable
import java.sql.Timestamp

class CartSanPham() :Parcelable {
    private var id:Long=0
    private var userId:Long=0
    private var sanPhamId:Long=0
    private var trangThai:Long=0
    private var trangThaiName:String=""
    private var soLuong:Int=0
    private var selected:Int=0
    private var tongTien:Int=0
    private var createAt: Timestamp?=null
    private var updateAt: Timestamp?=null
    private var deleted:Int=0
    private var deletedAt: Timestamp?=null
    private var tenSanPham:String=""
    private var giaSanPham:Int =0
    private var hinhAnh:String=""
    private var soLuongSanPham:Int =0

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        userId = parcel.readLong()
        sanPhamId = parcel.readLong()
        trangThai = parcel.readLong()
        trangThaiName = parcel.readString().toString()
        soLuong = parcel.readInt()
        selected = parcel.readInt()
        tongTien = parcel.readInt()
        deleted = parcel.readInt()
        tenSanPham = parcel.readString().toString()
        giaSanPham = parcel.readInt()
        hinhAnh = parcel.readString().toString()
        soLuongSanPham = parcel.readInt()
//        createAt =  Timestamp(parcel.readLong())
//        updateAt = Timestamp(parcel.readLong())
//        deletedAt = Timestamp(parcel.readLong())
    }

    fun getSoLuongSanPham():Int{
        return soLuongSanPham
    }
    fun setSoLuongSanPham(soluong:Int){
        soLuongSanPham = soluong
    }
    fun getTongTien():Int{
        return tongTien
    }
    fun setTongTien(tong:Int){
        tongTien = tong
    }
    fun getSelected():Int{
        return selected
    }
    fun setSelected(select:Int){
        selected = select
    }
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(userId)
        parcel.writeLong(sanPhamId)
        parcel.writeLong(trangThai)
        parcel.writeString(trangThaiName)
        parcel.writeInt(soLuong)
        parcel.writeInt(selected)
        parcel.writeInt(tongTien)
        parcel.writeInt(deleted)
        parcel.writeString(tenSanPham)
        parcel.writeInt(giaSanPham)
        parcel.writeString(hinhAnh)
        parcel.writeInt(soLuongSanPham)
//        createAt?.date?.let { parcel.writeLong(it.toLong()) }
//        updateAt?.date?.let { parcel.writeLong(it.toLong()) }
//        deletedAt?.date?.let { parcel.writeLong(it.toLong()) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CartSanPham> {
        override fun createFromParcel(parcel: Parcel): CartSanPham {
            return CartSanPham(parcel)
        }

        override fun newArray(size: Int): Array<CartSanPham?> {
            return arrayOfNulls(size)
        }
    }
}
package vu.pham.appbanhang.model

import android.os.Parcel
import android.os.Parcelable
import java.sql.Timestamp

class Cart() : Parcelable {
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

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        userId = parcel.readLong()
        sanPhamId = parcel.readLong()
        trangThai = parcel.readLong()
        trangThaiName = parcel.readString().toString()
        soLuong = parcel.readInt()
        deleted = parcel.readInt()
        createAt =  Timestamp(parcel.readLong())
        updateAt = Timestamp(parcel.readLong())
        deletedAt = Timestamp(parcel.readLong())
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

    override fun describeContents(): Int {
       return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(userId)
        parcel.writeLong(sanPhamId)
        parcel.writeLong(trangThai)
        parcel.writeString(trangThaiName)
        parcel.writeInt(soLuong)
        parcel.writeInt(deleted)
        createAt?.date?.let { parcel.writeLong(it.toLong()) }
        updateAt?.date?.let { parcel.writeLong(it.toLong()) }
        deletedAt?.date?.let { parcel.writeLong(it.toLong()) }
    }

    companion object CREATOR : Parcelable.Creator<Cart> {
        override fun createFromParcel(parcel: Parcel): Cart {
            return Cart(parcel)
        }

        override fun newArray(size: Int): Array<Cart?> {
            return arrayOfNulls(size)
        }
    }
}
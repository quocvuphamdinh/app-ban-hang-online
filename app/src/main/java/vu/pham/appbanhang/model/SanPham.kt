package vu.pham.appbanhang.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.sql.Timestamp

class SanPham():Parcelable {
    private var TenSanPham:String=""
    private var GiaSanPham:Int=0
    private var MoTa:String=""
    private var HinhAnh:String=""
    private var Loai:Long=0
    private var LoaiName:String=""
    private var id:Long=0
    private var createAt: Timestamp?=null
    private var updateAt: Timestamp?=null
    private var deleted:Int=0
    private var deletedAt: Timestamp?=null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        TenSanPham = parcel.readString().toString()
        GiaSanPham = parcel.readInt()
        MoTa = parcel.readString().toString()
        HinhAnh = parcel.readString().toString()
        Loai = parcel.readLong()
        LoaiName = parcel.readString().toString()
        deleted = parcel.readInt()
        createAt =  Timestamp(parcel.readLong())
        updateAt = Timestamp(parcel.readLong())
        deletedAt = Timestamp(parcel.readLong())
    }

    fun getLoaiName():String{
        return LoaiName
    }
    fun setLoaiName(loaiName:String){
        LoaiName = loaiName
    }
    fun getLoai():Long{
        return Loai
    }
    fun setLoai(loai:Long){
        Loai = loai
    }
    fun getTenSanPham():String{
        return TenSanPham
    }
    fun setTenSanPham(ten:String){
        TenSanPham=ten
    }
    fun getGiaSanPham():Int{
        return GiaSanPham
    }
    fun setGiaSanPham(gia:Int){
        GiaSanPham=gia
    }
    fun getMoTa():String{
        return MoTa
    }
    fun setMoTa(mota:String){
        MoTa=mota
    }
    fun getHinhAnh():String{
        return HinhAnh
    }
    fun setHinhAnh(hinh:String){
        HinhAnh=hinh
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

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(TenSanPham)
        parcel.writeInt(GiaSanPham)
        parcel.writeString(MoTa)
        parcel.writeString(HinhAnh)
        parcel.writeLong(Loai)
        parcel.writeString(LoaiName)
        parcel.writeInt(deleted)
        createAt?.date?.let { parcel.writeLong(it.toLong()) }
        updateAt?.date?.let { parcel.writeLong(it.toLong()) }
        deletedAt?.date?.let { parcel.writeLong(it.toLong()) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SanPham> {
        override fun createFromParcel(parcel: Parcel): SanPham {
            return SanPham(parcel)
        }

        override fun newArray(size: Int): Array<SanPham?> {
            return arrayOfNulls(size)
        }
    }
}
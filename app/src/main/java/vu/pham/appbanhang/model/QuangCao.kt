package vu.pham.appbanhang.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class QuangCao() : Parcelable {
    private var id:Long=0
    private var sanphamID:Long=0
    private var hinhAnh:String=""

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        sanphamID = parcel.readLong()
        hinhAnh = parcel.readString().toString()
    }

    fun getId():Long{
        return id
    }
    fun setId(id:Long){
        this.id=id
    }
    fun getSanPhamID():Long{
        return sanphamID
    }
    fun setSanPhamID(id:Long){
        sanphamID = id
    }
    fun getHinhAnh():String{
        return hinhAnh
    }
    fun setHinhAnh(hinh:String){
        hinhAnh = hinh
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(sanphamID)
        parcel.writeString(hinhAnh)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuangCao> {
        override fun createFromParcel(parcel: Parcel): QuangCao {
            return QuangCao(parcel)
        }

        override fun newArray(size: Int): Array<QuangCao?> {
            return arrayOfNulls(size)
        }
    }

}
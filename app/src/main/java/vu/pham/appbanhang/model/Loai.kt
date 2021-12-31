package vu.pham.appbanhang.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class Loai() : Parcelable {
    private var id:Long=0
    private var TenLoai:String=""

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        TenLoai = parcel.readString().toString()
    }

    fun getId():Long{
        return id
    }
    fun setId(id:Long){
        this.id=id
    }
    fun getTenLoai():String{
        return TenLoai
    }
    fun setTenLoai(tenLoai:String){
        TenLoai = tenLoai
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(TenLoai)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Loai> {
        override fun createFromParcel(parcel: Parcel): Loai {
            return Loai(parcel)
        }

        override fun newArray(size: Int): Array<Loai?> {
            return arrayOfNulls(size)
        }
    }
}
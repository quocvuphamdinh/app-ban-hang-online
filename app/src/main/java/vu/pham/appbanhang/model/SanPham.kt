package vu.pham.appbanhang.model

import java.io.Serializable

class SanPham() : Serializable{
    private var TenSanPham:String=""
    private var GiaSanPham:Int=0
    private var MoTa:String=""
    private var HinhAnh:String=""

    constructor(TenSanPham: String, GiaSanPham: Int, MoTa: String, HinhAnh: String) : this() {
        this.TenSanPham = TenSanPham
        this.GiaSanPham = GiaSanPham
        this.MoTa = MoTa
        this.HinhAnh=HinhAnh
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
}
package vu.pham.appbanhang.model

import java.sql.Timestamp

class DonHang {
    private var id:Long=0
    private var userId:Long=0
    private var gioHangId:Long=0
    private var tenDonHang:String=""
    private var trangThai:Long=0
    private var trangThaiName:String=""
    private var addressUser:String=""
    private var tongTien:Int=0
    private var timeDatHang:Timestamp?=null
    private var timeThanhToan:Timestamp?=null

    fun getId():Long{
        return id
    }
    fun setId(id:Long){
        this.id = id
    }
    fun getUserId():Long{
        return userId
    }
    fun setUserId(userId:Long){
        this.userId = userId
    }
    fun getGioHangId():Long{
        return gioHangId
    }
    fun setGioHangId(gioHangId:Long){
        this.gioHangId = gioHangId
    }
    fun getTenDonHang():String{
        return tenDonHang
    }
    fun setTenDonHang(tenDonHang:String){
        this.tenDonHang = tenDonHang
    }
    fun getTrangThai():Long{
        return trangThai
    }
    fun setTrangThai(trangThai:Long){
        this.trangThai = trangThai
    }
    fun getTrangThaiName():String{
        return trangThaiName
    }
    fun setTrangThaiName(trangThaiName:String){
        this.trangThaiName = trangThaiName
    }
    fun getAddressUser():String{
        return addressUser
    }
    fun setAddressUser(addressUser:String){
        this.addressUser = addressUser
    }
    fun getTongTien():Int{
        return tongTien
    }
    fun setTongTien(tongTien:Int){
        this.tongTien = tongTien
    }
    fun getTimeDatHang():Timestamp?{
        return timeDatHang
    }
    fun setTimeDatHang(timeDatHang:Timestamp){
        this.timeDatHang = timeDatHang
    }
    fun getTimeThanhToan():Timestamp?{
        return timeThanhToan
    }
    fun setTimeThanhToan(timeThanhToan:Timestamp){
        this.timeThanhToan = timeThanhToan
    }
}
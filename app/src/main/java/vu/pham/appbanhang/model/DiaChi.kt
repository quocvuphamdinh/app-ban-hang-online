package vu.pham.appbanhang.model

class DiaChi {
    private var id:Long=0
    private var userId:Long=0
    private var name:String=""
    private var phone:String=""
    private var address:String=""
    private var defaultAddress:Int=0

    fun getId():Long{
        return id
    }
    fun setId(id:Long){
        this.id = id
    }
    fun getUserId():Long{
        return userId
    }
    fun setUserId(userid:Long){
        this.userId = userid
    }
    fun getName():String{
        return name
    }
    fun setName(name:String){
        this.name = name
    }
    fun getPhone():String{
        return phone
    }
    fun setPhone(phone:String){
        this.phone = phone
    }
    fun getAddress():String{
        return address
    }
    fun setAddress(address:String){
        this.address = address
    }
    fun getDefaultAddress():Int{
        return defaultAddress
    }
    fun setDefaultAddress(defaultAddress:Int){
        this.defaultAddress= defaultAddress
    }
}
package vu.pham.appbanhang.model

class User {
    private var fullName:String
    private var userName:String
    private var passWord:String

    constructor(fullname:String, username:String, password:String){
        this.fullName=fullname
        this.userName=username
        this.passWord=password
    }
    fun getFullName():String{
        return fullName
    }
    fun setFullName(fulname:String){
        this.fullName=fulname
    }
    fun getUserName():String{
        return userName
    }
    fun setUserName(username:String){
        this.userName=username
    }
    fun getPassWord():String{
        return passWord
    }
    fun setPassWord(password:String){
        this.passWord=password
    }
    fun isValidUser():Boolean{
        if(fullName.isNotEmpty() && userName.length > 5 && passWord.length > 6){
            return true
        }
        return false
    }
}
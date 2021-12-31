package vu.pham.appbanhang.model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.sql.Timestamp

class User() : Parcelable{
    private var id:Long=0
    private var fullName:String=""
    private var userName:String=""
    private var passWord:String=""
    private var status:Long=0
    private var createAt: Timestamp?=null
    private var updateAt: Timestamp?=null
    private var deleted:Int=0
    private var deletedAt: Timestamp?=null

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        fullName = parcel.readString().toString()
        userName = parcel.readString().toString()
        passWord = parcel.readString().toString()
        status = parcel.readLong()
        deleted = parcel.readInt()
        createAt =  Timestamp(parcel.readLong())
        updateAt = Timestamp(parcel.readLong())
        deletedAt = Timestamp(parcel.readLong())
    }


    fun getId():Long{
        return id
    }
    fun setId(id:Long){
        this.id=id
    }
    fun getStatus():Long{
        return status
    }
    fun setStatus(st:Long){
        status =st
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
    fun isValidUser():Boolean{
        if(fullName.isNotEmpty() && userName.length > 5 && passWord.length > 6){
            return true
        }
        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(fullName)
        parcel.writeString(userName)
        parcel.writeString(passWord)
        parcel.writeLong(status)
        parcel.writeInt(deleted)
        createAt?.date?.let { parcel.writeLong(it.toLong()) }
        updateAt?.date?.let { parcel.writeLong(it.toLong()) }
        deletedAt?.date?.let { parcel.writeLong(it.toLong()) }
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
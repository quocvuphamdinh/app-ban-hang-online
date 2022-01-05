package vu.pham.appbanhang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import vu.pham.appbanhang.R
import vu.pham.appbanhang.activity.HomeActivity
import vu.pham.appbanhang.model.User
import java.sql.Timestamp

class ProfileFragment: Fragment() {

    private lateinit var imgViewHinh:ImageView
    private lateinit var txtFullName:TextView
    private lateinit var txtUsername:TextView
    private lateinit var txtStatus:TextView
    private lateinit var layoutGioHang:RelativeLayout
    private lateinit var layoutDonHang:RelativeLayout
    private lateinit var homeActivity: HomeActivity
    private lateinit var txtEdit:TextView
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        anhXa(view)
        getUser()

        layoutGioHang.setOnClickListener {
            homeActivity.goToGioHangFragment()
        }
        layoutDonHang.setOnClickListener {
            homeActivity.goToDonHangFragment()
        }
        imgViewHinh.setOnClickListener {
            Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
        }
        txtEdit.setOnClickListener {
            editTaiKhoan()
        }
        return view
    }

    private fun editTaiKhoan() {
        homeActivity.sendDataToEditTaiKhoanActivity(user)
    }

    @SuppressLint("SetTextI18n")
    private fun getUser() {
        val bundle = arguments
        user = bundle?.getParcelable("userProfile")!!
        val createAt:Timestamp? = bundle.getSerializable("createAtUser") as Timestamp?
        user.setCreateAt(createAt)
        txtFullName.text = user.getFullName()
        txtUsername.text = user.getUserName()
        if(user.getStatus()==0L){
            txtStatus.text = "Đang online"
        }else{
            txtStatus.text = "Đã offline"
        }
    }

    private fun anhXa(view: View) {
        imgViewHinh = view.findViewById(R.id.imageViewProfile)
        txtFullName = view.findViewById(R.id.textViewFullNameProfile)
        txtUsername = view.findViewById(R.id.textViewUsernameProfile)
        txtStatus = view.findViewById(R.id.textViewStatusProfile)
        layoutGioHang = view.findViewById(R.id.layoutGioHangProfile)
        layoutDonHang = view.findViewById(R.id.layoutDonHangProfile)
        homeActivity = activity as HomeActivity
        txtEdit = view.findViewById(R.id.textViewEditTaiKhoan)
    }
}
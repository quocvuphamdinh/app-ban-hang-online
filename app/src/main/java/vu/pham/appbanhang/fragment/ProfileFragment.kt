package vu.pham.appbanhang.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import vu.pham.appbanhang.R
import vu.pham.appbanhang.model.User

class ProfileFragment: Fragment() {

    private lateinit var imgViewHinh:ImageView
    private lateinit var txtFullName:TextView
    private lateinit var txtUsername:TextView
    private lateinit var txtStatus:TextView
    private lateinit var txtCreateAt:TextView
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_fragment, container, false)
        anhXa(view)
        getUser()
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun getUser() {
        val bundle = arguments
        user = bundle?.getParcelable("userProfile")!!
        txtFullName.text = user.getFullName()
        txtUsername.text = user.getUserName()
        if(user.getStatus()==0L){
            txtStatus.text = "Đang online"
        }else{
            txtStatus.text = "Đã offline"
        }
//        txtCreateAt.text = "${user.getCreateAt()?.day}/${user.getCreateAt()?.month?.plus(1)}/${user.getCreateAt()?.year?.plus(
//            1900
//        )}"
        txtCreateAt.text =user.getCreateAt().toString()
    }

    private fun anhXa(view: View) {
        imgViewHinh = view.findViewById(R.id.imageViewProfile)
        txtFullName = view.findViewById(R.id.textViewFullNameProfile)
        txtUsername = view.findViewById(R.id.textViewUsernameProfile)
        txtStatus = view.findViewById(R.id.textViewStatusProfile)
        txtCreateAt = view.findViewById(R.id.textViewCreateAtProfile)
    }
}
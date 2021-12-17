package vu.pham.appbanhang.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import vu.pham.appbanhang.R
import vu.pham.appbanhang.databinding.ActivitySignUpBinding
import vu.pham.appbanhang.model.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private var showPass = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sign_up)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener {
            onCLickSignUp()
        }
        binding.textviewLogin.setOnClickListener {
            onCLickLogin()
        }
        binding.imageViewShowPassSignup.setOnClickListener {
            onClickShowPass()
        }
    }

    private fun onClickShowPass() {
        if(showPass){
            showPass=false
            binding.editTextPasswordSignup.transformationMethod= PasswordTransformationMethod.getInstance()
            binding.imageViewShowPassSignup.setImageResource(R.drawable.eye)
        }else{
            showPass=true
            binding.editTextPasswordSignup.transformationMethod= HideReturnsTransformationMethod.getInstance()
            binding.imageViewShowPassSignup.setImageResource(R.drawable.hide_password)
        }
    }

    private fun onCLickLogin() {
        finish()
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
    }

    private fun onCLickSignUp() {
        var fullname = binding.editTextHoTenSignup.text.toString().trim()
        var username = binding.editTextUsernameSignup.text.toString().trim()
        var password = binding.editTextPasswordSignup.text.toString().trim()
        var user = User(fullname, username, password)
        if(user.isValidUser()){
            Toast.makeText(this@SignUpActivity, "Đăng ký thành công !", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this@SignUpActivity, "Đăng ký thất bại !"+"\nHọ tên không hợp lệ"+"\nTên tài khoản phải đủ 5 ký tự trở lên"+
                "\nMật khẩu phải đủ 6 ký tự trở lên", Toast.LENGTH_LONG).show()
        }
    }
}
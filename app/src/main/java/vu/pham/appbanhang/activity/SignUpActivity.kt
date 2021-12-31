package vu.pham.appbanhang.activity

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
    }
}
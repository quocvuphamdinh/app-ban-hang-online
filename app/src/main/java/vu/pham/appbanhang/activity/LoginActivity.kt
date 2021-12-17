package vu.pham.appbanhang.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import vu.pham.appbanhang.R
import vu.pham.appbanhang.databinding.ActivityLoginBinding
import vu.pham.appbanhang.model.User

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private var showPass = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textviewSignup.setOnClickListener {
            onCLickSignUp()
        }
        binding.buttonLogin.setOnClickListener {
            onClickLogin()
        }
        binding.imageViewShowPassLogin.setOnClickListener {
            onClickShowPass()
        }
    }

    private fun onClickShowPass() {
        if(showPass){
            showPass=false
            binding.editTextPasswordLogin.transformationMethod=PasswordTransformationMethod.getInstance()
            binding.imageViewShowPassLogin.setImageResource(R.drawable.eye)
        }else{
            showPass=true
            binding.editTextPasswordLogin.transformationMethod=HideReturnsTransformationMethod.getInstance()
            binding.imageViewShowPassLogin.setImageResource(R.drawable.hide_password)
        }
    }

    private fun onClickLogin() {
        var username = binding.editTextUsernameLogin.text.toString().trim()
        var password = binding.editTextPasswordLogin.text.toString().trim()
        var user = User("Quốc Vũ", username, password)
        if(user.getUserName().equals("vupham22") && user.getPassWord().equals("123")){
            Toast.makeText(this@LoginActivity, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show()
            val intent=Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
        }else{
            Toast.makeText(this@LoginActivity, "Đăng nhập thất bại !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onCLickSignUp() {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
    }
}
package vu.pham.appbanhang.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import vu.pham.appbanhang.R
import vu.pham.appbanhang.databinding.ActivitySignUpBinding
import vu.pham.appbanhang.loaddata.Insert
import vu.pham.appbanhang.model.User
import java.sql.Timestamp

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var userInsertLoader:LoaderManager.LoaderCallbacks<Long>
    private val USER_ID=1
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
    private fun insertUser(user: User){
        val timeNow = Timestamp(System.currentTimeMillis())
        userInsertLoader = object : LoaderManager.LoaderCallbacks<Long>{
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Long> {
                return Insert(this@SignUpActivity, "INSERT INTO user (username, password, full_name, create_at) VALUES " +
                        "('${user.getUserName()}', '${user.getPassWord()}', '${user.getFullName()}', '$timeNow')")
            }

            override fun onLoadFinished(loader: Loader<Long>, data: Long?) {
                if (data!=null){
                    Toast.makeText(this@SignUpActivity, "T???o t??i kho???n th??nh c??ng", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this@SignUpActivity, "T???o t??i kho???n th???t b???i", Toast.LENGTH_LONG).show()
                }
                finish()
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left)
            }
            override fun onLoaderReset(loader: Loader<Long>) {

            }
        }
        supportLoaderManager.initLoader(USER_ID, null, userInsertLoader)
    }
    private fun onCLickSignUp() {
        var fullname = binding.editTextHoTenSignup.text.toString().trim()
        var username = binding.editTextUsernameSignup.text.toString().trim()
        var password = binding.editTextPasswordSignup.text.toString().trim()
        val user = User()
        user.setUserName(username)
        user.setFullName(fullname)
        user.setPassWord(password)
        if (user.isValidUser()){
            insertUser(user)
        }else{
            Toast.makeText(this@SignUpActivity, "Th??ng tin t??i kho???n kh??ng h???p l??? !\nT??n t??i kho???n ph???i l???n 5\nM???t kh???u ph???i l???n 6",
            Toast.LENGTH_LONG).show()
        }
    }
}
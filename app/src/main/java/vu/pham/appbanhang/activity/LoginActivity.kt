package vu.pham.appbanhang.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import vu.pham.appbanhang.R
import vu.pham.appbanhang.databinding.ActivityLoginBinding
import vu.pham.appbanhang.model.User
import vu.pham.appbanhang.utils.Server
import java.sql.*
import android.view.View


class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private  var user: User? = null
    private var showPass = false
    private var username =""
    private var password =""
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
    @SuppressLint("StaticFieldLeak")
    inner class GetUser : AsyncTask<String, Void, User>(){
        override fun onPreExecute() {
            super.onPreExecute()
            binding.progressBarLogin.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): User {
            val userDatabase = User()
            try {
                Class.forName(Server.className)
                val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
                val statement = connection.createStatement()
                val resultSet = statement.executeQuery(params[0])
                while (resultSet.next()){
                    userDatabase.setId(resultSet.getLong("id"))
                    userDatabase.setUserName(resultSet.getString("username"))
                    userDatabase.setPassWord(resultSet.getString("password"))
                    userDatabase.setFullName(resultSet.getString("full_name"))
                    userDatabase.setStatus(resultSet.getLong("status_act"))
                    userDatabase.setCreateAt(resultSet.getTimestamp("create_at"))
                    userDatabase.setUpdateAt(resultSet.getTimestamp("update_at"))
                    userDatabase.setDeleted(resultSet.getInt("deleted"))
                    userDatabase.setDeletedAt(resultSet.getTimestamp("deleted_at"))
                }
                connection.close()
            }catch (e : SQLException) {
                Log.d("hivu", e.toString())
            }
            return userDatabase
        }

        override fun onPostExecute(result: User) {
            super.onPostExecute(result)
            user = result
            binding.progressBarLogin.visibility = View.GONE
            goToHomePage()
        }
    }
    private fun goToHomePage(){
        if(user?.getUserName()?.trim().equals(username) && user?.getPassWord()?.trim().equals(password)){
            Toast.makeText(this@LoginActivity, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show()
            val intent=Intent(this@LoginActivity, HomeActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            intent.putExtras(bundle)
            startActivity(intent)
            overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
        }else{
            Toast.makeText(this@LoginActivity, "Đăng nhập thất bại. Vì tài khoản không đúng !", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickLogin() {
        username = binding.editTextUsernameLogin.text.toString().trim()
        password = binding.editTextPasswordLogin.text.toString().trim()
        GetUser().execute("SELECT * FROM user WHERE username = '$username' AND password='$password'")
    }


    private fun onCLickSignUp() {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.login_to_sign_up, R.anim.sign_up_to_login)
    }
}
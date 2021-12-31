package vu.pham.appbanhang.utils

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class Server {
    companion object{
        const val ipServer ="192.168.0.108"
        const val className ="com.mysql.jdbc.Driver"
        const val database="appbanhang"
        const val username="quocvu"
        const val password="22082000vu"
        const val url = "jdbc:mysql://$ipServer:3306/$database?characterEncoding=utf8"
    }
}
package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.mapper.CartSanPhamMapper
import vu.pham.appbanhang.model.CartSanPham
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class GetListCartSanPham : AsyncTaskLoader<ArrayList<CartSanPham>> {
    private var sql=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }

    override fun loadInBackground(): ArrayList<CartSanPham>? {
        val lists:ArrayList<CartSanPham> = ArrayList()
        try {
            Class.forName(Server.className)
            val connection =
                DriverManager.getConnection(Server.url, Server.username, Server.password)
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()){
                val cartSanPhamMapper = CartSanPhamMapper()
                val cartSanPham = cartSanPhamMapper.mapper(resultSet)
                lists.add(cartSanPham)
            }
            connection.close()
            statement.close()
            resultSet.close()
        }catch (e:SQLException){
            Log.d("hivuCartSanPham", e.toString())
        }
        return lists
    }

    override fun onStartLoading() {
        forceLoad()
    }
}
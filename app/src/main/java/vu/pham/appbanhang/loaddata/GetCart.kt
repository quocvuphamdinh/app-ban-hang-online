package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.mapper.CartMapper
import vu.pham.appbanhang.model.Cart
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class GetCart : AsyncTaskLoader<Cart> {
    private var sql=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }


    override fun loadInBackground(): Cart? {
        var cart = Cart()
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()){
                val cartMapper = CartMapper()
                cart = cartMapper.mapper(resultSet)
            }
            connection.close()
            statement.close()
            resultSet.close()
        }catch (e:SQLException){
            Log.d("hivuCheckCart", e.toString())
        }
        return cart
    }

    override fun onStartLoading() {
        forceLoad()
    }
}
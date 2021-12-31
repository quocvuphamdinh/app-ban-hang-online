package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.mapper.CartMapper
import vu.pham.appbanhang.model.Cart
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class GetListCart : AsyncTaskLoader<ArrayList<Cart>> {
    private var sql=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }

    override fun loadInBackground(): ArrayList<Cart>? {
        val lists:ArrayList<Cart> = ArrayList()
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()){
                val cartMapper = CartMapper()
                val cart = cartMapper.mapper(resultSet)
                lists.add(cart)
            }
            connection.close()
            statement.close()
            resultSet.close()
        }catch (e:SQLException){
            Log.d("hivu", e.toString())
        }
        return lists
    }

    override fun onStartLoading() {
        forceLoad()
    }
}
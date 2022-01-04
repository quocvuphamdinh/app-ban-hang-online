package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.mapper.DonHangMapper
import vu.pham.appbanhang.model.DonHang
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class GetListDonHang : AsyncTaskLoader<ArrayList<DonHang>> {
    private var sql =""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }

    override fun loadInBackground(): ArrayList<DonHang>? {
        val lists : ArrayList<DonHang> = ArrayList()
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()){
                val donHangMapper = DonHangMapper()
                val donHang = donHangMapper.mapper(resultSet)
                lists.add(donHang)
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
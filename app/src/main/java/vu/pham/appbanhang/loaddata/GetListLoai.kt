package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.mapper.LoaiMapper
import vu.pham.appbanhang.model.Loai
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class GetListLoai : AsyncTaskLoader<ArrayList<Loai>> {
    private var sql:String=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }

    override fun loadInBackground(): ArrayList<Loai>? {
        val lists:ArrayList<Loai> = ArrayList()
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()){
                val loaiMapper = LoaiMapper()
                val loai = loaiMapper.mapper(resultSet)
                lists.add(loai)
            }
            connection.close()
            statement.close()
            resultSet.close()
        }catch (e: SQLException){
            Log.d("hivu", e.toString())
        }
        return lists
    }

    override fun onStartLoading() {
        forceLoad()
    }
}
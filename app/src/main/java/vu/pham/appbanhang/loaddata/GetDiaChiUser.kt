package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.mapper.DiaChiMapper
import vu.pham.appbanhang.model.DiaChi
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class GetDiaChiUser : AsyncTaskLoader<DiaChi>{
    private var sql=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }


    override fun loadInBackground(): DiaChi? {
        var diaChi = DiaChi()
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()){
                val diaChiMapper = DiaChiMapper()
                diaChi = diaChiMapper.mapper(resultSet)
            }
            connection.close()
            statement.close()
            resultSet.close()
        }catch (e:SQLException){
            Log.d("hivu", e.toString())
        }
        return diaChi
    }

    override fun onStartLoading() {
        forceLoad()
    }
}
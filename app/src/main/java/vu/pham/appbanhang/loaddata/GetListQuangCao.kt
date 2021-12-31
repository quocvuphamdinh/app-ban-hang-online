package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.mapper.QuangCaoMapper
import vu.pham.appbanhang.model.QuangCao
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException

class GetListQuangCao : AsyncTaskLoader<ArrayList<QuangCao>> {
    private var sql:String=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }

    override fun loadInBackground(): ArrayList<QuangCao>? {
        val lists:ArrayList<QuangCao> = ArrayList()
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            val statement = connection.createStatement()
            val resultSet = statement.executeQuery(sql)
            while (resultSet.next()){
                val quangCaoMapper = QuangCaoMapper()
                val quangCao=quangCaoMapper.mapper(resultSet)
                lists.add(quangCao)
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
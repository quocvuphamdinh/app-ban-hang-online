package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class Update : AsyncTaskLoader<Boolean> {
    private var sql=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }


    override fun loadInBackground(): Boolean? {
        var resultSet:Int?=null
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            connection.autoCommit = false
            val statement = connection.prepareStatement(sql)
            resultSet = statement.executeUpdate()
            connection.commit()
            connection.close()
            statement.close()
        }catch (e:SQLException){
            Log.d("hivuUpdate", e.toString())
        }
        return resultSet!! > 0
    }

    override fun onStartLoading() {
        forceLoad()
    }
}
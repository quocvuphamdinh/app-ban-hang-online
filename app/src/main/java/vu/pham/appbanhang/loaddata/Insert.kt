package vu.pham.appbanhang.loaddata

import android.content.Context
import android.util.Log
import androidx.loader.content.AsyncTaskLoader
import vu.pham.appbanhang.utils.Server
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

class Insert : AsyncTaskLoader<Long> {
    private var sql=""

    constructor(context: Context, sql: String) : super(context) {
        this.sql = sql
    }


    override fun loadInBackground(): Long? {
        var id:Long=0
        try {
            Class.forName(Server.className)
            val connection = DriverManager.getConnection(Server.url, Server.username, Server.password)
            connection.autoCommit = false
            val statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
            statement.executeUpdate()
            val resultSet = statement.generatedKeys
            while (resultSet.next()){
                id = resultSet.getLong(1)
            }
            connection.commit()
            connection.close()
            statement.close()
            resultSet.close()
        }catch (e:SQLException){
            Log.d("hivu", e.toString())
        }
        return id
    }

    override fun onStartLoading() {
        forceLoad()
    }
}
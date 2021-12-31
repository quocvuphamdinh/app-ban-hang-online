package vu.pham.appbanhang.mapper

import vu.pham.appbanhang.model.Loai
import java.sql.ResultSet

class LoaiMapper() {
    fun mapper(resultSet: ResultSet):Loai{
        val loai = Loai()
        loai.setId(resultSet.getLong("id"))
        loai.setTenLoai(resultSet.getString("tenloai"))
        return loai
    }
}
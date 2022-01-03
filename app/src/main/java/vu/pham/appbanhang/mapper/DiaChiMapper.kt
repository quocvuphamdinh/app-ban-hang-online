package vu.pham.appbanhang.mapper

import vu.pham.appbanhang.model.DiaChi
import java.sql.ResultSet

class DiaChiMapper {
    fun mapper(resultSet: ResultSet):DiaChi{
        val diaChi = DiaChi()
        diaChi.setId(resultSet.getLong("id"))
        diaChi.setUserId(resultSet.getLong("user_id"))
        diaChi.setName(resultSet.getString("name"))
        diaChi.setPhone(resultSet.getString("phone"))
        diaChi.setAddress(resultSet.getString("address"))
        diaChi.setDefaultAddress(resultSet.getInt("default_address"))
        return diaChi
    }
}
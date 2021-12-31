package vu.pham.appbanhang.mapper

import vu.pham.appbanhang.model.QuangCao
import java.sql.ResultSet

class QuangCaoMapper(){
    fun mapper(resultSet: ResultSet):QuangCao{
        val quangCao = QuangCao()
        quangCao.setId(resultSet.getLong("id"))
        quangCao.setSanPhamID(resultSet.getLong("sanpham_id"))
        quangCao.setHinhAnh(resultSet.getString("hinhanh"))
        return quangCao
    }
}
package vu.pham.appbanhang.mapper

import vu.pham.appbanhang.model.Cart
import java.sql.ResultSet

class CartMapper {
    fun mapper(resultSet: ResultSet):Cart{
        val cart = Cart()
        cart.setId(resultSet.getLong("id"))
        cart.setUserId(resultSet.getLong("user_id"))
        cart.setSanPhamId(resultSet.getLong("sanpham_id"))
        cart.setTrangThai(resultSet.getLong("trangthai"))
        cart.setCreateAt(resultSet.getTimestamp("create_at"))
        cart.setUpdateAt(resultSet.getTimestamp("update_at"))
        cart.setDeleted(resultSet.getInt("deleted"))
        cart.setDeletedAt(resultSet.getTimestamp("deleted_at"))
        cart.setSoLuong(resultSet.getInt("soluong"))
        return cart
    }
}
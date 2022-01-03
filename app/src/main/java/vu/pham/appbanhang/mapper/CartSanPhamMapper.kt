package vu.pham.appbanhang.mapper

import vu.pham.appbanhang.model.CartSanPham
import java.sql.ResultSet

class CartSanPhamMapper {
    fun mapper(resultSet: ResultSet):CartSanPham{
        val cartSanPham = CartSanPham()
        cartSanPham.setId(resultSet.getLong("id"))
        cartSanPham.setUserId(resultSet.getLong("user_id"))
        cartSanPham.setSanPhamId(resultSet.getLong("sanpham_id"))
        cartSanPham.setTrangThai(resultSet.getLong("trangthai"))
        cartSanPham.setCreateAt(resultSet.getTimestamp("create_at"))
        cartSanPham.setUpdateAt(resultSet.getTimestamp("update_at"))
        cartSanPham.setDeleted(resultSet.getInt("deleted"))
        cartSanPham.setDeletedAt(resultSet.getTimestamp("deleted_at"))
        cartSanPham.setSoLuong(resultSet.getInt("soluong"))
        cartSanPham.setSelected(resultSet.getInt("selected"))
        cartSanPham.setTongTien(resultSet.getInt("tongtien"))
        cartSanPham.setTenSanPham(resultSet.getString("tensanpham"))
        cartSanPham.setGiaSanPham(resultSet.getInt("giasanpham"))
        cartSanPham.setHinhAnh(resultSet.getString("hinhanh"))
        cartSanPham.setSoLuongSanPham(resultSet.getInt("soluongsanpham"))
        return cartSanPham
    }
}
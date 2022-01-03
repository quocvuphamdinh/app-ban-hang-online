package vu.pham.appbanhang.mapper

import vu.pham.appbanhang.model.SanPham
import java.sql.ResultSet

class SanPhamMapper() {
    fun mapper(resultSet: ResultSet): SanPham {
        val sanPham = SanPham()
        sanPham.setId(resultSet.getLong("id"))
        sanPham.setTenSanPham(resultSet.getString("tensanpham"))
        sanPham.setGiaSanPham(resultSet.getInt("giasanpham"))
        sanPham.setMoTa(resultSet.getString("mota"))
        sanPham.setHinhAnh(resultSet.getString("hinhanh"))
        sanPham.setLoai(resultSet.getLong("loai_id"))
        sanPham.setCreateAt(resultSet.getTimestamp("create_at"))
        sanPham.setUpdateAt(resultSet.getTimestamp("update_at"))
        sanPham.setDeleted(resultSet.getInt("deleted"))
        sanPham.setDeletedAt(resultSet.getTimestamp("deleted_at"))
        sanPham.setLoaiName(resultSet.getString("tenloai"))
        sanPham.setSoLuongSanPham(resultSet.getInt("soluongsanpham"))
        return sanPham
    }
}
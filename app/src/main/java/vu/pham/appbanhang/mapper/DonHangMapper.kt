package vu.pham.appbanhang.mapper

import vu.pham.appbanhang.model.DonHang
import java.sql.ResultSet

class DonHangMapper {
    fun mapper(resultSet: ResultSet):DonHang{
        var donHang= DonHang()
        donHang.setId(resultSet.getLong("id"))
        donHang.setUserId(resultSet.getLong("user_id"))
        donHang.setGioHangId(resultSet.getLong("giohang_id"))
        donHang.setTenDonHang(resultSet.getString("tendonhang"))
        donHang.setTrangThai(resultSet.getLong("trangthai"))
        donHang.setAddressUserId(resultSet.getLong("address_user"))
        donHang.setTongTien(resultSet.getInt("tongtien"))
        donHang.setTimeDatHang(resultSet.getTimestamp("time_dat_hang"))
        donHang.setTimeThanhToan(resultSet.getTimestamp("time_thanh_toan"))
        donHang.setAddressUser(resultSet.getString("address"))
        donHang.setTrangThaiName(resultSet.getString("name"))
        return donHang
    }
}
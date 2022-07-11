package dao;

import connection.Connect_MySQL;
import model.NhanVien;
import model.PhongBan;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO implements CRUD<NhanVien> {
    PhongBanDAO phongBanDAO = new PhongBanDAO();

    @Override
    public List<NhanVien> getAll() {
        String sql = "select * from nhanvien";
        List<NhanVien> nhanViens = new ArrayList<>();
        try (Connection connection = Connect_MySQL.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idNv = resultSet.getInt("idnv");
                String tenNv = resultSet.getString("tennv");
                Date ngaySinh = resultSet.getDate("ngaysinh");
                String diaChi = resultSet.getString("diachi");
                String soDienThoai = resultSet.getString("sodienthoai");
                String email = resultSet.getString("email");
                PhongBan phongBan = phongBanDAO.findById(resultSet.getInt("idphongban"));

                nhanViens.add(new NhanVien(idNv, tenNv, ngaySinh, diaChi, soDienThoai, email, phongBan));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nhanViens;
    }

    public List<NhanVien> getAllByName(String name) {
        String sql = "select * from nhanvien where tennv like concat('%',?,'%')";
        List<NhanVien> nhanViens = new ArrayList<>();
        try (Connection connection = Connect_MySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int idNv = resultSet.getInt("idnv");
                String tenNv = resultSet.getString("tennv");
                Date ngaySinh = resultSet.getDate("ngaysinh");
                String diaChi = resultSet.getString("diachi");
                String soDienThoai = resultSet.getString("sodienthoai");
                String email = resultSet.getString("email");
                PhongBan phongBan = phongBanDAO.findById(resultSet.getInt("idphongban"));

                nhanViens.add(new NhanVien(idNv, tenNv, ngaySinh, diaChi, soDienThoai, email, phongBan));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return nhanViens;
    }

    @Override
    public boolean create(NhanVien nhanVien) {
        String sql = "insert into nhanvien value (?,?,?,?,?,?,?)";
        try (Connection connection = Connect_MySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, nhanVien.getIdNv());
            preparedStatement.setString(2, nhanVien.getTenNv());
            preparedStatement.setDate(3, nhanVien.getNgaySinh());
            preparedStatement.setString(4, nhanVien.getDiaChi());
            preparedStatement.setString(5, nhanVien.getSoDienThoai());
            preparedStatement.setString(6, nhanVien.getEmail());
            preparedStatement.setInt(7, nhanVien.getPhongBan().getIdPhongBan());
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean edit(int id, NhanVien nhanVien) {
        String sql = "UPDATE nhanvien SET tennv = ?,ngaysinh = ?, " +
                "diachi = ?,sodienthoai = ?,email = ?, idphongban=? WHERE (idnv = ?)";
        try (Connection connection = Connect_MySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(7, nhanVien.getIdNv());
            preparedStatement.setString(1, nhanVien.getTenNv());
            preparedStatement.setDate(2, nhanVien.getNgaySinh());
            preparedStatement.setString(3, nhanVien.getDiaChi());
            preparedStatement.setString(4, nhanVien.getSoDienThoai());
            preparedStatement.setString(5, nhanVien.getEmail());
            preparedStatement.setInt(6, nhanVien.getPhongBan().getIdPhongBan());
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from nhanvien WHERE idnv = ?";
        try (Connection connection = Connect_MySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public NhanVien findById(int id) {
        String sql = "select * from nhanvien where idnv = " + id;
        try (Connection connection = Connect_MySQL.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            resultSet.next();
            int idNv = resultSet.getInt("idnv");
            String tenNv = resultSet.getString("tennv");
            Date ngaySinh = resultSet.getDate("ngaysinh");
            String diaChi = resultSet.getString("diachi");
            String soDienThoai = resultSet.getString("sodienthoai");
            String email = resultSet.getString("email");
            PhongBan phongBan = phongBanDAO.findById(resultSet.getInt("idphongban"));

            return new NhanVien(idNv, tenNv, ngaySinh, diaChi, soDienThoai, email, phongBan);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}

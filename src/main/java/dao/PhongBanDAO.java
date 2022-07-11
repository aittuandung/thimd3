package dao;

import connection.Connect_MySQL;
import model.PhongBan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongBanDAO implements CRUD<PhongBan> {
    @Override
    public List<PhongBan> getAll() {
        String sql = "SELECT * FROM thimd3.phongban";
        List<PhongBan> phongBans = new ArrayList<>();
        try (Connection connection = Connect_MySQL.getConnect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int idPhongBan = resultSet.getInt(1);
                String tenPhongBan = resultSet.getString(2);
                phongBans.add(new PhongBan(idPhongBan, tenPhongBan));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return phongBans;
    }

    @Override
    public boolean create(PhongBan phongBan) {
        return false;
    }

    @Override
    public boolean edit(int id, PhongBan phongBan) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public PhongBan findById(int id) {
        String sql = "SELECT * FROM thimd3.phongban where id = ?";
        try (Connection connection = Connect_MySQL.getConnect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int idPhongBan = resultSet.getInt(1);
            String tenPhongBan = resultSet.getString(2);
            PhongBan phongBan = new PhongBan(idPhongBan, tenPhongBan);
            return phongBan;


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}

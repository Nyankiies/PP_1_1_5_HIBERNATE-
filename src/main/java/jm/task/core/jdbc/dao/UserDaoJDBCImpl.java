package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection connect = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        System.out.println(connect);
        try(Statement statement = connect.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS User " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT not null, name VARCHAR(45),lastName VARCHAR(45), age INT)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate("Drop TABLE IF EXISTS User");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connect.prepareStatement("INSERT INTO User(name, lastName, age) VALUE (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connect.prepareStatement("DELETE FROM User WHERE id = ?")) {
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = connect.createStatement().executeQuery("SELECT * FROM User")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connect.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE User");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

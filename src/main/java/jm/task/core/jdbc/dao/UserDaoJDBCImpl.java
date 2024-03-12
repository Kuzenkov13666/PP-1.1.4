package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS  Users (
                id  BIGSERIAL PRIMARY KEY ,
                name TEXT UNIQUE,
                last_name TEXT,
                age SMALLINT
                );
                                
                """;
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void dropUsersTable() {
        String sql = """
                DROP TABLE IF EXISTS Users
                 """;
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO  users (name,
                            last_name,
                            age) VALUES (?, ?, ?);
                """;
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users
                WHERE id = ?;
                """;
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        String sql = """
                SELECT   id, name, last_name, age
                   FROM users
                """;
        List<User> list = new ArrayList<>();
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            var executeResult = preparedStatement.executeQuery();
            while (executeResult.next()) {
                User user = new User(executeResult.getString("name"),
                        executeResult.getString("last_name"),
                        executeResult.getByte("age"));
                user.setId(executeResult.getLong("id"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        String sql = """
                DELETE
                FROM users
                """;
        try (var connection = getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

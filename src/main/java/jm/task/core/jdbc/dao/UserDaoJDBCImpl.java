package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS User(id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(40), " +
                "lastname VARCHAR(40), " +
                "age INT(120));";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при создании таблицы. createUsersTable()");
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS User;";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при удалении таблицы. dropUsersTable()");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO User (name, lastName, age) VALUES (?, ?, ?);";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User с именем - " + name + " добавлен в базу данных.");
            } else {
                System.out.println("Ошибка добавления пользователя.");
            }

        } catch (SQLException e) {
            System.out.println("Возникла ошибка в добавлении пользователя. saveUser()");
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM User WHERE id = ?";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Возникла ошибка в удалении пользователя. removeUserById()");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                Byte age = resultSet.getByte("age");

                users.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            System.out.println("Возникла ошибка в получении пользователей. getAllUsers()");
        }

        return users;
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM User";

        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Возникла ошибка в удалении пользователя. removeUserById()");
        }
    }
}

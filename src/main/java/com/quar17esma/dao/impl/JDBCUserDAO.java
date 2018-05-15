package com.quar17esma.dao.impl;

import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Role;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDAO implements UserDAO {
    private static final Logger LOGGER = Logger.getLogger(JDBCUserDAO.class);

    private Connection connection;

    public JDBCUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement("SELECT * FROM user ")) {
            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                User user = createUser(rs);
                users.add(user);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find users", ex);
            throw new RuntimeException(ex);
        }
        return users;
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM user " +
                                     "WHERE id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                User user = createUser(rs);

                result = Optional.of(user);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find user with id = " + id, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM user " +
                                     "WHERE email = ?")) {
            query.setString(1, email);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                User user = createUser(rs);

                result = Optional.of(user);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find user with email = " + email, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    private User createUser(ResultSet rs) throws SQLException {

        User user = new User.Builder()
                .setId(rs.getInt("user.id"))
                .setEmail(rs.getString("user.email"))
                .setPassword(rs.getString("user.password"))
                .setRole(Role.valueOf(rs.getString("user.role").toUpperCase()))
                .build();
        
        return user;
    }

    @Override
    public boolean update(User user) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE user " +
                                     "SET email = ?, password = ?, role = ?" +
                                     "WHERE id = ?")) {
            query.setString(1, user.getEmail());
            query.setString(2, user.getPassword());
            query.setString(3,user.getRole().name());
            query.setInt(4, user.getId());

            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            LOGGER.error("Fail to update user with id = " + user.getId(), ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "DELETE FROM user " +
                                     "WHERE id = ?")) {
            query.setInt(1, id);
            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            LOGGER.error("Fail to delete user with id = " + id, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(User user) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO user (email, password) " +
                                     "VALUES(?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, user.getEmail());
            query.setString(2, user.getPassword());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                user.setId(result);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to insert user: " + user.toString(), ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}

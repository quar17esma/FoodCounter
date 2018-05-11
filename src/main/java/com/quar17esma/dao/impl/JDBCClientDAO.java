package com.quar17esma.dao.impl;

import com.quar17esma.dao.ClientDAO;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Gender;
import com.quar17esma.enums.Lifestyle;
import com.quar17esma.enums.Role;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCClientDAO implements ClientDAO {
//    private static final Logger LOGGER = Logger.getLogger(JDBCClientDAO.class);

    private Connection connection;

    public JDBCClientDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM client " +
                        "JOIN user ON client.id = user.id ")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Client client = createClientWithUser(rs);
                clients.add(client);
            }
        } catch (Exception ex) {
//            LOGGER.error("Fail to find clients", ex);
            throw new RuntimeException(ex);
        }
        return clients;
    }

    @Override
    public Optional<Client> findById(int id) {

        Optional<Client> result = Optional.empty();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM client " +
                        "JOIN user ON client.id = user.id " +
                        "WHERE client.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Client client = createClientWithUser(rs);
                result = Optional.of(client);
            }
        } catch (Exception ex) {
//            LOGGER.error("Fail to find client by id", ex);
            throw new RuntimeException(ex);
        }
        return result;
    }

    private Client createClientWithUser(ResultSet rs) throws SQLException {

        Client client = new Client.Builder()
                .setId(rs.getInt("client.id"))
                .setName(rs.getString("client.name"))
                .setBirthDate(rs.getDate("client.birth_date").toLocalDate())
                .setHeight(rs.getInt("client.height"))
                .setWeight(rs.getInt("client.weight"))
                .setGender(Gender.valueOf(rs.getString("client.gender").toUpperCase()))
                .setLifestyle(Lifestyle.valueOf(rs.getString("client.lifestyle").toUpperCase()))
                .setUser(new User.Builder()
                        .setId(rs.getInt("user.id"))
                        .setEmail(rs.getString("user.email"))
                        .setPassword(rs.getString("user.password"))
                        .setRole(Role.valueOf(rs.getString("user.role").toUpperCase()))
                        .build())
                .build();

        return client;
    }

    @Override
    public boolean update(Client client) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE client " +
                                     "SET name = ?, birth_date = ?, height = ?, weight = ?, gender = ?, lifestyle = ? " +
                                     "WHERE id = ?")) {
            query.setString(1, client.getName());
            query.setDate(2, Date.valueOf(client.getBirthDate()));
            query.setInt(3, client.getHeight());
            query.setInt(4, client.getWeight());
            query.setString(5, client.getGender().toString());
            query.setString(6, client.getLifestyle().toString());
            query.setInt(7, client.getId());

            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
//            LOGGER.error("Fail to update client", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "DELETE FROM client " +
                                     "WHERE id = ?")) {
            query.setInt(1, id);
            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
//            LOGGER.error("Fail to delete client", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(Client client) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO client (id, name, birth_date, height, weight, gender, lifestyle) " +
                                     "VALUES(?, ?, ?, ?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            query.setInt(1, client.getId());
            query.setString(2, client.getName());
            query.setDate(3, Date.valueOf(client.getBirthDate()));
            query.setInt(4, client.getHeight());
            query.setInt(5, client.getWeight());
            query.setString(6, client.getGender().toString());
            query.setString(7, client.getLifestyle().toString());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                client.setId(result);
            }
        } catch (Exception ex) {
//            LOGGER.error("Fail to insert client", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
package com.quar17esma.service.impl;

import com.quar17esma.dao.ClientDAO;
import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.IClientsService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class ClientsService extends Service implements IClientsService {
    private static final Logger LOGGER = Logger.getLogger(ClientsService.class);

    private ClientsService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static ClientsService INSTANCE =
                new ClientsService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static ClientsService getInstance() {
        return Holder.INSTANCE;
    }

    public void registerClient(Client client) throws BusyEmailException {

        try (Connection connection = connectionPool.getConnection();
             UserDAO userDAO = factory.createUserDAO(connection);
             ClientDAO clientDAO = factory.createClientDAO(connection)) {

            connection.setAutoCommit(false);

            Optional<User> user = userDAO.findByEmail(client.getUser().getEmail());
            if (user.isPresent()) {
                throw new BusyEmailException("Fail to register client, email is busy",
                        client.getName(), client.getUser().getEmail());
            }

            int userId = userDAO.insert(client.getUser());
            client.setId(userId);
            clientDAO.insert(client);

            connection.commit();
            connection.setAutoCommit(true);
        } catch (BusyEmailException e) {
            throw new BusyEmailException(e);
        } catch (Exception e) {
            LOGGER.error("Fail to register client: " + client, e);
            throw new RuntimeException(e);
        }
    }

    public Client getClientByEmail(String email) {
        Optional<Client> client;

        try (Connection connection = connectionPool.getConnection();
             UserDAO userDAO = factory.createUserDAO(connection);
             ClientDAO clientDAO = factory.createClientDAO(connection)) {
            connection.setAutoCommit(false);

            Optional<User> user = userDAO.findByEmail(email);
            client = clientDAO.findById(user.get().getId());

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {
            LOGGER.error("Fail to get client with email = " + email, e);
            throw new RuntimeException(e);
        }

        return client.get();
    }
}

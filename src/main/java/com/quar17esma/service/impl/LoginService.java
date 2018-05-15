package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.LoginException;
import com.quar17esma.service.IClientsService;
import com.quar17esma.service.ILoginService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.Optional;

public class LoginService extends Service implements ILoginService {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class);

    private IClientsService clientsService;

    private LoginService(DaoFactory factory, IClientsService clientsService, ConnectionPool connectionPool) {
        super(factory, connectionPool);
        this.clientsService = clientsService;
    }

    private static class Holder {
        private static LoginService INSTANCE =
                new LoginService(DaoFactory.getInstance(), ClientsService.getInstance(), ConnectionPool.getInstance());
    }

    public static LoginService getInstance() {
        return Holder.INSTANCE;
    }

    public Client login(String email, String password) throws LoginException {
        if (checkLogin(email, password)) {
            return clientsService.getClientByEmail(email);
        } else {
            throw new LoginException("Fail to login", email);
        }
    }

    private boolean checkLogin(String email, String password) {
        boolean result = false;

        if (email != null &&
                password != null &&
                !email.isEmpty() &&
                !password.isEmpty()) {

            try(Connection connection = connectionPool.getConnection();
                UserDAO userDAO = factory.createUserDAO(connection)) {
                connection.setAutoCommit(true);

                Optional<User> user = userDAO.findByEmail(email);
                if (user.isPresent()) {
                    result = user.get().getPassword().equals(password);
                }

            } catch (Exception e) {
                LOGGER.error("Fail to find user with email = " + email, e);
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}

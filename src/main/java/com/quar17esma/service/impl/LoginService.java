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
//    private static final Logger LOGGER = Logger.getLogger(LoginService.class);

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

    public Client login(String login, String password) throws LoginException {
        if (checkLogin(login, password)) {
            return clientsService.getClientByEmail(login);
        } else {
            throw new LoginException("Fail to login", login);
        }
    }

    private boolean checkLogin(String login, String password) {
        boolean result = false;

        if (login != null &&
                password != null &&
                !login.isEmpty() &&
                !password.isEmpty()) {

            try(Connection connection = connectionPool.getConnection();
                UserDAO userDAO = factory.createUserDAO(connection)) {
                connection.setAutoCommit(true);
                Optional<User> user = userDAO.findByEmail(login);
                if (user.isPresent()) {
                    result = user.get().getPassword().equals(password);
                }
            } catch (Exception e) {
//                LOGGER.error("Fail to find user by email", e);
                throw new RuntimeException(e);
            }
        }

        return result;
    }
}

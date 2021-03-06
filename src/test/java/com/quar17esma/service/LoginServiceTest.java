package com.quar17esma.service;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.LoginException;
import com.quar17esma.service.impl.ClientsService;
import com.quar17esma.service.impl.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private ClientsService clientsService;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private UserDAO userDAO;
    @Mock
    private User user;

    @InjectMocks
    private LoginService loginService;

    private String login;
    private String password;

    @Before
    public void setUp() {
        when(user.getPassword()).thenReturn(password);
        when(userDAO.findByEmail(login)).thenReturn(Optional.of(user));
        when(connectionPool.getConnection()).thenReturn(connection);
        when(factory.createUserDAO(any(Connection.class))).thenReturn(userDAO);
    }

    @Test
    public void loginCorrectDataUserExists() throws Exception {
        login = "john@gmail.com";
        password = "john";

        when(user.getPassword()).thenReturn(password);
        when(userDAO.findByEmail(login)).thenReturn(Optional.of(user));

        loginService.login(login, password);

        verify(connectionPool).getConnection();
        verify(factory).createUserDAO(connection);
        verify(userDAO).findByEmail(login);
        verify(clientsService).getClientByEmail(login);
    }

    @Test(expected = LoginException.class)
    public void loginPasswordNull() throws Exception {
        login = "john@gmail.com";
        password = null;

        when(user.getPassword()).thenReturn(password);
        when(userDAO.findByEmail(login)).thenReturn(Optional.of(user));

        loginService.login(login, password);

        verify(connectionPool, never()).getConnection();
        verify(factory, never()).createUserDAO(connection);
        verify(userDAO, never()).findByEmail(login);
        verify(clientsService, never()).getClientByEmail(login);
    }

    @Test(expected = LoginException.class)
    public void loginLoginNull() throws Exception {
        login = null;
        password = "john";

        when(user.getPassword()).thenReturn(password);
        when(userDAO.findByEmail(login)).thenReturn(Optional.of(user));

        loginService.login(login, password);

        verify(connectionPool, never()).getConnection();
        verify(factory, never()).createUserDAO(connection);
        verify(userDAO, never()).findByEmail(login);
        verify(clientsService, never()).getClientByEmail(login);
    }

    @Test(expected = LoginException.class)
    public void loginPasswordEmpty() throws Exception {
        login = "john@gmail.com";
        password = "";

        when(user.getPassword()).thenReturn(password);
        when(userDAO.findByEmail(login)).thenReturn(Optional.of(user));

        loginService.login(login, password);

        verify(connectionPool, never()).getConnection();
        verify(factory, never()).createUserDAO(connection);
        verify(userDAO, never()).findByEmail(login);
        verify(clientsService, never()).getClientByEmail(login);
    }

    @Test(expected = LoginException.class)
    public void loginLoginEmpty() throws Exception {
        login = "";
        password = "john";

        when(user.getPassword()).thenReturn(password);
        when(userDAO.findByEmail(login)).thenReturn(Optional.of(user));

        loginService.login(login, password);

        verify(connectionPool, never()).getConnection();
        verify(factory, never()).createUserDAO(connection);
        verify(userDAO, never()).findByEmail(login);
        verify(clientsService, never()).getClientByEmail(login);
    }
}
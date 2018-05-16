package com.quar17esma.service;

import com.quar17esma.dao.ClientDAO;
import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.UserDAO;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.User;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.impl.ClientsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientsServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private ClientDAO clientDAO;
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private ClientsService clientsService;

    @Before
    public void setUp() {
        when(connectionPool.getConnection()).thenReturn(connection);
        when(factory.createClientDAO(connection)).thenReturn(clientDAO);
        when(factory.createUserDAO(connection)).thenReturn(userDAO);
    }

    @Test
    public void registerClient() throws Exception {
        Client client = mock(Client.class);
        User user = mock(User.class);
        int userId = 12;
        when(userDAO.findByEmail(anyString())).thenReturn(Optional.empty());
        when(client.getUser()).thenReturn(user);
        when(userDAO.insert(user)).thenReturn(userId);

        clientsService.registerClient(client);

        verify(connectionPool).getConnection();
        verify(factory).createUserDAO(connection);
        verify(factory).createClientDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(userDAO).findByEmail(anyString());
        verify(userDAO).insert(user);
        verify(client).setId(userId);
        verify(clientDAO).insert(client);
        verify(connection).commit();
    }

    @Test(expected = BusyEmailException.class)
    public void registerClientBusyEmailException() throws Exception {
        Client client = mock(Client.class);
        User user = mock(User.class);
        int userId = 12;
        when(userDAO.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(client.getUser()).thenReturn(user);

        clientsService.registerClient(client);

        verify(connectionPool).getConnection();
        verify(factory).createUserDAO(connection);
        verify(factory).createClientDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(userDAO).findByEmail(anyString());

        verify(userDAO, never()).insert(user);
        verify(client, never()).setId(userId);
        verify(clientDAO, never()).insert(client);
        verify(connection, never()).commit();
    }

    @Test
    public void getClientByEmail() throws Exception {
        String email = "john@gmail.com";
        User user = mock(User.class);
        Client client = mock(Client.class);
        when(userDAO.findByEmail(email)).thenReturn(Optional.of(user));
        when(clientDAO.findById(anyInt())).thenReturn(Optional.of(client));

        Client clientResult = clientsService.getClientByEmail(email);

        verify(connectionPool).getConnection();
        verify(factory).createUserDAO(connection);
        verify(factory).createClientDAO(connection);
        verify(connection).setAutoCommit(false);
        verify(userDAO).findByEmail(email);
        verify(clientDAO).findById(anyInt());
        verify(connection).commit();

        assertEquals(client, clientResult);
    }
}
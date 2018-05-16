package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Client;
import com.quar17esma.exceptions.LoginException;
import com.quar17esma.service.impl.LoginService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoginTest {
    @Mock
    private LoginService loginService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Client client;
    @Mock
    private LoginException loginException;

    @InjectMocks
    private Login login;

    private String locale;
    private String email;
    private String password;
    private String errorLoginPassMessage;

    @Before
    public void setUp() {
        locale = "en_US";
        email = "test@gmail.com";
        password = "123456";
        errorLoginPassMessage = LabelManager.getProperty("message.login.error", locale);

        when(session.getAttribute("locale")).thenReturn(locale);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
    }

    @Test
    public void executeLoginPass() throws Exception {
        String page = ConfigurationManager.getProperty("path.page.welcome");

        when(loginService.login(email, password)).thenReturn(client);

        String resultPage = login.execute(request);

        verify(loginService).login(email, password);
        verify(session).setAttribute("client", client);
        verify(request, never()).setAttribute("errorLoginPassMessage", errorLoginPassMessage);

        assertEquals(resultPage, page);
    }

    @Test
    public void executeLoginNotPass() throws Exception {
        String page = ConfigurationManager.getProperty("path.page.login");

        when(loginService.login(email, password)).thenThrow(loginException);

        String resultPage = login.execute(request);

        verify(loginService).login(email, password);
        verify(request).setAttribute("errorLoginPassMessage", errorLoginPassMessage);
        verify(session, never()).setAttribute("client", client);

        assertEquals(resultPage, page);
    }
}

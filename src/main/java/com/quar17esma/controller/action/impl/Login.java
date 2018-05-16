package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Client;
import com.quar17esma.exceptions.LoginException;
import com.quar17esma.service.ILoginService;
import com.quar17esma.service.impl.LoginService;

import javax.servlet.http.HttpServletRequest;

public class Login implements Action {
    private ILoginService loginService;

    public Login() {
        this.loginService = LoginService.getInstance();
    }

    public Login(ILoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        if (locale == null) {
            request.getSession().setAttribute("locale", LabelManager.DEFAULT_LOCALE);
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Client client = loginService.login(email, password);
            request.getSession().setAttribute("client", client);
            page = ConfigurationManager.getProperty("path.page.welcome");

        } catch (LoginException e) {
            request.setAttribute("errorLoginPassMessage",
                    LabelManager.getProperty("message.login.error", locale));

            page = ConfigurationManager.getProperty("path.page.login");
        }

        return page;
    }
}
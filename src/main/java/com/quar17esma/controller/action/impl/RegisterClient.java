package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputClientChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.User;
import com.quar17esma.enums.Gender;
import com.quar17esma.enums.Lifestyle;
import com.quar17esma.exceptions.BusyEmailException;
import com.quar17esma.service.IClientsService;
import com.quar17esma.service.impl.ClientsService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class RegisterClient implements Action {
    private IClientsService clientsService;
    private InputClientChecker checker;

    public RegisterClient() {
        this.clientsService = ClientsService.getInstance();
        this.checker = new InputClientChecker();
    }

    public RegisterClient(IClientsService clientsService, InputClientChecker checker) {
        this.clientsService = clientsService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        if (locale == null) {
            locale = "en_US";
        }

        String name = request.getParameter("name").trim();
        String login = request.getParameter("login").trim();
        String password = request.getParameter("password").trim();
        int height = Integer.parseInt(request.getParameter("height"));
        int weight = Integer.parseInt(request.getParameter("weight"));
        Gender gender = Gender.valueOf(request.getParameter("gender").toUpperCase());
        Lifestyle lifestyle = Lifestyle.valueOf(request.getParameter("lifestyle").toUpperCase());
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));

        boolean isDataCorrect = checkInputData(name, login);

        if (isDataCorrect) {
            Client client = makeClient(name, login, password, height, weight, gender, lifestyle, birthDate);
            page = registerClient(client, request, locale);
        } else {
            request.setAttribute("errorRegistrationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.registration");
        }

        return page;
    }

    private Client makeClient(String name, String login, String password,
                              int height, int weight, Gender gender,
                              Lifestyle lifestyle, LocalDate birthDate) {
        return new Client.Builder()
                .setName(name)
                .setHeight(height)
                .setWeight(weight)
                .setGender(gender)
                .setLifestyle(lifestyle)
                .setBirthDate(birthDate)
                .setUser(new User.Builder()
                        .setEmail(login)
                        .setPassword(password)
                        .build())
                .build();
    }

    private String registerClient(Client client, HttpServletRequest request, String locale) {
        String page = null;

        try {
            clientsService.registerClient(client);
            request.setAttribute("successRegistrationMessage",
                    LabelManager.getProperty("message.success.registration", locale));
            page = ConfigurationManager.getProperty("path.page.login");

        } catch (BusyEmailException e) {
            request.setAttribute("name", client.getName());
            request.setAttribute("login", client.getUser().getEmail());
            request.setAttribute("height", client.getHeight());
            request.setAttribute("weight", client.getWeight());
//            request.setAttribute("gender", client.getGender().toString());
//            request.setAttribute("lifestyle", client.getLifestyle().toString());
            request.setAttribute("birthDate", client.getBirthDate());

            request.setAttribute("errorBusyEmailMessage",
                    LabelManager.getProperty("message.error.busy.email", locale));

            page = ConfigurationManager.getProperty("path.page.registration");
        }

        return page;
    }

    private boolean checkInputData(String name, String login) {
        return checker.isInputDataCorrect(name, login);
    }

}

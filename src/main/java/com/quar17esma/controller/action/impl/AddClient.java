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

public class AddClient implements Action {
    private IClientsService clientsService;
    private InputClientChecker checker;

    public AddClient() {
        this.clientsService = ClientsService.getInstance();
        this.checker = new InputClientChecker();
    }

    public AddClient(IClientsService clientsService, InputClientChecker checker) {
        this.clientsService = clientsService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = getLocaleOrSetDefault(request);

        String name = request.getParameter("name").trim();
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();
        int height = Integer.parseInt(request.getParameter("height"));
        int weight = Integer.parseInt(request.getParameter("weight"));
        Gender gender = Gender.valueOf(request.getParameter("gender").toUpperCase());
        Lifestyle lifestyle = Lifestyle.valueOf(request.getParameter("lifestyle").toUpperCase());
        LocalDate birthDate = LocalDate.parse(request.getParameter("birthDate"));

        boolean isDataCorrect = checker.isInputDataCorrect(name, email, height, weight, birthDate);

        if (isDataCorrect) {
            Client client = makeClient(name, email, password, height, weight, gender, lifestyle, birthDate);
            page = registerClient(client, request, locale);
        } else {
            setDataAttributes(request, name, email, height, weight, birthDate);

            request.setAttribute("errorRegistrationMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.edit.client");
        }

        return page;
    }

    private String getLocaleOrSetDefault(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");
        if (locale == null) {
            locale = LabelManager.DEFAULT_LOCALE;
        }
        return locale;
    }

    private Client makeClient(String name, String email, String password,
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
                        .setEmail(email)
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
            setDataAttributes(request, client.getName(), client.getUser().getEmail(), client.getHeight(),
                    client.getWeight(), client.getBirthDate());
            request.setAttribute("errorBusyEmailMessage",
                    LabelManager.getProperty("message.error.busy.email", locale));
            page = ConfigurationManager.getProperty("path.page.edit.client");
        }

        return page;
    }

    private void setDataAttributes(HttpServletRequest request,
                                   String name, String email, int height, int weight, LocalDate birthDate) {
        request.setAttribute("name", name);
        request.setAttribute("email", email);
        request.setAttribute("height", height);
        request.setAttribute("weight", weight);
        request.setAttribute("birthDate", birthDate);
    }
}

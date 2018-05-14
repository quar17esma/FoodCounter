package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.Meal;
import com.quar17esma.service.IMealService;
import com.quar17esma.service.impl.MealService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowMyMeals implements Action {
    private IMealService mealService;

    public ShowMyMeals() {
        this.mealService = MealService.getInstance();
    }

    public ShowMyMeals(IMealService mealService) {
        this.mealService = mealService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");

        List<Meal> meals = mealService.getMealsByClientId(client.getId());

        request.setAttribute("meals", meals);

        return ConfigurationManager.getProperty("path.page.my.meals");
    }
}

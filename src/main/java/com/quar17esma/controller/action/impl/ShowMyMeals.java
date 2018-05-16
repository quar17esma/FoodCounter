package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.Meal;
import com.quar17esma.service.ICalorieCounter;
import com.quar17esma.service.IMealService;
import com.quar17esma.service.impl.CalorieCounter;
import com.quar17esma.service.impl.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public class ShowMyMeals implements Action {
    private IMealService mealService;
    private ICalorieCounter calorieCounter;

    public ShowMyMeals() {
        this.mealService = MealService.getInstance();
        this.calorieCounter = CalorieCounter.getInstance();
    }

    public ShowMyMeals(IMealService mealService, ICalorieCounter calorieCounter) {
        this.mealService = mealService;
        this.calorieCounter = calorieCounter;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Client client = (Client) request.getSession().getAttribute("client");

        List<Meal> meals = mealService.getMealsByClientIdAndDate(client.getId(), LocalDate.now());
        int calories = calorieCounter.countDailyCalorieNeed(client);
        int caloriesLeft = calorieCounter.countTodayLeftCalories(client);

        request.setAttribute("meals", meals);
        request.setAttribute("calories", calories);
        request.setAttribute("caloriesLeft", caloriesLeft);

        return ConfigurationManager.getProperty("path.page.my.meals");
    }
}

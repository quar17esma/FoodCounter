package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.Food;
import com.quar17esma.entity.Meal;
import com.quar17esma.service.ICalorieCounter;
import com.quar17esma.service.IFoodService;
import com.quar17esma.service.IMealService;
import com.quar17esma.service.impl.CalorieCounter;
import com.quar17esma.service.impl.FoodService;
import com.quar17esma.service.impl.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public class AddMeal implements Action {
    private IMealService mealService;
    private IFoodService foodService;
    private ICalorieCounter calorieCounter;

    public AddMeal() {
        this.mealService = MealService.getInstance();
        this.foodService = FoodService.getInstance();
        this.calorieCounter = CalorieCounter.getInstance();
    }

    public AddMeal(IMealService mealService, IFoodService foodService, ICalorieCounter calorieCounter) {
        this.mealService = mealService;
        this.foodService = foodService;
        this.calorieCounter = calorieCounter;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String locale = (String) request.getSession().getAttribute("locale");

        int gram = Integer.parseInt(request.getParameter("gram"));
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        Food food = foodService.getFoodById(foodId);
        Client client = (Client) request.getSession().getAttribute("client");

        int mealCalories = calorieCounter.countMealKcal(gram, food.getKcal());

        mealService.addMeal(new Meal.Builder()
                .setFood(food)
                .setClient(client)
                .setGram(gram)
                .setKcal(mealCalories)
                .setMealDate(LocalDate.now())
                .build()
        );

        request.setAttribute("successAddMeal",
                LabelManager.getProperty("message.success.add.meal", locale));

        return ConfigurationManager.getProperty("path.page.welcome");
    }

}

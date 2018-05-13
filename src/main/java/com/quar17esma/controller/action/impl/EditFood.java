package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Food;
import com.quar17esma.service.IFoodService;
import com.quar17esma.service.impl.FoodService;

import javax.servlet.http.HttpServletRequest;

public class EditFood implements Action {
    private IFoodService foodService;

    public EditFood() {
        this.foodService = FoodService.getInstance();
    }

    public EditFood(IFoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String foodIdString = request.getParameter("foodId");

        if (foodIdString != null) {
            int foodId = Integer.parseInt(foodIdString);

            Food food = foodService.getFoodById(foodId);

            request.setAttribute("food", food);
        }

        return ConfigurationManager.getProperty("path.page.edit.food");
    }
}

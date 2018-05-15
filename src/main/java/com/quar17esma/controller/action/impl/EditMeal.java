package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Food;
import com.quar17esma.service.IFoodService;
import com.quar17esma.service.impl.FoodService;

import javax.servlet.http.HttpServletRequest;

public class EditMeal implements Action {
    private IFoodService foodService;

    public EditMeal() {
        this.foodService = FoodService.getInstance();
    }

    public EditMeal(IFoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int foodId = Integer.parseInt(request.getParameter("foodId"));

        Food food = foodService.getFoodById(foodId);

        request.setAttribute("food", food);

        return ConfigurationManager.getProperty("path.page.edit.meal");
    }
}

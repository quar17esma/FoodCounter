package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.checker.InputFoodChecker;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Food;
import com.quar17esma.service.IFoodService;
import com.quar17esma.service.impl.FoodService;

import javax.servlet.http.HttpServletRequest;

public class AddFood implements Action {
    private IFoodService foodService;
    private InputFoodChecker checker;

    public AddFood() {
        this.foodService = FoodService.getInstance();
        this.checker = new InputFoodChecker();
    }

    public AddFood(IFoodService foodService, InputFoodChecker checker) {
        this.foodService = foodService;
        this.checker = checker;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String foodIdString =  request.getParameter("foodId");
        String name = request.getParameter("name").trim();
        int carbs = Integer.parseInt(request.getParameter("carbs"));
        int protein = Integer.parseInt(request.getParameter("protein"));
        int fat = Integer.parseInt(request.getParameter("fat"));
        int kcal = Integer.parseInt(request.getParameter("kcal"));

        boolean isDataCorrect = checker.isInputDataCorrect(name, carbs, protein, fat, kcal);

        if (isDataCorrect) {
            Food food = makeFood(name, carbs, protein, fat, kcal);

            if (foodIdString != null && !foodIdString.isEmpty()) {
                int foodId = Integer.parseInt(foodIdString);
                food.setId(foodId);
                foodService.updateFood(food);
            } else {
                foodService.addFood(food);
            }

            request.setAttribute("successAddFoodMessage",
                    LabelManager.getProperty("message.success.add.food", locale));
            page = ConfigurationManager.getProperty("path.page.welcome");
        } else {
            request.setAttribute("errorAddFoodMessage",
                    LabelManager.getProperty("message.error.wrong.data", locale));
            page = ConfigurationManager.getProperty("path.page.edit.food");
        }

        return page;
    }

    private Food makeFood(String name, int carbs, int protein, int fat, int kcal) {
        return new Food.Builder()
                .setName(name)
                .setCarbs(carbs)
                .setProtein(protein)
                .setFat(fat)
                .setKcal(kcal)
                .build();
    }
}

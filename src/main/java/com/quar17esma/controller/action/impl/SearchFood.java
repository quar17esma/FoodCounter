package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.action.ActionEnum;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Food;
import com.quar17esma.service.IFoodService;
import com.quar17esma.service.impl.FoodService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SearchFood implements Action {
    private IFoodService foodService;

    public SearchFood() {
        this.foodService = FoodService.getInstance();
    }

    public SearchFood(IFoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String page = null;

        String locale = (String) request.getSession().getAttribute("locale");
        String searchString = request.getParameter("searchString");

        if (!searchString.isEmpty()) {
            List<Food> foods = foodService.getFoodBySearchInName(searchString);

            if (foods != null && !foods.isEmpty()){
                request.setAttribute("foods", foods);
            } else {
                request.setAttribute("sorryFoodNotFoundMessage",
                        LabelManager.getProperty("message.food.not.found", locale));
            }

            request.setAttribute("searchString", searchString);

            page = ConfigurationManager.getProperty("path.page.foods");
        } else {
            page = ActionEnum.SHOW_FOODS.getCurrentCommand().execute(request);
        }

        return page;
    }
}

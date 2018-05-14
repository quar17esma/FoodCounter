package com.quar17esma.controller.action.impl;

import com.quar17esma.controller.action.Action;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.entity.Food;
import com.quar17esma.service.IFoodService;
import com.quar17esma.service.impl.FoodService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ShowFoods implements Action {
    private static final int DEFAULT_PAGE = 1;
    private static final int FOODS_ON_PAGE = 5;
    private IFoodService foodService;

    public ShowFoods() {
        this.foodService = FoodService.getInstance();
    }

    public ShowFoods(IFoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        int page = setPageOrGetDefault(request.getParameter("page"));
        List<Food> foods = foodService.getFoodByPage(page, FOODS_ON_PAGE);

        int allFoodsQuantity = foodService.getAllFoodQuantity();
        int pagesQuantity = countPagesQuantity(allFoodsQuantity);

        request.setAttribute("foods", foods);
        request.setAttribute("pagesQuantity", pagesQuantity);
        return ConfigurationManager.getProperty("path.page.foods");
    }

    private int countPagesQuantity(int allFoodsQuantity){
        int pagesQuantity;

        if (allFoodsQuantity % FOODS_ON_PAGE != 0){
            pagesQuantity = allFoodsQuantity / FOODS_ON_PAGE + 1;
        } else {
            pagesQuantity = allFoodsQuantity / FOODS_ON_PAGE;
        }

        return pagesQuantity;
    }

    private int setPageOrGetDefault(String pageString){
        int page;

        if (pageString != null) {
            page = Integer.parseInt(pageString);
        } else {
            page = DEFAULT_PAGE;
        }

        return page;
    }
}

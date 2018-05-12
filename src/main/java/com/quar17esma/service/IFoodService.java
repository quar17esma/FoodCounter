package com.quar17esma.service;

import com.quar17esma.entity.Food;

import java.util.List;

public interface IFoodService {

    List<Food> getAllFood();

    List<Food> getFoodByPage(int page, int foodOnPage);

    int getAllFoodQuantity();

    Food getFoodById(int foodId);

    void deleteFoodById(int foodId);

    void addFood(Food food);

    void updateFood(Food food);

}

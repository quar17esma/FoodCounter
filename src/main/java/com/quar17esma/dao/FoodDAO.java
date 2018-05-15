package com.quar17esma.dao;

import com.quar17esma.entity.Food;

import java.util.List;

public interface FoodDAO extends GenericDAO<Food> {

    List<Food> findByPage(int page, int goodsOnPage);

    int countAllFood();
}

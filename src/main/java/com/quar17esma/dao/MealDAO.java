package com.quar17esma.dao;

import com.quar17esma.entity.Meal;

import java.util.List;

public interface MealDAO extends GenericDAO<Meal> {
    List<Meal> findAllByClientId(int clientId);
}

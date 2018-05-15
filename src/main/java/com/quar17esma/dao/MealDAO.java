package com.quar17esma.dao;

import com.quar17esma.entity.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealDAO extends GenericDAO<Meal> {
    List<Meal> findAllByClientIdOrderByMealDateTimeDesc(int clientId);

    List<Meal> findAllByClientIdAndMealDate(int clientId, LocalDate mealDate);
}

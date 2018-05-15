package com.quar17esma.service;

import com.quar17esma.entity.Meal;

import java.time.LocalDate;
import java.util.List;

public interface IMealService {

    List<Meal> getMealsByClientId(int clientId);

    List<Meal> getMealsByClientIdAndDate(int clientId, LocalDate date);

    void addMeal(Meal meal);

}

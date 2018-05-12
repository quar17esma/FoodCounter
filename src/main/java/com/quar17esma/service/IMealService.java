package com.quar17esma.service;

import com.quar17esma.entity.Meal;

import java.util.List;

public interface IMealService {

    List<Meal> getMealsByClientId(int clientId);

}

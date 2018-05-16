package com.quar17esma.service;

import com.quar17esma.entity.Client;

public interface ICalorieCounter {

    int countDailyCalorieNeed(Client client);

    int countTodayLeftCalories(Client client);

    int countMealKcal(int gram, int kcal);
}

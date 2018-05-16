package com.quar17esma.service.impl;

import com.quar17esma.entity.Client;
import com.quar17esma.entity.Meal;
import com.quar17esma.enums.Gender;
import com.quar17esma.enums.Lifestyle;
import com.quar17esma.service.ICalorieCounter;
import com.quar17esma.service.IMealService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class CalorieCounter implements ICalorieCounter {
    private static final int MEN_ADDEND = 5;
    private static final int WOMEN_ADDEND = -161;

    private static final double LAZY_MULTIPLIER = 1.2;
    private static final double ORDINARY_MULTIPLIER = 1.45;
    private static final double ACTIVE_MULTIPLIER = 1.75;

    private IMealService mealService;

    public CalorieCounter() {
        this.mealService = MealService.getInstance();
    }

    public CalorieCounter(IMealService mealService) {
        this.mealService = mealService;
    }

    private static class Holder {
        private static CalorieCounter INSTANCE = new CalorieCounter();
    }

    public static CalorieCounter getInstance() {
        return CalorieCounter.Holder.INSTANCE;
    }


    @Override
    public int countDailyCalorieNeed(Client client) {
        int result = 0;

        int addend = countGenderAddend(client.getGender());
        int age = countClientsAge(client.getBirthDate());
        double multiplier = countLifestyleMultiplier(client.getLifestyle());

        result = (int) (((9.99 * client.getWeight()) + (6.25 * client.getHeight()) - (4.92 * age) + addend) * multiplier);

        return result;
    }

    private double countLifestyleMultiplier(Lifestyle lifestyle) {
        double multiplier = 0.0;

        switch (lifestyle) {
            case LAZY:
                multiplier = LAZY_MULTIPLIER;
                break;
            case ORDINARY:
                multiplier = ORDINARY_MULTIPLIER;
                break;
            case ACTIVE:
                multiplier = ACTIVE_MULTIPLIER;
                break;
        }

        return multiplier;
    }

    private int countGenderAddend(Gender gender) {
        int adder;
        if (gender.equals(Gender.MALE)){
            adder = MEN_ADDEND;
        } else {
            adder = WOMEN_ADDEND;
        }
        return adder;
    }

    private int countClientsAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public int countTodayLeftCalories(Client client) {
        int dailyNeed = countDailyCalorieNeed(client);
        int todayConsumedCalories = countTodayConsumedCalories(client);

        return dailyNeed - todayConsumedCalories;
    }

    private int countTodayConsumedCalories(Client client) {
        int todayConsumedCalories = 0;

        List<Meal> todayMeals = mealService.getMealsByClientIdAndDate(client.getId(), LocalDate.now());

        if (todayMeals != null && todayMeals.size() > 0) {
            for (Meal meal : todayMeals) {
                todayConsumedCalories += meal.getKcal();
            }
        }

        return todayConsumedCalories;
    }

    @Override
    public int countMealKcal(int gram, int kcal) {
        double i = (double) gram / 100.00;

        return (int) (kcal * i);
    }
}

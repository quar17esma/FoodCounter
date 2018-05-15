package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.entity.Client;
import com.quar17esma.enums.Gender;
import com.quar17esma.service.ICalorieCounter;

import java.time.LocalDate;
import java.time.Period;

public class CalorieCounter implements ICalorieCounter {
    private static final int MEN_ADDEND = 5;
    private static final int WOMEN_ADDEND = -161;

    private static final double LAZY_MULTIPLIER = 1.2;
    private static final double ORDINARY_MULTIPLIER = 1.45;
    private static final double ACTIVE_MULTIPLIER = 1.75;

    private static class Holder {
        private static CalorieCounter INSTANCE = new CalorieCounter();
    }

    public static CalorieCounter getInstance() {
        return CalorieCounter.Holder.INSTANCE;
    }


    @Override
    public int countCalorie(Client client) {
        int result = 0;

        int addend = countAddend(client);
        int age = countClientsAge(client);
        double multiplier = countMultiplier(client);

        result = (int) (((9.99 * client.getWeight()) + (6.25 * client.getHeight()) - (4.92 * age) + addend) * multiplier);

        return result;
    }

    private double countMultiplier(Client client) {
        double multiplier = 0.0;

        switch (client.getLifestyle()) {
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

    private int countAddend(Client client) {
        int adder;
        if (client.getGender().equals(Gender.MALE)){
            adder = MEN_ADDEND;
        } else {
            adder = WOMEN_ADDEND;
        }
        return adder;
    }

    private int countClientsAge(Client client) {
        return Period.between(client.getBirthDate(), LocalDate.now()).getYears();
    }
}

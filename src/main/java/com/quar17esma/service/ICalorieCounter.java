package com.quar17esma.service;

import com.quar17esma.entity.Client;

public interface ICalorieCounter {

    int countCalorie(Client client);

    int countLeftCalories(Client client);
}

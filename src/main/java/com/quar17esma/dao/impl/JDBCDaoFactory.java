package com.quar17esma.dao.impl;

import com.quar17esma.dao.*;

import java.sql.Connection;

public class JDBCDaoFactory extends DaoFactory {

    @Override
    public ClientDAO createClientDAO(Connection connection) {
        return new JDBCClientDAO(connection);
    }

    @Override
    public UserDAO createUserDAO(Connection connection) {
        return new JDBCUserDAO(connection);
    }

    @Override
    public FoodDAO createFoodDAO(Connection connection) {
        return new JDBCFoodDAO(connection);
    }

    @Override
    public MealDAO createMealDAO(Connection connection) {
        return new JDBCMealDAO(connection);
    }
}

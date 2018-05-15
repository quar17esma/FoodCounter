package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.MealDAO;
import com.quar17esma.entity.Meal;
import com.quar17esma.service.IMealService;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class MealService extends Service implements IMealService {
//    private static final Logger LOGGER = Logger.getLogger(OrdersService.class);

    private MealService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static MealService INSTANCE = new MealService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static MealService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Meal> getMealsByClientId(int clientId) {
        List<Meal> meals = null;

        try (Connection connection = connectionPool.getConnection();
             MealDAO mealDAO = factory.createMealDAO(connection)) {
            connection.setAutoCommit(true);

            meals = mealDAO.findAllByClientIdOrderByMealDateTimeDesc(clientId);

        } catch (Exception e) {
//            LOGGER.error("Fail to get meals by client id", e);
            throw new RuntimeException(e);
        }

        return meals;
    }

    @Override
    public List<Meal> getMealsByClientIdAndDate(int clientId, LocalDate date) {
        List<Meal> meals = null;

        try (Connection connection = connectionPool.getConnection();
             MealDAO mealDAO = factory.createMealDAO(connection)) {
            connection.setAutoCommit(true);

            meals = mealDAO.findAllByClientIdAndMealDate(clientId, date);

        } catch (Exception e) {
//            LOGGER.error("Fail to get meals by client id", e);
            throw new RuntimeException(e);
        }

        return meals;
    }

    @Override
    public void addMeal(Meal meal) {
        try (Connection connection = connectionPool.getConnection();
             MealDAO mealDAO = factory.createMealDAO(connection)) {
            connection.setAutoCommit(true);
            mealDAO.insert(meal);
        } catch (Exception e) {
//            LOGGER.error("Fail to add food", e);
            throw new RuntimeException(e);
        }
    }
}

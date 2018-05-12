package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FoodDAO;
import com.quar17esma.dao.MealDAO;
import com.quar17esma.entity.Food;
import com.quar17esma.entity.Meal;
import com.quar17esma.service.IMealService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

//        try (Connection connection = connectionPool.getConnection();
//             MealDAO mealDAO = factory.createMealDAO(connection);
//             FoodDAO foodDao = factory.createFoodDAO(connection)) {
//            connection.setAutoCommit(false);
//
//            meals = mealDAO.findAllByClientId(clientId);
//            for (Meal meal : meals) {
//                List<Food> foods = foodDao.findByOrderId(meal.getId());
//                meal.setGoods(foods);
//            }
//
//            connection.commit();
//            connection.setAutoCommit(true);
//        } catch (Exception e) {
////            LOGGER.error("Fail to get meals by client id", e);
//            throw new RuntimeException(e);
//        }

        return meals;
    }
}

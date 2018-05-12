package com.quar17esma.service.impl;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FoodDAO;
import com.quar17esma.entity.Food;
import com.quar17esma.service.IFoodService;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class FoodService extends Service implements IFoodService {
//    private static final Logger LOGGER = Logger.getLogger(FoodService.class);

    private FoodService(DaoFactory factory, ConnectionPool connectionPool) {
        super(factory, connectionPool);
    }

    private static class Holder {
        private static FoodService INSTANCE = new FoodService(DaoFactory.getInstance(), ConnectionPool.getInstance());
    }

    public static FoodService getInstance() {
        return Holder.INSTANCE;
    }

    public List<Food> getAllFood() {
        List<Food> foodList = null;

        try (Connection connection = connectionPool.getConnection();
             FoodDAO foodDAO = factory.createFoodDAO(connection)) {
            connection.setAutoCommit(true);
            foodList = foodDAO.findAll();
        } catch (Exception e) {
//            LOGGER.error("Fail to get all food", e);
            throw new RuntimeException(e);
        }

        return foodList;
    }

    public List<Food> getFoodByPage(int page, int foodOnPage) {
        List<Food> foodList = null;

        try (Connection connection = connectionPool.getConnection();
             FoodDAO foodDAO = factory.createFoodDAO(connection)) {
            connection.setAutoCommit(true);
            foodList = foodDAO.findByPage(page, foodOnPage);
        } catch (Exception e) {
//            LOGGER.error("Fail to get all foodList", e);
            throw new RuntimeException(e);
        }

        return foodList;
    }

    public int getAllFoodQuantity() {
        int foodCounter;
        try (Connection connection = connectionPool.getConnection();
             FoodDAO foodDAO = factory.createFoodDAO(connection)) {
            connection.setAutoCommit(true);
            foodCounter = foodDAO.countAllFood();
        } catch (Exception e) {
//            LOGGER.error("Fail to get all foods", e);
            throw new RuntimeException(e);
        }
        return foodCounter;
    }

    public Food getFoodById(int foodId) {
        try(Connection connection = connectionPool.getConnection();
            FoodDAO foodDAO = factory.createFoodDAO(connection)) {
            connection.setAutoCommit(true);
            Optional<Food> food = foodDAO.findById(foodId);
            return food.get();
        } catch (Exception e) {
//            LOGGER.error("Fail to find food by id", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteFoodById(int foodId) {
        try (Connection connection = connectionPool.getConnection();
             FoodDAO foodDAO = factory.createFoodDAO(connection)) {
            connection.setAutoCommit(true);
            foodDAO.delete(foodId);
        } catch (Exception e) {
//            LOGGER.error("Fail to delete food", e);
            throw new RuntimeException(e);
        }
    }

    public void addFood(Food food) {
        try (Connection connection = connectionPool.getConnection();
             FoodDAO foodDAO = factory.createFoodDAO(connection)) {
            connection.setAutoCommit(true);
            foodDAO.insert(food);
        } catch (Exception e) {
//            LOGGER.error("Fail to add food", e);
            throw new RuntimeException(e);
        }
    }

    public void updateFood(Food food) {
        try (Connection connection = connectionPool.getConnection();
             FoodDAO foodDAO = factory.createFoodDAO(connection)) {
            connection.setAutoCommit(true);
            foodDAO.update(food);
        } catch (Exception e) {
//            LOGGER.error("Fail to update food", e);
            throw new RuntimeException(e);
        }
    }
}

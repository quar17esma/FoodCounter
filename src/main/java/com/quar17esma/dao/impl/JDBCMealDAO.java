package com.quar17esma.dao.impl;

import com.quar17esma.dao.MealDAO;
import com.quar17esma.entity.Food;
import com.quar17esma.entity.Meal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCMealDAO implements MealDAO {
//    private static final Logger LOGGER = Logger.getLogger(JDBCMealDAO.class);

    private Connection connection;

    public JDBCMealDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Meal> findAll() {
        List<Meal> meals = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM meal " +
                        "JOIN food ON meal.id = food.id")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Meal meal = createMealWithFood(rs);
                meals.add(meal);
            }
        } catch (Exception ex) {
//            LOGGER.error("Fail to find meals", ex);
            throw new RuntimeException(ex);
        }

        return meals;
    }

    public List<Meal> findAllByClientId(int clientId) {
        List<Meal> meals = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM meal " +
                        "JOIN food ON meal.id = food.id " +
                        "WHERE meal.client_id = ?")) {
            query.setInt(1, clientId);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Meal meal = createMealWithFood(rs);
                meals.add(meal);
            }
        } catch (Exception ex) {
//            LOGGER.error("Fail to find meals", ex);
            throw new RuntimeException(ex);
        }

        return meals;
    }

    @Override
    public Optional<Meal> findById(int id) {

        Optional<Meal> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM meal " +
                                     "JOIN food ON meal.id = food.id " +
                                     "WHERE meal.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Meal meal = createMealWithFood(rs);
                result = Optional.of(meal);
            }
        } catch (Exception ex) {
//            LOGGER.error("Fail to find order by id", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    private Meal createMealWithFood(ResultSet rs) throws SQLException {
        Meal meal = new Meal.Builder()
                .setId(rs.getInt("meals.id"))
                .setMealDateTime(rs.getTimestamp("meals.meal_date_time").toLocalDateTime())
                .setGram(rs.getInt("meals.gram"))
                .setKcal(rs.getInt("meals.kcal"))
                .setFood(new Food.Builder()
                        .setId(rs.getInt("food.id"))
                        .setName(rs.getString("food.name"))
                        .setCarbs(rs.getInt("food.carbs"))
                        .setProtein(rs.getInt("food.protein"))
                        .setFat(rs.getInt("food.fat"))
                        .setKcal(rs.getInt("food.kcal"))
                        .build())
                .build();

        return meal;
    }

    @Override
    public boolean update(Meal meal) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE meal " +
                                     "SET gram = ?, kcal = ?, meal_date_time = ? " +
                                     "WHERE id = ?")) {
            query.setInt(1, meal.getGram());
            query.setInt(2, meal.getKcal());
            query.setTimestamp(3, Timestamp.valueOf(meal.getMealDateTime()));
            query.setInt(4, meal.getId());

            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
//            LOGGER.error("Fail to update meal", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "DELETE FROM meal " +
                                     "WHERE id = ?")) {
            query.setInt(1, id);
            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
//            LOGGER.error("Fail to delete order", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(Meal meal) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO meal (meal_date_time, food_id, gram, kcal, client_id) " +
                                     "VALUES(?, ?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setTimestamp(1, Timestamp.valueOf(meal.getMealDateTime()));
            query.setInt(2, meal.getFood().getId());
            query.setInt(3,meal.getGram());
            query.setInt(4,meal.getKcal());
            query.setInt(5, meal.getClient().getId());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                meal.setId(result);
            }
        } catch (Exception ex) {
//            LOGGER.error("Fail to insert meal", ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}

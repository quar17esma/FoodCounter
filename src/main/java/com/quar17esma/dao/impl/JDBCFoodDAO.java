package com.quar17esma.dao.impl;

import com.quar17esma.dao.FoodDAO;
import com.quar17esma.entity.Food;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCFoodDAO implements FoodDAO {
    private static final Logger LOGGER = Logger.getLogger(JDBCFoodDAO.class);

    private Connection connection;

    public JDBCFoodDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Food> findAll() {
        List<Food> foods = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM food")) {
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Food food = createFood(rs);
                foods.add(food);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find foods", ex);
            throw new RuntimeException(ex);
        }

        return foods;
    }

    public List<Food> findByPage(int page, int foodsOnPage) {
        List<Food> foods = new ArrayList<>();

        int offset = (page - 1) * foodsOnPage;

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM food LIMIT ?,?")) {
            query.setInt(1, offset);
            query.setInt(2, foodsOnPage);
            ResultSet rs = query.executeQuery();

            while (rs.next()) {
                Food food = createFood(rs);
                foods.add(food);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find foods by Page, page = " + page +
                    ", foodsOnPage = " + foodsOnPage, ex);
            throw new RuntimeException(ex);
        }

        return foods;
    }

    @Override
    public int countAllFood() {
        int foodCounter = 0;
        try (PreparedStatement query = connection.prepareStatement(
                "SELECT COUNT(id) FROM food")) {
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                foodCounter = rs.getInt("COUNT(id)");
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to count foods", ex);
            throw new RuntimeException(ex);
        }
        return foodCounter;
    }

    @Override
    public List<Food> findBySearchInName(String searchString) {
        List<Food> foods = new ArrayList<>();

        try (PreparedStatement query = connection.prepareStatement(
                "SELECT * FROM food WHERE food.name LIKE ?")) {
            query.setString(1, "%" + searchString + "%");

            ResultSet rs = query.executeQuery();
            while (rs.next()) {
                Food food = createFood(rs);
                foods.add(food);
            }

        } catch (Exception ex) {
            LOGGER.error("Fail to find food by search in name = " + searchString, ex);
            throw new RuntimeException(ex);
        }

        return foods;
    }

    @Override
    public Optional<Food> findById(int id) {

        Optional<Food> result = Optional.empty();

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "SELECT * FROM food " +
                                     "WHERE food.id = ?")) {
            query.setInt(1, id);
            ResultSet rs = query.executeQuery();

            if (rs.next()) {
                Food food = createFood(rs);
                result = Optional.of(food);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to find food with id = " + id, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    private Food createFood(ResultSet rs) throws SQLException {

        Food good = new Food.Builder()
                .setId(rs.getInt("food.id"))
                .setName(rs.getString("food.name"))
                .setCarbs(rs.getInt("food.carbs"))
                .setProtein(rs.getInt("food.protein"))
                .setFat(rs.getInt("food.fat"))
                .setKcal(rs.getInt("food.kcal"))
                .build();

        return good;
    }

    @Override
    public boolean update(Food food) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "UPDATE food " +
                                     "SET name = ?, carbs = ?, protein = ?, fat = ?, kcal = ? " +
                                     "WHERE id = ?")) {
            query.setString(1, food.getName());
            query.setInt(2, food.getCarbs());
            query.setInt(3, food.getProtein());
            query.setInt(4, food.getFat());
            query.setInt(5, food.getKcal());
            query.setInt(6, food.getId());
            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            LOGGER.error("Fail to update food with id = " + food.getId(), ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "DELETE FROM food " +
                                     "WHERE id = ?")) {
            query.setInt(1, id);
            query.executeUpdate();

            result = true;
        } catch (Exception ex) {
            LOGGER.error("Fail to delete food with id = " + id, ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public int insert(Food food) {
        int result = -1;

        try (PreparedStatement query =
                     connection.prepareStatement(
                             "INSERT INTO food (" +
                                     "name, carbs, protein, fat, kcal) " +
                                     "VALUES(?, ?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            query.setString(1, food.getName());
            query.setInt(2, food.getCarbs());
            query.setInt(3, food.getProtein());
            query.setInt(4, food.getFat());
            query.setInt(5, food.getKcal());

            query.executeUpdate();
            ResultSet rsId = query.getGeneratedKeys();
            if (rsId.next()) {
                result = rsId.getInt(1);
                food.setId(result);
            }
        } catch (Exception ex) {
            LOGGER.error("Fail to insert food: " + food.toString(), ex);
            throw new RuntimeException(ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}

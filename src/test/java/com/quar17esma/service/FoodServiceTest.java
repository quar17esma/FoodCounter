package com.quar17esma.service;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FoodDAO;
import com.quar17esma.entity.Food;
import com.quar17esma.service.impl.FoodService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FoodServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private FoodDAO foodDAO;

    @InjectMocks
    private FoodService foodService;

    @Before
    public void setUp() {
        when(connectionPool.getConnection()).thenReturn(connection);
        when(factory.createFoodDAO(connection)).thenReturn(foodDAO);
    }

    @Test
    public void getAllFood() throws Exception {
        foodService.getAllFood();
        verify(connectionPool).getConnection();
        verify(factory).createFoodDAO(connection);
        verify(foodDAO).findAll();
    }

    @Test
    public void getFoodById() throws Exception {
        int foodId = 3;
        Food food = mock(Food.class);
        when(foodDAO.findById(foodId)).thenReturn(Optional.of(food));

        Food resultFood = foodService.getFoodById(foodId);

        verify(connectionPool).getConnection();
        verify(factory).createFoodDAO(connection);
        verify(foodDAO).findById(foodId);

        assertEquals(food, resultFood);
    }

    @Test(expected = RuntimeException.class)
    public void getFoodByIdException() throws Exception {
        int foodId = 3;
        when(foodDAO.findById(foodId)).thenReturn(Optional.empty());

        foodService.getFoodById(foodId);

        verify(connectionPool).getConnection();
        verify(factory).createFoodDAO(connection);
        verify(foodDAO).findById(foodId);
    }

    @Test
    public void deleteFoodById() throws Exception {
        int foodId = 3;

        foodService.deleteFoodById(foodId);

        verify(connectionPool).getConnection();
        verify(factory).createFoodDAO(connection);
        verify(foodDAO).delete(foodId);
    }

    @Test
    public void addFood() throws Exception {
        Food food = mock(Food.class);

        foodService.addFood(food);

        verify(connectionPool).getConnection();
        verify(factory).createFoodDAO(connection);
        verify(foodDAO).insert(food);
    }

    @Test
    public void updateFood() throws Exception {
        Food food = mock(Food.class);

        foodService.updateFood(food);

        verify(connectionPool).getConnection();
        verify(factory).createFoodDAO(connection);
        verify(foodDAO).update(food);
    }

    @Test
    public void getFoodBySearchInName() throws Exception {
        String searchString = "potato";
        List<Food> foodList = new ArrayList<>();
        foodList.add(mock(Food.class));
        foodList.add(mock(Food.class));
        when(foodDAO.findBySearchInName(searchString)).thenReturn(foodList);

        List<Food> resultFoodList = foodService.getFoodBySearchInName(searchString);

        verify(connectionPool).getConnection();
        verify(factory).createFoodDAO(connection);
        verify(foodDAO).findBySearchInName(searchString);

        assertEquals(foodList, resultFoodList);
    }
}
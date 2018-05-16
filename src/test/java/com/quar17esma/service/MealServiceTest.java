package com.quar17esma.service;

import com.quar17esma.dao.ConnectionPool;
import com.quar17esma.dao.DaoFactory;
import com.quar17esma.dao.FoodDAO;
import com.quar17esma.dao.MealDAO;
import com.quar17esma.entity.Meal;
import com.quar17esma.service.impl.MealService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MealServiceTest {
    @Mock
    private DaoFactory factory;
    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private MealDAO mealDAO;

    @InjectMocks
    private MealService mealService;

    @Before
    public void setUp() {
        when(connectionPool.getConnection()).thenReturn(connection);
        when(factory.createMealDAO(connection)).thenReturn(mealDAO);
    }

    @Test
    public void getMealsByClientIdCorrect() throws Exception {
        List<Meal> meals = new ArrayList<>();
        Meal meal = mock(Meal.class);
        when(meal.getId()).thenReturn(3);
        meals.add(meal);
        when(mealDAO.findAllByClientIdOrderByMealDateTimeDesc(anyInt())).thenReturn(meals);

        mealService.getMealsByClientId(1);

        verify(connectionPool).getConnection();
        verify(factory).createMealDAO(connection);
        verify(connection).setAutoCommit(true);
        verify(mealDAO).findAllByClientIdOrderByMealDateTimeDesc(1);
    }

    @Test
    public void getMealsByClientIdEmptyMeals() throws Exception {
        List<Meal> meals = new ArrayList<>();
        when(mealDAO.findAllByClientIdOrderByMealDateTimeDesc(anyInt())).thenReturn(meals);

        List<Meal> resultMeals = mealService.getMealsByClientId(1);

        verify(connectionPool).getConnection();
        verify(factory).createMealDAO(connection);
        verify(connection).setAutoCommit(true);
        verify(mealDAO).findAllByClientIdOrderByMealDateTimeDesc(1);

        assertTrue(resultMeals.isEmpty());
    }

    @Test
    public void addMeal() throws Exception {
        Meal meal = mock(Meal.class);

        mealService.addMeal(meal);

        verify(connectionPool).getConnection();
        verify(factory).createMealDAO(connection);
        verify(mealDAO).insert(meal);
    }
}
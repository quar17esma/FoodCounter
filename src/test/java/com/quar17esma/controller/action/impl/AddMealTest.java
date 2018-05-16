package com.serhii.shutyi.controller.action.impl;

import com.quar17esma.controller.action.impl.AddMeal;
import com.quar17esma.controller.manager.ConfigurationManager;
import com.quar17esma.controller.manager.LabelManager;
import com.quar17esma.entity.Client;
import com.quar17esma.entity.Food;
import com.quar17esma.entity.Meal;
import com.quar17esma.service.impl.CalorieCounter;
import com.quar17esma.service.impl.FoodService;
import com.quar17esma.service.impl.MealService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AddMealTest {
    @Mock
    private MealService mealService;
    @Mock
    private FoodService foodService;
    @Mock
    private CalorieCounter calorieCounter;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpSession session;
    @Mock
    private Food food;
    @Mock
    private Client client;

    @InjectMocks
    private AddMeal addMeal;

    @Test
    public void execute() throws Exception {
        String page = ConfigurationManager.getProperty("path.page.welcome");
        String locale = "en_US";
        String foodId = "5";
        String gram = "300";
        int foodKcal = 80;
        String successAddMeal = LabelManager.getProperty("message.success.add.meal", locale);

        when(session.getAttribute("locale")).thenReturn(locale);
        when(session.getAttribute("client")).thenReturn(client);

        when(request.getSession()).thenReturn(session);
        when(request.getParameter("foodId")).thenReturn(foodId);
        when(request.getParameter("gram")).thenReturn(gram);

        when(foodService.getFoodById(Integer.parseInt(foodId))).thenReturn(food);

        when(food.getKcal()).thenReturn(foodKcal);

        String resultPage = addMeal.execute(request);

        verify(foodService).getFoodById(Integer.parseInt(foodId));
        verify(calorieCounter).countMealKcal(Integer.parseInt(gram), foodKcal);
        verify(mealService).addMeal(any(Meal.class));
        verify(request).setAttribute("successAddMeal", successAddMeal);

        assertEquals(resultPage, page);
    }

}
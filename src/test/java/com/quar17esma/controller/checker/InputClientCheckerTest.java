package com.quar17esma.controller.checker;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class InputClientCheckerTest {

    @Test
    public void returnFalseWithNameNull() throws Exception {
        String name = null;
        String email = "some@gmail.com";
        int height = 182;
        int weight = 83;
        LocalDate birthDate = LocalDate.of(1984, 10, 25);

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email, height, weight, birthDate);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithEmailNull() throws Exception {
        String name = "John";
        String email = null;
        int height = 182;
        int weight = 83;
        LocalDate birthDate = LocalDate.of(1984, 10, 25);

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email, height, weight, birthDate);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithNameEmpty() throws Exception {
        String name = "";
        String email = "some@gmail.com";
        int height = 182;
        int weight = 83;
        LocalDate birthDate = LocalDate.of(1984, 10, 25);

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email, height, weight, birthDate);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithEmailEmpty() throws Exception {
        String name = "John";
        String email = "";
        int height = 182;
        int weight = 83;
        LocalDate birthDate = LocalDate.of(1984, 10, 25);

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email, height, weight, birthDate);

        assertFalse(result);
    }

    @Test
    public void returnTrueWithCorrectEmailName() throws Exception {
        String name = "John";
        String email = "john@gmail.com";
        int height = 182;
        int weight = 83;
        LocalDate birthDate = LocalDate.of(1984, 10, 25);

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email, height, weight, birthDate);

        assertTrue(result);
    }

    @Test
    public void returnFalseWithCorrectEmailNameWithNumbers() throws Exception {
        String name = "John123";
        String email = "john@gmail.com";
        int height = 182;
        int weight = 83;
        LocalDate birthDate = LocalDate.of(1984, 10, 25);

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email, height, weight, birthDate);

        assertFalse(result);
    }

    @Test
    public void returnFalseWithCorrectNameWrongEmail() throws Exception {
        String name = "John";
        String email = "johngmail.com";
        int height = 182;
        int weight = 83;
        LocalDate birthDate = LocalDate.of(1984, 10, 25);

        InputClientChecker inputClientChecker = new InputClientChecker();
        boolean result = inputClientChecker.isInputDataCorrect(name, email, height, weight, birthDate);

        assertFalse(result);
    }
}
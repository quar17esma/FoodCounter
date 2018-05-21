package com.quar17esma.controller.checker;

import java.time.LocalDate;

public class InputClientChecker extends InputDataChecker {
    private static final int NAME_LENGTH_MAX = 100;
    private static final int EMAIL_LENGTH_MAX = 100;
    private static final int HEIGHT_MIN = 1;
    private static final int HEIGHT_MAX = 250;
    private static final int WEIGHT_MIN = 1;
    private static final int WEIGHT_MAX = 500;

    public boolean isInputDataCorrect(String name, String email, int height, int weight, LocalDate birthDate) {
        boolean result = false;

        if (isNameCorrect(name) &&
                isEmailCorrect(email) &&
                isHeightCorrect(height) &&
                isWeightCorrect(weight) &&
                isBirthDateCorrect(birthDate)) {

            result = true;
        }

        return result;
    }

    private boolean isNameCorrect(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        return isMatches(CheckPatterns.NAME, name) &&
                name.length() <= NAME_LENGTH_MAX;
    }

    private boolean isEmailCorrect(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        return isMatches(CheckPatterns.EMAIL, email) &&
                email.length() <= EMAIL_LENGTH_MAX;
    }

    private boolean isHeightCorrect(int height) {

        return isIntInRange(height, HEIGHT_MIN, HEIGHT_MAX);
    }

    private boolean isWeightCorrect(int weight) {

        return isIntInRange(weight, WEIGHT_MIN, WEIGHT_MAX);
    }

    private boolean isBirthDateCorrect(LocalDate birthDate) {
        boolean result = false;

        if (birthDate.isBefore(LocalDate.now())) {
            result = true;
        }

        return result;
    }
}

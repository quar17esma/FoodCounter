package com.quar17esma.controller.checker;

public class InputFoodChecker extends InputDataChecker {
    private static final int NAME_LENGTH_MAX = 100;
    private static final int CARBS_MIN = 0;
    private static final int PROTEIN_MIN = 0;
    private static final int FAT_MIN = 0;
    private static final int KCAL_MIN = 0;

    public boolean isInputDataCorrect(String name, int carbs, int protein, int fat, int kcal) {

        if (name == null || name.isEmpty()) {
            return false;
        }

        return isMatches(CheckPatterns.CHAR_DIGIT_PUNCT, name) &&
                name.length() <= NAME_LENGTH_MAX &&
                carbs >= CARBS_MIN &&
                protein >= PROTEIN_MIN &&
                fat >= FAT_MIN &&
                kcal >= KCAL_MIN;
    }
}

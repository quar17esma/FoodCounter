package com.quar17esma.entity;

import java.time.LocalDate;

public class Meal {
    private int id;
    private LocalDate mealDate;
    private Client client;
    private Food food;
    private int gram;
    private int kcal;

    public Meal() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getMealDate() {
        return mealDate;
    }

    public void setMealDate(LocalDate mealDate) {
        this.mealDate = mealDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getGram() {
        return gram;
    }

    public void setGram(int gram) {
        this.gram = gram;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", mealDate=" + mealDate +
                ", client=" + client +
                ", food=" + food +
                ", gram=" + gram +
                ", kcal=" + kcal +
                '}';
    }

    public static class Builder {
        private Meal meal;

        public Builder() {
            this.meal = new Meal();
        }

        public Meal build() {
            return meal;
        }

        public Builder setId(int id) {
            meal.setId(id);
            return this;
        }

        public Builder setMealDate(LocalDate mealDate) {
            meal.setMealDate(mealDate);
            return this;
        }

        public Builder setClient(Client client) {
            meal.setClient(client);
            return this;
        }

        public Builder setFood(Food food) {
            meal.setFood(food);
            return this;
        }

        public Builder setGram(int gram) {
            meal.setGram(gram);
            return this;
        }

        public Builder setKcal(int kcal) {
            meal.setKcal(kcal);
            return this;
        }
    }
}

package com.quar17esma.entity;

public class Food {
    private int id;
    private String name;
    private int carbs;
    private int protein;
    private int fat;
    private int kcal;

    public Food() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", carbs=" + carbs +
                ", protein=" + protein +
                ", fat=" + fat +
                ", kcal=" + kcal +
                '}';
    }

    public static class Builder {
        private Food food;

        public Builder() {
            this.food = new Food();
        }

        public Food build() {
            return food;
        }

        public Builder setId(int id) {
            food.setId(id);
            return this;
        }

        public Builder setName(String name) {
            food.setName(name);
            return this;
        }
        public Builder setCarbs(int carbs) {
            food.setCarbs(carbs);
            return this;
        }
        public Builder setProtein(int protein) {
            food.setProtein(protein);
            return this;
        }
        public Builder setFat(int fat) {
            food.setFat(fat);
            return this;
        }
        public Builder setKcal(int kcal) {
            food.setKcal(kcal);
            return this;
        }
    }
}

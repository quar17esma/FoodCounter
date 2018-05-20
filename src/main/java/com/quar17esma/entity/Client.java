package com.quar17esma.entity;

import com.quar17esma.enums.Gender;
import com.quar17esma.enums.Lifestyle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private int id;

    private String name;

    private LocalDate birthDate;

    private int height;

    private int weight;

    private Gender gender;

    private Lifestyle lifestyle;

    private User user;

    private List<Meal> meals;

    public Client() {
        this.meals = new ArrayList<>();
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Lifestyle getLifestyle() {
        return lifestyle;
    }

    public void setLifestyle(Lifestyle lifestyle) {
        this.lifestyle = lifestyle;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return id == client.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", height=" + height +
                ", weight=" + weight +
                ", gender=" + gender +
                ", lifestyle=" + lifestyle +
                ", meals=" + meals +
                '}';
    }

    public static class Builder {
        private  Client client;

        public Builder() {
            this.client = new Client();
        }

        public Client build() {
            return client;
        }

        public Builder setId(int id) {
            client.setId(id);
            return this;
        }

        public Builder setName(String name) {
            client.setName(name);
            return this;
        }

        public Builder setBirthDate(LocalDate birthDate) {
            client.setBirthDate(birthDate);
            return this;
        }

        public Builder setHeight(int height) {
            client.setHeight(height);
            return this;
        }

        public Builder setWeight(int weight) {
            client.setWeight(weight);
            return this;
        }

        public Builder setGender(Gender gender) {
            client.setGender(gender);
            return this;
        }

        public Builder setLifestyle(Lifestyle lifestyle) {
            client.setLifestyle(lifestyle);
            return this;
        }

        public Builder setUser(User user) {
            client.setUser(user);
            return this;
        }

        public Builder setMeals(List<Meal> meals) {
            client.setMeals(meals);
            return this;
        }
    }
}

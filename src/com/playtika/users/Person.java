package com.playtika.users;

public class Person {
    private final String name;
    private int age;
    private String city;

    public Person(String name, int age, String city) {
        if (name == null) {
            throw new IllegalArgumentException("Name of person mustn't be null");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age can't be negative");
        }
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCity() {
        return city;
    }
}

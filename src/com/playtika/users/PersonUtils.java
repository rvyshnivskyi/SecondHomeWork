package com.playtika.users;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;

public class PersonUtils {
    public static double getAverageAge(List<? extends Person> persons) {
        return persons.stream()
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
    }

    public static <T extends Person> T getOldestPerson(List<T> persons) {
        return persons.stream()
                .max(comparing(Person::getAge))
                .get();
    }

    public static int getNumberOfDave(List<? extends Person> persons) {
        return (int)persons.stream()
                .filter(p -> p.getName().equals("Dave"))
                .count();
    }

    public static Map<Integer, Long> countOfPeopleWithAge(List<? extends Person> persons) {
        return persons.stream()
                .collect(groupingBy(Person::getAge, counting()));
    }

    public static String getTopPopulationCity(List<? extends Person> persons) {
        return persons.stream()
                .filter(p -> nonNull(p.getCity()))
                .collect(groupingBy(Person::getCity, counting()))
                .entrySet().stream()
                .max(comparing(Map.Entry::getValue))
                .get().getKey();
    }

    public static Map<String, Double> getAverageAgeOfAdultsPerCity(List<? extends Person> persons) {
        return persons.stream()
                .filter(p -> nonNull(p.getCity()))
                .filter(p -> p.getAge() >= 18)
                .collect(groupingBy(Person::getCity, averagingDouble(Person::getAge)));
    }
}

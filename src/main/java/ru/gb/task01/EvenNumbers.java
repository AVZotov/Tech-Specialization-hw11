package ru.gb.task01;

import java.util.Arrays;
import java.util.List;

public class EvenNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        getEvenNumbersAverage(numbers);
    }

    public static void getEvenNumbersAverage(List<Integer> numbers){
        System.out.println(numbers.stream().filter(number -> number % 2 == 0).mapToInt(Integer::intValue).average().orElse(0));
    }
}

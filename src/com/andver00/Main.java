package com.andver00;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100);

        private final int value;

        RomanNumeral(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    static boolean checkForArabic(String value) {
        switch (value) {
            case "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" -> { return true; }
            default -> { return false; }
        }
    }

    static boolean checkForRoman(String value) {
        switch (value) {
            case "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X" -> { return true; }
            default -> { return false; }
        }
    }

    static String numArabicToRoman(Integer value) {
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while ((value > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= value) {
                stringBuilder.append(currentSymbol.name());
                value -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return stringBuilder.toString();
    }

    // As we have input values in [0; 10] then we can use switch function, which time complexity is only O(1)
    static String numRomanToArabic(String value) {
        switch (value) {
            case "I" -> { return "1"; }
            case "II" -> { return "2"; }
            case "III" -> { return "3"; }
            case "IV" -> { return "4"; }
            case "V" -> { return "5"; }
            case "VI" -> { return "6"; }
            case "VII" -> { return "7"; }
            case "VIII" -> { return "8"; }
            case "IX" -> { return "9"; }
            case "X" -> { return "10"; }
            default -> { return ""; }
        }
    }

    static Integer processFunction(Integer value1, Integer value2, String sign) {
        switch (sign) {
            case "+" -> { return value1 + value2; }
            case "-" -> { return value1 - value2; }
            case "*" -> { return value1 * value2; }
            case "/" -> { return value1 / value2; }
            default -> { throw new RuntimeException("Wrong sign between numbers!"); }
        }
    }

    static String calc(String input) {
        var inputSplitMas = input.split(" ");

        if (inputSplitMas.length != 3) {
            throw new RuntimeException("Input format exception!");
        }

        var num1 = inputSplitMas[0];
        var sign = inputSplitMas[1];
        var num2 = inputSplitMas[2];

        if (checkForArabic(num1) && checkForArabic(num2)) {
            var value1 = Integer.valueOf(num1);
            var value2 = Integer.valueOf(num2);
            return String.valueOf(processFunction(value1, value2, sign));
        }
        else if (checkForRoman(num1) && checkForRoman(num2)) {
            var value1 = Integer.valueOf(numRomanToArabic(num1));
            var value2 = Integer.valueOf(numRomanToArabic(num2));
            var result = processFunction(value1, value2, sign);
            if (result <= 0) {
                throw new RuntimeException("Roman numbers cannot be negative or zero!");
            }
            return numArabicToRoman(result);
        }
        else {
            throw new RuntimeException("Input format exception!");
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        System.out.println(calc(input));
    }
}
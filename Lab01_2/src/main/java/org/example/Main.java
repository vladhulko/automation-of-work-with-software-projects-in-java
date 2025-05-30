package org.example;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        int minRange = 1;
        int maxRange = 100;
        int numberToGuess = random.nextInt(maxRange - minRange + 1) + minRange;
        int numberOfTries = 0;
        int userGuess;
        boolean hasGuessed = false;

        System.out.println("Hello! I've picked a number between " + minRange + " and " + maxRange + ".");
        System.out.println("Try to guess it!");

        while (!hasGuessed) {
            System.out.print("Your guess: ");
            if (sc.hasNextInt()) {
                userGuess = sc.nextInt();
                numberOfTries++;

                if (userGuess < minRange || userGuess > maxRange) {
                    System.out.println("Please enter a number within the range of " + minRange + " to " + maxRange + ".");
                } else if (userGuess < numberToGuess) {
                    System.out.println("The secret number is higher!");
                } else if (userGuess > numberToGuess) {
                    System.out.println("The secret number is lower!");
                } else {
                    hasGuessed = true;
                    System.out.println("Congratulations! You guessed the number " + numberToGuess + " in " + numberOfTries + " tries!");
                }
            } else {
                System.out.println("That doesn't look like a number. Try again.");
                sc.next();
            }
        }
    }
}
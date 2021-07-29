package com.company;
import java.util.Random;
import java.util.Scanner;
public class RockPaperScissors {
    public static void main(String[] args) {

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter 0 for Rock ,1 for paper , 2 for scissors ");
            int userInput = sc.nextInt();

            Random r = new Random();
            int computerInput = r.nextInt(3);

            if (userInput == computerInput) {
                System.out.println("Match Draw!");
            } else if (userInput == 0 && computerInput == 2 || userInput == 1 && computerInput == 0 || userInput == 2 && computerInput == 1) {
                System.out.println("You win!");
            } else {
                System.out.println("Computer win!");
            }
    }
}

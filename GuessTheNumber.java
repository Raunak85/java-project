package com.company;
import java.util.*;

class Game{

    public int number;
    public int userInput;
    public int noOfGuesses = 0;

    public int getNumberOfGuesses()
    {
        return noOfGuesses;
    }
    public void setNumberOfGuesses(int noOfGuesses)
    {
        this.noOfGuesses=noOfGuesses;
    }

    Game() {
        Random random=new Random();
        this.number=random.nextInt(100);
    }

    void takeUserInput()
    {
        Scanner sc=new Scanner(System.in);
        System.out.println("Please Enter number between 1 to 100");
        userInput=sc.nextInt();
    }

    boolean isCorrectNumber()
    {
        noOfGuesses++;
        if (userInput == number)
        {
            System.out.format("Congratulation!! You guessed it right\nYou guessed it in %d attempts\n",noOfGuesses);
            return true;
        }
        else if (userInput<number )
        {
            System.out.println("You guessed lowest number, please enter highest number\n");
        }
        else {
            System.out.println("You guessed Highest number , Please enter Lowest Number\n");
        }
            return false;
    }






}
public class GuessTheNumber {
    public static void main(String[] args) {

        Game g=new Game();
        boolean b=false;
        while (!b)
        {
            g.takeUserInput();
            b = g.isCorrectNumber();
        }



    }
}

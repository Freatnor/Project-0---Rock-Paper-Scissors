package com.company;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by administrator on 6/23/16.
 *
 * Rock, Paper, and Scissors moves are saved here as
 * 0 - Rock
 * 1 - Paper
 * 2 - Scissors
 *
 * Match outcomes are saved as
 * 0 - Loss
 * 1 - Win
 * 2 - Tie
 */
public class Game {

    private List<Integer> results, playerMoves, compMoves;
    private int currentGame;
    private String userName;
    private String secondUserName;
    private Scanner userInput;


    public Game(Scanner s, String userName){
        this.userInput = s;
        currentGame = 0;
        results = new ArrayList<>();
        playerMoves = new ArrayList<>();
        compMoves = new ArrayList<>();
        this.userName = userName;

    }

    public Game(Scanner s, String userName, String secondUserName){
        this.secondUserName = secondUserName;
        this.userInput = s;
        currentGame = 0;
        results = new ArrayList<>();
        playerMoves = new ArrayList<>();
        compMoves = new ArrayList<>();
        this.userName = userName;
    }

    /****
     * Constructor for use in already played games
     */
    public Game(String userName){
        currentGame = 0;
        results = new ArrayList<>();
        playerMoves = new ArrayList<>();
        compMoves = new ArrayList<>();
        this.userName = userName;
    }

    public int getCurrentGame() {
        return currentGame;
    }

    public String getUserName(){
        return userName;
    }

    public void play(){
        if(secondUserName == null)
            comPlay();
        System.out.println("Please enter a move\n" +
                "(\"Rock\", \"Paper\", or \"Scissors\" are permitted)");
        //parse user input and check for valid input
        if(!parsePlayerInput(this.userInput.nextLine(), 1)){
            System.out.println("Not a valid input. Please try again.");
            play();
            return;
        }
        if(secondUserName != null){
            for (int i = 0; i < 30; i++) {
                System.out.println();
            }
            System.out.println("Player 2 please enter a move\n" +
                    "(\"Rock\", \"Paper\", or \"Scissors\" are permitted)");
            if(!parsePlayerInput(this.userInput.nextLine(), 2)){
                System.out.println("Not a valid input. Please try again.");
                play();
                return;
            }
        }
        determineWinner();
        printResults();
        if(results.get(currentGame) == 2){
            currentGame++;
            play();
        }

        return;
    }

    private boolean parsePlayerInput(String playerInput, int player){
        playerInput = playerInput.toLowerCase().trim();
        Integer move;
        if(playerInput.equals("rock")){
            move = 0;
        }
        else if(playerInput.equals("paper")){
            move = 1;
        }
        else if(playerInput.equals("scissors")){
            move = 2;
        }
        else{
            return false;
        }
        //Check if you need to increment the list or set the current value
        if(player == 1) {
            if ((playerMoves.size() - 1) < currentGame) {
                playerMoves.add(move);
            } else {
                playerMoves.set(currentGame, move);
            }
        }
        else {
            if ((compMoves.size() - 1) < currentGame) {
                compMoves.add(move);
            } else {
                compMoves.set(currentGame, move);
            }
        }
        return true;
    }

    //Method to create computer input

    /***
     * Randomly generates the computer's move using a random integer and converts that result to a String.
     *
     * @return computer's play as a String
     */
    private void comPlay() {
        int move = (int) (Math.random() * 3);
        if((compMoves.size() -1) < currentGame){
            compMoves.add(move);
        }
        else {
            compMoves.set(currentGame, move);
        }
    }

    public void determineWinner(){
        int comp = compMoves.get(currentGame);
        switch(playerMoves.get(currentGame)) {
            case 0:
                switch (comp) {
                    case 0:
                        results.add(2);
                        return;
                    case 1:
                        results.add(0);
                        return;
                    case 2:
                        results.add(1);
                        return;
                    default:
                        results.add(-1);
                        return;
                }
            case 1:
                switch (comp) {
                    case 0:
                        results.add(1);
                        return;
                    case 1:
                        results.add(2);
                        return;
                    case 2:
                        results.add(0);
                        return;
                    default:
                        results.add(-1);
                        return;
                }
            case 2:
                switch (comp) {
                    case 0:
                        results.add(0);
                        return;
                    case 1:
                        results.add(1);
                        return;
                    case 2:
                        results.add(2);
                        return;
                    default:
                        results.add(-1);
                        return;
                }
        }

    }

    public void printResults(){
        System.out.println("P1 throws " + convertPlay(playerMoves.get(currentGame)) + "!");
        if(secondUserName == null)
            System.out.println("Computer throws " + convertPlay(compMoves.get(currentGame)) + "!");
        else
            System.out.println("P2 throws " + convertPlay(compMoves.get(currentGame)) + "!");
        if(results.get(currentGame) == 0){
            System.out.println("P1 Lose!");
        }
        else if(results.get(currentGame) == 1){
            System.out.println("P1 Win!");
        }
        else if(results.get(currentGame) == 2){
            System.out.println("It's a tie! Try again:\n");
        }
        else{
            System.out.println("Something went wrong...");
        }
    }

    /***
     *
     * @param play - the int corresponding to a rock, paper, or scissors play
     * @return the string conversion of that play. returns null if not a valid move
     */
    private static String convertPlay(int play){
        switch (play){
            case 0:
                return "rock";
            case 1:
                return "paper";
            case 2:
                return "scissors";
            default:
                return null;
        }
    }

    private static String convertResult(int result){
        switch (result){
            case 0:
                return "loss";
            case 1:
                return "win";
            case 2:
                return "tie";
            default:
                return null;
        }
    }


    private String toString(int i) {
        return "Game " + i + "{" + convertResult(results.get(i)) + ": Your Move: " + convertPlay(playerMoves.get(i)) + ", Computer Move: " + convertPlay(compMoves.get(i)) + "}";
    }

    @Override
    public String toString() {
        String returnString ="";

        for (int i = 0; i < currentGame + 1; i++) {
            returnString = returnString + toString(i);
            if(i < currentGame)
                returnString = returnString + "\n";
        }
        return returnString;
    }

    public String toSaveString(){
        String returnString ="";

        for (int i = 0; i < currentGame + 1; i++) {
            returnString = returnString + toSaveString(i);
            returnString = returnString + "\n";
        }
        return returnString;
    }

    public String toSaveString(int game){
        return userName + "\nGame " + game + "," + results.get(game) + "," + playerMoves.get(game) + "," + compMoves.get(game);
    }

    public boolean addGame(int result, int pMove, int cMove){
        results.add(result);
        playerMoves.add(pMove);
        compMoves.add(cMove);
        currentGame = results.size() - 1;
        return true;
    }
}

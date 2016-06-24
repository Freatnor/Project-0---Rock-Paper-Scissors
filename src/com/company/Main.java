package com.company;

import java.util.*;
import java.io.*;
import com.company.Game;


public class Main {

    private static ArrayList<Game> sessionHistory, loadedHistory;
    private static String userName;

    public static void main(String[] args) {
	// write your code here
        Scanner playerInput = new Scanner(System.in);
        String inputLine;
        sessionHistory = new ArrayList<>();
        loadedHistory = new ArrayList<>();

        //Load History??
        loadHistory();

        System.out.println("Please enter your username");
        userName = playerInput.nextLine().toLowerCase().trim();

        do{
            printMenu();
            inputLine = playerInput.nextLine();
            inputLine = inputLine.toLowerCase().trim();
            if(inputLine.equals("play")){
                Game g = new Game(playerInput, userName);
                g.play();
                sessionHistory.add(g);
            }
            else if(inputLine.equals("history")){
                if(!printHistory(playerInput, false)){
                    System.out.println("error?");
                }
            }
            else if(inputLine.equals("ahistory")){
                if(!printHistory(playerInput, true)){
                    System.out.println("error?");
                }
            }
            else if(inputLine.equals("quit")) {
                continue;
            }
            else {
                System.out.println("Invalid input! Please check what you entered and try again.");
            }
            clearConsole();

        }
        while(!inputLine.equals("quit"));
        System.out.println("Thanks for playing!");
        saveHistory();
        System.exit(1);
    }


    //Menu Print method
    public static boolean printMenu(){

        System.out.println("Welcome to Rock Paper Scissors!\n" + "/*****************************************\\" + "\n" +
                "Please type \"Play\" to play a game of Rock Paper Scissors.\n" +
                "Alternatively, type \"History\" to see your match history, \"aHistory\" to print every user's history, or \"Quit\" to exit the app.");
        return true;
    }

    //Print history method

    /***
     * Prints stored history and waits for the user to hit enter to continue
     *
     * @param s, boolean to determine whether to print all or only user
     * @return whether history exists or not
     */
    public static boolean printHistory(Scanner s, boolean all){
        if(sessionHistory == null){
            return false;
        }
        if((sessionHistory.size() < 1) && (loadedHistory.size() < 1)){
            System.out.println("No game history found");
        }

        if(loadedHistory.size() >= 1) {
            System.out.println("Loaded History:");
            for (int i = 0; i < loadedHistory.size(); i++) {
                Game g = loadedHistory.get(i);
                if(g.getUserName().equals(userName) || all) {
                    System.out.println(g.getUserName() + " Match " + i + ":");
                    System.out.println(g.toString());
                }
            }
        }
        if(sessionHistory.size() >= 1) {
            System.out.println("Current Session:");
            for (int i = 0; i < sessionHistory.size(); i++) {
                System.out.println("Match " + i + ":");
                System.out.println(sessionHistory.get(i).toString());
            }
        }
        s.nextLine();
        return true;
    }


    //Load saved history
    private static void loadHistory(){
        File saveFile = new File("saves/rpssave.txt");
        Scanner fis;
        Scanner lineParser;
        String line;
        String savedUser;
        if(!saveFile.isFile())
            return;
        try{
            fis = new Scanner(saveFile);
        }
        catch(Exception e){
            return;
        }
        Game g = null;
        int[] vals = new int[3];
        while(fis.hasNext()){
            savedUser = fis.nextLine();
            line = fis.nextLine();
            if(line.startsWith("Game 0")) {
                g = new Game(savedUser);
                loadedHistory.add(g);
            }
            line = line.substring(7);
            lineParser = new Scanner(line);
            lineParser.useDelimiter(",");
            for (int i = 0; i < 3; i++) {
                vals[i] = Integer.parseInt(lineParser.next().trim());
            }
            g.addGame(vals[0], vals[1], vals[2]);

        }


    }

    //access saved history method

    /***
     * Saves the history to a txt file to be read later
     * @return true if successful
     */
    private static boolean saveHistory(){
        File saveFile = new File("saves/rpssave.txt");
        FileOutputStream ps;
        if(!saveFile.isFile()){
            saveFile.getParentFile().mkdir();
            try{saveFile.createNewFile();}
            catch(Exception e) {
                return false;
            }
        }
        try{
            ps = new FileOutputStream(saveFile, true);
        }
        catch(Exception e){
            return false;
        }
        for (int i = 0; i < sessionHistory.size(); i++) {
            try{
                ps.write(sessionHistory.get(i).toSaveString().getBytes());
                ps.flush();
            }
            catch(Exception e){
                return false;
            }
        }
        try{ps.close();}
        catch(Exception e){
            return false;
        }

        return true;
    }

    public final static void clearConsole()
    {
        /*try
        {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows"))
            {
                Runtime.getRuntime().exec("cls");
            }
            else
            {
                Runtime.getRuntime().exec("clear");
            }
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
        */
        //System.out.println("\033[H\033[2J");
        System.out.println("\n\n");

    }


}

package com.example.guessing_game_app;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

public class GuessingGame  {
    private int winningNumber;
    private int numGuesses;
    private ArrayList<String> theGuesses;

    public GuessingGame() {
        theGuesses = new ArrayList<>();
        startNewGame();
    }

    public void startNewGame()
    {
        winningNumber = (int)Math.ceil(Math.random()*3);
    }

    public int getWinningNumber() {
        return winningNumber;
    }

    public void addGuessNum(){
        this.numGuesses++;
    }

    public int getNumGuesses(){
        return this.numGuesses;
    }
    public void setNumGuesses (int guesses){
       if (guesses >= 0){
           this.numGuesses = guesses;
       }
    }
    public ArrayList<String> getGuesses(){
        return this.theGuesses;
    }
    public void addGuess(String guess){
        if (guess != null){
            theGuesses.add(guess);
        }
    }

    public double[] calculateStats(){
        double numWins = 0;
        double numLosses = 0;
        double total = theGuesses.size();

        for (int i=0; i<theGuesses.size(); i++){
            if (theGuesses.get(i).equals("yes")){
                numWins ++;
            }
            else{
                numLosses ++;
            }
        }

        double percentWins = 0;
        double percentLosses = 0;


        if (theGuesses.size() != 0){
            percentWins = numWins/total * 100;
            percentLosses = numLosses/total * 100;
        }

        return new double[]{numWins, numLosses, percentWins, percentLosses};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GuessingGame that = (GuessingGame) o;

        return winningNumber == that.winningNumber;
    }

    @Override
    public int hashCode() {
        return winningNumber;
    }

    /**
     * toString method returns the object name and winning number
     * @return Object name and winning number
     */
    @Override
    @NonNull
    public String toString() {
        return "GuessingGame{" +
                "winningNumber=" + winningNumber +
                '}';
    }

    public String getJSONStringFromThis ()
    {
        return new Gson().toJson(this);
    }

    public static GuessingGame getGuessingGameObjectFromJSONString (String jsonString)
    {
        return (GuessingGame)new Gson().fromJson(jsonString, GuessingGame.class);
    }


}

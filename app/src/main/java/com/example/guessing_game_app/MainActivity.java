package com.example.guessing_game_app;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import com.example.guessing_game_app.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private GuessingGame mGame;
    private Snackbar snackBar;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("GAME", mGame.getJSONStringFromThis());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.includeToolbar.toolbar);

        setUpFAB();

        mGame = savedInstanceState != null
                ? GuessingGame.getGuessingGameObjectFromJSONString(
                        savedInstanceState.getString("GAME"))
                : new GuessingGame();
        mGame.setNumGuesses(0);
    }

    private void setUpFAB() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGame.setNumGuesses(0);
                mGame.startNewGame();
                Snackbar.make(view, "You have started a new game", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.statistics) {
            showStats();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pick123(View view) {
        mGame.addGuessNum();

        Button currentButton = (Button) view;
        String currentButtonText = currentButton.getText().toString();

        String theResult;
        if (mGame.getWinningNumber() == Integer.parseInt(currentButtonText)){
            theResult = "You guessed the correct number, " + currentButtonText + "!!!";
            Snackbar.make(view, theResult, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            if (mGame.getNumGuesses() == 1){
                mGame.addGuess("yes");
            }
        }
        else{
            theResult = "Sorry, the correct number was " + mGame.getWinningNumber();
            Snackbar.make(view, theResult, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            if (mGame.getNumGuesses() == 1){
                mGame.addGuess("no");
            }
        }
    }

    private void dismissSnackBarIfShown() {
        if (snackBar.isShown()) {
            snackBar.dismiss();
        }
    }

    private void showStats() {
        //dismissSnackBarIfShown();
        double[] statsArray = mGame.calculateStats();
        DialogUtils.showInfoDialog(MainActivity.this, "Guessing Game Stats",
                "Number of wins: " + statsArray[0] + "\nNumber of losses: " + statsArray[1]
        + "\nWin percent: " + statsArray[2] + "\nLoss percent: " + statsArray[3]);
    }
}
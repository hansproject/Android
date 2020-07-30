package com.example.hp.activitypractice;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class GameEngine  {
    
    public boolean isP1        = true;
    public final int NO_PLAYER = 0;
    public final int PLAYER1 = 1;
    public final int AI_PLAYER = 2;// Player 2 for AI
    public int computerMove = 0;
    public Board board;

// Constructor
//_________________________________________________________________
    public GameEngine()  { }

// Check if the board is empty
//_________________________________________________________________
    public boolean isEmpty(int[] currentState){

        for(int i = 0; i< 9; i++)
            if(currentState[i] != 0)
                return false;
        return true;

    }

// Check if the board is full
//_________________________________________________________________
    public boolean isFull(int [] currentState){

        for(int i = 0; i< 9; i++)
            if(currentState[i] == 0)
                return false;
        return true;
    }

// Check if there is a winner
//_________________________________________________________________
    public boolean evaluateFunction(int[] gameState, int currentPlayer){

        //Diagonals
        if((gameState[0] == currentPlayer && gameState[4] == currentPlayer  && gameState[8]== currentPlayer)
                || (gameState[2] == currentPlayer && gameState[4] == currentPlayer  && gameState[6]== currentPlayer))
                return true;

        // rows
        for(int i = 0; i < 9 ; i+=3){

            if(gameState[i] == currentPlayer && gameState[i + 1] == currentPlayer && gameState[i + 2] == currentPlayer)
                    return true;
                }


        // columns
        for(int i = 0; i< 3; i+=1)
            if  (gameState[i] == currentPlayer && gameState[i+3] == currentPlayer && gameState[i+6] ==currentPlayer)
                    return true;
        return false; //currentPlayer hasn't won

    }

// Determine the score of a move
//_________________________________________________________________
    public int minimax(int[] gameState, int currentPlayer,int depth) {
        Move move = new Move();


        if (evaluateFunction(gameState, PLAYER1))// for player1
        {
            move.score = -10;
            return -10 + depth;
        }

        if (evaluateFunction(gameState, AI_PLAYER))// for ai
        {
            move.score = 10;
            return 10 - depth;
        }
        if (isEmpty(gameState)) {
            move.score = 0;
            return 0;
        }

        //     int min = Integer.MIN_VALUE; // negative infinity
        if (currentPlayer == 2) {

            int best = -1000; // positive infinity

            for (int i = 0; i < 9; i++) {

                if (gameState[i] == 0) {

                    move.position = i;
                    gameState[i] = currentPlayer;
                    best = Math.max(minimax(gameState, PLAYER1,depth+1), best);
                    gameState[i] = 0;


                }
            }

            return best;

        }
        else{
            int best = 1000;
            for (int i = 0; i < 9; i++) {

                if (gameState[i] == 0) {

                    move.position = i;
                    gameState[i] = currentPlayer;
                    best = Math.min(minimax(gameState, AI_PLAYER,depth+1), best);
                    gameState[i] = 0;

                }

            }
            return best;

        }

    }

// Find the move with the highest score
//_________________________________________________________________
    public Move findBestMove(int [] gameState, int currentPlayer) {
        int bestVal = -1000;
        int moveVal;

        Move bestMove = new Move();
        bestMove.position = 0;

        for (int i = 0; i < 9; i++) {

            if (gameState[i] == 0) {
                // Make the move
                gameState[i] = currentPlayer;

                // compute evaluation function for this
                // move.
                if(currentPlayer == 2)
                   moveVal = minimax(gameState, 1,0);

                else
                    moveVal = minimax(gameState, 2,0);
                // Undo the move
                gameState[i] = 0;

                // If the value of the current move is
                // more than the best value, then update
                // best
                if (moveVal > bestVal) {
                    bestMove.position = i;
                    bestVal = moveVal;
                }
            }

        }

        if(bestMove.position == 0)
            for (int i=0; i<9; i++)
                if(gameState[i]==0) {
                    bestMove.position = i;
                    return bestMove;
                }
        return bestMove;
    }

}

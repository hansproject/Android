package com.example.hp.activitypractice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class Board extends RelativeLayout{

    public RelativeLayout relativeLayout;
    public RelativeLayout container; // The main layout that contains everything
    public Button newGame;
    public CustomView [] view;
    private View.OnTouchListener handleTouch;
    private boolean isP1 = true;
    GameEngine gameEngine;
    public Pawn [] pawns;
    LayoutParams [] pawnsParams   = null;
    public int [] gameState;
    public int screenHeight       = 0;
    public int screenWidth        = 0;
    private int numbPlayers;
    private int STROKE_WIDTH;

// Constructor
//_________________________________________________________________
    public Board(Context context, LayoutParams p, int screenHeight, int screenWidth,int numbPlayers){

        super(context);

        try{
            gameEngine       = new GameEngine();
            view             = new CustomView[9];
            gameState        = new int[9];// There are 9 positions in the board and each correspond to a game state
            pawns            = new Pawn[9];
            pawnsParams      = new LayoutParams[9];
            this.numbPlayers = numbPlayers;

            // Initialize the view and pawn base on the screen size
            for(int i = 0;i < 9;i++) {
                view[i]       = new CustomView(context);
                pawns[i]      = new Pawn(context);
                gameState[i]  = 0;

                pawns[i].setSpecs(screenWidth,screenHeight);
                pawnsParams[i] = new LayoutParams(screenHeight/3, screenWidth/3 );

                view[i].setTag(i);
                view[i].setScaleType(ImageView.ScaleType.CENTER_CROP);
                view[i].setScreenWidth(screenWidth);
                view[i].setScreenHeight(screenHeight);
        }
        // Add a listener to each cell
        addListener();

        setScreenHeight(screenHeight);
        setScreenWidth(screenWidth);
        setStrokeWidth(0);

        relativeLayout = new RelativeLayout(context);
        container = new RelativeLayout(context);

        //Create a linear layout and his parameters
        LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth/3,screenHeight/3);
        params.leftMargin = screenWidth* 2/3;
        params.topMargin  =screenHeight;
       // linearLayout.setBackgroundColor(Color.RED);
        linearLayout.setLayoutParams(params);

        // Add a new game button to the linear layout
        newGame = new Button(context);
        LayoutParams buttonParams = new LayoutParams(screenWidth/3,screenHeight/7);
        newGame.setText("New Game");
        newGame.setTextSize(20);
        newGame.setTextColor(Color.parseColor("#D8D8D8"));
        newGame.setBackgroundResource( R.drawable.bt_style);
        linearLayout.addView(newGame,buttonParams);

        relativeLayout.setLayoutParams(p);
        relativeLayout.setBackgroundColor(Color.WHITE);

        container.addView(relativeLayout);
        container.addView(linearLayout,params);
        container.setBackgroundResource(R.drawable.backgroundpic);

        generateBoard();} catch (Exception e){
            Toast.makeText(context,"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
        }
    }

// If the game is done set all the cases as occupied
//_________________________________________________________________
    public void done(){

        try {
            for (int i = 0; i < 9; i++)
                if (gameState[i] == 0)
                    view[i].isTouched = true;
        }catch (Exception e){
            Toast.makeText(getContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
        }
    }

// Place a pawn at a specific position in the board
//_________________________________________________________________
    public void placeMove(int position, int currentPlayer){

        try {
            if (gameState[position] == 0) {
                if (currentPlayer == 1) {

                    relativeLayout.addView(pawns[position], pawnsParams[position]);

                }

                if (currentPlayer == 2) {

                    pawns[position].isRed = false;
                    relativeLayout.addView(pawns[position], pawnsParams[position]);

                }
            }
            view[position].isTouched = true;
            gameState[position] = currentPlayer;
        }catch (Exception e){
            Toast.makeText(getContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
        }
    }

// Check if the game is over
//_________________________________________________________________
    public boolean isGameOver(){

        try {
            boolean isOver = gameEngine.evaluateFunction(gameState, 1);

            if (isOver) {

                Toast.makeText(view[0].getContext(),
                        "Game is over. Player 1 wins", Toast.LENGTH_LONG).show();
                done();
                return true;
            }
            isOver = gameEngine.evaluateFunction(gameState, 2);

            Log.i("Is game over ", "" + isOver);
            if (isOver) {
                if(numbPlayers == 1)
                    Toast.makeText(view[0].getContext(),
                        "Game is over. AI wins", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(view[0].getContext(),
                            "Game is over. Player 2 wins", Toast.LENGTH_LONG).show();
                done();
                return true;
            }

            if (gameEngine.isFull(gameState)) {

                Toast.makeText(view[0].getContext(),
                        "Game is over. Draw", Toast.LENGTH_LONG).show();
                done();
                return true;
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
        }

        return false;
    }

// Generate the board and set the position where the pawns should be drawn
//_________________________________________________________________
    private void generateBoard(){

        // set x, y coordinates to place to the pawns and draw the views
        int x     = screenWidth/3;
        int y     = screenHeight/3 ;
        int count = 0;
        LayoutParams params[] = new LayoutParams[9];

        try {
            for(int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    params[count] = new LayoutParams(screenWidth / 3, screenHeight / 3);

                    params[count].leftMargin = x * j;
                    params[count].topMargin = y * i;

                    pawnsParams[count].leftMargin = x * j;
                    pawnsParams[count].topMargin = y * i;
                    view[count].x = x * j;
                    view[count].y = y * i;
                    relativeLayout.addView(view[count], params[count]);

                    view[count].centerViewX = view[count].getPivotX() + screenWidth / 6;
                    view[count].centerViewY = view[count].getPivotY() + screenHeight / 6;

                    pawns[count].centerViewX = view[count].getPivotX() + screenWidth / 6;
                    pawns[count].centerViewY = view[count].getPivotY() + screenHeight / 6;
                    count++;
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
        }

    }

// Add a listener for an ontouch event
//_________________________________________________________________
    private void addListener(){

        handleTouch = new OnTouchListener() {

            @Override
            public boolean onTouch(View aView, MotionEvent motionEvent) {

                try {
                    if (!view[(int) aView.getTag()].isTouched) {

                        if(numbPlayers == 1){
                            placeMove((int) aView.getTag(), 1);
                            isGameOver();
                            AIMove();
                            isGameOver();
                        }
                        else{
                            if (isP1) {
                                placeMove((int) aView.getTag(), 1);
                                isGameOver();
                                isP1 = false;
                            }
                            else{
                                placeMove((int) aView.getTag(), 2);
                                isGameOver();
                                isP1 = true;
                            }
                        }


                    }
                }catch (Exception e){
                    Toast.makeText(getContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
                }
                return false;
            }
        };
        for(int i=0; i<9; i++)
            view[i].setOnTouchListener(handleTouch);
    }

// Make a move for the AI
//_________________________________________________________________
    public void AIMove(){

        try {
            Move bestMove;
            bestMove = gameEngine.findBestMove(gameState, 2);

            Move p1Move = gameEngine.findBestMove(gameState, 1);
            gameState[p1Move.position] = 1;

            // Check if p1 makes a winning move
            if (gameEngine.evaluateFunction(gameState, 1)) {
                bestMove = p1Move;
                gameState[p1Move.position] = 0;
            } else
                gameState[p1Move.position] = 0;
            if (!view[bestMove.position].isTouched)
                placeMove(bestMove.position, 2);
        }catch (Exception e){
            Toast.makeText(getContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
        }
    }

//_________________________________________________________________
    private void setStrokeWidth(int i){
        STROKE_WIDTH = view[i].getStrokeWidth();
    }

//_________________________________________________________________
    private void setScreenHeight(int screenHeight){
        this.screenHeight = screenHeight;
    }

    private void setScreenWidth(int screenWidth){
        this.screenWidth  = screenWidth;
    }
}

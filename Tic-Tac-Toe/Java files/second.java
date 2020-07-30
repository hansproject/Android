package com.example.hp.activitypractice;


import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Random;

public class second extends AppCompatActivity {

    private GameEngine gameEngine;
    private int numbPlayers;
    View.OnTouchListener buttonListener;
//_________________________________________________________________
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        numbPlayers = Integer.parseInt(getIntent().getStringExtra("playersNumber"));

        startNewGame();

    }

// Start a new game
//_________________________________________________________________
    private void startNewGame( ){

        setUpGame(numbPlayers);
        playGameWithAI();

    }

// Set a listener for the new game button
//_________________________________________________________________
    private void setButtonListener(){

        try {
            Button newgame = (Button) findViewById(R.id.newgame);
            buttonListener = new View.OnTouchListener() {

                @Override
                public boolean onTouch(View aView, MotionEvent motionEvent) {

                    startNewGame();
                    return false;

                }
            };
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
        }

    }
// Set up a new game
//_________________________________________________________________
    private void setUpGame(int players) {

        try {
            Point pt = new Point();
            getWindowManager().getDefaultDisplay().getSize(pt);

            int height = pt.y;
            int width = pt.x;


            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(pt.x, pt.y * 2 / 3);
            params.height = height * 2 / 3;
            params.width = width;

            gameEngine = new GameEngine();
            gameEngine.board = new Board(getApplicationContext(), params, height * 2 / 3, width, numbPlayers);
            setContentView(gameEngine.board.container);

            gameEngine.board.newGame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            setUpGame(numbPlayers);
                            playGameWithAI();
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Oops! An error occured. Contact the System Adminstrator \n for more info",Toast.LENGTH_LONG).show();;
                }

            }
// Make a move for the AI with the AI starting first
//_________________________________________________________________
    private void playGameWithAI() {

          if(numbPlayers == 1) {
              try {
                  Random rn = new Random();
                  int range = 9;
                  gameEngine.board.placeMove(rn.nextInt(9), 2);
                  // gameEngine.board.AIMove();
              } catch (Exception e) {
                  Toast.makeText(getApplicationContext(), "Oops! An error occured. Contact the System Adminstrator \n for more info", Toast.LENGTH_LONG).show();
                    }
          }
    }

}

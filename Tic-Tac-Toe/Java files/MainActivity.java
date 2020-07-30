package com.example.hp.activitypractice;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.activitypractice.R;

public class MainActivity extends AppCompatActivity {

    private EditText in = null;
    @Override

//_________________________________________________________________
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initializeGame();
        } catch (Exception e) {
            Toast.makeText(this, "Oops! An error occured. Contact the System Adminstrator \n for more info", Toast.LENGTH_LONG).show();
            ;
        }
    }

//_________________________________________________________________
    public void initializeGame() {

        Point pt = new Point();
        getWindowManager().getDefaultDisplay().getSize(pt);

        float height = pt.y;
        float width = pt.x;

        EditText e = (EditText) findViewById(R.id.input);
        setIn(e);

        TextView view = (TextView) findViewById(R.id.title);
        view.setVisibility(View.INVISIBLE);

        ImageView view1 = (ImageView) findViewById(R.id.red);
        view1.animate().rotation(2600).translationX(width/4).setDuration(3000);

        ImageView view2 = (ImageView) findViewById(R.id.yellow);
        view2.animate().rotation(2600).translationX(-width/4).setDuration(3000);

        Handler setDelay;
        Runnable startDelay;

        setDelay = new Handler();
        startDelay = new Runnable() {
            @Override
            public void run() {
                TextView view = (TextView) findViewById(R.id.title);
                view.setVisibility(View.VISIBLE);
            }
        };
        setDelay.postDelayed(startDelay, 3000);

        Button input = (Button) findViewById(R.id.ok);
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int result = Integer.parseInt(in.getText().toString());

                    if (result <= 2 && result > 0) {
                        Intent i1 = new Intent(getApplicationContext(), second.class);
                        i1.putExtra("playersNumber", "" + result);
                        startActivity(i1);
                    } else
                        Toast.makeText(MainActivity.this, "Oops! Wrong input. Please try again", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Oops! Wrong input. Please try again", Toast.LENGTH_LONG).show();
                }

            }


        });
    }

//_________________________________________________________________
    private void setIn(EditText e){
        in = e;
    }

//_________________________________________________________________
    private EditText getIn(){
        return in;
    }
}
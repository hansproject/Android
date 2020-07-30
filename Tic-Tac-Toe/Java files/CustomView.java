package com.example.hp.activitypractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;


public class CustomView extends ImageView{


    public Canvas canvas            = null;
    public Paint  paint1            = null;
    public Paint  redPawnPaint      = null;
    public Paint  yellowPawnPaint   = null;
    public float  centerViewX       = 0;
    public float  centerViewY       = 0;
    public int screenHeight         = 0;
    public int screenWidth          = 0;
    public float x;
    public float y;
    public final int STROKE_WIDTH   = 11;
    public boolean isTouched        = false;

// Constructor
//_________________________________________________________________
    public CustomView(Context context) {

        super(context);
        canvas = new Canvas();
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        redPawnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellowPawnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint1.setStrokeWidth(STROKE_WIDTH);
        paint1.setColor(Color.BLACK);
        paint1.setStyle(Paint.Style.STROKE);

        yellowPawnPaint.setStrokeWidth(STROKE_WIDTH);
        yellowPawnPaint.setColor(Color.YELLOW);
        yellowPawnPaint.setStyle(Paint.Style.FILL);

        redPawnPaint.setStrokeWidth(STROKE_WIDTH);
        redPawnPaint.setColor(Color.RED);
        redPawnPaint.setStyle(Paint.Style.FILL);

    }
//_________________________________________________________________
    @Override
    protected void onDraw(Canvas canvas){

        super.onDraw(canvas);
        Rect aRect   = new Rect();
        aRect.top    = 0;
        aRect.left   = 0;
        aRect.right  = screenWidth/3 -  STROKE_WIDTH;
        aRect.bottom = screenHeight/3 - STROKE_WIDTH;
        canvas.drawRect(aRect,paint1);

    }

//_________________________________________________________________
    public void setScreenHeight(int screenHeight){
        this.screenHeight = screenHeight;
    }

//_________________________________________________________________
    public void setScreenWidth(int screenWidth){
        this.screenWidth  = screenWidth;
    }

//_________________________________________________________________
    public int getStrokeWidth(){ return STROKE_WIDTH; }
}

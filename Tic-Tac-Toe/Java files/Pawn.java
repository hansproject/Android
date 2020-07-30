package com.example.hp.activitypractice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class Pawn extends View {

    public Canvas canvas            = null;
    public Paint  redPawnPaint      = null;
    public Paint  yellowPawnPaint   = null;
    public float  centerViewX       = 0;
    public float  centerViewY       = 0;
    public int screenHeight         = 0;
    public int screenWidth          = 0;
    public boolean isRed            = true;
    public final int STROKE_WIDTH   = 13;

    public Pawn(Context context) {

        super(context);
        canvas = new Canvas();
        redPawnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        yellowPawnPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        yellowPawnPaint.setStrokeWidth(STROKE_WIDTH);
        yellowPawnPaint.setColor(Color.YELLOW);
        yellowPawnPaint.setStyle(Paint.Style.FILL);

        redPawnPaint.setStrokeWidth(STROKE_WIDTH);
        redPawnPaint.setColor(Color.RED);
        redPawnPaint.setStyle(Paint.Style.FILL);

    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        float cx     = centerViewX;
        float cy     = centerViewY;
        float radius = (screenHeight/3+ screenWidth/3)/6;

        if(isRed){
            canvas.drawCircle(cx,cy,radius,redPawnPaint);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(STROKE_WIDTH);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(cx,cy,radius,paint);
        }

        else {
            canvas.drawCircle(cx, cy, radius, yellowPawnPaint);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(STROKE_WIDTH);
            paint.setColor(Color.BLACK);
            canvas.drawCircle(cx,cy,radius,paint);
        }
    }

    public void setSpecs(int screenWidth, int screenHeight){

        this.screenWidth  = screenWidth;
        this.screenHeight = screenHeight;

    }
}

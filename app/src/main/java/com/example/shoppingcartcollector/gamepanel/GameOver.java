package com.example.shoppingcartcollector.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.R;

/*
Game over panel that draws Game Over to the screen.
 */

public class GameOver {

    private Context context;
    public GameOver (Context context){
        this.context = context;
    }
    public void draw(Canvas canvas) {
        String text = "Game Over";
        float x = 800;
        float y = 200;

        //set up game over colour
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(colour);
        paint.setTextSize(150);
        canvas.drawText(text, x, y, paint);
    }
}

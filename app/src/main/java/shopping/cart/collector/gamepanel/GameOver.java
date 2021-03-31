package shopping.cart.collector.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import shopping.cart.collector.R;

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
        float x = 400;
        float y = 210;

        //set up game over colour
        Paint paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.gameOver);
        paint.setColor(colour);
        //set text size for "Game Over"
        paint.setTextSize(100);
        canvas.drawText(text, x, y, paint);
        //set up text for restart text below game over
        text = "Press the back button to restart.";
        paint.setTextSize(50);
        canvas.drawText(text, x, y+60, paint);
    }
}

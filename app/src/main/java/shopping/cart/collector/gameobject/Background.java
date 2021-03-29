package shopping.cart.collector.gameobject;

/*
Background images used during gameplay
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import shopping.cart.collector.R;
import shopping.cart.collector.gamepanel.GameDisplay;


public class Background extends GameObject{
    private int width = 0, height = 0;
    private Drawable myImage;

    public Background(Context context, double positionX, double positionY, int width, int height) {
        super(positionX, positionY);
        this.width = width;
        this.height = height;

        this.myImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.background2, null);
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {
        //get background coords on screen relative to player
        double screenX = gameDisplay.gameToDisplayX(positionX);
        double screenY = gameDisplay.gameToDisplayY(positionY);

        //canvas.set
        Rect imageBounds = canvas.getClipBounds();
        imageBounds.left = (int)screenX;
        imageBounds.top = (int)screenY;
        imageBounds.right = (int)(screenX+width);
        imageBounds.bottom = (int)(screenY + height);
        myImage.setBounds(imageBounds);
        myImage.draw(canvas);

    }

    @Override
    public void update() {

    }


    public int getWidth() { return width; }
    public int getHeight() { return height; }

}


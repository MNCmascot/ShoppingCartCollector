package shopping.cart.collector.gameobject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import shopping.cart.collector.R;
import shopping.cart.collector.gamepanel.GameDisplay;


/*
Walls (or curbs) that the player may collide with
//Wall image from here https://opengameart.org/content/wall-of-large-bricks
* */

public class Wall extends GameObject{

    private int width = 0, height = 0;
    //private Paint paint;
    private Drawable myImage;
    public Wall(Context context, double positionX, double positionY, int width, int height) {
        super(positionX, positionY);
        this.width = width;
        this.height = height;

        //set image
        this.myImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.wall, null);

        //set up wall colour
        //paint = new Paint();
        //int colour = ContextCompat.getColor(context, R.color.wall);
        //paint.setColor(colour);

    }


    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {

        //get wall coords on screen relative to player position
        double screenX = gameDisplay.gameToDisplayX(positionX);
        double screenY = gameDisplay.gameToDisplayY(positionY);

        Rect imageBounds = canvas.getClipBounds();
        imageBounds.left = (int)screenX;
        imageBounds.top = (int)screenY;
        imageBounds.right = (int)(screenX+width);
        imageBounds.bottom = (int)(screenY + height);
        myImage.setBounds(imageBounds);
        myImage.draw(canvas);

        //canvas.drawRect((float)positionX, (float)positionY,
        //        (float)(positionX+width), (float)(positionY + height), paint);

    }

    @Override
    public void update() {
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
}

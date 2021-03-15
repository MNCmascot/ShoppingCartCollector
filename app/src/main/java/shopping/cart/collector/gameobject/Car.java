package shopping.cart.collector.gameobject;

/*
The Car class represents the cars that move across the screen, which the player may collide with.
Drawn as rectangles.
Sedan image - https://opengameart.org/content/car-sedan
Pickup image - https://opengameart.org/content/car-pickup
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import shopping.cart.collector.R;

import java.util.Random;

public class Car extends GameObject{
    private int width = 0, height = 0;
    //private Paint paint;
    private Drawable myImage;

    public Car(Context context, double positionX, double positionY, int width, int height) {
        super(positionX, positionY);
        this.width = width;
        this.height = height;

        Random r = new Random();
        int vehicle = r.nextInt(2);
        if (vehicle == 0){
            //set image as pickup truck
            this.myImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.pickup, null);
        } else {
            //set image as sedan
            this.myImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.sedan, null);

        }


        //set up car colour
        //paint = new Paint();
        //int colour = ContextCompat.getColor(context, R.color.car);
        //paint.setColor(colour);
    }

    @Override
    public void draw(Canvas canvas) {
        Rect imageBounds = canvas.getClipBounds();
        imageBounds.left = (int)positionX;
        imageBounds.top = (int)positionY;
        imageBounds.right = (int)(positionX+width);
        imageBounds.bottom = (int)(positionY + height);
        myImage.setBounds(imageBounds);
        myImage.draw(canvas);
        //canvas.drawRect((float)positionX, (float)positionY,
       //         (float)(positionX+width), (float)(positionY + height), paint);
    }

    @Override
    public void update() {
        //Move car to the right (deletion handled in Game.java)
        positionX += 7;

    }


    public int getWidth() { return width; }
    public int getHeight() { return height; }

}

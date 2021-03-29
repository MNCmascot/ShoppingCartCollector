package shopping.cart.collector.gameobject;

/*
The Cart class represents all the shopping carts the player is supposed to collect
and bring back to the store.
They spawn randomly in the parking lot over time.
Extension of GameObject

Image from here: https://opengameart.org/content/low-poly-shopping-cart
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import shopping.cart.collector.GameLoop;
import shopping.cart.collector.R;
import shopping.cart.collector.gamepanel.GameDisplay;

public class Cart extends GameObject{

    private static double SPAWNS_PER_MINUTE, SPAWNS_PER_SECOND, UPDATES_PER_SPAWN;
    private static double updatesUntilNextSpawn;
    private final Player player;
    private int width = 0, height = 0;
   // private Paint paint;
    private Drawable myImage;

    public Cart(Context context, Player player, double positionX, double positionY, int width, int height, int spawnsPerMin) {
        super(positionX, positionY);
        this.player = player;
        this.width = width;
        this.height = height;

        //set image
        this.myImage = ResourcesCompat.getDrawable(context.getResources(), R.drawable.cart, null);

        //Take in spawnsPerMin from game, which increases as the player plays longer
        SPAWNS_PER_MINUTE = spawnsPerMin;
        SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
        UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
        updatesUntilNextSpawn = UPDATES_PER_SPAWN;

        //set up Cart colour
        //paint = new Paint();
       // int colour = ContextCompat.getColor(context, R.color.cart);
       // paint.setColor(colour);

    }

    //check if a new cart should spawn based on SPAWNS_PER_MINUTE
    public static boolean readyToSpawn() {
        //ready to spawn another cart
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN; //reset timer
            return true;
        //Don't spawn a cart yet, decrease timer
        } else {
            updatesUntilNextSpawn--;
            return false;
        }
    }

    @Override
    public void draw(Canvas canvas, GameDisplay gameDisplay) {

        //get cart coords on screen relative to player position
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
               // (float)(positionX+width), (float)(positionY + height), paint);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    @Override
    public void update() {

    }
}

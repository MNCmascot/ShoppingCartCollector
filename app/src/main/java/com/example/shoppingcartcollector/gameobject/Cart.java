package com.example.shoppingcartcollector.gameobject;

/*
The Cart class represents all the shopping carts the player is supposed to collect
and bring back to the store.
They spawn randomly in the parking lot over time.
Extension of GameObject
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.shoppingcartcollector.GameLoop;
import com.example.shoppingcartcollector.R;

public class Cart extends GameObject{

    private static double SPAWNS_PER_MINUTE, SPAWNS_PER_SECOND, UPDATES_PER_SPAWN;
    private static double updatesUntilNextSpawn;
    private final Player player;
    private int width = 0, height = 0;
    private Paint paint;

    public Cart(Context context, Player player, double positionX, double positionY, int width, int height, int spawnsPerMin) {
        super(positionX, positionY);
        this.player = player;
        this.width = width;
        this.height = height;

        //Take in spawnsPerMin from game, which increases as the player plays longer
        SPAWNS_PER_MINUTE = spawnsPerMin;
        SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
        UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
        updatesUntilNextSpawn = UPDATES_PER_SPAWN;

        //set up Cart colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.cart);
        paint.setColor(colour);

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

    //TODO: Change this from a circle to a shopping cart
    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect((float)positionX, (float)positionY,
                (float)(positionX+width), (float)(positionY + height), paint);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    @Override
    public void update() {

    }
}

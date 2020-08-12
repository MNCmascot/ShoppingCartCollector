package com.example.shoppingcartcollector.object;

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

    private static final double SPAWNS_PER_MINUTE = 20;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS/SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private final Player player;
    private double radius;
    private Paint paint;

    public Cart(Context context, Player player, double positionX, double positionY, double radius) {
        super(positionX, positionY);
        this.player = player;
        this.radius = radius;

        //set up Cart colour
        paint = new Paint();
        int colour = ContextCompat.getColor(context, R.color.cart);
        paint.setColor(colour);

    }

    public Cart(Context context, Player player, double radius) {
        //Call constructor above with random position (not in top section of screen)
        this(context, player, Math.random()*2000+50, Math.random()*750+300, radius);
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
        canvas.drawCircle((float)positionX, (float)positionY, (float)radius, paint);
    }

    public double getRadius() { return radius; }

    @Override
    public void update() {

    }
}

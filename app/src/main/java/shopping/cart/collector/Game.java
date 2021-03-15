package shopping.cart.collector;

import android.content.Context;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import shopping.cart.collector.R;
import shopping.cart.collector.gameobject.Background;
import shopping.cart.collector.gameobject.Cart;
import shopping.cart.collector.gameobject.CartZone;
import shopping.cart.collector.gameobject.Player;
import shopping.cart.collector.gameobject.Wall;
import shopping.cart.collector.gameobject.Car;
import shopping.cart.collector.gamepanel.GameOver;
import shopping.cart.collector.gamepanel.Joystick;
import shopping.cart.collector.gamepanel.Performance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
The Game class manages all objects in the game, as well as updating states and rendering objects to the screen

 */


public class Game extends SurfaceView implements SurfaceHolder.Callback{
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    private Background background;
    private List<Cart> cartList = new ArrayList<Cart>();
    private List<Wall> wallList = new ArrayList<Wall>();
    private List<Car> carList = new ArrayList<Car>();
    private CartZone cartZone;
    private int joystickPointerId = 0;
    private GameOver gameOver;
    private boolean playedGameOverSound;
    private Performance performance;
    //spawn time used for spawning cars
    private long spawnTime;

    private boolean drawBackground = false;

    public Game(Context context) {
        super(context);

        //get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        //Create GameLoop class
        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize game over panel
        gameOver = new GameOver(context);

        //used to play gameOver crash only once
        playedGameOverSound = false;

        ImageView backgroundPanel = new ImageView(context);
        backgroundPanel.setBackgroundResource(R.drawable.background);



        //initialize Joystick panel
        joystick = new Joystick(context, 275, 700, 70, 40);

        //initialize performance panel (for drawing UPS and FPS)
        performance = new Performance(context, gameLoop);

        //initialize background
        background = new Background(context, 0, 0, 2132, 1080);

        //Initialize player
        player = new Player(context, joystick,1000, 500, 30);

        //Initialize walls
        //Centre wall left
        Wall wall1 = new Wall(context, 637, 396, 243, 100);
        wallList.add(wall1);
        //Centre wall right
        Wall wall2 = new Wall(context, 1623, 404, 243, 100);
        wallList.add(wall2);
        //Store (counted as a wall)
        Wall wall3 = new Wall(context, 845, 100, 335, 180);
        wallList.add(wall3);
        //top
        Wall wall4 = new Wall(context, 0, 0, 2132, 34);
        wallList.add(wall4);
        //top left side
        Wall wall5 = new Wall(context, 0, 34, 27, 255);
        wallList.add(wall5);
        //top right side
        Wall wall6 = new Wall(context, 2109, 34, 27, 255);
        wallList.add(wall6);
        //bottom left side
        Wall wall7 = new Wall(context, 0, 406, 27, 674);
        wallList.add(wall7);
        //bottom left corner piece
        Wall wall8 = new Wall(context, 27, 982, 408, 74);
        wallList.add(wall8);
        //bottom
        Wall wall9 = new Wall(context, 27, 1056, 2105, 24);
        wallList.add(wall9);
        //bottom right side
        Wall wall10 = new Wall(context, 2107, 408, 29, 672);
        wallList.add(wall10);
        //bottom centre piece
        Wall wall11 = new Wall(context, 1131, 986, 260, 70);
        wallList.add(wall11);
        //left car entrance
        Wall wall12 = new Wall(context, -1, 290, 1, 116);
        wallList.add(wall12);
        //right car entrance
        Wall wall13 = new Wall(context, 2133, 290, 1, 118);
        wallList.add(wall13);


        //Initialize Cart Zone (to bring the carts to)
        cartZone = new CartZone(context, 1178, 156, 695, 97, 30);

        setFocusable(true);
    }


    //handle touch event actions
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                //joystick was pressed
                if (joystick.isPressed((double)event.getX(), (double)event.getY())){
                    //store ID (to handle multi-touches)
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    // set it as pressed
                    joystick.setIsPressed(true);
                }
               // player.setPosition((double)event.getX(), (double)event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                //if joystick is pressed:
                if (joystick.getIsPressed()) {
                    joystick.setActuator((double)event.getX(), (double)event.getY());
                }
              //  player.setPosition((double)event.getX(), (double)event.getY());
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if (joystickPointerId == event.getPointerId(event.getActionIndex())){
                    //let go of joystick
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }

                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
       // Log.d("Game.java", "surfaceCreated()");

        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, holder);
        }
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
      //  Log.d("MainActivity.java", "surfaceChanged()");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Log.d("MainActivity.java", "surfaceDestroyed()");
    }


    /*
    Draw to canvas (displayed in the order from bottom to top. ex: player should not be displayed
    over the joystick)
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
       // canvas.drawColor(0, PorterDuff.Mode.CLEAR);

       // if (!drawBackground){
            background.draw(canvas);
            //drawBackground=true;
        //}

        //display joystick
        joystick.draw(canvas);
        //display carts
        for (Cart cart: cartList) {
            cart.draw(canvas);
        }
        //display player
        player.draw(canvas);

        //display walls
        /*
        for (Wall wall: wallList) {
            wall.draw(canvas);
        }
         */


        //display cars
        for (Car car: carList) {
            car.draw(canvas);
        }
        //display CartZone (currently just draws carts left in store, background handles the rest)
        cartZone.draw(canvas);

        //display UPS and FPS
        performance.draw(canvas);



        //Draw Game Over if the player is dead
        if (player.isDead()) {
            gameOver.draw(canvas);

            //play crash sound if player died to a crash
            if (cartZone.getCartsLeft() > 0 && playedGameOverSound == false){
                //Play crash sound effect https://freesound.org/people/bolkmar/sounds/434136/
                playedGameOverSound = true;
                MediaPlayer crashSound = MediaPlayer.create(getContext(), R.raw.crash);
                crashSound.setVolume(0.3f, 0.3f);
                crashSound.start();
            }

        }
    }


    //update game state
    public void update() {

        //stop updating the game if the player is dead
        if (player.isDead()){
            return;
        }

        player.update();
        joystick.update();
        cartZone.update();

        /*
        //spawn a new cart if it is time to spawn one
        //make sure it is not colliding with the joystick outer circle or a Wall
        */
        if (Cart.readyToSpawn()) {
            //decrease number of carts in store by 1
            cartZone.setCartsLeft(cartZone.getCartsLeft()-1);
           // Log.d("DEBUG", cartZone.getCartsLeft() + " carts left.");
            //If no carts left, game over
            if (cartZone.getCartsLeft() <= 0) {
                player.setDead(true);
                performance.setGameOver();
            }

            Cart tempCart; //the cart we are trying to spawn
            double distance; //distance between joystick and new cart
            double distanceToCollision; //distance between radii
            boolean underCart, underJoystick, underWall; //temporary booleans if under something
            do { //get another position if new cart is under joystick or under wall
                double tempX = Math.random() * 2000 + 50; //random X location
                double tempY = Math.random() * 750 + 300; //random Y location
                underWall = false; //Initialize as false, not under a wall
                underJoystick = false; //Initialize as false, not under joystick
                underCart = false;  //Initialize as false, not under a cart

                //Make game slowly get more difficult the longer the player has been playing, currently every 10 seconds
                int difficulty = (performance.getSeconds()/15);
                cartList.add(new Cart(getContext(), player, tempX, tempY, 60, 40, 20+difficulty*4));
                tempCart = cartList.get(cartList.size() - 1); //last cart added


                //Check if under joystick
                underJoystick = Utils.circleRectangleCollision(joystick.getOuterCircleCenterPositionX(), joystick.getOuterCircleCenterPositionY(), joystick.getOuterCircleRadius(),
                                                               tempCart.getPositionX(), tempCart.getPositionY(), tempCart.getWidth(), tempCart.getHeight());

                //Iterate through cartList to check if cart is under and of the other carts
                Iterator<Cart> cartIterator = cartList.iterator();
                while (cartIterator.hasNext()) {
                    Cart cartCheck = cartIterator.next();
                    //don't check if this is the last cart in the list (itself)
                    if (cartIterator.hasNext() == false){
                        break;
                    }
                    if (Utils.RectangleRectangleCollision(tempCart.getPositionX(), tempCart.getPositionY(), tempCart.getWidth(), tempCart.getHeight(),
                            cartCheck.getPositionX(), cartCheck.getPositionY(), cartCheck.getWidth(), cartCheck.getHeight())){
                        underCart = true;
                        break;
                    }
                }

                //Iterate through wallList to check if cart is under any of the walls
                Iterator<Wall> wallIterator = wallList.iterator();
                while (wallIterator.hasNext()) {
                    Wall wallCheck = wallIterator.next();
                    if (Utils.RectangleRectangleCollision(tempCart.getPositionX(), tempCart.getPositionY(), tempCart.getWidth(), tempCart.getHeight(),
                            wallCheck.getPositionX(), wallCheck.getPositionY(), wallCheck.getWidth(), wallCheck.getHeight())){
                        underWall = true;
                        break;
                    }
                }

                if (underCart || underJoystick|| underWall) { //under joystick or under wall, remove cart
                    cartList.remove(cartList.size()-1);
                   // Log.d("DEBUG", "cart spawned under something");
                }

            } while (underCart || underJoystick || underWall);
        }

        //update each Cart
        for (Cart cart : cartList) {
            cart.update();
        }

        //update each Wall
        for (Wall wall : wallList) {
            wall.update();
        }

        //spawn a new car if it's ready, starting off screen
        if (carReadyToSpawn()) {
            carList.add(new Car(getContext(), -200, 300, 200, 100));
        }

        //update each Car
        for (Car car : carList) {
            car.update();
            //remove car if it's too far to the right
            if (car.getPositionX() > 2200){
                carList.remove(car);
            }
        }

        //iterate through cartList to check for collisions with the player
        Iterator<Cart> cartIterator = cartList.iterator();
        while (cartIterator.hasNext()) {
            Cart cartCheck = cartIterator.next();
            //player has collided with cart (pick it up)
            if (Utils.circleRectangleCollision(player.getPositionX(), player.getPositionY(), player.getRadius(),
                    cartCheck.getPositionX(), cartCheck.getPositionY(), cartCheck.getWidth(), cartCheck.getHeight())){
                //remove cart if it collides with the player
                cartIterator.remove();
                //increase number of carts collected
                player.incrementCartsCollected();

                //Play sound effect https://freesound.org/people/Twiggie2000/sounds/366131/
                MediaPlayer cartCollectedSound = MediaPlayer.create(getContext(), R.raw.cart_collected);
                //cartCollectedSound.setVolume(0.3f, 0.3f);
                cartCollectedSound.start();
            }
        }

        //Iterate through wallList to check for collisions with the player
        Iterator<Wall> wallIterator = wallList.iterator();
        while (wallIterator.hasNext()) {
            Wall wallCheck = wallIterator.next();

            if(Utils.circleRectangleCollision(player.getPositionX(), player.getPositionY(), player.getRadius(),
                    wallCheck.getPositionX(), wallCheck.getPositionY(), wallCheck.getWidth(), wallCheck.getHeight())){
               // Log.d("DEBUG", "player colliding with wall!");
                player.setDead(true);
                //tell performance to stop updating timer
                performance.setGameOver();
            }
        }

        //Iterate through carList to check for collisions with the player
        Iterator<Car> carIterator = carList.iterator();
        while (carIterator.hasNext()) {
            Car carCheck = carIterator.next();

            if(Utils.circleRectangleCollision(player.getPositionX(), player.getPositionY(), player.getRadius(),
                    carCheck.getPositionX(), carCheck.getPositionY(), carCheck.getWidth(), carCheck.getHeight())){
                player.setDead(true);
                //tell performance to stop updating timer
                performance.setGameOver();
            }
        }


        //Check if player has carts and "collided" with the Cart Zone and increase number of carts left if so.
        if( player.getCartsCollected() > 0 &&
            Utils.circleRectangleCollision(player.getPositionX(), player.getPositionY(), player.getRadius(),
                cartZone.getPositionX(), cartZone.getPositionY(), cartZone.getWidth(), cartZone.getHeight())){
            //Increase the carts left by the amount the player has
            cartZone.setCartsLeft(cartZone.getCartsLeft() + player.getCartsCollected());
            //Set player's collected amount to 0 (no carts collected anymore)
            player.setCartsCollected(0);

            //Play sound effect https://freesound.org/people/cgrote/sounds/133841/
            MediaPlayer cartsReturnedSound = MediaPlayer.create(getContext(), R.raw.carts_returned);
            cartsReturnedSound.setVolume(0.3f, 0.3f);
            cartsReturnedSound.start();

        }

    }

    //Pause the game when user exits
    public void pause() {
        gameLoop.stopLoop();
    }

    public boolean carReadyToSpawn(){

        long millis;
        int seconds;
        //Time since last spawn
        millis = System.currentTimeMillis() - spawnTime;
        //time in seconds
        seconds = (int) (millis/1000);

        //spawn another car if it has been longer than threshold
        if (seconds > 10) {
            //reset spawn timer
            spawnTime = System.currentTimeMillis();
            return true;
        }

        //otherwise, not ready to spawn
        return false;
    }
}

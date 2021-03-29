package shopping.cart.collector.gamepanel;

import shopping.cart.collector.gameobject.GameObject;

public class GameDisplay {

    private double gameToDisplayOffsetX;
    private double gameToDisplayOffsetY;
    private double gameCenterX;
    private double gameCenterY;
    private double displayCenterX;
    private double displayCenterY;
    private GameObject centerObject;

    public GameDisplay(int widthPixels, int heightPixels, GameObject centerObject) {
        this.centerObject = centerObject;

        displayCenterX = widthPixels/2.0;
        displayCenterY = heightPixels/2.0;
    }

    public void update() {
        gameCenterX = centerObject.getPositionX();
        gameCenterY = centerObject.getPositionY();

        gameToDisplayOffsetX = displayCenterX - gameCenterX;
        gameToDisplayOffsetY = displayCenterY - gameCenterY;

    }

    public double gameToDisplayX(double positionX) {
        return positionX + gameToDisplayOffsetX;
    }

    public double gameToDisplayY(double positionY) {
        return positionY + gameToDisplayOffsetY;
    }
}

package com.example.shoppingcartcollector;

public class Utils {
    public static double getDistanceBetweenPoints(double p1x, double p1y, double p2x, double p2y) {
        return Math.sqrt(
                Math.pow(p1x - p2x, 2) +
                Math.pow(p1y - p2y, 2));
    }

    //limit value to range between min and max)
    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    public static boolean circleRectangleCollision(double circlex, double circley, double radius, double rectx, double recty, double rectwidth, double rectheight){
        //closest point to circle within rectangle
        double closestX = clamp(circlex, rectx, (rectx+rectwidth));
        double closestY = clamp(circley, recty, (recty+rectheight));

        //distance between circle center and closest point
        double distanceX = circlex - closestX;
        double distanceY = circley - closestY;

        //intersetion if distance less than circle's radius
        double distanceSquared = Math.pow(distanceX, 2) + Math.pow(distanceY, 2);
        return distanceSquared < (Math.pow(radius, 2));
    }
}

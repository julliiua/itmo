package yuliya.service;

import yuliya.model.Point;

public class AreaCheckService {

    public static boolean checkHit(double x, double y, double r) {
        boolean inCircle = (x <= 0 && y >= 0 && x*x + y*y <= r*r);
        boolean inRectangle = (x <= 0 && y <= 0 && x >= -r && y >= -r/2);
        boolean inTriangle = (x >= 0 && y >= 0 && y <= -x + r/2);

        return inCircle || inRectangle || inTriangle;
    }
    public static boolean checkHit(Point point) {
        return checkHit(point.getX(), point.getY(), point.getR());
    }
}
package yuliya.model;

import java.util.Date;

public class Point {
    private double x;
    private double y;
    private double r;
    private boolean hit;
    private Date timestamp;

    public Point(double x, double y, double r, boolean hit, Date timestamp) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.hit = hit;
        this.timestamp = timestamp;
    }

    // геттеры
    public double getX() { return x; }
    public double getY() { return y; }
    public double getR() { return r; }
    public boolean isHit() { return hit; }
    public Date getTimestamp() { return timestamp; }

    public String getHitText() {
        return hit ? "Попадание" : "Промах";
    }

    public String getHitClass() {
        return hit ? "hit" : "miss";
    }
}
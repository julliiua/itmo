package ru.yuliya.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@Embeddable
public class Location implements Serializable {
    @NotNull
    private Float x;

    private int y;

    private float z;

    public Float getX() { return x; }
    public void setX(Float x) { this.x = x; }

    public int getY() { return y; }
    public void setY(int y) { this.y = y; }

    public float getZ() { return z; }
    public void setZ(float z) { this.z = z; }

}

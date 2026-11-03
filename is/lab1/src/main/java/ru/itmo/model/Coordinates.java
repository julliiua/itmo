package ru.itmo.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class Coordinates implements Serializable {
    @Max(689)
    private int x;

    @NotNull
    @DecimalMax("310")
    private Double y;

    public int getX() { return x; }
    public void setX(int x) { this.x = x; }

    public Double getY() { return y; }
    public void setY(Double y) { this.y = y; }

}

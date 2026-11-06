package ru.yuliya.model;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class DragonHead implements Serializable {
    private float size;

    public float getSize() { return size; }
    public void setSize(float size) { this.size = size; }

}

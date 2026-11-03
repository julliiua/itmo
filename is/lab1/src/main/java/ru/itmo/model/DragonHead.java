package ru.itmo.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class DragonHead implements Serializable {
    private float size;

    public float getSize() { return size; }
    public void setSize(float size) { this.size = size; }

}

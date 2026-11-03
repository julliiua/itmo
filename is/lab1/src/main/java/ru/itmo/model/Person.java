package ru.itmo.model;

import javax.persistence.*;
import org.hibernate.annotations.Check;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Check(constraints="id > 0 and length(trim(name)) > 0 and weight > 0")
@Entity
@Table(name="person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Color eyeColor;

    @Enumerated(EnumType.STRING)
    private Color hairColor;

    @NotNull
    @Embedded
    private Location location;

    @NotNull
    @Min(1)
    @Column(nullable=false)
    private Long weight;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Country nationality;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Color getEyeColor() { return eyeColor; }
    public void setEyeColor(Color eyeColor) { this.eyeColor = eyeColor; }

    public Color getHairColor() { return hairColor; }
    public void setHairColor(Color hairColor) { this.hairColor = hairColor; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public Long getWeight() { return weight; }
    public void setWeight(Long weight) { this.weight = weight; }

    public Country getNationality() { return nationality; }
    public void setNationality(Country nationality) { this.nationality = nationality; }

}

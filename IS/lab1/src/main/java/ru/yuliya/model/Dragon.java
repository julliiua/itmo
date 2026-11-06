package ru.yuliya.model;

import javax.persistence.*;
import org.hibernate.annotations.Check;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Check(constraints="id > 0 and length(trim(name)) > 0 and x <= 689 and y <= 310 and (age is null or age > 0) and wingspan > 0 and weight > 0")
@Entity
@Table(name="dragon")
public class Dragon implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false)
    private String name;

    @NotNull
    @Embedded
    private Coordinates coordinates;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false, updatable=false)
    private Date creationDate;

    @NotNull
    @ManyToOne(optional=false)
    @JoinColumn(nullable=false)
    private DragonCave cave;

    @ManyToOne
    private Person killer;

    @Min(1)
    private Integer age;

    @Min(1)
    @Column(nullable=false)
    private int wingspan;

    @Min(1)
    @Column(nullable=false)
    private int weight;

    @Enumerated(EnumType.STRING)
    private Color color;

    @Embedded
    private DragonHead head;

    @Column(nullable=false)
    private boolean alive;

    @PrePersist
    public void beforeSave() { if (creationDate == null) creationDate = new Date(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Coordinates getCoordinates() { return coordinates; }
    public void setCoordinates(Coordinates coordinates) { this.coordinates = coordinates; }

    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public DragonCave getCave() { return cave; }
    public void setCave(DragonCave cave) { this.cave = cave; }

    public Person getKiller() { return killer; }
    public void setKiller(Person killer) { this.killer = killer; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public int getWingspan() { return wingspan; }
    public void setWingspan(int wingspan) { this.wingspan = wingspan; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }

    public DragonHead getHead() { return head; }
    public void setHead(DragonHead head) { this.head = head; }

    public boolean getAlive() { return alive; }
    public void setAlive(boolean alive) { this.alive = alive; }

}

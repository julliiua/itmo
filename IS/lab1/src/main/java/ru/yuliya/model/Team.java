package ru.yuliya.model;

import javax.persistence.*;
import org.hibernate.annotations.Check;
import javax.validation.constraints.*;
import java.io.Serializable;

@Check(constraints="id > 0 and length(trim(name)) > 0")
@Entity
@Table(name="team")
public class Team implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable=false)
    private String name;

    @ManyToOne
    private Dragon dragon;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Dragon getDragon() { return dragon; }
    public void setDragon(Dragon dragon) { this.dragon = dragon; }

}

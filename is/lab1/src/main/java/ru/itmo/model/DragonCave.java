package ru.itmo.model;

import javax.persistence.*;
import org.hibernate.annotations.Check;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Check(constraints="id > 0 and (numberOfTreasures is null or numberOfTreasures > 0)")
@Entity
@Table(name="dragon_cave")
public class DragonCave implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value="0", inclusive=false)
    private Double numberOfTreasures;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getNumberOfTreasures() { return numberOfTreasures; }
    public void setNumberOfTreasures(Double numberOfTreasures) { this.numberOfTreasures = numberOfTreasures; }

}

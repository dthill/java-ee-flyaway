package pgfsd.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Destinations")
public class Destination {
    @Id
    @Column(nullable = false, length = 3)
    private String code;
    @Column(nullable = false)
    private String name;
    public Destination() {
    }

    public Destination(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object destination) {
        if (this == destination) return true;
        if (destination == null || getClass() != destination.getClass()) return false;
        Destination that = (Destination) destination;
        return Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}

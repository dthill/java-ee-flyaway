package pgfsd.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Flights")
public class Flight {
    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    private Airline airline;
    @ManyToOne
    private Destination departureDestination;
    @Column(nullable = false)
    private Date departureDate;
    @ManyToOne
    private Destination arrivalDestination;
    @Column(nullable = false)
    private Date arrivalDate;
    @Column(nullable = false)
    private Double price;

    public Flight() {
    }

    public Flight(Long id, Airline airline, Destination departureDestination, Date departureDate, Destination arrivalDestination, Date arrivalDate, Double price) {
        this.id = id;
        this.airline = airline;
        this.departureDestination = departureDestination;
        this.departureDate = departureDate;
        this.arrivalDestination = arrivalDestination;
        this.arrivalDate = arrivalDate;
        this.price = price;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public Destination getArrivalDestination() {
        return arrivalDestination;
    }

    public void setArrivalDestination(Destination arrivalDestination) {
        this.arrivalDestination = arrivalDestination;
    }

    public Destination getDepartureDestination() {
        return departureDestination;
    }

    public void setDepartureDestination(Destination departureDestination) {
        this.departureDestination = departureDestination;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
package com.uexcel.regular.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String phone;
    private String description;
    @OneToMany(mappedBy = "reservation",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<ReservationDates> reservationDates;

}

package com.uexcel.regular.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class RegularRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String roomNumber;
    private double price;
    @OneToMany(mappedBy = "regularRoom",fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    private List<ReservationDates> reservationDates;
}

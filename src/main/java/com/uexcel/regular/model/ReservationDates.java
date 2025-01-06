package com.uexcel.regular.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Getter
@Setter
public class ReservationDates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    private Reservation reservation;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false,cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinColumn(name = "roomNumber",referencedColumnName = "roomNumber",
            foreignKey = @ForeignKey(name = "FK_RD_REGULAR"))
    private RegularRoom regularRoom;
}

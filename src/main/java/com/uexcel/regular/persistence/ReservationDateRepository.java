package com.uexcel.regular.persistence;

import com.uexcel.regular.model.ReservationDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationDateRepository extends JpaRepository<ReservationDates,Long> {
    List<ReservationDates> findByDate(LocalDate date);
}

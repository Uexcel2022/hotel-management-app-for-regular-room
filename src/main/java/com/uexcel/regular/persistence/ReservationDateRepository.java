package com.uexcel.regular.persistence;

import com.uexcel.regular.model.ReservationDates;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationDateRepository extends JpaRepository<ReservationDates,Long> {
    List<ReservationDates> findByDate(LocalDate date);
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from reservation_dates where id = ?")
    void deleteById(@Param("id") Long id);
}

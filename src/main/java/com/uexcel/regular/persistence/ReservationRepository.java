package com.uexcel.regular.persistence;

import com.uexcel.regular.model.Reservation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from reservation where id = ?")
    @Override
    void deleteById(@Param("id") Long id);
}

package com.uexcel.regular.persistence;

import com.uexcel.regular.model.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckinRepository extends JpaRepository<Checkin, Long> {
    Checkin findByRegularRoom_RoomNumberAndDateOut(String roomNumber, String dateIn);
}

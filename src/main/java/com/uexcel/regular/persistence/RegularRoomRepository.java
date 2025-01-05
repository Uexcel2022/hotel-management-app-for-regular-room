package com.uexcel.regular.persistence;

import com.uexcel.regular.model.RegularRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegularRoomRepository extends JpaRepository<RegularRoom, Long> {
    RegularRoom findByRoomNumber (String roomNumber);
    boolean existsByRoomNumber (String roomNumber);
}

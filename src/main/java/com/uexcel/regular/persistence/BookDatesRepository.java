package com.uexcel.regular.persistence;

import com.uexcel.regular.model.BookDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDatesRepository extends JpaRepository<BookDates,Long> {
}

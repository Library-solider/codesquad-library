package com.librarycodesquad.admin.domain.rental;

import com.librarycodesquad.prod.domain.rental.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalAdminRepository extends JpaRepository<Rental, Long> {

    Page<Rental> findAllByIsReturnedFalse(Pageable pageable);

    List<Rental> findAllByBookIdAndIsReturnedFalse(Long bookId);
}

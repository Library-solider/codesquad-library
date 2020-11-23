package com.librarycodesquad.admin.domain.rental;

import com.librarycodesquad.prod.domain.rental.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalAdminRepository extends JpaRepository<Rental, Long> {

    Page<Rental> findAllByIsReturnedFalse(Pageable pageable);
}

package com.librarycodesquad.admin.domain.rental;

import com.librarycodesquad.prod.domain.rental.Rental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RentalAdminRepository extends JpaRepository<Rental, Long> {

    Page<Rental> findAllByIsReturnedFalse(Pageable pageable);

    Optional<Rental> findByBookIdAndIsReturnedFalse(Long bookId);

    List<Rental> findAllByAccountIdAndIsReturnedFalse(Long accountId);
}

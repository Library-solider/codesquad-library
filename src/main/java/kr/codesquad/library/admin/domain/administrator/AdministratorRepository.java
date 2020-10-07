package kr.codesquad.library.admin.domain.administrator;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, String> {

    Optional<Administrator> findByAdminName(String adminName);
}

package com.prophiuslimited.ProphiusLimitedAssessment.repositories;


import com.prophiuslimited.ProphiusLimitedAssessment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByUserId(String id);
//    @Query( nativeQuery = true, value = "SELECT * FROM user_tbl WHERE username = ?")
//    Page<User> findAllUserByUsername(Pageable pageRequest);
    Optional<User> findByEmail(String email);

}

package com.tristanruecker.interviewexampleproject.models.repositores;

import com.tristanruecker.interviewexampleproject.models.objects.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

}

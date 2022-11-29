package com.tristanruecker.interviewexampleproject.models.repositores;

import com.tristanruecker.interviewexampleproject.models.objects.Captcha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface CaptchaRepository extends JpaRepository<Captcha, UUID> {

    void findByCreationDateBefore(Instant creationDate);
}

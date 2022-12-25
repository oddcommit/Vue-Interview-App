package com.tristanruecker.interviewexampleproject.chronjobs;

import com.tristanruecker.interviewexampleproject.models.objects.entities.Captcha;
import com.tristanruecker.interviewexampleproject.models.repositores.CaptchaRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class CaptchaCleanup {

    private final CaptchaRepository captchaRepository;

    public CaptchaCleanup(CaptchaRepository captchaRepository) {
        this.captchaRepository = captchaRepository;
    }

    @Scheduled(cron = "${captcha.cleanup.job}")
    @PostConstruct
    public void cleanUpCaptcha() {
        Instant instant = Instant.now();
        instant = instant.minus(1, ChronoUnit.HOURS);
        List<Captcha> multipleCaptcha = captchaRepository.findByCreationDateBefore(instant);
        if(CollectionUtils.isNotEmpty(multipleCaptcha)) {
            captchaRepository.deleteAll(multipleCaptcha);
        }
    }

}

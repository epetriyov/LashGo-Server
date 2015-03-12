package com.lashgo.service;

import com.lashgo.domain.Check;
import com.lashgo.repository.CheckDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Eugene on 22.02.2015.
 */
@Service
@EnableScheduling
public class NotificationService {

    @Autowired
    private GcmService gcmService;

    @Autowired
    private ApnsService apnsService;

    @Autowired
    private CheckDao checkDao;

    @Scheduled(cron = "1 * * * * *")
    public void sendNotifications() {
        Check currentCheck = checkDao.getCurrentCheck();
        Check voteCheck = checkDao.getVoteCheck();
        gcmService.sendGcm(currentCheck, voteCheck);
        apnsService.sendApn(currentCheck, voteCheck);
    }

    public void sendApn() {

    }

    public void sendGcm() {

    }
}

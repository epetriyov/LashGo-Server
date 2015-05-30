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
    public void sendGcmNotifications() {
        Check currentCheck = checkDao.getCurrentCheck();
        Check voteCheck = checkDao.getVoteCheck();
        Check finishedCheck = checkDao.getFinishedCheck();
        gcmService.sendGcm(currentCheck, voteCheck,finishedCheck);
    }

    @Scheduled(cron = "1 * * * * *")
    public void sendApnsNotifications() {
        Check currentCheck = checkDao.getCurrentCheck();
        Check voteCheck = checkDao.getVoteCheck();
        Check finishedCheck = checkDao.getFinishedCheck();
        apnsService.sendApn(currentCheck, voteCheck,finishedCheck);
    }

    public void sendGcm() {

    }

    public void sendApn() {

    }
}

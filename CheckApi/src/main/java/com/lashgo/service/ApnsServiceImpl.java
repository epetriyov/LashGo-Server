package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Check;
import com.lashgo.error.ValidationException;
import com.lashgo.model.GcmEventType;
import com.lashgo.model.dto.ApnTokenDto;
import com.lashgo.repository.ApnDao;
import com.lashgo.repository.SessionDao;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsServiceBuilder;
import com.notnoop.exceptions.ApnsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Eugene on 22.02.2015.
 */
@Service
@EnableScheduling
@Transactional(readOnly = true)
public class ApnsServiceImpl implements ApnsService {

    private final Logger logger = LoggerFactory.getLogger("FILE");

    @Autowired
    private ApnDao apnDao;

    @Autowired
    private SessionDao sessionDao;

    @Override
    @Transactional
    public void addToken(String sessionId, ApnTokenDto apnTokenDto) throws ValidationException {
        int userId = sessionId != null ? sessionDao.getSessionById(sessionId).getUserId() : -1;
        if (userId > 0) {
            if (!apnDao.isApnsTokenExists(apnTokenDto.getToken())) {
                apnDao.addApnsToken(apnTokenDto.getToken(), userId);
            } else {
                apnDao.updateRegisterDate(apnTokenDto.getToken());
            }
        }
    }

    @Override
    public void sendApn(Check currentCheck, Check voteCheck) {
        List<String> apnTokens = apnDao.getAllApnsTokens();
        sendApns(currentCheck, GcmEventType.CHECK_STARTED,apnTokens);
        sendApns(voteCheck, GcmEventType.VOTE_STARTED,apnTokens);
    }

    private void sendApns(Check check, GcmEventType eventType,List<String> apnTokens) {
        try {
            if (check != null) {
                ApnsServiceBuilder apnsServiceBuilder =
                        APNS.newService()
                                .withCert(CheckConstants.APNS_CERT_PATH, CheckConstants.APNS_CERT_PASSWORD);
                apnsServiceBuilder.withProductionDestination();
                com.notnoop.apns.ApnsService service = apnsServiceBuilder.build();
                String payload = APNS.newPayload().localizedKey(eventType.name()).
                        localizedArguments(new String[]{check.getName()}).
                        customField(CheckConstants.CURRENT_CHECK_ID, check.getId()).
                        sound("default").
                        badge(1).
                        build();
                if (!CollectionUtils.isEmpty(apnTokens)) {
                    logger.debug("SEND APNS  payload {}", payload);
                    service.push(apnTokens, payload);
                }
            }
        } catch (ApnsException e) {
            logger.debug("sendApns failed, check {}, exception {}", check, e);
        }

    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void feedbackService() {
        try {
            ApnsServiceBuilder apnsServiceBuilder =
                    APNS.newService()
                            .withCert(CheckConstants.APNS_CERT_PATH, CheckConstants.APNS_CERT_PASSWORD);
            apnsServiceBuilder.withProductionDestination();
            com.notnoop.apns.ApnsService service = apnsServiceBuilder.build();
            Map<String, Date> inactiveDevices = service.getInactiveDevices();
            List<String> inactiveTokens = new ArrayList<>();
            for (String deviceToken : inactiveDevices.keySet()) {
                Date inactiveAsOf = inactiveDevices.get(deviceToken);
                Date registerDate = apnDao.getRegisterDateByToken(deviceToken);
                if (registerDate != null && inactiveAsOf.after(registerDate)) {
                    inactiveTokens.add(deviceToken);
                }
            }
            if (!CollectionUtils.isEmpty(inactiveDevices)) {
                apnDao.removeInactiveTokens(inactiveTokens);
            }
        } catch (ApnsException e) {
            logger.debug("feedbackService failed, exception {}", e);
        }
    }

    @Override
    public void sendApn(String token) {
        try {
            ApnsServiceBuilder apnsServiceBuilder =
                    APNS.newService()
                            .withCert(CheckConstants.APNS_CERT_PATH, CheckConstants.APNS_CERT_PASSWORD);
            apnsServiceBuilder.withProductionDestination();
            com.notnoop.apns.ApnsService service = apnsServiceBuilder.build();
            String payload = APNS.newPayload().localizedKey(GcmEventType.CHECK_STARTED.name()).
                    localizedArguments(new String[]{"Check name"}).
                    customField(CheckConstants.CURRENT_CHECK_ID, -1).
                    sound("default").
                    badge(1).
                    build();
            logger.debug("TEST APNS token {}, payload {}", token, payload);
            service.push(token, payload);
        } catch (ApnsException e) {
            logger.debug("sendApn failed, token {}, exception {}", token, e);
        }
    }
}

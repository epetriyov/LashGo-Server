package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Check;
import com.lashgo.error.ValidationException;
import com.lashgo.gcm.InvalidRequestException;
import com.lashgo.gcm.Message;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.dto.GcmRegistrationDto;
import com.lashgo.model.dto.MulticastResult;
import com.lashgo.repository.CheckDao;
import com.lashgo.repository.GcmDao;
import com.lashgo.repository.SessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lashgo.gcm.Constants.*;

/**
 * Created by Eugene on 19.03.14.
 */
@Service
@Transactional(readOnly = true)
@EnableScheduling
public class GcmServiceImpl implements GcmService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GcmDao gcmDao;

    @Autowired
    private SessionDao sessionDao;

    @Autowired
    private CheckDao checkDao;

    private final Logger logger = LoggerFactory.getLogger("FILE");

    private static final String CURRENT_CHECK_ID = "check_id";

    private static final String CURRENT_CHECK_NAME = "check_name";

    /**
     * Sends a message without retrying in case of service unavailability. See
     *
     * @return multicast results if the message was sent successfully,
     * {@literal null} if it failed but could be retried.
     * @throws IllegalArgumentException if registrationIds is {@literal null} or
     *                                  empty.
     * @throws InvalidRequestException  if GCM didn't returned a 200 status.
     * @throws IOException              if there was a JSON parsing error
     */
    private void sendNoRetry(Message message,
                             List<String> registrationIds) {
        if (StringUtils.isEmpty(registrationIds)) {
            logger.error(ErrorCodes.REGISTRATION_ID_IS_EMPTY);
        }
        Map<Object, Object> jsonRequest = new HashMap<>();
        setJsonField(jsonRequest, PARAM_TIME_TO_LIVE, message.getTimeToLive());
        setJsonField(jsonRequest, PARAM_COLLAPSE_KEY, message.getCollapseKey());
        setJsonField(jsonRequest, PARAM_RESTRICTED_PACKAGE_NAME, message.getRestrictedPackageName());
        setJsonField(jsonRequest, PARAM_DELAY_WHILE_IDLE,
                message.isDelayWhileIdle());
        setJsonField(jsonRequest, PARAM_DRY_RUN, message.isDryRun());
        jsonRequest.put(JSON_REGISTRATION_IDS, registrationIds);
        Map<String, String> payload = message.getData();
        if (!payload.isEmpty()) {
            jsonRequest.put(JSON_PAYLOAD, payload);
        }

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "key=" + CheckConstants.GCM_PROJECT_API);
        HttpEntity entity = new HttpEntity(jsonRequest, headers);
        try {
            ResponseEntity<MulticastResult> responseEntity = restTemplate.exchange(GCM_SEND_ENDPOINT, HttpMethod.POST, entity, MulticastResult.class);
            logger.info(responseEntity.getBody().toString());
        } catch (RestClientException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Sets a JSON field, but only if the value is not {@literal null}.
     */
    private void setJsonField(Map<Object, Object> json, String field,
                              Object value) {
        if (value != null) {
            json.put(field, value);
        }
    }

    @Transactional
    @Override
    public void addRegistrationId(String sessionId, GcmRegistrationDto registrationDto) throws ValidationException {
        int userId = sessionId != null ? sessionDao.getSessionById(sessionId).getUserId() : -1;
        if (userId > 0) {
            if (!gcmDao.isRegistrationIdExists(registrationDto.getRegistrationId())) {
                gcmDao.addRegistrationId(registrationDto.getRegistrationId(), userId);
            }
        }
    }

    public void sendChecks() {
        List<String> registrationIds = gcmDao.getAllRegistrationIds();
        Check check = checkDao.getCurrentCheck();
        if (check != null) {
            System.out.println("Send check");
            Message.Builder messageBuilder = new Message.Builder();
            messageBuilder.addData(CURRENT_CHECK_ID, String.valueOf(check.getId()));
            messageBuilder.addData(CURRENT_CHECK_NAME, check.getName());
            sendNoRetry(messageBuilder.build(), registrationIds);
        }
    }

    @Scheduled(cron = "1 0 * * * *")
    @Override
    public void multicastSend() {
        sendChecks();
    }
}

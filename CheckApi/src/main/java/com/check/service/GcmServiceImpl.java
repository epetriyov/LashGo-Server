package main.java.com.check.service;

import com.check.model.dto.GcmRegistrationDto;
import com.check.model.dto.MulticastResult;
import com.check.model.dto.Result;
import main.java.com.check.CheckConstants;
import main.java.com.check.gcm.*;
import main.java.com.check.repository.GcmDao;
import main.java.com.check.rest.error.ErrorCodes;
import main.java.com.check.rest.error.GcmSendException;
import main.java.com.check.rest.error.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.java.com.check.gcm.Constants.*;

/**
 * Created by Eugene on 19.03.14.
 */
@Service
@Transactional
public class GcmServiceImpl implements GcmService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private GcmDao gcmDao;

    /**
     * Sends a message without retrying in case of service unavailability. See
     *
     * @return multicast results if the message was sent successfully,
     *         {@literal null} if it failed but could be retried.
     * @throws IllegalArgumentException if registrationIds is {@literal null} or
     *                                  empty.
     * @throws InvalidRequestException  if GCM didn't returned a 200 status.
     * @throws IOException              if there was a JSON parsing error
     */
    private MulticastResult sendNoRetry(Message message,
                                       List<String> registrationIds) throws ValidationException, GcmSendException {
        if (StringUtils.isEmpty(registrationIds)) {
            throw new ValidationException(ErrorCodes.REGISTRATION_ID_IS_EMPTY);
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
            ResponseEntity<MulticastResult> responseEntity = restTemplate.exchange(GCM_SEND_ENDPOINT, HttpMethod.POST, entity, MulticastResult.class, null);
            return responseEntity.getBody();
        } catch (RestClientException e) {
            throw new GcmSendException(e);
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

    @Override
    public void addRegistrationId(String uuid, GcmRegistrationDto registrationDto) throws ValidationException {
        if (!gcmDao.isRegistrationIdExists(registrationDto.getRegistrationId())) {
            gcmDao.addRegistrationId(uuid, registrationDto);
        } else {
            throw new ValidationException(ErrorCodes.REGISTRATION_ID_ALREADY_EXISTS);
        }
    }

    @Override
    public MulticastResult multicastSend() throws ValidationException, GcmSendException {
        List<String> registrationIds = gcmDao.getAllRegistrationIds();
        Message.Builder messageBuilder = new Message.Builder();
        messageBuilder.addData("test", "test");
        return sendNoRetry(messageBuilder.build(), registrationIds);
    }
}

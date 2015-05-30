package com.lashgo.service;

import com.lashgo.domain.Check;
import com.lashgo.error.ValidationException;
import com.lashgo.model.dto.GcmRegistrationDto;

/**
 * Created with IntelliJ IDEA.
 * User: Eugene
 * Date: 17.03.14
 * Time: 22:30
 * To change this template use File | Settings | File Templates.
 */
public interface GcmService {
    void addRegistrationId(String sessionId, GcmRegistrationDto registrationDto) throws ValidationException;

    void sendGcm(Check currentCheck, Check voteCheck, Check finishedCheck);

    void sendGcm(String registrationId);
}

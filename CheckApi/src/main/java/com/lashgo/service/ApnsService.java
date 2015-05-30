package com.lashgo.service;

import com.lashgo.domain.Check;
import com.lashgo.error.ValidationException;
import com.lashgo.model.dto.ApnTokenDto;

/**
 * Created by Eugene on 21.02.2015.
 */
public interface ApnsService {

    void addToken(String sessionId, ApnTokenDto apnTokenDto) throws ValidationException;

    void sendApn(Check currentCheck, Check voteCheck, Check finishedCheck);

    void sendApn(String token);

    void feedbackService();
}

package com.lashgo.service;

import com.lashgo.error.UnautharizedException;
import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.ErrorCodes;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Eugene on 21.04.2014.
 */
@Service
@Transactional(readOnly = true)
public class SessionValidator {

    public void validate(HttpHeaders httpHeaders) {
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        if (CollectionUtils.isEmpty(sessionId)) {
            throw new UnautharizedException(ErrorCodes.SESSION_IS_EMPTY);
        }
    }
}

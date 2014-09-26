package com.lashgo.service;

import com.lashgo.CheckConstants;
import com.lashgo.domain.Sessions;
import com.lashgo.error.UnautharizedException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.ErrorCodes;
import com.lashgo.repository.SessionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final Logger logger = LoggerFactory.getLogger("FILE");

    @Autowired
    private SessionDao sessionDao;

    public void validate(HttpHeaders httpHeaders) {
        validate(httpHeaders, true);
    }

    private void validate(HttpHeaders httpHeaders, boolean throwUnauth) {
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        if (CollectionUtils.isEmpty(sessionId)) {
            if (throwUnauth) {
                logger.error("Отправлена пустая сессия");
                throw new UnautharizedException(ErrorCodes.SESSION_IS_EMPTY);
            }
        } else {
            Sessions session = sessionDao.getSessionById(sessionId.get(0));
            if (session == null) {
                logger.error("Сессия {} не существует",session);
                throw new ValidationException(ErrorCodes.WRONG_SESSION);
            }
//            long currentTimestamp = System.currentTimeMillis();
//            if (currentTimestamp - session.getStartTime().getTime() > CheckConstants.SESSION_EXPIRE_PERIOD_MILLIS) {
//                throw new ValidationException(ErrorCodes.SESSION_EXPIRED);
//            }
        }
    }

    public void validateWithoutUnauthEx(HttpHeaders httpHeaders) {
        validate(httpHeaders, false);
    }
}

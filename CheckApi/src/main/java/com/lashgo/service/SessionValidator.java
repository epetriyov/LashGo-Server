package main.java.com.lashgo.service;

import com.lashgo.model.CheckApiHeaders;
import main.java.com.lashgo.CheckConstants;
import main.java.com.lashgo.domain.Sessions;
import main.java.com.lashgo.repository.SessionDao;
import main.java.com.lashgo.error.ErrorCodes;
import main.java.com.lashgo.error.UnautharizedException;
import main.java.com.lashgo.error.ValidationException;
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
        List<String> sessionId = httpHeaders.get(CheckApiHeaders.SESSION_ID);
        if (CollectionUtils.isEmpty(sessionId)) {
            throw new UnautharizedException(ErrorCodes.SESSION_IS_EMPTY);
        }
        Sessions session = sessionDao.getSessionById(sessionId.get(0));
        if (session == null) {
            throw new ValidationException(ErrorCodes.WRONG_SESSION);
        }
        long currentTimestamp = System.currentTimeMillis();
        logger.info("Current timestamp - " + currentTimestamp);
        logger.info("Session start date timestamp - " + session.getStartTime().getTime());
        if (currentTimestamp - session.getStartTime().getTime() > CheckConstants.SESSION_EXPIRE_PERIOD_MILLIS) {
            throw new ValidationException(ErrorCodes.SESSION_EXPIRED);
        }
    }
}

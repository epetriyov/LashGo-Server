package com.lashgo.controller;

import com.lashgo.domain.Sessions;
import com.lashgo.error.UnautharizedException;
import com.lashgo.error.ValidationException;
import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.ClientTypes;
import com.lashgo.model.ErrorCodes;
import com.lashgo.model.Path;
import com.lashgo.repository.SessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Eugene on 13.02.14.
 */
@Component
public class CheckInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionDao sessionDao;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        validateCommonHeaders(httpServletRequest);
        return true;
    }

    private void validateCommonHeaders(HttpServletRequest httpServletRequest) {
        if (!httpServletRequest.getPathInfo().equals(Path.JSONDOC)) {
            String uuid = httpServletRequest.getHeader(CheckApiHeaders.UUID);
            if (StringUtils.isEmpty(uuid)) {
                throw new ValidationException(ErrorCodes.UUID_IS_EMPTY);
            }
            String clientType = httpServletRequest.getHeader(CheckApiHeaders.CLIENT_TYPE);
            if (!ClientTypes.isClientTypeValid(clientType)) {
                throw new ValidationException(ErrorCodes.INVALID_CLIENT_TYPE);
            }
            String sessionId = httpServletRequest.getHeader(CheckApiHeaders.SESSION_ID);
            if (!StringUtils.isEmpty(sessionId)) {
                Sessions session = sessionDao.getSessionById(sessionId);
                if (session == null) {
                    throw new UnautharizedException(ErrorCodes.WRONG_SESSION);
                }
//            long currentTimestamp = System.currentTimeMillis();
//            if (currentTimestamp - session.getStartTime().getTime() > CheckConstants.SESSION_EXPIRE_PERIOD_MILLIS) {
//                throw new ValidationException(ErrorCodes.SESSION_EXPIRED);
//            }
            }
        }
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        addAuthenticateHeader(httpServletResponse);
    }

    private void addAuthenticateHeader(HttpServletResponse httpServletResponse) {
        if (httpServletResponse.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
            httpServletResponse.setHeader("WWW-Authenticate", "None");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

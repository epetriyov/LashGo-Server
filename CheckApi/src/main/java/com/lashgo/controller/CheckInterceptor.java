package com.lashgo.controller;

import com.lashgo.model.ErrorCodes;
import com.lashgo.error.ValidationException;
import com.lashgo.model.CheckApiHeaders;
import com.lashgo.model.ClientTypes;
import com.lashgo.model.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Eugene on 13.02.14.
 */
@Component
public class CheckInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger("FILE");

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("Request uri: " + httpServletRequest.getRequestURI());
        if (!httpServletRequest.getPathInfo().equals(Path.JSONDOC)) {
            String uuid = httpServletRequest.getHeader(CheckApiHeaders.UUID);
            if (StringUtils.isEmpty(uuid)) {
                throw new ValidationException(ErrorCodes.UUID_IS_EMPTY);
            }
            logger.info("UUID: " + uuid);
            String clientType = httpServletRequest.getHeader(CheckApiHeaders.CLIENT_TYPE);
            if (!ClientTypes.isClientTypeValid(clientType)) {
                throw new ValidationException(ErrorCodes.INVALID_CLIENT_TYPE);
            }
            logger.info("Client type: " + clientType);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("Response status {}", httpServletResponse.getStatus());
        if (httpServletResponse.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
            httpServletResponse.setHeader("WWW-Authenticate", "None");
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}

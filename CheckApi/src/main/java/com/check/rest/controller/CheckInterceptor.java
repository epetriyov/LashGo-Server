package main.java.com.check.rest.controller;

import com.check.model.CheckApiHeaders;
import com.check.model.ClientTypes;
import main.java.com.check.rest.error.ErrorCodes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Eugene on 13.02.14.
 */

public class CheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (httpServletRequest.getHeader(CheckApiHeaders.UUID) == null) {
            throw new Exception(ErrorCodes.UUID_IS_EMPTY);
        }
        String clientType = httpServletRequest.getHeader(CheckApiHeaders.CLIENT_TYPE);
        if (clientType == null || (clientType != ClientTypes.ANDROID && clientType != ClientTypes.IOS)) {
            throw new Exception(ErrorCodes.INVALID_CLIENT_TYPE);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

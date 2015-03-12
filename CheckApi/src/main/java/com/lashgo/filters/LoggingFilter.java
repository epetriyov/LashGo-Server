package com.lashgo.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Eugene on 06.03.2015.
 */
public class LoggingFilter extends GenericFilterBean {

    private static Logger logger = LoggerFactory.getLogger("FILE");

    private void logRequest(LashgoRequestWrapper httpServletRequest) {
        StringBuilder requestInfoBuilder = new StringBuilder("REQUEST INFO: ");
        requestInfoBuilder.append(httpServletRequest.getMethod());
        requestInfoBuilder.append(" ");
        requestInfoBuilder.append(httpServletRequest.getRequestURI());
        requestInfoBuilder.append("; Headers: ");
        String headerName;
        while (httpServletRequest.getHeaderNames().hasMoreElements()) {
            headerName = httpServletRequest.getHeaderNames().nextElement();
            requestInfoBuilder.append(headerName);
            requestInfoBuilder.append(" = ");
            requestInfoBuilder.append(httpServletRequest.getHeader(headerName));
            requestInfoBuilder.append("; ");
        }
        requestInfoBuilder.append(" Body: ");
        requestInfoBuilder.append(httpServletRequest.getRequestBody());
        logger.debug(requestInfoBuilder.toString());
    }

    private void logResponse(LashGoResponseWrapper httpServletResponse) {
        StringBuilder requestInfoBuilder = new StringBuilder("RESPONSE INFO: ");
        requestInfoBuilder.append(httpServletResponse.getStatus());
        requestInfoBuilder.append("; Headers: ");
        for (String headerName : httpServletResponse.getHeaderNames()) {
            requestInfoBuilder.append(headerName);
            requestInfoBuilder.append(" = ");
            requestInfoBuilder.append(httpServletResponse.getHeader(headerName));
            requestInfoBuilder.append("; ");
        }
        requestInfoBuilder.append(" Body: ");
        requestInfoBuilder.append(httpServletResponse.getContent());
        logger.debug(requestInfoBuilder.toString());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LashgoRequestWrapper requestWrapper = new LashgoRequestWrapper((HttpServletRequest) request);
        LashGoResponseWrapper responseWrapper = new LashGoResponseWrapper((HttpServletResponse) response);
        if(!requestWrapper.getRequestURI().equals("/")) {
            logRequest(requestWrapper);
            logResponse(responseWrapper);
        }
        chain.doFilter(requestWrapper, responseWrapper);
    }
}